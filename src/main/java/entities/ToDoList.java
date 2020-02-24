package entities;

import java.time.LocalDateTime;

public class ToDoList {

    private int id;
    private String title;
    private int userId;

    private LocalDateTime creationDate;
    private int creatorId;
    private LocalDateTime lastChangeDate;
    private int userMadeLastChangeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(LocalDateTime lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public int getUserMadeLastChangeId() {
        return userMadeLastChangeId;
    }

    public void setUserMadeLastChangeId(int userMadeLastChangeId) {
        this.userMadeLastChangeId = userMadeLastChangeId;
    }
}




