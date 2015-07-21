package se.customervalue.cvs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.domain.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service
public class GenerationServiceImpl implements GenerationService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private OwnedProductRepository ownedProductRepository;

	@Autowired
	private SalesDataRepository salesDataRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	Map<String, ProductGenerator> productGenerators;

	@Override @Transactional
	public void generate(GennyRequestRepresentation request) throws CompanyNotFoundException, EmployeeNotFoundException, OwnedProductNotFoundException, SalesDataNotFoundException, UnknownProductTypeException {
		Company requestCompany;
		Employee requestEmployee;
		OwnedProduct requestOwnedProduct;
		SalesData requestSalesData;

		requestCompany = companyRepository.findByCompanyId(request.getCompanyId());
		if(requestCompany == null) {
			throw new CompanyNotFoundException();
		}

		requestEmployee = employeeRepository.findByEmployeeId(request.getEmployeeId());
		if(requestEmployee == null) {
			throw new EmployeeNotFoundException();
		}

		requestOwnedProduct = ownedProductRepository.findByOwnedProductId(request.getOwnedProductId());
		if(requestOwnedProduct == null) {
			throw new OwnedProductNotFoundException();
		}

		requestSalesData = salesDataRepository.findBySalesDataId(request.getSalesDataId());
		if(requestSalesData == null) {
			throw new SalesDataNotFoundException();
		}

		// Create new report in DB with status set to Generating!
		Report newReport = new Report(new Date(), "", ReportStatus.GENERATING);
		newReport.setReporter(requestEmployee);
		requestEmployee.getGeneratedReports().add(newReport);
		newReport.setCompany(requestCompany);
		requestCompany.getReports().add(newReport);
		newReport.setProduct(requestOwnedProduct.getProduct());
		newReport.setSalesData(requestSalesData);
		requestSalesData.getReports().add(newReport);
		reportRepository.save(newReport);
		companyRepository.save(requestCompany);
		employeeRepository.save(requestEmployee);
		ownedProductRepository.save(requestOwnedProduct);
		salesDataRepository.save(requestSalesData);

		switch(requestOwnedProduct.getProduct().getType()) {
			case NEWBIZ:
				log.debug("[Generation Service] Requesting NewBiz report generation!");
				productGenerators.get("newBiz").start(request, newReport.getReportId());
				break;
			case PREDICTIVE:
				log.debug("[Generation Service] Requesting Predictive report generation!");
				productGenerators.get("predictive").start(request, newReport.getReportId());
				break;
			default:
				throw new UnknownProductTypeException();
		}
	}
}
