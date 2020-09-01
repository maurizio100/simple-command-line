package com.moonrider.exception;

public class NotACommandException extends Exception {

	private String errMessage;
	
	public NotACommandException( String errMessage ){
		this.errMessage = errMessage;
	}
	
	@Override
	public String getMessage(){
		return errMessage;
	}
	
}
