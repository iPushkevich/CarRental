package by.epamtc.pushkevich.controller.command.car;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;
import by.epamtc.pushkevich.service.order.OrderService;
import by.epamtc.pushkevich.service.order.OrderServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CarInfoCommand implements Command {
    private final CarServiceFactory carServiceFactory = CarServiceFactory.getInstance();
    private final CarService carService = carServiceFactory.getCarService();

    private final OrderServiceFactory orderServiceFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderServiceFactory.getOrderService();

    private static final String CAR_ID = "carID";
    private static final String ADD_REVIEW = "addReview";
    private static final String DELETE_REVIEW = "deleteReview";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
    private static final String RENT_END_DATE = "rentEndDate";
    private static final String RENT_END_DATE_PAGE_FORMAT = "rentEndDatePageFormat";
    private static final String CAR = "car";
    private static final String CAR_RENT_INFO = "carRentInfo";
    private static final String IS_AVAILABLE = "isAvailable";
    private static final String REVIEWS = "reviews";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.CAR_INFO.getDirection();

        HttpSession session = request.getSession(false);

        int carID = Integer.parseInt(request.getParameter(CAR_ID));
        String review = request.getParameter(ADD_REVIEW);
        String deleteReview = request.getParameter(DELETE_REVIEW);

        try {
            Car car = carService.getCarByID(carID);
            CarRentInfo carRentInfo = carService.getCarRentInfoByCarID(carID);
            boolean isAvailable = carService.isCarAvailable(carID);
            List<String> reviews = carService.getCarReviewsByCarID(carID);

            if (deleteReview != null) {
                carService.deleteCarReviewByCarID(deleteReview, carID);

                reviews.removeIf(r -> r.equals(deleteReview));
            }

            if (review != null) {
                carService.addCarReviewByCarID(review, carID);
                reviews.add(review);
            }


            if (!isAvailable) {
                Date rentEnd = orderService.getCarRentEndDate(carID);

                if (rentEnd == null) {
                    carService.changeCarAvailability(carID);
                } else {
                    SimpleDateFormat htmlDateFormat = new SimpleDateFormat(DATE_FORMAT);
                    SimpleDateFormat localDateFormat = new SimpleDateFormat();

                    String rentEndDate = localDateFormat.format(rentEnd);
                    String rentEndDatePageFormat = htmlDateFormat.format(rentEnd);

                    session.setAttribute(RENT_END_DATE, rentEndDate);
                    session.setAttribute(RENT_END_DATE_PAGE_FORMAT, rentEndDatePageFormat);
                }
            }

            session.setAttribute(CAR, car);
            session.setAttribute(CAR_RENT_INFO, carRentInfo);
            session.setAttribute(IS_AVAILABLE, isAvailable);
            session.setAttribute(REVIEWS, reviews);
            session.setAttribute(CAR_ID, carID);

            response.sendRedirect(direction);
        }
        catch (ServiceException e){
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
        }

    }
}
