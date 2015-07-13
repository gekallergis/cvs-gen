package se.customervalue.cvs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.domain.Company;
import se.customervalue.cvs.domain.Employee;
import se.customervalue.cvs.domain.OwnedProduct;
import se.customervalue.cvs.domain.SalesData;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class GenerationServiceImpl implements GenerationService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private Company requestCompany;
	private Employee requestEmployee;
	private OwnedProduct requestOwnedProduct;
	private SalesData reportSalesData;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private OwnedProductRepository ownedProductRepository;

	@Autowired
	private SalesDataRepository salesDataRepository;

	@Autowired
	Map<String, ProductGenerator> productGenerators;

	@Override @Transactional
	public void generate(GennyRequestRepresentation request) throws CompanyNotFoundException, EmployeeNotFoundException, OwnedProductNotFoundException, SalesDataNotFoundException, UnknownProductTypeException {
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

		reportSalesData = salesDataRepository.findBySalesDataId(request.getSalesDataId());
		if(reportSalesData == null) {
			throw new SalesDataNotFoundException();
		}

		switch(requestOwnedProduct.getProduct().getType()) {
			case NEWBIZ:
				log.debug("[Generation Service] Requesting NewBiz report generation!");
				productGenerators.get("newBiz").start(request);
				break;
			case PREDICTIVE:
				log.debug("[Generation Service] Requesting Predictive report generation!");
				productGenerators.get("predictive").start(request);
				break;
			default:
				throw new UnknownProductTypeException();
		}
	}
}
