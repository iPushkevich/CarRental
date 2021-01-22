package by.epamtc.pushkevich.service.user;

import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.IncorrectLoginOrPasswordException;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.exception.UserAlreadyExistsException;
import by.epamtc.pushkevich.repository.user.UserRepository;
import by.epamtc.pushkevich.repository.user.UserRepositoryFactory;
import by.epamtc.pushkevich.service.validator.EmailValidator;
import by.epamtc.pushkevich.service.validator.PasswordValidator;
import by.epamtc.pushkevich.service.validator.Validator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserRepositoryFactory factory = UserRepositoryFactory.getInstance();
    private final UserRepository repository = factory.getUserRepository();

    private static final String USER_ALREADY_EXISTS = "This email is already registered";
    private static final String INCORRECT_LOGIN_OR_PASS = "Incorrect login or password";

    @Override
    public Map<Integer, User> getAllUsers() throws ServiceException {
        Map<Integer, User> allUsers;

        try {
            allUsers =  repository.getAllUsers();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return allUsers;
    }

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
                throw new ServiceException(e);
            }
        }
        else {
            IncorrectLoginOrPasswordException exception = new IncorrectLoginOrPasswordException(INCORRECT_LOGIN_OR_PASS);
            throw new ServiceException(exception.getMessage());
        }
    }

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

    @Override
    public String hashPassword(String password) {
        String hasPassword;
        String salt = BCrypt.gensalt();
        hasPassword = BCrypt.hashpw(password, salt);

        return hasPassword;
    }

    @Override
    public boolean isPasswordConfirmed(String password, String hasPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, hasPassword);
    }

    @Override
    public boolean isUserExists(String email) {
        boolean isExists = false;

        try {
            isExists = repository.isUserExists(email);
        }
        catch (RepositoryException e) {
            e.printStackTrace();
        }

        return isExists;
    }

    @Override
    public User getUser(String email, String password) throws ServiceException {
        try {
            String hashPassword = getUserHashPassword(email);

            if (!hashPassword.equals(password)) {
                boolean isEqual = isPasswordConfirmed(password, hashPassword);

                if (!isEqual) {
                    password = "-1";
                }
                else {
                    password = hashPassword;
                }
            }

            return repository.getUser(email, password);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getUserId(String email, String password) throws ServiceException {
        int userId = 0;
        String hashPassword = getUserHashPassword(email);

        if (!hashPassword.equals(password)) {
            boolean isEqual = isPasswordConfirmed(password, hashPassword);

            if (!isEqual) {
                password = "-1";
            }
            else {
                password = hashPassword;
            }
        }

        try {
            userId = repository.getUserId(email, password);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return userId;
    }

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

    @Override
    public User changeUserInfo(User user) throws ServiceException {
        User newUser;

        try {
            newUser = repository.changeUserInfo(user);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return newUser;
    }

    public void setUserRoleByUserId(int userID, String role) throws ServiceException {
        try {
            repository.setUserRoleByUserId(userID, role);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getUserRoleByUserId(int id) throws ServiceException {
        String role;

        try {
            role = repository.getUserRoleByUserId(id);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return role;
    }

    @Override
    public Date getUserRegistrationDateByEmail(String userEmail) throws ServiceException {
        Date date;

        try {
            date = repository.getUserRegistrationDateByEmail(userEmail);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return date;
    }

    @Override
    public boolean userHasActiveRent(int userID) throws ServiceException {
        boolean userHas;

        try {
            userHas = repository.userHasActiveRent(userID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return userHas;
    }

    @Override
    public void changeUserHasActiveRent(int userID) throws ServiceException {
        try {
            repository.changeUserHasActiveRent(userID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> getUsersIDByOrderID(int orderID) throws ServiceException {
        List<Integer> usersID;

        try {
            usersID = repository.getUsersIDByOrderID(orderID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return usersID;
    }

}
