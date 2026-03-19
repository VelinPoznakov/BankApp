package ProgramInterface.Interfaces;

import Entities.IBAN;
import Entities.Loans.Loan;
import Entities.Users.User;

import java.util.List;

public interface IAdminPanelInterface {

    void AdminMenu();
    void AllUsersDisplay(List<User> users);
    void DisplayLoans(List<Loan> loans);
    void DisplayIBANs(List<IBAN> ibans);
    void DisplayCards(List<User> users);
    void DisplaySummary(List<User> users);
}
