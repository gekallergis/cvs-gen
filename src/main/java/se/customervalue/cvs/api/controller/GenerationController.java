package se.customervalue.cvs.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.APIResponseRepresentation;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.service.GenerationService;

import javax.validation.Valid;

@RestController
public class GenerationController {
	@Autowired
	private GenerationService generationService;

	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public APIResponseRepresentation generateReportEndpoint(@RequestBody @Valid GennyRequestRepresentation request) throws CompanyNotFoundException, EmployeeNotFoundException, OwnedProductNotFoundException, SalesDataNotFoundException, ReportGenerationException, UnknownProductTypeException {
		generationService.generate(request);
		return new APIResponseRepresentation("020", "Report generation started successfully!");
	}
}
