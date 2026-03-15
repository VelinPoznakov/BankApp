package Users;

import Exceptions.CustomerTypeException;
import Exceptions.InvalidMoneyNumber;
import Users.Enums.CustomerType;

public class Customer extends User {

    private int customerId;

    // loans set
//    private Set<Loan> Loans = new HashSet<Loan>();
    private double monthlyIncome;
    private double money;
//    private List<Cards> Cards = new ArrayList<Cards>();

    private CustomerType customerType;


    public Customer(int id,
                    String username,
                    String password,
                    String birthDate,
                    int customerId,
                    double money,
                    double monthlyIncome,
                    String customerType) {
        super(id, username, password, birthDate);
        setCustomerId(customerId);
        setMoney(money);
        setMonthlyIncome(monthlyIncome);
        setCustomerType(customerType);
    }

    @Override
    public String getUserDetails() {
        return customerType.toString() + " " + username + " " + getMoney();
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        if(monthlyIncome < 0){
            throw new InvalidMoneyNumber("Money cannot be lower than 0");
        }
        this.monthlyIncome = monthlyIncome;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if(money < 0){
            throw new InvalidMoneyNumber("Money cannot be lower than 0");
        }
        this.money = money;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        if(customerType.equals("student")){
            this.customerType = CustomerType.STUDENT;
            return;
        }else if(customerType.equals("adult")){
            this.customerType = CustomerType.ADULT;
            return;
        }

        throw new CustomerTypeException("Invalid Customer Type");
    }
}
