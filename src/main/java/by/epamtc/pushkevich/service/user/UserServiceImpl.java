package by.epamtc.pushkevich.service.user;

import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.IncorrectLoginOrPasswordException;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.exception.UserAlreadyExistsException;
import by.epamtc.pushkevich.repository.car.CarSQLRepository;
import by.epamtc.pushkevich.repository.user.UserRepository;
import by.epamtc.pushkevich.repository.user.UserRepositoryFactory;
import by.epamtc.pushkevich.repository.user.UserSQLRepository;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.validator.EmailValidator;
import by.epamtc.pushkevich.service.validator.PasswordValidator;
import by.epamtc.pushkevich.service.validator.Validator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is implementation of{@link UserService} service layer for various operations with <code>User</code>
 * Every method can throw <code>RepositoryException</code> with <strong>Database error</strong> message. If you get an exception with this message
 * in your catch block you should throw new <code>RuntimeException</code>
 * @author Igor Pushkevich
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private final UserRepositoryFactory factory = UserRepositoryFactory.getInstance();
    private final UserRepository repository = factory.getUserRepository();

    private static final String USER_ALREADY_EXISTS = "This email is already registered";
    private static final String INCORRECT_LOGIN_OR_PASS = "Incorrect login or password";

    /**
     * @see UserSQLRepository#getAllUsers
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Map<Integer, User> getAllUsers() throws ServiceException {
        Map<Integer, User> allUsers;

        try {
            allUsers =  repository.getAllUsers();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return allUsers;
    }

    /**
     * Before add <code>User</code> to database method try find this user in database. If user already exists throws <code>UserNotExistException</code>
     * with specific message for user.
     * Than validate user email and password. If it incorrect then throw <code>IncorrectLoginOrPasswordException</code>
     * @see UserSQLRepository#addNewUser
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void addNewUser(User user) throws ServiceException {
        if (isUserExists(user.getEmail())) {
            UserAlreadyExistsException existsException = new UserAlreadyExistsException(USER_ALREADY_EXISTS);
            throw new ServiceException(existsException.getMessage());
        }

        Validator emailValidator = new EmailValidator();
        Validator passwordValidator = new PasswordValidator();

        String email = user.getEmail();
        String password = user.getPassword();

        if (emailValidator.check(email) && passwordValidator.check(password)) {
            password = hashPassword(password);
            user.setPassword(password);

            try {
                repository.addNewUser(user);
            }
            catch (RepositoryException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        else {
            IncorrectLoginOrPasswordException exception = new IncorrectLoginOrPasswordException(INCORRECT_LOGIN_OR_PASS);
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * @see UserSQLRepository#getUserHashPassword
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public String getUserHashPassword(String email) throws ServiceException {
        String hashedPassword;

        try {
            hashedPassword = repository.getUserHashPassword(email);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return hashedPassword;
    }

    /**
     * Convert user password to hashed view
     * @param password user password
     * @return string of hashed password
     */
    @Override
    public String hashPassword(String password) {
        String hasPassword;
        String salt = BCrypt.gensalt();
        hasPassword = BCrypt.hashpw(password, salt);

        return hasPassword;
    }

    /**
     * Checks if inputted password is equal with hashed password in database
     * @param password entered password
     * @param hasPassword hashed password
     * @return <strong>true</strong> if passwords are equal, <strong>false</strong> otherwise
     */
    @Override
    public boolean isPasswordConfirmed(String password, String hasPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, hasPassword);
    }

    /**
     * @see UserSQLRepository#isUserExists
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public boolean isUserExists(String email) throws ServiceException {
        boolean isExists;

        try {
            isExists = repository.isUserExists(email);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return isExists;
    }

    /**
     * First of all, method calls {@link UserServiceImpl#getUserHashPassword}, than with {@link UserServiceImpl#isPasswordConfirmed}
     * compares passwords
     * @see UserSQLRepository#getUser
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public User getUser(String email, String password) throws ServiceException {
        try {
            String hashPassword = getUserHashPassword(email);

            if (isPasswordConfirmed(password, hashPassword)) {
                password = hashPassword;
            }

            return repository.getUser(email, password);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * First of all, method calls {@link UserServiceImpl#getUserHashPassword}, than with {@link UserServiceImpl#isPasswordConfirmed}
     * compares passwords
     * @see UserSQLRepository#getUserId
     * @throws ServiceException wrapper for RepositoryException
     * @return 0 if passwords arn`t equal
     */
    @Override
    public int getUserId(String email, String password) throws ServiceException {
        int userId = 0;
        try {
            String hashPassword = getUserHashPassword(email);

            if (isPasswordConfirmed(password, hashPassword)) {
                password = hashPassword;
                userId = repository.getUserId(email, password);
            }

        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return userId;
    }

    /**
     * @see UserSQLRepository#getUserById
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public User getUserById(int id) throws ServiceException {
        User user;

        try {
            user = repository.getUserById(id);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * @see UserSQLRepository#changeUserInfo
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public User changeUserInfo(User user) throws ServiceException {
        User newUser;

        try {
            newUser = repository.changeUserInfo(user);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return newUser;
    }

    /**
     * @see UserSQLRepository#setUserRoleByUserId
     * @throws ServiceException wrapper for RepositoryException
     */
    public void setUserRoleByUserId(int userID, String role) throws ServiceException {
        try {
            repository.setUserRoleByUserId(userID, role);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see UserSQLRepository#getUserRoleByUserId
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public String getUserRoleByUserId(int id) throws ServiceException {
        String role;

        try {
            role = repository.getUserRoleByUserId(id);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return role;
    }

    /**
     * @see UserSQLRepository#getUserRegistrationDateByEmail
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Date getUserRegistrationDateByEmail(String userEmail) throws ServiceException {
        Date date;

        try {
            date = repository.getUserRegistrationDateByEmail(userEmail);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return date;
    }

    /**
     * @see UserSQLRepository#userHasActiveRent
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public boolean userHasActiveRent(int userID) throws ServiceException {
        boolean userHas;

        try {
            userHas = repository.userHasActiveRent(userID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return userHas;
    }

    /**
     * @see UserSQLRepository#changeUserHasActiveRent
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void changeUserHasActiveRent(int userID) throws ServiceException {
        try {
            repository.changeUserHasActiveRent(userID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see UserSQLRepository#getUsersIDByOrderID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public List<Integer> getUsersIDByOrderID(int orderID) throws ServiceException {
        List<Integer> usersID;

        try {
            usersID = repository.getUsersIDByOrderID(orderID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }

        return usersID;
    }
}
