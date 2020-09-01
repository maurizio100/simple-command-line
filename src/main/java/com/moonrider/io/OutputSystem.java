package com.moonrider.io;

import java.io.PrintWriter;

import com.moonrider.config.IOUnitConfig;

public class OutputSystem {

	private PrintWriter out = null;
	
	public OutputSystem(String welcomeMessage){
		out = new PrintWriter(System.out);
		out.println(welcomeMessage);
		out.print("> ");
		out.flush();
	}
	
	public void receiveInstruction(String instruction) {
		out.println(instruction);
		out.print("> ");
		out.flush();
	}

	public void closeOutput() {
		out.println(IOUnitConfig.OUTPUTSALUTATION);
		out.close();
	}
	
}
