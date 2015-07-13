package se.customervalue.cvs.api.exception;

public class OwnedProductNotFoundException extends Exception {
	public OwnedProductNotFoundException() {}

	public OwnedProductNotFoundException(String message)
	{
		super(message);
	}
}
