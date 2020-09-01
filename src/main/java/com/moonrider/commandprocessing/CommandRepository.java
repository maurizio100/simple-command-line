package com.moonrider.commandprocessing;

import java.util.HashMap;

import com.moonrider.commandprocessing.command.Command;


public class CommandRepository {

	private HashMap<String, Command> repository = null;
	
	private CommandRepository(){
		repository = new HashMap<String, Command>();
	}
	
	public CommandRepository(Command command){
		this();
		createRepository(command);
	}
	
	public CommandRepository( Command[] commandList ){
		this();
		createRepository(commandList);
	}
	
	private void createRepository(Command command){
		repository.put(command.getName(), command);
	}
	
	private void createRepository(Command[] command){
		for( Command c : command ){
			createRepository(c);
		}
	}
	
	public Command checkCommand(String command){
		String[] splittedString = command.split("!");
		return repository.get(splittedString[1]);
	}

}
