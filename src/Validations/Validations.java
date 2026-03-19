package Validations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;

public class Validations {

    public static boolean validateFile(String fileName){
        Path folder = Path.of("files", fileName);

        return Files.exists(folder);
    }

    public static boolean ValidatePayment(double paymentAmount, double accountBalance){
        return !(paymentAmount > accountBalance);
    }

    public static boolean ValidateCardDate(String date) {
        LocalDate today = LocalDate.now();
        LocalDate cardDate = LocalDate.parse(date);

        return !today.isAfter(cardDate);


    }

    public static String CommandCheck(String input){

        if(input.equals("e"))
            return null;

        return input;
    }
}
