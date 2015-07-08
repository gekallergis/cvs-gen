package se.customervalue.cvs.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.APIResponseRepresentation;

@ControllerAdvice
@ResponseBody
public class APIErrorHandler {

	@ExceptionHandler(CompanyNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponseRepresentation companyNotFoundExceptionHandler(CompanyNotFoundException ex) {
		return  new APIResponseRepresentation("G00", "The requested company was not found! Cannot generate report!");
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponseRepresentation employeeNotFoundExceptionHandler(EmployeeNotFoundException ex) {
		return  new APIResponseRepresentation("G01", "The requested employee was not found! Cannot generate report!");
	}

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponseRepresentation productNotFoundExceptionHandler(ProductNotFoundException ex) {
		return  new APIResponseRepresentation("G02", "The requested product was not found! Cannot generate report!");
	}

	@ExceptionHandler(SalesDataNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponseRepresentation salesDataNotFoundExceptionHandler(SalesDataNotFoundException ex) {
		return  new APIResponseRepresentation("G03", "The requested batch of transactions was not found! Cannot generate report!");
	}

	@ExceptionHandler(ReportGenerationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponseRepresentation reportGenerationExceptionHandler(ReportGenerationException ex) {
		return  new APIResponseRepresentation("G04", "There was an error while generating the report! Generation failed!");
	}
}
