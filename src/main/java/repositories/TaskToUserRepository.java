package repositories;

import java.io.*;
import java.util.*;

import entities.Share;
import entities.TaskToUser;
import services.AuthenticationServices;
import tools.*;

public class TaskToUserRepository {

    private String fileName;

    public TaskToUserRepository(String fileName) throws IOException{

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

            while ((value = bufferedReader.readLine()) != null){

                TaskToUser item = new TaskToUser();

                item.setId(Integer.parseInt(value));
                item.setTaskId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));

                if (nextId < item.getId())
                    nextId = item.getId();
            }
        }
        return nextId + 1;
    }

    public TaskToUser GetById(int id) throws NumberFormatException, IOException {

        TaskToUser result = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                TaskToUser item = new TaskToUser();

                item.setId(Integer.parseInt(value));
                item.setTaskId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));

                if (item.getId() == id) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<TaskToUser> GetAll() throws NumberFormatException, IOException {

        ArrayList<TaskToUser> result = new ArrayList<TaskToUser>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                TaskToUser item = new TaskToUser();

                item.setId(Integer.parseInt(value));
                item.setTaskId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));

                result.add(item);
            }
        }
        return result;
    }

    public void Add(TaskToUser item) throws IOException{

        item.setId(GetNextId());

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))){

            printWriter.println(item.getId());
            printWriter.println(item.getTaskId());
            printWriter.println(item.getUserCurrentLogId());
            printWriter.println(item.getUserToShareId());
        }
    }

    public void Delete(TaskToUser item) throws FileNotFoundException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))){

            String value = "";

            while((value = bufferedReader.readLine()) != null){

                TaskToUser tempEntity = new TaskToUser();

                tempEntity.setId(Integer.parseInt(value));
                tempEntity.setTaskId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));

                if(tempEntity.getId() != item.getId()){

                    printWriter.println(item.getId());
                    printWriter.println(item.getTaskId());
                    printWriter.println(item.getUserCurrentLogId());
                    printWriter.println(item.getUserToShareId());
                }
            }
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public ArrayList<TaskToUser> GetAll(int parentId) throws NumberFormatException, IOException {

        ArrayList<TaskToUser> result = new ArrayList<TaskToUser>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                TaskToUser taskToUser = new TaskToUser();

                taskToUser.setId(Integer.parseInt(value));
                taskToUser.setTaskId(Integer.parseInt(bufferedReader.readLine()));
                taskToUser.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                taskToUser.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));

                if (taskToUser.getId() == parentId)
                    result.add(taskToUser);
            }
        }
        return result;
    }

    public ArrayList<TaskToUser> GetAllSharedWithLoggedUser() throws NumberFormatException, IOException {


        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId();

        ArrayList<TaskToUser> result = new ArrayList<TaskToUser>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                TaskToUser item = new TaskToUser();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setTaskId(Integer.parseInt(bufferedReader.readLine()));

                if (loggedUserId == item.getUserCurrentLogId()) {
                    result.add(item);
                }
                /*else {
                    ConsoleManager.WriteLine("No");
                }*/
            }
        }
        return result;
    }

    public ArrayList<TaskToUser> GetAllSharedFromLoggedUser() throws NumberFormatException, IOException {

        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId(); //2

        ArrayList<TaskToUser> result = new ArrayList<TaskToUser>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                TaskToUser item = new TaskToUser();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setTaskId(Integer.parseInt(bufferedReader.readLine()));

                if (loggedUserId == item.getUserToShareId()) {
                    result.add(item);
                }
                /*else {
                    ConsoleManager.WriteLine("No");
                }*/
            }
        }
        return result;
    }
}
