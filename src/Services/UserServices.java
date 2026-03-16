package Services;
import Exceptions.UserNotFoundException;
import Entities.Users.Customer;
import Entities.Users.User;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ProgramInterface.Interfaces.IUserInterface;
import Services.Interfaces.IUserService;
import Validations.Validations;

public class UserServices implements IUserService {

    public static String FILENAME = "users.bin";
    public static Path file = Path.of("files", FILENAME);
    public static List<User> users;

    public List<User> LoadUsers(){

        if(!Validations.validateFile(FILENAME)){
            try{
                Files.createFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (Files.size(file) == 0) {
                return users =  new ArrayList<>();
            }

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file.toFile()));

            users = (List<User>) in.readObject();
            in.close();

            return users;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void CreateUserInFile(){

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))){
            out.writeObject(users);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        users = LoadUsers();
    }

    public List<User> GetCustomerOnly(){
        List<User> customers = new ArrayList<>();

        for(User user: users){
            if(user instanceof Customer){
                users.add(user);
            }
        }
        return customers;
    }

    public User GetUser(int userId){
        for(User user: users){
            if(user.getId() == userId){
                return user;
            }
        }
        throw new UserNotFoundException("User not found");
    }
}
