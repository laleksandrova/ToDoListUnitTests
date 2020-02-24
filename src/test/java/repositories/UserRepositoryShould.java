package repositories;

import entities.User;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryShould {

    private static UserRepository repository;
    private static User item;
    private static int itemId;

    @BeforeAll
    static void beforeAll() throws IOException{

        var fileName = "users_test.txt";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }

        repository = new UserRepository(fileName);
        item = new User();

        item.setUsername("admin2");
        item.setPassword("admin2");
        item.setFirstName("Administrator2");
        item.setLastName("Administrator2");

        item.setcreationDate(LocalDateTime.now());
        item.setcreatorID(0);
        item.setlastChangeDate(LocalDateTime.now());
        item.setuserMadeLastChangeId(0);

        item.setIsAdmin(true);
    }

    @Test
    @Order(1)
    public void addUserCorrectly() throws IOException{
        repository.Add(item);
        User user = repository.GetById(item.getId());
        assertNotNull(user);
    }

    @Test
    @Order(2)
    public void editUserCorrectly() throws IOException{

        User editItem = repository.GetByUsernameAndPassword(UserRepositoryShould.item.getUsername(),
                UserRepositoryShould.item.getPassword());
        itemId = editItem.getId();

        String updatedFirstName = "Updated First Name";
        String updatedLastName = "Updated Last Name";
        editItem.setFirstName(updatedFirstName);
        editItem.setLastName(updatedLastName);

        repository.Edit(editItem);

        boolean isUpdated = true;
        var updatedEntity = repository.GetById(editItem.getId());

        if(!updatedEntity.getFirstName().equals(updatedFirstName) || !updatedEntity.getLastName().equals(updatedLastName)){
            isUpdated = false;
        }

        assertTrue(isUpdated);
    }

    @Test
    @Order(3)
    public void getByIdCorrectly() throws IOException{
        User user = repository.GetById(itemId);

        assertEquals(UserRepositoryShould.item.getUsername(), user.getUsername());
        assertEquals(UserRepositoryShould.item.getPassword(), user.getPassword());
    }

    @Test
    @Order(4)
    public void getByUsernameAndPasswordCorrectly() throws IOException{
        User userByUsernameAndPassword = repository.GetByUsernameAndPassword(item.getUsername(), item.getPassword());

        assertNotEquals(null, userByUsernameAndPassword);
    }

    @Test
    @Order(5)
    public void deleteUserCorrectly() throws IOException{

        User user = repository.GetById(itemId);

        ArrayList<User> allUsers = repository.GetAll();
        int initialCount = allUsers.size();
        repository.Delete(user);
        allUsers = repository.GetAll();

        assertEquals(initialCount - 1, allUsers.size());
    }

    @Test
    @Order(6)
    public void getAllCorrectly() throws IOException{
        ArrayList<User> allUsers = repository.GetAll();

        assertEquals(1, allUsers.size());
    }

}