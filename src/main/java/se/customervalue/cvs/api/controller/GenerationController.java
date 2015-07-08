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

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public APIResponseRepresentation generateReportEndpoint(@RequestBody @Valid GennyRequestRepresentation report) throws CompanyNotFoundException, EmployeeNotFoundException, ProductNotFoundException, SalesDataNotFoundException, ReportGenerationException {
		return generationService.generate(report);
	}
}
