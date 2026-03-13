import Loans.AbstractLoan;

import java.util.*;

public class Bank {

    public String name;
    private List<AbstractLoan> availableLoans;
    private List<User> users;
    private List<Worker> workers;

    public Bank(String name) {
        this.name = name;
        availableLoans = new ArrayList<>();
        users = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public String createUser(String name, int phoneNumber, int age, int myPin) {

        for (User user : users) {
            if (user.getName().equals(name)) {
                return "User " + name + " already exists";
            }
        }

        User newUser = new User(name, phoneNumber, age, myPin);
        users.add(newUser);

        for (Worker worker : workers) {
            if (worker.name.equals(newUser.getName())) {
                newUser.isWorker = true;
            }
        }

        return "New user " + name + " was created";
    }

    public String getUserDetails(String name) {

        for (User user : users) {
            if (user.getName().equals(name)) {
                return user.userDetails();
            }
        }

        return "User not found";
    }

    public String getWorkerDetails(String name) {

        for (Worker worker : workers) {
            if (worker.name.equals(name)) {
                return worker.workerDetails();
            }
        }

        return "Worker not found";
    }

}