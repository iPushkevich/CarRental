package by.epamtc.pushkevich.controller.command;

public enum CommandParameterName {
    GO_TO_MAIN_PAGE("WEB-INF/view/home.jsp"),
    GO_TO_REGISTRATION_PAGE("WEB-INF/view/registration.jsp"),
    ADD_NEW_USER("controller?command=go_to_user_info_page"),
    GO_TO_LOGIN_PAGE("WEB-INF/view/login.jsp"),
    LOGIN("controller?command=go_to_main_page"),
    LOGOUT("controller?command=go_to_main_page"),
    GO_TO_USER_INFO_PAGE("WEB-INF/view/userInfo.jsp"),
    ADD_USER_INFO("controller?command=go_to_user_info_page"),
    GO_TO_USER_ACCOUNT_PAGE("WEB-INF/view/account.jsp"),
    SHOW_CARS("controller?command=show_cars"),
    GO_TO_CARS_PAGE("WEB-INF/view/cars.jsp"),
    ADD_NEW_CAR(""),
    GO_TO_NEW_CAR_PAGE("WEB-INF/view/addcar.jsp"),
    SELECT_CAR("controller?command=go_to_select_car_page"),
    GO_TO_SELECT_CAR_PAGE("WEB-INF/view/selectCar.jsp"),
    GO_TO_ADMIN_PANEL("WEB-INF/view/adminPanel.jsp"),
    CAR_INFO("controller?command=go_to_car_info_page"),
    GO_TO_CAR_INFO_PAGE("WEB-INF/view/carInfo.jsp"),
    GO_TO_ORDER_PAGE("WEB-INF/view/order.jsp"),
    ADD_ORDER("controller?command=go_to_confirm_order_page"),
    GO_TO_CONFIRM_ORDER_PAGE("WEB-INF/view/confirmOrder.jsp"),
    USER_ORDERS(""),
    GO_TO_ALL_ORDERS_PAGE("WEB-INF/view/allOrders.jsp"),
    ALL_ORDERS(""),
    GO_TO_ALL_USERS_PAGE("WEB-INF/view/allUsers.jsp"),
    ALL_USERS("controller?command=all_users"),
    GO_TO_ORDER_INFO_PAGE("WEB-INF/view/orderInfo.jsp"),
    ORDER_INFO("controller?command=go_to_order_info_page"),
    CHANGE_LOCALE("controller?command=go_to_main_page"),
    ;


    private final String direction;

    CommandParameterName(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}

