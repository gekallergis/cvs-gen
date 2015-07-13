package se.customervalue.cvs.service;

import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

public interface GenerationService {
	void generate(GennyRequestRepresentation request) throws CompanyNotFoundException, EmployeeNotFoundException, OwnedProductNotFoundException, SalesDataNotFoundException, UnknownProductTypeException;
}
