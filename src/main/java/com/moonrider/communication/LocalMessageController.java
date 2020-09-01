package com.moonrider.communication;

import com.moonrider.common.LocalMessageReceiver;
import com.moonrider.common.LocalMessageSender;


public class LocalMessageController
implements LocalMessageReceiver, LocalMessageSender{

	private LocalMessageReceiver clientModel;
	
	@Override
	public void registerMessageReceiver(LocalMessageReceiver receiver) {
		clientModel = receiver;
	}

	@Override
	public void receiveLocalMessage(String message) {
		clientModel.receiveLocalMessage(message);
	}


}
