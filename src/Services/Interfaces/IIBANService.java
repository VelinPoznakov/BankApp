package Services.Interfaces;

import Entities.IBAN;
import Entities.Users.User;

import java.util.List;

public interface IIBANService {

    List<IBAN> LoadIBANs();
    void CreateIBANsInFile();
    IBAN IBANForUser(int userId);
    void CreateAdminIBAN();
}
