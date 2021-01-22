package by.epamtc.pushkevich.repository.user;

import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.RepositoryException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserRepository {
    Map<Integer, User> getAllUsers() throws RepositoryException;

    void addNewUser(User user) throws RepositoryException;

    boolean isUserExists(String email) throws RepositoryException;

    String getUserHashPassword(String email) throws RepositoryException;

    User getUser(String email, String password) throws RepositoryException;

    int getUserId(String email, String password) throws RepositoryException;

    User getUserById(int id) throws RepositoryException;

    User changeUserInfo(User user) throws RepositoryException;

    void setUserRoleByUserId(int userID, String role) throws RepositoryException;

    String getUserRoleByUserId(int id) throws RepositoryException;

    Date getUserRegistrationDateByEmail(String email) throws RepositoryException;

    boolean userHasActiveRent(int userID) throws RepositoryException;

    void changeUserHasActiveRent(int userID) throws RepositoryException;

    List<Integer> getUsersIDByOrderID(int orderID) throws RepositoryException;

    int getUserInfoID(int userID) throws RepositoryException;
}
