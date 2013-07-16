package net.cazzar.corelib.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.List;

/**
 * User: Cayde
 */
public abstract class Command implements ICommand {
    private final String name;

    /**
     * Initializes the {@link Command} class
     *
     * @param name
     */
    public Command(String name) {
        this.name = name;
    }

    /**
     * Gets the command name
     *
     * @return the command name
     */
    @Override
    public final String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/" + name;
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }
}
