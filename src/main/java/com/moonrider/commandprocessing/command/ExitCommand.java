package com.moonrider.commandprocessing.command;

import com.moonrider.commandprocessing.CommandProcessor;

public class ExitCommand extends Command{

	private CommandProcessor receiver;
	
	public ExitCommand(CommandProcessor receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		receiver.exit();
	}

	@Override
	public String getName() {
		return CommandConfig.END;
	}


}
