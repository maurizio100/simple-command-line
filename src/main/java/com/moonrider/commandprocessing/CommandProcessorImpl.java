package com.moonrider.commandprocessing;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.moonrider.commandprocessing.command.Command;
import com.moonrider.commandprocessing.command.CommandConfig;
import com.moonrider.commandprocessing.command.ExitCommand;
import com.moonrider.common.ExitObserver;
import com.moonrider.common.ExitSender;
import com.moonrider.common.LocalMessageReceiver;
import com.moonrider.common.LocalMessageSender;
import com.moonrider.config.GlobalConfig;
import com.moonrider.exception.NotACommandException;
import com.moonrider.io.IOInstructionReceiver;
import com.moonrider.io.IOInstructionSender;
import com.moonrider.io.IOUnit;


public class CommandProcessorImpl 
implements CommandProcessor, LocalMessageReceiver, IOInstructionSender, ExitSender {

	/*--------------Communication----------------------*/
	private LocalMessageSender localMessenger = null;
	private IOInstructionReceiver ioReceiver = null;
	private List<ExitObserver> eObservers = new LinkedList<>();

	private String currentCommand = "";
	private String[] splittedString;

	private CommandRepository commandRepository;
	private Command[] clientCommands = {
			new ExitCommand(this)
	};
	
	/* ------------------- Constructors ------------------ */
	public CommandProcessorImpl(LocalMessageSender lmc) {
		localMessenger = lmc;
		localMessenger.registerMessageReceiver(this);
		commandRepository = new CommandRepository(clientCommands);
	}
	
	@Override
	public void receiveLocalMessage(String message) {
		if( !message.isEmpty() ){
			parseLocalMessage(message);
		}
	}

	private void parseLocalMessage(String message) {
		try {
			if( this.isCommand(message) ){
				parseCommand(message).orElseThrow(() ->
						new NotACommandException(GlobalConfig.INFOSTRING + " " + splittedString[CommandConfig.POSCOMMAND] + " is not a command!")
				).execute();
			}
		} catch(NotACommandException nae) {
			sendToIOUnit(nae.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			currentCommand = null;
		}
	}
	
	private boolean isCommand(String message) {
		if( message.length()  < 1 ) return false;
		return message.charAt(0) == CommandConfig.COMMANDNOTIFIER;
	}
	
	private Optional<Command> parseCommand(String command) throws NotACommandException{
		splittedString = command.split(CommandConfig.ARGSEPARATOR);
		currentCommand = command;
		
		return Optional.ofNullable(commandRepository.checkCommand(command));
	}

	/*------------------------IO-Unit--------------------------------_*/
	@Override
	public void registerIOReceiver(IOInstructionReceiver receiver) {
		ioReceiver = receiver;
	}

	private void sendToIOUnit( String message ){
		ioReceiver.processInstruction(message);
	}

	/* ---------- Exit management ------------ */
	@Override
	public void registerExitObserver(ExitObserver e) {
		eObservers.add(e);
	}

	@Override
	public void sendExit() {
		ExitObserver ioUnit = null;
		for( ExitObserver eo : eObservers){
			if( eo instanceof IOUnit ){ ioUnit = eo; }else{ eo.exit(); }
		}	
		ioUnit.exit();
	}

	@Override
	public void exit() {
		sendExit();
	}
}
