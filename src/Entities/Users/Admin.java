package Entities.Users;

import Entities.Users.Enums.UserRoles;

public class Admin extends User {

    public UserRoles role = UserRoles.ADMIN;

    public Admin(int id, String username, String password, String birthDate) {
        super(id, username, password, birthDate);
    }

//    @Override
//    public String getUserDetails() {
//        return "Admin " + username + "\n" +
//                "Birth Date : " + birthDate + "\n" +
//                "Created At : " + createdAt + "\n";
//    }
}
