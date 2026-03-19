import Entities.IBAN;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.Interfaces.IUserService;
import Services.UserServices;

public class DataLoader {

    private final IUserService userServices;
    private final IIBANService ibanService;
    private final ILoanService loanService;
    private final ICardService cardService;

    public DataLoader(IUserService userServices,
                      IIBANService ibanService,
                      ILoanService loanService,
                      ICardService cardService) {

        this.userServices = userServices;
        this.ibanService = ibanService;
        this.loanService = loanService;
        this.cardService = cardService;
    }

    public void Loader(){
        userServices.LoadUsers();
        ibanService.LoadIBANs();
        loanService.LoadLoans();
        cardService.LoadCards();

        if(UserServices.users.isEmpty()){
            userServices.CreateAdminUserInFile();
            ibanService.CreateAdminIBAN();
            cardService.CreateAdminCard();
        }

        System.out.println("Data load successfully!");
    }
}
