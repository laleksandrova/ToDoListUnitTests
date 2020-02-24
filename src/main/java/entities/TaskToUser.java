package entities;

public class TaskToUser {

    private int id;
    private int taskId;
    private int userCurrentLogId;
    private int userToShareId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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
}
