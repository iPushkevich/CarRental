package by.epamtc.pushkevich.controller.command.car;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AddCarCommand implements Command {
    private final CarServiceFactory carServiceFactory = CarServiceFactory.getInstance();
    private final CarService carService = carServiceFactory.getCarService();

    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String YEAR = "year";
    private static final String MILEAGE = "mileage";
    private static final String ENGINE_TYPE = "engineType";
    private static final String ENGINE_SIZE = "engineSize";
    private static final String TRANSMISSION = "transmission";
    private static final String WHEELS_DRIVE = "wheelsDrive";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String COST = "cost";
    private static final String RATING = "rating";
    private static final String FUEL_LVL = "fuelLvl";
    private static final String CAR_ID = "carID";
    private static final String REVIEWS = "reviews";
    private static final String CAR = "car";
    private static final String CAR_RENT_INFO = "carRentInfo";
    private static final String CAR_INFO_CHANGED = "carInfoChanged";
    private static final String CAR_INFO_CHANGED_MSG = "Information successfully saved";
    private static final String CAR_ADDED = "carAdded";
    private static final String CAR_ADDED_MSG = "Car successfully added";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction;

        HttpSession session = request.getSession(false);

        String brand = request.getParameter(BRAND);
        String model = request.getParameter(MODEL);
        String year = request.getParameter(YEAR);
        String mileage = request.getParameter(MILEAGE);
        String engineType = request.getParameter(ENGINE_TYPE);
        String engineSize = request.getParameter(ENGINE_SIZE);
        String transmission = request.getParameter(TRANSMISSION);
        String wheelsDrive = request.getParameter(WHEELS_DRIVE);
        String type = request.getParameter(TYPE);

        String description = request.getParameter(DESCRIPTION);
        String cost = request.getParameter(COST);
        String rating = request.getParameter(RATING);
        String fuelLvl = request.getParameter(FUEL_LVL);

        Car car = new Car();
        CarRentInfo carRentInfo = new CarRentInfo();
        int carID = 0;

        carRentInfo.setDescription(description);
        carRentInfo.setRentCost(Short.parseShort(cost));
        carRentInfo.setCarRating(Double.parseDouble(rating));
        carRentInfo.setFuelLvl(Double.parseDouble(fuelLvl));

        List<String> reviews = new LinkedList<>();

        try {
            if (session.getAttribute(CAR_ID) != null) {
                carID = (int) session.getAttribute(CAR_ID);
                reviews = carService.getCarReviewsByCarID(carID);
                car.setId(carID);
            }

            car.setBrand(brand);
            car.setModel(model);
            car.setYear(Short.parseShort(year));
            car.setMileage(Integer.parseInt((mileage)));
            car.setEngineType(engineType);
            car.setEngineSize(Double.parseDouble(engineSize));
            car.setTransmissionType(transmission);
            car.setWheelsDrive(wheelsDrive);
            car.setType(type);

            if (carID == 0) {
                carService.addNewCar(car);
                carService.addCarRentInfoByCarID(carRentInfo, car.getId());
            } else {
                carService.updateCar(car);
                carService.updateCarRentInfoByCarID(carRentInfo, car.getId());
            }


            if (session.getAttribute(CAR_ID) != null) {
                for (String review : reviews) {
                    carService.addCarReviewByCarID(review, carID);
                }

                session.setAttribute(REVIEWS, reviews);
                session.setAttribute(CAR, car);
                session.setAttribute(CAR_RENT_INFO, carRentInfo);
                session.setAttribute(CAR_INFO_CHANGED, CAR_INFO_CHANGED_MSG);

                direction = CommandParameterName.GO_TO_CAR_INFO_PAGE.getDirection();
            } else {
                session.setAttribute(CAR_ADDED, CAR_ADDED_MSG);

                direction = CommandParameterName.SHOW_CARS.getDirection();
            }

            response.sendRedirect(direction);
        }
        catch (ServiceException e){
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
        }
    }
}
