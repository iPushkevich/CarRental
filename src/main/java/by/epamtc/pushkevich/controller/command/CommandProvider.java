package by.epamtc.pushkevich.controller.command;

import by.epamtc.pushkevich.controller.command.car.*;
import by.epamtc.pushkevich.controller.command.direction.*;
import by.epamtc.pushkevich.controller.command.localization.ChangeLocaleCommand;
import by.epamtc.pushkevich.controller.command.order.AddOrderCommand;
import by.epamtc.pushkevich.controller.command.order.AllOrdersCommand;
import by.epamtc.pushkevich.controller.command.order.OrderInfoCommand;
import by.epamtc.pushkevich.controller.command.order.UserOrdersCommand;
import by.epamtc.pushkevich.controller.command.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandParameterName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commands.put(CommandParameterName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commands.put(CommandParameterName.ADD_NEW_USER, new RegistrationCommand());
        commands.put(CommandParameterName.GO_TO_LOGIN_PAGE, new GoToLoginPage());
        commands.put(CommandParameterName.LOGIN, new LoginCommand());
        commands.put(CommandParameterName.LOGOUT, new LogoutCommand());
        commands.put(CommandParameterName.GO_TO_USER_INFO_PAGE, new GoToUserInfoPage());
        commands.put(CommandParameterName.ADD_USER_INFO, new UserInfoCommand());
        commands.put(CommandParameterName.GO_TO_USER_ACCOUNT_PAGE, new GoToUserAccountPage());
        commands.put(CommandParameterName.SHOW_CARS, new AllCarsCommand());
        commands.put(CommandParameterName.GO_TO_CARS_PAGE, new GoToCarsPage());
        commands.put(CommandParameterName.ADD_NEW_CAR, new AddCarCommand());
        commands.put(CommandParameterName.GO_TO_NEW_CAR_PAGE, new GoToNewCarPage());
        commands.put(CommandParameterName.SELECT_CAR, new SelectCarCommand());
        commands.put(CommandParameterName.GO_TO_SELECT_CAR_PAGE, new GoToSelectCarPage());
        commands.put(CommandParameterName.GO_TO_ADMIN_PANEL, new GoToAdminPanel());
        commands.put(CommandParameterName.CAR_INFO, new CarInfoCommand());
        commands.put(CommandParameterName.GO_TO_CAR_INFO_PAGE, new GoToCarInfoPage());
        commands.put(CommandParameterName.GO_TO_ORDER_PAGE, new GoToOrderPage());
        commands.put(CommandParameterName.ADD_ORDER, new AddOrderCommand());
        commands.put(CommandParameterName.GO_TO_CONFIRM_ORDER_PAGE, new GoToConfirmOrderPage());
        commands.put(CommandParameterName.USER_ORDERS, new UserOrdersCommand());
        commands.put(CommandParameterName.GO_TO_ALL_ORDERS_PAGE, new GoToAllOrdersPage());
        commands.put(CommandParameterName.ALL_ORDERS, new AllOrdersCommand());
        commands.put(CommandParameterName.GO_TO_ALL_USERS_PAGE, new GoToAllUsersPage());
        commands.put(CommandParameterName.ALL_USERS, new AllUsersCommand());
        commands.put(CommandParameterName.GO_TO_ORDER_INFO_PAGE, new GoToOrderInfoPage());
        commands.put(CommandParameterName.ORDER_INFO, new OrderInfoCommand());
        commands.put(CommandParameterName.CHANGE_LOCALE, new ChangeLocaleCommand());
    }

    public Command getCommand(String commandName) {
        Command command;
        CommandParameterName commandParameterName;

        commandName = commandName.toUpperCase();
        commandParameterName = CommandParameterName.valueOf(commandName);

        command = commands.get(commandParameterName);

        return command;
    }
}
