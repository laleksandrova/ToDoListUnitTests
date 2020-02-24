package services;

import entities.User;
import repositories.UserRepository;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServicesShould {

    @Mock
    private UserRepository userRepository;
    private static User user;
    private static String username;
    private static String password;

    @BeforeAll
    static void setup(){
        username = "admin";
        password = "adminpass";

        user = new User();
        user.setId(1);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName("Administrator");
        user.setLastName("Administrator");
        user.setIsAdmin(true);
    }

    @Test
    public void authenticateUserCorrectly() throws IOException {
        when(userRepository.GetByUsernameAndPassword(username, password)).thenReturn(user);
        AuthenticationServices.getInstance().AuthenticateUser(username, password, userRepository);

        assertEquals(AuthenticationServices.getInstance().getLoggedUser(), user);
    }
}