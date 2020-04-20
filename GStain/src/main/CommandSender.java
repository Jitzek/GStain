package main;

import main.commands.Command;

import java.util.ArrayList;
import java.util.ListIterator;

public class CommandSender {
    private final ArrayList<Command> commands;
    private int pointer;

    public CommandSender() {
        pointer = 0;
        commands = new ArrayList<>();
    }

    /**
     * Cleans List before pointer reset to remove unreachable commands
     */
    private void cleanList() {
        int count = 0;
        ListIterator<Command> iterator = commands.listIterator();
        while(iterator.hasNext()){
            if(count >= pointer) {
                break;
            }
            iterator.next();
            iterator.remove();
            count++;
        }
    }

    public void execute(Command command) {
        cleanList();
        pointer = 0;
        commands.add(0, command);
        command.execute();
    }

    public void undo() {
        if (pointer >= commands.size()) return;
        commands.get(pointer).undo();
        pointer++;
    }

    public void redo() {
        if (pointer == 0) return;
        pointer--;
        commands.get(pointer).redo();
    }
}
