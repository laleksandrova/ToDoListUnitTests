package repositories;

import entities.TaskToUser;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class TaskToUserRepositoryShould {

    private static TaskToUserRepository repo;
    private static TaskToUser item;
    private static int itemId;

    @BeforeAll
    public static void beforeClass() throws IOException {

        var fileName = "taskToUser_test.txt";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }

        repo = new TaskToUserRepository(fileName);
        item = new TaskToUser();

        item.setTaskId(1);
        item.setUserCurrentLogId(1);
        item.setUserToShareId(1);
    }

    @Test
    @Order(1)
    public void addTaskToUserCorrectly() throws IOException{

        repo.Add(item);
        TaskToUser taskToUser = repo.GetById(item.getId());
        itemId = item.getId();

        assertNotNull(taskToUser);
    }

    @Test
    @Order(2)
    public void getTaskToUserByIdCorrectly() throws IOException{

        TaskToUser taskToUser = repo.GetById(itemId);

        assertEquals(item.getTaskId(), taskToUser.getTaskId());
        assertEquals(item.getUserCurrentLogId(), taskToUser.getUserCurrentLogId());
        assertEquals(item.getUserToShareId(), taskToUser.getUserToShareId());
    }

    @Test
    @Order(3)
    public void getAllByParentIdCorrectly() throws IOException{

        ArrayList<TaskToUser> all = repo.GetAll(item.getTaskId());

        assertEquals(1, all.size());
        assertEquals(itemId, all.get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteTaskToUserCorrectly() throws IOException{
        TaskToUser taskToUser = repo.GetById(itemId);
        ArrayList<TaskToUser> all = repo.GetAll();

        int initialCount = all.size();
        repo.Delete(taskToUser);
        all = repo.GetAll();

        assertEquals(initialCount - 1, all.size());
    }

    @Test
    @Order(5)
    public void testGetAllCorrectly() throws IOException{
        ArrayList<TaskToUser> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(6)
    public void getAllSharedWithLoggedUserCorrectly() throws IOException{
        ArrayList<TaskToUser> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(7)
    public void getAllSharedFromLoggedUserCorrectly() throws IOException{
        ArrayList<TaskToUser> all = repo.GetAll();

        assertEquals(0, all.size());
    }
}