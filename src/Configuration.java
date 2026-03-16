import ProgramInterface.Interfaces.IUserInterface;
import ProgramInterface.UserInterface;
import Services.IBANService;
import Services.LoanService;
import Services.UserServices;

public class Configuration {

    private final UserServices userServices;
    private final IBANService ibanService;
    private final LoanService loanService;

    public Configuration(UserServices userServices, IBANService ibanService, LoanService loanService) {
        this.userServices = userServices;
        this.ibanService = ibanService;
        this.loanService = loanService;
    }


    public String Loader(){
//        isInitialLoad();
        userServices.LoadUsers();
        ibanService.LoadIBANs();
        loanService.LoadLoans();

        return "Data load successfully!";
    }

//    public static boolean isInitialLoad(){
//        //admin user seed
//        return true;
//    }
}
