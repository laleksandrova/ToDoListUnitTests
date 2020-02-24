package entities;

public class Share {

    private int id;
    private int userCurrentLogId;
    private int userToShareId;
    private int toDoListId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCurrentLogId() {
        return userCurrentLogId;
    }

    public void setUserCurrentLogId(int userCurrentLogId) {
        this.userCurrentLogId = userCurrentLogId;
    }

    public int getUserToShareId() {
        return userToShareId;
    }

    public void setUserToShareId(int userToShareId) {
        this.userToShareId = userToShareId;
    }

    public int getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(int toDoListId) {
        this.toDoListId = toDoListId;
    }
}
