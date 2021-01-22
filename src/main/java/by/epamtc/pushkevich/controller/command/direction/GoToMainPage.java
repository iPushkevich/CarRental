package by.epamtc.pushkevich.controller.command.direction;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class GoToMainPage implements Command {
    private final CarServiceFactory factory = CarServiceFactory.getInstance();
    private final CarService service = factory.getCarService();

    private static final String BRANDS = "brands";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            Set<String> carBrands = service.getAllBrands();

            request.setAttribute(BRANDS, carBrands);

            RequestDispatcher dispatcher = request.getRequestDispatcher(CommandParameterName.GO_TO_MAIN_PAGE.getDirection());
            dispatcher.forward(request, response);
        }
        catch (ServiceException e){
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
