import Services.IBANService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.Interfaces.IUserService;
import Services.LoanService;
import Services.UserServices;

public class DataLoader {

    private final IUserService userServices;
    private final IIBANService ibanService;
    private final ILoanService loanService;

    public DataLoader(IUserService userServices,
                      IIBANService ibanService,
                      ILoanService loanService) {

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
}
