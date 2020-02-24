package views;

import java.io.*;
import java.util.*;

import entities.*;
import repositories.*;
import services.AuthenticationServices;
import tools.*;

public class ShareManagementView {

    private ToDoList parent;

    public ShareManagementView(ToDoList parent) {
        this.parent = parent;
    }

    public void Run() throws IOException {
        while (true) {
            MenuEnumeration choice = RenderMenu();

            switch (choice) {

                case List: {
                    List();
                    break;
                }
                case Add: {
                    Add();
                    break;
                }
                case Delete: {
                    Delete();
                    break;
                }
                case Exit:{
                    return;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() throws IOException {

        ShareRepository userToToDoListRepo = new ShareRepository("shares.txt");

        while (true) {

            ConsoleManager.Clear();

            ConsoleManager.WriteLine("ToDoList Details:");
            ConsoleManager.Write("Title: ");
            ConsoleManager.WriteLine(parent.getTitle());
            ConsoleManager.WriteLine();

            ArrayList<Share> userToToDoLists = userToToDoListRepo.GetAll(parent.getId());

            for(int i = 0; i< userToToDoLists.size(); i++){

                Share userToToDoList = userToToDoLists.get(i);

                ConsoleManager.WriteLine(userToToDoList.getId());
                ConsoleManager.WriteLine(userToToDoList.getUserCurrentLogId());
                ConsoleManager.WriteLine(userToToDoList.getUserToShareId());
                ConsoleManager.WriteLine(userToToDoList.getToDoListId());
            }

            ConsoleManager.WriteLine("\n###############################\n");

            ConsoleManager.WriteLine("[L]ist shares");
            ConsoleManager.WriteLine("[A]dd a share");
            ConsoleManager.WriteLine("[D]elete a share");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch (choice.toUpperCase()) {
                case "L": {
                    return MenuEnumeration.List;
                }
                case "A":{
                    return MenuEnumeration.Add;
                }
                case "D":{
                    return MenuEnumeration.Delete;
                }
                case "X":{
                    return MenuEnumeration.Exit;
                }
                default: {
                    ConsoleManager.Clear();
                    ConsoleManager.WriteLine("Invalid choice!");
                    ConsoleManager.WriteLine("Press [Enter] to continue");
                    ConsoleManager.ReadLine();
                    break;
                }
            }
        }
    }

    private void List() throws NumberFormatException, IOException{

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("List of Shared with me");
        ConsoleManager.WriteLine("*****");

        ShareRepository userToToDoListRepo = new ShareRepository("shares.txt");
        ArrayList<Share> userToToDoLists = userToToDoListRepo.GetAllSharedWithLoggedUser();

        for(int i=0;i<userToToDoLists.size();i++){

            Share userToToDoList = userToToDoLists.get(i);

            ConsoleManager.Write("ID of a share: ");
            ConsoleManager.WriteLine(userToToDoList.getId());
            ConsoleManager.Write("ID of user who shared the ToDoList: ");
            ConsoleManager.WriteLine(userToToDoList.getUserCurrentLogId());
            ConsoleManager.Write("ID of user to share with: ");
            ConsoleManager.WriteLine(userToToDoList.getUserToShareId());
            ConsoleManager.Write("ID of ToDoList: ");
            ConsoleManager.WriteLine(userToToDoList.getToDoListId());

            ConsoleManager.WriteLine("---------------------------------");
        }

        ConsoleManager.WriteLine("List of Shared from me");
        ConsoleManager.WriteLine("*****");

        ShareRepository shareRepository = new ShareRepository("shares.txt");
        ArrayList<Share> shares = shareRepository.GetAllSharedFromLoggedUser();

        for(int i=0;i<shares.size();i++){

            Share userToToDoList = shares.get(i);

            ConsoleManager.Write("ID of a share: ");
            ConsoleManager.WriteLine(userToToDoList.getId());
            ConsoleManager.Write("ID of currently logged user: ");
            ConsoleManager.WriteLine(userToToDoList.getUserCurrentLogId());
            ConsoleManager.Write("ID of user to share with: ");
            ConsoleManager.WriteLine(userToToDoList.getUserToShareId());
            ConsoleManager.Write("ID of ToDoList: ");
            ConsoleManager.WriteLine(userToToDoList.getToDoListId());

            ConsoleManager.WriteLine("---------------------------------");
        }

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Add() throws IOException{
        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Add a share");

        Share item = new Share();
        item.setId(parent.getId());

        UserRepository userRepo = new UserRepository("users.txt");
        ArrayList<User> users = userRepo.GetAll();

        for(int i=0;i<users.size();i++){

            User user = users.get(i);

            ConsoleManager.Write(user.getUsername() + " ( " + user.getId() + " )\t");

            if(i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();

        item.setUserCurrentLogId(AuthenticationServices.getInstance().getLoggedUser().getId());

        ConsoleManager.Write("Enter ID of a user to share with: ");
        item.setUserToShareId(Integer.parseInt(ConsoleManager.ReadLine()));

        ToDoListRepository toDoListRepo = new ToDoListRepository("toDoLists.txt");
        ArrayList<ToDoList> toDoLists = toDoListRepo.GetAllCreatedFromLoggedUser();

        for(int i=0;i<toDoLists.size();i++){

            ToDoList toDoList = toDoLists.get(i);

            ConsoleManager.Write(toDoList.getTitle() + " ( " + toDoList.getId() + " )\t");

            if(i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of a ToDoList to share: ");
        item.setToDoListId(Integer.parseInt(ConsoleManager.ReadLine()));

        ShareRepository userToToDoListRepo = new ShareRepository("shares.txt");
        userToToDoListRepo.Add(item);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item added successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Delete() throws NumberFormatException, IOException{

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Delete a Share");

        ShareRepository userToToDoListRepo = new ShareRepository("shares.txt");
        ArrayList<Share> shares = userToToDoListRepo.GetAllSharedFromLoggedUser();

        for(int i = 0; i< shares.size(); i++){

            Share share = shares.get(i);

            ConsoleManager.Write("User" + share.getUserToShareId() + " ( " + share.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ArrayList<Share> sharesWith = userToToDoListRepo.GetAllSharedWithLoggedUser();

        for(int i = 0; i< sharesWith.size(); i++){

            Share share = sharesWith.get(i);

            ConsoleManager.Write("User" + share.getUserToShareId() + " ( " + share.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of a share: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Share share = userToToDoListRepo.GetById(id);
        userToToDoListRepo.Delete(share);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item deleted successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }
}
