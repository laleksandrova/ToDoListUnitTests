package repositories;

import entities.ToDoList;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToDoListRepositoryShould {

    private static ToDoListRepository repo;
    private static ToDoList item;
    private static int itemId;


    @BeforeAll
    public static void beforeClass() throws Exception{

        var fileName = "toDoLists_test.txt";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }

        repo = new ToDoListRepository(fileName);
        item = new ToDoList();
        item.setTitle("UnitTest");
        item.setUserId(1);

        item.setCreationDate(LocalDateTime.now());
        item.setCreatorId(0);
        item.setLastChangeDate(LocalDateTime.now());
        item.setUserMadeLastChangeId(0);
    }

    @Test
    @Order(1)
    public void addToDoListCorrectly() throws IOException{

        repo.Add(item);
        ToDoList toDoList = repo.GetById(item.getId());

        assertNotNull(toDoList);
    }

    @Test
    @Order(2)
    public void editToDoListCorrectly() throws IOException{

        ToDoList editItem = repo.GetById(item.getId());
        itemId = editItem.getId();

        String editedTitle = "Updated title";
        editItem.setTitle(editedTitle);
        item.setTitle(editedTitle);

        repo.Edit(editItem);

        boolean isUpdated = true;
        var updatedEntity = repo.GetById(editItem.getId());

        if(!updatedEntity.getTitle().equals(editedTitle)){
            isUpdated = false;
        }
        assertTrue(isUpdated);
    }

    @Test
    @Order(3)
    public void getToDoListByIdCorrectly() throws IOException{

        ToDoList toDoList = repo.GetById(itemId);

        assertEquals(item.getTitle(), toDoList.getTitle());
        assertEquals(item.getUserId(), toDoList.getUserId());
    }

    @Test
    @Order(4)
    public void getAllByParentIdCorrectly() throws IOException{

        ArrayList<ToDoList> all = repo.GetAll(item.getUserId());

        assertEquals(1, all.size());
        assertEquals(itemId, all.get(0).getId());
    }

    @Test
    @Order(5)
    public void deleteContactCorrectly() throws IOException{

        ToDoList toDoList = repo.GetById(itemId);
        ArrayList<ToDoList> all = repo.GetAll();

        int initialCount = all.size();
        repo.Delete(toDoList);
        all = repo.GetAll();

        assertEquals(initialCount - 1, all.size());
    }

    @Test
    @Order(6)
    public void testGetAllCorrectly() throws IOException{
        ArrayList<ToDoList> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(7)
    public void getAllCreatedFromLoggedUserCorrectly() throws IOException{
        ArrayList<ToDoList> all = repo.GetAll();

        assertEquals(0, all.size());
    }
}