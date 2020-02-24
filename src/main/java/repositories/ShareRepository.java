package repositories;

import java.io.*;
import java.util.*;

import entities.Share;
import services.AuthenticationServices;
import tools.*;

public class ShareRepository {

    private String fileName;

    public ShareRepository(String fileName) throws IOException{
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

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

                if (nextId < item.getId())
                    nextId = item.getId();
            }
        }
        return nextId + 1;
    }

    public Share GetById(int id) throws NumberFormatException, IOException{

        Share result = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

            String value = "";

            while ((value = bufferedReader.readLine()) != null){

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

                if (item.getId() == id) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<Share> GetAll() throws NumberFormatException, IOException {

        ArrayList<Share> result = new ArrayList<Share>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<Share> GetAllSharedWithLoggedUser() throws NumberFormatException, IOException {

        //(ToDoList)GetAllCreatedFromLoggedUser = (Share)GetAllSharedWithLoggedUser

        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId();

        ArrayList<Share> result = new ArrayList<Share>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

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

    public ArrayList<Share> GetAllSharedFromLoggedUser() throws NumberFormatException, IOException {

        int loggedUserId = AuthenticationServices.getInstance().getLoggedUser().getId(); //2

        ArrayList<Share> result = new ArrayList<Share>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

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

    public void Add(Share item) throws IOException{

        item.setId(GetNextId());

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))){

            printWriter.println(item.getId());
            printWriter.println(item.getUserCurrentLogId());
            printWriter.println(item.getUserToShareId());
            printWriter.println(item.getToDoListId());
        }
    }

    public void Delete(Share item) throws FileNotFoundException, IOException{

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";

            while((value = bufferedReader.readLine()) != null) {

                Share tempEntity = new Share();

                tempEntity.setId(Integer.parseInt(value));
                tempEntity.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setToDoListId(Integer.parseInt(bufferedReader.readLine()));

                if (tempEntity.getId() != item.getId()) {

                    printWriter.println(tempEntity.getId());
                    printWriter.println(tempEntity.getUserCurrentLogId());
                    printWriter.println(tempEntity.getUserToShareId());
                    printWriter.println(tempEntity.getToDoListId());
                }
            }
        }
        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    public ArrayList<Share> GetAll(int parentId) throws NumberFormatException, IOException {

        ArrayList<Share> result = new ArrayList<Share>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Share item = new Share();

                item.setId(Integer.parseInt(value));
                item.setUserCurrentLogId(Integer.parseInt(bufferedReader.readLine()));
                item.setUserToShareId(Integer.parseInt(bufferedReader.readLine()));
                item.setToDoListId(Integer.parseInt(bufferedReader.readLine()));
            }
        }
        return result;
    }
}
