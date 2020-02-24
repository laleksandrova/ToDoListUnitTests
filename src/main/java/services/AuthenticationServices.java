package services;

import java.io.FileNotFoundException;
import java.io.IOException;

import entities.*;
import repositories.*;
import views.AdministrationViewAdmin;
import views.AdministrationViewNonAdmin;

public class AuthenticationServices {

    private static AuthenticationServices instance = null;

    public static  AuthenticationServices getInstance(){

        if (AuthenticationServices.instance == null)
            AuthenticationServices.instance = new AuthenticationServices();

        return AuthenticationServices.instance;
    }

    private AuthenticationServices(){

    }

    private User authenticatedUser = null;

    public User getLoggedUser() { return authenticatedUser; }

    public void AuthenticateUser(String username, String password, UserRepository userRepository) throws FileNotFoundException, IOException{

        this.authenticatedUser = userRepository.GetByUsernameAndPassword(username, password);

        /*if (authenticatedUser.getIsAdmin() == true){
            AdministrationViewAdmin view = new AdministrationViewAdmin();
            view.Run();
        }
        else{
            AdministrationViewNonAdmin viewNonAdmin = new AdministrationViewNonAdmin();
            viewNonAdmin.Run();
        }*/
    }

}
