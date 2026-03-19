package Services;

import Entities.IBAN;
import Entities.Users.User;
import Services.Interfaces.IIBANService;
import Validations.Validations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IBANService implements IIBANService {

    public static String FILENAME = "IBANs.bin";
    public static Path file = Path.of("files", FILENAME);
    public static List<IBAN> ibans;

    @Override
    public List<IBAN> LoadIBANs() {

        if(!Validations.validateFile(FILENAME)){
            try{
                Files.createFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (Files.size(file) == 0) {
                return ibans = new ArrayList<>();
            }

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file.toFile()));

            ibans = (List<IBAN>) in.readObject();
            in.close();

            return ibans;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateIBANsInFile(){

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))){
            out.writeObject(ibans);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        ibans = LoadIBANs();
    }

    @Override
    public IBAN IBANForUser(int userId){
        IBAN userIban = null;

        for(IBAN iban : ibans){
            if(iban.getUserId() == userId){
                userIban = iban;
            }
        }

        return userIban;
    }

    @Override
    public void CreateAdminIBAN() {
        IBAN iban = new IBAN(
                "BG01",
                1000,
                1
        );

        ibans.add(iban);
        CreateIBANsInFile();
    }
}
