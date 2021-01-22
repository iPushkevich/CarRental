package by.epamtc.pushkevich.controller.command.car;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;
import jdk.jshell.spi.ExecutionControl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AllCarsCommand implements Command {
    private final CarServiceFactory carServiceFactory = CarServiceFactory.getInstance();
    private final CarService carService = carServiceFactory.getCarService();

    private static final String CARS = "cars";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_ADMIN_PANEL.getDirection();

        try {
            Map<Integer, Car> cars = carService.getAllCars();

            request.setAttribute(CARS, cars);

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
