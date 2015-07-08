package se.customervalue.cvs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.api.exception.*;
import se.customervalue.cvs.api.representation.APIResponseRepresentation;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

import javax.transaction.Transactional;

@Service
public class GenerationServiceImpl implements GenerationService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	private SalesDataRepository salesDataRepository;
//
//	@Autowired
//	private TransactionRepository transactionRepository;

	@Override @Transactional
	public APIResponseRepresentation generate(GennyRequestRepresentation report) throws CompanyNotFoundException, EmployeeNotFoundException, ProductNotFoundException, SalesDataNotFoundException, ReportGenerationException {
		log.warn("[Genny] Generating!");

//		Instant start = Instant.now();
//		System.out.println("COUNT: " + transactionRepository.countBySalesDataBatch(salesDataRepository.findBySalesDataId(2)));
//		Instant end = Instant.now();
//		System.out.println(Duration.between(start, end).getNano() / 1000000 + "ms");
//
//		final int pageLimit = 10;
//		int pageNumber = 0;
//		Page<Transaction> page = transactionRepository.findAll(new PageRequest(pageNumber, pageLimit));
//		while (page.hasNext()) {
//			processPageContent(page.getContent());
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			page = transactionRepository.findAll(new PageRequest(++pageNumber, pageLimit));
//		}
//		// process last page
//		processPageContent(page.getContent());

		return new APIResponseRepresentation("000", "Genny here!");
	}

//	private void processPageContent(List<Transaction> list) {
//		for (Transaction transaction : list) {
//			System.out.println(transaction.getAmount());
//		}
//	}
}
