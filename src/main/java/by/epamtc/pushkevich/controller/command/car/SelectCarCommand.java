package by.epamtc.pushkevich.controller.command.car;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class SelectCarCommand implements Command {
    private final CarServiceFactory carServiceFactory = CarServiceFactory.getInstance();
    private final CarService carService = carServiceFactory.getCarService();

    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String IS_AVAILABLE = "isAvailable";
    private static final String CARS_BY_BRAND = "carsByBrand";
    private static final String MODELS = "models";
    private static final String BRANDS = "brands";
    private static final String AVAILABLE_CARS = "availableCars";
    private static final String CARS_BY_MODEL = "carsByModel";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_SELECT_CAR_PAGE.getDirection();

        String brand = request.getParameter(BRAND);
        String model = request.getParameter(MODEL);
        String isAvailableCars = request.getParameter(IS_AVAILABLE);

        Map<Integer, Car> carsByBrand;
        Set<String> models;
        Set<String> carBrands;

        try {
            carBrands = carService.getAllBrands();

            if (brand != null) {
                carsByBrand = carService.getCarsByBrand(brand);
                models = carService.getAllModelsByBrand(brand);

                request.setAttribute(CARS_BY_BRAND, carsByBrand);
                request.setAttribute(MODELS, models);
            }

            if (model != null) {
                Map<Integer, Car> carsByModel = carService.getCarsByModel(model);

                if (isAvailableCars != null) {
                    carsByModel = carService.getAvailableCars(carsByModel);

                    request.setAttribute(AVAILABLE_CARS, AVAILABLE_CARS);
                }

                request.setAttribute(CARS_BY_MODEL, carsByModel);
                request.setAttribute(MODEL, model);
            }

            request.setAttribute(BRAND, brand);
            request.setAttribute(BRANDS, carBrands);

            RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
            dispatcher.forward(request, response);
        }
        catch (ServiceException e){
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
        }
    }
}
