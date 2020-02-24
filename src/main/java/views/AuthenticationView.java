package views;

import java.io.*;

import repositories.UserRepository;
import services.*;
import tools.*;

public class AuthenticationView {

    public void Run() throws IOException{

        while (AuthenticationServices.getInstance().getLoggedUser() == null){
            ConsoleManager.Clear();

            ConsoleManager.Write("Username: ");
            String username = ConsoleManager.ReadLine();

            ConsoleManager.Write("Password: ");
            String password = ConsoleManager.ReadLine();

            UserRepository userRepository = new UserRepository("users.txt");
            AuthenticationServices.getInstance().AuthenticateUser(username, password, userRepository);
        }
    }
}
