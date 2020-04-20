package main.commands;

public interface Command {
    void execute();
    void undo();
    void redo();
}
