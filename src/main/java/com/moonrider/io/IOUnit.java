package com.moonrider.io;

import com.moonrider.common.ExitObserver;
import com.moonrider.common.ExitSender;
import com.moonrider.common.LocalMessageReceiver;

public class IOUnit implements IOInstructionReceiver, ExitObserver{

	private final LocalMessageReceiver localMessenger;
	private final IOInstructionSender clientModel;
	private final OutputSystem output;
	private final InputSystem input;

	public IOUnit(LocalMessageReceiver rcv, IOInstructionSender instructionSender, ExitSender s, String welcomeMessage) {
		localMessenger = rcv;
		clientModel = instructionSender;
		clientModel.registerIOReceiver(this);
		s.registerExitObserver(this);

		output = new OutputSystem(welcomeMessage);
		input = new InputSystem( this );
	}

	@Override
	public void processInstruction(String instruction) {
		sendToOutput(instruction);
	}

	private void sendToOutput(String instruction) {
		output.receiveInstruction( instruction );
	}

	public void sendMessage(String message) {
		sendToLocalMessenger(message);
	}

	private void sendToLocalMessenger(String message) {
		localMessenger.receiveLocalMessage(message);
	}

	@Override
	public void exit() {
		input.closeInput();
		output.closeOutput();
	}

	@Override
	public String performInput() {
		return input.getInput();
	}

}
