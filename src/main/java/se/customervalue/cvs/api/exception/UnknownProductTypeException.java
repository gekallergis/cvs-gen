package se.customervalue.cvs.api.exception;

public class UnknownProductTypeException extends Exception {
	public UnknownProductTypeException() {}

	public UnknownProductTypeException(String message)
	{
		super(message);
	}
}
