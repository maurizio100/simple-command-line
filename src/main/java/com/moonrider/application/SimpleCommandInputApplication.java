package com.moonrider.application;

import com.moonrider.commandprocessing.CommandProcessorImpl;
import com.moonrider.communication.LocalMessageController;
import com.moonrider.io.IOUnit;

public class SimpleCommandInputApplication {

	public static final String WELCOME = "This is the simple Command input Application. Feel free to extend it!\n"
			+ "With this it is possible to test multiple parts of an application and play around with.";

	public static void main(String[] args) {
		LocalMessageController lmc = new LocalMessageController();
		CommandProcessorImpl model = new CommandProcessorImpl(lmc);
		new IOUnit(lmc, model, model, WELCOME);
	}
}
