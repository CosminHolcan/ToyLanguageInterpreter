package view;

import java.util.HashMap;
import java.util.Scanner;

public class TextMenu {

    private HashMap<String, Command> commands;

    public TextMenu()
    {
        this.commands = new HashMap<String, Command>();
    }

    public void addCommand(Command command)
    {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu()
    {
        for (Command command : this.commands.values())
        {
            String line = "Command "+command.getKey() +" : "+ command.getDescription();
            System.out.println(line);
        }
    }

    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            this.printMenu();
            System.out.println("Your command : ");
            String key = scanner.nextLine();
            if (! this.commands.containsKey(key))
            {
                System.out.println("Invalid command");
            }
            else
            {
                Command command = this.commands.get(key);
                command.execute();
            }
        }
    }
}
