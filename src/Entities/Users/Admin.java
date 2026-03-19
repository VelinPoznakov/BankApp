package Entities.Users;

import Entities.Users.Enums.UserRoles;

public class Admin extends User {

    public Admin(int id, String username, String password, String birthDate, double monthlyIncome) {
        super(id, username, password, birthDate, monthlyIncome, UserRoles.ADMIN, "adult");
    }

    @Override
    public String GetUserDetails() {
        return "Admin " + username + "\n"
                + "Birth Date : " + birthDate + "\n"
                + "Monthly Income " + getMonthlyIncome() + "\n"
                + "Created At : " + createdAt + "\n";
    }
}


