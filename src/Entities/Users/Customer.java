package Entities.Users;

import Entities.Enums.UserRoles;

public class Customer extends User {

    public Customer(int id,
                    String username,
                    String password,
                    String birthDate,
                    double monthlyIncome,
                    String customerType) {
        super(id, username, password, birthDate, monthlyIncome, UserRoles.CUSTOMER, customerType);
        setMonthlyIncome(monthlyIncome);
    }

    @Override
    public String GetUserDetails() {
        return getCustomerType().toString() + " " + getUsername() + "\n"
                + "Birth Date " + birthDate + "\n"
                + "Monthly Income " + getMonthlyIncome() + "\n";
    }

}
