package Services.Interfaces;

import Entities.Users.User;

import java.util.List;

public interface IUserService {
    List<User> LoadUsers();
    void CreateUserInFile();
    List<User> GetCustomerOnly();
    User GetUser(int userId);
}
