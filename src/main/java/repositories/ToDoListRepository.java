package repositories;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import entities.*;
import services.AuthenticationServices;
import tools.*;

public class ToDoListRepository {

    private String fileName;

    public ToDoListRepository(String fileName) throws IOException{
        this.fileName = fileName;

        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    private int GetNextId() throws IOException{
        int nextId = 0;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

            String value = "";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while((value = bufferedReader.readLine()) != null){

                ToDoList item = new ToDoList();

                item.setId(Integer.parseInt(value));

                item.setTitle(bufferedReader.readLine());

                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if (nextId < item.getId())
                    nextId = item.getId();
            }
        }
        return nextId + 1;
    }

    public ToDoList GetById(int id) throws NumberFormatException, IOException{

        ToDoList result = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                ToDoList item = new ToDoList();

                item.setId(Integer.parseInt(value));
                item.setTitle(bufferedReader.readLine());
                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if (item.getId() == id) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<ToDoList> GetAll() throws NumberFormatException, IOException{

        ArrayList<ToDoList> result = new ArrayList<ToDoList>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                ToDoList item = new ToDoList();

                item.setId(Integer.parseInt(value));
                item.setTitle(bufferedReader.readLine());
                item.setUserId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime creationDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setCreationDate(creationDate);

                item.setCreatorId(Integer.parseInt(bufferedReader.readLine()));

                LocalDateTime lastChangeDate = LocalDateTime.parse(bufferedReader.readLine(), formatter);
                item.setLastChangeDate(lastChangeDate);

                item.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                result.add(item);
            }
        }
        return result;
    }

    public void Add(ToDoList item) throws IOException{

        item.setId(GetNextId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))){

            printWriter.println(item.getId());
            printWriter.println(item.getTitle());
            printWriter.println(item.getUserId());

            printWriter.println(item.getCreationDate().format(formatter));
            printWriter.println(item.getCreatorId());
            printWriter.println(item.getLastChangeDate().format(formatter));
            printWriter.println(item.getUserMadeLastChangeId());
        }
    }

    public void Edit(ToDoList item) throws NumberFormatException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))){

            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                ToDoList tempEntity = new ToDoList();

                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setTitle(bufferedReader.readLine());

                tempEntity.setUserId(Integer.parseInt(bufferedReader.readLine()));

                tempEntity.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if(tempEntity.getId() != item.getId()){

                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getTitle());

                    printWriter.println(tempEntity.getUserId());

                    printWriter.println(tempEntity.getCreationDate().format(formatter));
                    printWriter.println(tempEntity.getCreatorId());
                    printWriter.println(tempEntity.getLastChangeDate().format(formatter));
                    printWriter.println(tempEntity.getUserMadeLastChangeId());
                }
                else {
                    printWriter.println(item.getId());

                    printWriter.println(item.getTitle());

                    printWriter.println(item.getUserId());

                    printWriter.println(item.getCreationDate().format(formatter));
                    printWriter.println(item.getCreatorId());
                    printWriter.println(item.getLastChangeDate().format(formatter));
                    printWriter.println(item.getUserMadeLastChangeId());
                }
            }
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public void Delete(ToDoList item) throws FileNotFoundException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                ToDoList tempEntity = new ToDoList();
                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setTitle(bufferedReader.readLine());

                tempEntity.setUserId(Integer.parseInt(bufferedReader.readLine()));

                tempEntity.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                tempEntity.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if (tempEntity.getId() != item.getId()) {
                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getTitle());
                    printWriter.println(tempEntity.getUserId());

                    printWriter.println(item.getCreationDate().format(formatter));
                    printWriter.println(item.getCreatorId());
                    printWriter.println(item.getLastChangeDate().format(formatter));
                    printWriter.println(item.getUserMadeLastChangeId());
                }
            }
        }
        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public ArrayList<ToDoList> GetAll(int parentId) throws NumberFormatException, IOException{

        ArrayList<ToDoList> result = new ArrayList<ToDoList>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                ToDoList toDoList = new ToDoList();

                toDoList.setId(Integer.parseInt(value));
                toDoList.setTitle(bufferedReader.readLine());
                toDoList.setUserId(Integer.parseInt(bufferedReader.readLine()));

                toDoList.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                toDoList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                toDoList.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                toDoList.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if (toDoList.getId() == parentId) {
                    result.add(toDoList);
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<ToDoList> GetAllCreatedFromLoggedUser() throws NumberFormatException, IOException {

        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId();

        ArrayList<ToDoList> result = new ArrayList<ToDoList>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                ToDoList toDoList = new ToDoList();

                toDoList.setId(Integer.parseInt(value));
                toDoList.setTitle(bufferedReader.readLine());
                toDoList.setUserId(Integer.parseInt(bufferedReader.readLine()));

                toDoList.setCreationDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                toDoList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                toDoList.setLastChangeDate(LocalDateTime.parse(bufferedReader.readLine(), formatter));
                toDoList.setUserMadeLastChangeId(Integer.parseInt(bufferedReader.readLine()));

                if (loggedUserId == toDoList.getUserId()) {
                    result.add(toDoList);
                }
                /*else {
                    ConsoleManager.WriteLine("No");
                }*/
            }
        }
        return result;
    }

}

