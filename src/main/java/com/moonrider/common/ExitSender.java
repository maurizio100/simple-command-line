package com.moonrider.common;

public interface ExitSender {

	public void registerExitObserver(ExitObserver e);
	public void sendExit();
	
}
