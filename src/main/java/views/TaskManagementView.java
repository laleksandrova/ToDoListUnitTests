package views;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import entities.*;
import repositories.*;
import services.AuthenticationServices;
import tools.*;

public class TaskManagementView {

    private ToDoList parent;

    public TaskManagementView(ToDoList parent) {this.parent = parent;}

    public void Run() throws IOException{

        while (true){
            MenuEnumeration choice = RenderMenu();

            switch(choice){

                case List: {
                    List();
                    break;
                }
                case Add: {
                    Add();
                    break;
                }
                case Edit:{
                    Edit();
                    break;
                }
                case Delete: {
                    Delete();
                    break;
                }
                case View: {
                    View();
                    break;
                }
                case Exit: {
                    return;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() throws NumberFormatException, IOException{

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");

        while (true){

            ConsoleManager.Clear();

            ConsoleManager.WriteLine("ToDoList Details:");
            ConsoleManager.Write("Title: ");
            ConsoleManager.WriteLine(parent.getTitle());
            ConsoleManager.WriteLine();

            ArrayList<Task> tasks = taskManagementRepo.GetAll(parent.getId());

            for(int i = 0; i< tasks.size(); i++){

                Task task = tasks.get(i);

                ConsoleManager.WriteLine(task.getTitle());
                ConsoleManager.WriteLine(task.getDescription());

                task.setUserId(AuthenticationServices.getInstance().getLoggedUser().getId());

                task.setCreationDate(LocalDateTime.now());
                task.setCreatorId(AuthenticationServices.getInstance().getLoggedUser().getId());
                task.setLastChangeDate(LocalDateTime.now());
                task.setUserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

                ConsoleManager.WriteLine(task.getStatus().toString());
            }

            ConsoleManager.WriteLine("\n###############################\n");

            ConsoleManager.WriteLine("[L]ist Tasks");
            ConsoleManager.WriteLine("[A]dd Task");
            ConsoleManager.WriteLine("[E]dit Task");
            ConsoleManager.WriteLine("[D]elete Task");
            ConsoleManager.WriteLine("[V]iew Task");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch (choice.toUpperCase()){
                case "L":{
                    return MenuEnumeration.List;
                }
                case "A":{
                    return MenuEnumeration.Add;
                }
                case "E":{
                    return MenuEnumeration.Edit;
                }
                case "D":{
                    return MenuEnumeration.Delete;
                }
                case "V":{
                    return MenuEnumeration.View;
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

    private void Add() throws IOException{
        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Add a task");

        Task item = new Task();
        item.setToDoListId(parent.getId());

        ConsoleManager.Write("Title: ");
        item.setTitle(ConsoleManager.ReadLine());
        ConsoleManager.Write("Description: ");
        item.setDescription(ConsoleManager.ReadLine());

        item.setUserId(AuthenticationServices.getInstance().getLoggedUser().getId());

        item.setCreationDate(LocalDateTime.now());
        item.setCreatorId(AuthenticationServices.getInstance().getLoggedUser().getId());
        item.setLastChangeDate(LocalDateTime.now());
        item.setUserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

        ConsoleManager.Write("Task Status (Pending/Completed): ");
        String valueStatus = ConsoleManager.ReadLine();
        TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus);
        item.setStatus(status);

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");
        taskManagementRepo.Add(item);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item added successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void List() throws NumberFormatException, IOException{
        ConsoleManager.Clear();
        ConsoleManager.WriteLine("List of tasks");

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");
        ArrayList<Task> tasks = taskManagementRepo.GetAllCreatedFromLoggedUser();

        for(int i = 0; i< tasks.size(); i++){

            Task task = tasks.get(i);

            ConsoleManager.Write("ID: ");
            ConsoleManager.WriteLine(task.getId());
            ConsoleManager.Write("Title: ");
            ConsoleManager.WriteLine(task.getTitle());
            ConsoleManager.Write("Description: ");
            ConsoleManager.WriteLine(task.getDescription());

            task.setUserId(AuthenticationServices.getInstance().getLoggedUser().getId());

            task.setCreationDate(LocalDateTime.now());
            task.setCreatorId(AuthenticationServices.getInstance().getLoggedUser().getId());
            task.setLastChangeDate(LocalDateTime.now());
            task.setUserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

            ConsoleManager.Write("Status: ");
            ConsoleManager.WriteLine(task.getStatus().toString());

            ConsoleManager.WriteLine("---------------------------------");
        }

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Edit() throws NumberFormatException, IOException{

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Edit task");

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");
        ArrayList<Task> tasks = taskManagementRepo.GetAllCreatedFromLoggedUser();

        for(int i = 0; i< tasks.size(); i++){

            Task task = tasks.get(i);

            ConsoleManager.Write(task.getTitle() + " ( " + task.getId() + " )\t");

            if(i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of task: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Task task = taskManagementRepo.GetById(id);

        ConsoleManager.Write("Title of a task: (" + task.getTitle() + " ): ");
        task.setTitle(ConsoleManager.ReadLine());

        ConsoleManager.Write("Description of a task: (" + task.getDescription() + " ): ");
        task.setDescription(ConsoleManager.ReadLine());

        task.setUserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

        task.setLastChangeDate(LocalDateTime.now());
        task.setUserMadeLastChangeId(AuthenticationServices.getInstance().getLoggedUser().getId());

        ConsoleManager.Write("Change status: (" + task.getStatus() + " ): ");
        String valueStatus = ConsoleManager.ReadLine();
        TaskStatusEnumeration status = Enum.valueOf(TaskStatusEnumeration.class, valueStatus) ;
        task.setStatus(status);

        taskManagementRepo.Edit(task);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item updated successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Delete() throws NumberFormatException, IOException{

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Delete Task");

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");
        ArrayList<Task> tasks = taskManagementRepo.GetAllCreatedFromLoggedUser();

        for(int i = 0; i< tasks.size(); i++){

            Task task = tasks.get(i);

            ConsoleManager.Write(task.getTitle() + " ( " + task.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of task: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Task task = taskManagementRepo.GetById(id);
        taskManagementRepo.Delete(task);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item deleted successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void View() throws NumberFormatException, IOException{
        ConsoleManager.Clear();
        ConsoleManager.WriteLine("View Task");

        TaskRepository taskManagementRepo = new TaskRepository("tasks.txt");
        ArrayList<Task> tasks = taskManagementRepo.GetAllCreatedFromLoggedUser();

        for(int i = 0; i< tasks.size(); i++){

            Task task = tasks.get(i);

            ConsoleManager.Write(task.getTitle() + " ( " + task.getId() + " )\t");

            if(i>0 && i%5==0){
                ConsoleManager.WriteLine();
            }
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of task: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Task task = taskManagementRepo.GetById(id);

        ConsoleManager.Clear();

        TaskToUserManagementView taskAssignView = new TaskToUserManagementView(task);
        taskAssignView.Run();
    }
}