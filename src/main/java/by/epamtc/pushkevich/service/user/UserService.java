package by.epamtc.pushkevich.service.user;

import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<Integer, User> getAllUsers() throws ServiceException;

    void addNewUser(User user) throws ServiceException;

    String getUserHashPassword(String email) throws ServiceException;

    String hashPassword(String password);

    boolean isPasswordConfirmed(String password, String hashPassword);

    boolean isUserExists(String email) throws ServiceException;

    User getUser(String email, String password) throws ServiceException;

    int getUserId(String email, String password) throws ServiceException;

    User getUserById(int id) throws ServiceException;

    User changeUserInfo(User user) throws ServiceException;

    void setUserRoleByUserId(int userID, String role) throws ServiceException;

    String getUserRoleByUserId(int id) throws ServiceException;

    Date getUserRegistrationDateByEmail(String userEmail) throws ServiceException;

    boolean userHasActiveRent(int userID) throws ServiceException;

    void changeUserHasActiveRent(int userID) throws ServiceException;

    List<Integer> getUsersIDByOrderID(int orderID) throws ServiceException;
}
