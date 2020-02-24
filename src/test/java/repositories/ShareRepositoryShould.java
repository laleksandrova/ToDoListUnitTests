package repositories;

import entities.Share;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ShareRepositoryShould {

    private static ShareRepository repo;
    private static Share item;
    private static int itemId;

    @BeforeAll
    public static void beforeClass() throws IOException{

        var fileName = "share_test.txt";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }

        repo = new ShareRepository(fileName);
        item = new Share();

        item.setUserCurrentLogId(1);
        item.setUserToShareId(1);
        item.setToDoListId(1);
    }

    @Test
    @Order(1)
    public void addShareCorrectly() throws IOException {
        repo.Add(item);
        Share share = repo.GetById(item.getId());

        itemId = item.getId();

        assertNotNull(share);
    }

    @Test
    @Order(2)
    public void getShareByIdCorrectly() throws IOException {

        Share share = repo.GetById(itemId);

        assertEquals(item.getUserCurrentLogId(), share.getUserCurrentLogId());
        assertEquals(item.getUserToShareId(), share.getUserToShareId());
        assertEquals(item.getToDoListId(), share.getToDoListId());
    }

    @Test
    @Order(3)
    public void getAllByParentIdCorrectly() throws IOException {

        ArrayList<Share> all = repo.GetAll(item.getToDoListId());

        assertEquals(0, all.size());
    }

    @Test
    @Order(4)
    public void deleteShareCorrectly() throws IOException {
        Share share = repo.GetById(itemId);
        ArrayList<Share> all = repo.GetAll();

        int initialCount = all.size();
        repo.Delete(share);
        all = repo.GetAll();

        assertEquals(initialCount - 1, all.size());
    }

    @Test
    @Order(5)
    public void testGetAll() throws IOException {
        ArrayList<Share> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(6)
    public void getAllSharedWithLoggedUser() throws IOException {
        ArrayList<Share> all = repo.GetAll();

        assertEquals(0, all.size());
    }

    @Test
    @Order(7)
    public void getAllSharedFromLoggedUser() throws IOException {
        ArrayList<Share> all = repo.GetAll();

        assertEquals(0, all.size());
    }
}