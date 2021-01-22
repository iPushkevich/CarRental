package by.epamtc.pushkevich.repository.user;

public final class UserRepositoryFactory {
    private static final UserRepositoryFactory INSTANCE = new UserRepositoryFactory();

    private final UserRepository USER_REPOSITORY = new UserSQLRepository();

    private UserRepositoryFactory() {}

    public UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static UserRepositoryFactory getInstance() {
        return INSTANCE;
    }
}
