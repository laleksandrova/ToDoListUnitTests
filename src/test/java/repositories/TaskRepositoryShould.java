package repositories;

import entities.Task;
import org.junit.jupiter.api.*;
import views.TaskStatusEnumeration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class TaskRepositoryShould {

    private static TaskRepository repo;
    private static Task item;
    private static int itemId;

    @BeforeAll
    public static void beforeClass() throws IOException{

        var fileName = "task_test.txt";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }

        repo = new TaskRepository(fileName);
        item = new Task();

        item.setTitle("DescriptionTest");
        item.setDescription("DescriptionTask");
        item.setUserId(1);

        item.setCreationDate(LocalDateTime.now());
        item.setCreatorId(0);
        item.setLastChangeDate(LocalDateTime.now());
        item.setUserMadeLastChangeId(0);

        item.setStatus(TaskStatusEnumeration.Pending);
    }

    @Test
    @Order(1)
    public void addTaskCorrectly() throws IOException{

        repo.Add(item);
        Task task = repo.GetById(item.getId());

        assertNotNull(task);
    }

    @Test
    @Order(2)
    public void editTaskCorrectly() throws IOException{
        Task editItem = repo.GetById(item.getId());
        itemId = editItem.getId();

        String editedTitle = "Updated title";
        String editedDescription = "Updated description";
        editItem.setTitle(editedTitle);
        editItem.setDescription(editedDescription);

        item.setTitle(editedTitle);
        item.setDescription(editedDescription);

        repo.Edit(editItem);

        boolean isUpdated = true;
        var updatedEntity = repo.GetById(editItem.getId());

        if(!updatedEntity.getTitle().equals(editedTitle) || !updatedEntity.getDescription().equals(editedDescription)){
            isUpdated = false;
        }
        assertTrue(isUpdated);
    }

    @Test
    @Order(3)
    public void getTaskByIdCorrectly() throws IOException{
        Task task = repo.GetById(itemId);

        assertEquals(item.getTitle(), task.getTitle());
        assertEquals(item.getDescription(), task.getDescription());
        assertEquals(item.getUserId(), task.getUserId());
    }

    @Test
    @Order(4)
    public void getAllByParentIdCorrectly() throws IOException{
        ArrayList<Task> all = repo.GetAll(item.getToDoListId());

        assertEquals(1, all.size());
        assertEquals(itemId, all.get(0).getId());
    }

    @Test
    @Order(5)
    public void deleteTaskCorrectly() throws IOException{
        Task task = repo.GetById(itemId);
        ArrayList<Task> all = repo.GetAll();

        int initialCount = all.size();
        repo.Delete(task);
        all = repo.GetAll();

        assertEquals(initialCount - 1, all.size());
    }

    @Test
    @Order(6)
    public void testGetAllCorrectly() throws IOException{
        ArrayList<Task> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(7)
    public void getAllCreatedFromLoggedUser() throws IOException{
        ArrayList<Task> all = repo.GetAll();

        assertEquals(0, all.size());
    }
}