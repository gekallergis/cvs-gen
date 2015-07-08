package se.customervalue.cvs.service;

import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.APIResponseRepresentation;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

public interface GenerationService {
	APIResponseRepresentation generate(GennyRequestRepresentation report) throws CompanyNotFoundException, EmployeeNotFoundException, ProductNotFoundException, SalesDataNotFoundException, ReportGenerationException;
}
