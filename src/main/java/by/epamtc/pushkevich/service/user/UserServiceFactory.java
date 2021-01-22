package by.epamtc.pushkevich.service.user;

public final class UserServiceFactory {
    private static final UserServiceFactory INSTANCE = new UserServiceFactory();

    private final UserService USER_SERVICE = new UserServiceImpl();

    private UserServiceFactory() {}

    public UserService geUserService() {
        return USER_SERVICE;
    }

    public static UserServiceFactory getInstance() {
        return INSTANCE;
    }
}
