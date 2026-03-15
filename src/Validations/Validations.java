package Validations;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validations {

    public static boolean validateFile(String fileName){
        Path folder = Path.of("files", fileName);
        


        return Files.exists(folder);
    }
}
