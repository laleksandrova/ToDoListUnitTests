package repositories;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import entities.Share;
import entities.Task;
import entities.ToDoList;
import services.AuthenticationServices;
import tools.*;
import views.TaskStatusEnumeration;

public class TaskRepository {

    private String fileName;

    public TaskRepository(String fileName) throws IOException{
        this.fileName = fileName;

        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    private int GetNextId() throws IOException{

        int nextId = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while ((value = bufferedReader.readLine()) != null){

                Task item = new Task();
                item.setId(Integer.parseInt(value));

                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                item.setTitle(bufferedReader.readLine());
                item.setDescription(bufferedReader.readLine());

                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                item.setStatus(status);

                if (nextId < item.getId())
                    nextId = item.getId();
            }
        }
        return nextId + 1;
    }

    public Task GetById(int id) throws NumberFormatException, IOException {

        Task result = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                Task item = new Task();

                item.setId(Integer.parseInt(value));

                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                item.setTitle(bufferedReader.readLine());
                item.setDescription(bufferedReader.readLine());

                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                item.setStatus(status);

                if (item.getId() == id) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<Task> GetAll() throws NumberFormatException, IOException {

        ArrayList<Task> result = new ArrayList<Task>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Task item = new Task();

                item.setId(Integer.parseInt(value));

                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                item.setTitle(bufferedReader.readLine());
                item.setDescription(bufferedReader.readLine());

                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                item.setStatus(status);

                result.add(item);
            }
        }
        return result;
    }

    public void Add(Task item) throws IOException{

        item.setId(GetNextId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))){
            printWriter.println(item.getId());

            printWriter.println(item.getToDoListId());
            printWriter.println(item.getTitle());
            printWriter.println(item.getDescription());

            printWriter.println(item.getUserId());

            printWriter.println(item.getCreationDate().format(formatter));
            printWriter.println(item.getCreatorId());
            printWriter.println(item.getLastChangeDate().format(formatter));
            printWriter.println(item.getUserMadeLastChangeId());

            printWriter.println(item.getStatus());
        }
    }

    public void Edit(Task item) throws NumberFormatException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))){

            String value = "";

            while((value = bufferedReader.readLine()) != null){

                Task task = new Task();

                task.setId(Integer.parseInt(value));

                task.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());

                task.setUserId(Integer.parseInt(bufferedReader.readLine()));

                task.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                task.setStatus(status);

                if(task.getId() != item.getId()){

                    printWriter.println(task.getId());

                    printWriter.println(task.getToDoListId());
                    printWriter.println(task.getTitle());
                    printWriter.println(task.getDescription());

                    printWriter.println(task.getUserId());

                    printWriter.println(task.getCreationDate().format(formatter));
                    printWriter.println(task.getCreatorId());
                    printWriter.println(task.getLastChangeDate().format(formatter));
                    printWriter.println(task.getUserMadeLastChangeId());

                    printWriter.println(task.getStatus());
                }

                else{

                    printWriter.println(item.getId());

                    printWriter.println(item.getToDoListId());
                    printWriter.println(item.getTitle());
                    printWriter.println(item.getDescription());

                    printWriter.println(item.getUserId());

                    printWriter.println(item.getCreationDate().format(formatter));
                    printWriter.println(item.getCreatorId());
                    printWriter.println(item.getLastChangeDate().format(formatter));
                    printWriter.println(item.getUserMadeLastChangeId());

                    printWriter.println(item.getStatus());
                }
            }
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public void Delete(Task item) throws FileNotFoundException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))){

            String value = "";

            while((value = bufferedReader.readLine()) != null){

                Task tempEntity = new Task();

                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setTitle(bufferedReader.readLine());
                tempEntity.setDescription(bufferedReader.readLine());

                tempEntity.setUserId(Integer.parseInt(bufferedReader.readLine()));

                tempEntity.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                tempEntity.setStatus(status);

                if(tempEntity.getId() != item.getId()){

                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getToDoListId());
                    printWriter.println(tempEntity.getTitle());
                    printWriter.println(tempEntity.getDescription());

                    printWriter.println(tempEntity.getUserId());

                    printWriter.println(item.getCreationDate().format(formatter));
                    printWriter.println(item.getCreatorId());
                    printWriter.println(item.getLastChangeDate().format(formatter));
                    printWriter.println(item.getUserMadeLastChangeId());

                    printWriter.println(tempEntity.getStatus());
                }
            }
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public ArrayList<Task> GetAll(int parentId) throws NumberFormatException, IOException {

        ArrayList<Task> result = new ArrayList<Task>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Task task = new Task();

                task.setId(Integer.parseInt(value));

                task.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());

                task.setUserId(Integer.parseInt(bufferedReader.readLine()));

                task.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                task.setStatus(status);

                if (task.getToDoListId() == parentId)
                    result.add(task);
            }
        }
        return result;
    }

    public ArrayList<Task> GetAllCreatedFromLoggedUser() throws NumberFormatException, IOException {

        //показва всички ToDoLists създадени от логнатия user
        //(ToDoList)GetAllCreatedFromLoggedUser = (Share)GetAllSharedWithLoggedUser

        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId();

        ArrayList<Task> result = new ArrayList<Task>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                Task task = new Task();

                task.setId(Integer.parseInt(value));

                task.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());

                task.setUserId(Integer.parseInt(bufferedReader.readLine()));

                task.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                task.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                String valueStatus = bufferedReader.readLine();
                TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
                task.setStatus(status);

                if (loggedUserId == task.getUserId()) {
                    result.add(task);
                }
                /*else {
                    ConsoleManager.WriteLine("No");
                }*/
            }
        }
        return result;
    }
}
