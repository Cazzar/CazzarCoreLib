/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.List;

/**
 * A base class for commands for simple creation of the command.
 */
public abstract class Command implements ICommand {
    private final String name;

    /**
     * Initializes the class
     *
     * @param name The name of the command
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

    /**
     * Get a list of the aliases to the command
     *
     * @return The command Aliases
     */

    @Override
    public List getCommandAliases() {
        return null;
    }

    /**
     * Check if the command can be used by the sender
     *
     * @return true if the given command sender is allowed to use this command.
     */
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     *
     * @param icommandsender the sender of the command
     * @param astring        the current params of the command
     *
     * @return the tab completion list of the command
     */
    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
        return null;
    }

    /**
     * @param astring the current params of the command
     * @param i       the index currently working on
     *
     * @return whether the specified command parameter index is a username parameter.
     */
    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }
}
