package entities;

import views.TaskStatusEnumeration;

import java.time.LocalDateTime;

public class Task {

    private int id;
    private int toDoListId;
    private String title;
    private String description;

    private int userId;

    private LocalDateTime creationDate;
    private int creatorId;
    private LocalDateTime lastChangeDate;
    private int userMadeLastChangeId;

    private TaskStatusEnumeration status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(int toDoListId) {
        this.toDoListId = toDoListId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TaskStatusEnumeration getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnumeration status) {
        this.status = status;
    }
}
