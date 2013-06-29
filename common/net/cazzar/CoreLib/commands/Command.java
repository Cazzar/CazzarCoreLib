/**
 * 
 */
package net.cazzar.CoreLib.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * @author Cayde
 * 
 */
public abstract class Command implements ICommand {
	String	name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public int compareTo(ICommand par1ICommand) {
		return this.getCommandName().compareTo(par1ICommand.getCommandName());
	}
	
	public int compareTo(Object par1Obj) {
		return this.compareTo((ICommand) par1Obj);
	}
	
	@Override
	public String getCommandName() {
		return name;
	}
	
	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return null;
	}
	
	@Override
	public List getCommandAliases() {
		return null;
	}
	
	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] astring) {
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}
	
	public void register() {
		CommandHandler ch = (CommandHandler) MinecraftServer.getServer().getCommandManager();
        ch.registerCommand(this);
	}
	
}
