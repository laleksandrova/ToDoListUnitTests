package views;

import  java.io.*;
import tools.*;

public class AdministrationViewNonAdmin {
    public void Run() throws IOException{

        while (true){
            ConsoleManager.Clear();

            ConsoleManager.WriteLine("To[D]oList Management");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch (choice.toUpperCase()){

                case "D":{
                    ToDoListManagement();
                    break;
                }
                case "X":{
                    return;
                }
                default:{
                    ConsoleManager.Clear();
                    ConsoleManager.WriteLine("Invalid choice!");
                    ConsoleManager.WriteLine("Press [Enter] to continue");
                    ConsoleManager.ReadLine();
                    break;
                }
            }
        }
    }

    private void ToDoListManagement() throws IOException{
        ToDoListManagementView view = new ToDoListManagementView();
        view.Run();
    }

}
