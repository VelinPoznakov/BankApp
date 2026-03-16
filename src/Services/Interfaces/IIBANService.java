package Services.Interfaces;

import Entities.IBAN;

import java.util.List;

public interface IIBANService {

    List<IBAN> LoadIBANs();
    void CreateIBANsInFile();
    IBAN IBANForUser(int userId);
}
