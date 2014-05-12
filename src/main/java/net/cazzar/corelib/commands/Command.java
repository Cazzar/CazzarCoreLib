/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
