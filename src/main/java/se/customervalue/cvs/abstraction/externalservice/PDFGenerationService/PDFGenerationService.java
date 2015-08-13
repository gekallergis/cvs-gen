package se.customervalue.cvs.abstraction.externalservice.PDFGenerationService;

import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator.exception.PDFGenerationException;

public interface PDFGenerationService {
	void generateNewBizPDF(String[] charts, GennyRequestRepresentation options, int newReportId) throws PDFGenerationException;
}
