package se.customervalue.cvs.dependency.externalservice.ProductGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.ExchangeRateService;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.exception.ExchangeRateException;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.exception.CalculationException;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.domain.*;

import java.math.BigDecimal;

@Service("newBiz")
public class ProductGeneratorNewBiz implements ProductGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private OwnedProductRepository ownedProductRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private ExchangeRateService fixer;

	@Override @Async @Transactional
	public void start(GennyRequestRepresentation request, int newReportId) {
		try {
			log.warn("[NewBiz] Started generation!");

			// TODO: Perform analysis
			calculate(request);

			// TODO: Generate graphs
			// TODO: Generate PDF

			// Update report status to Ready and reduce owned products
			Report generatedReport = reportRepository.findByReportId(newReportId);
			generatedReport.setStatus(ReportStatus.READY);
			reportRepository.save(generatedReport);

			OwnedProduct requestOwnedProduct = ownedProductRepository.findByOwnedProductId(request.getOwnedProductId());
			requestOwnedProduct.setQuantity(requestOwnedProduct.getQuantity() - 1);
			ownedProductRepository.save(requestOwnedProduct);
		} catch (CalculationException ce) {
			log.error("[NewBiz] Error Analyzing Data!");
			Report generatedReport = reportRepository.findByReportId(newReportId);
			generatedReport.setStatus(ReportStatus.ERROR);
			reportRepository.save(generatedReport);
		}
	}

	@Override
	public void calculate(GennyRequestRepresentation request) throws CalculationException {
		try {
			log.warn("[NewBiz] Calculating!");

			Currency reportCurrency = currencyRepository.findByCurrencyId(request.getCurrencyId());

			BigDecimal sum = new BigDecimal("0.00");
			final int pageLimit = 10;
			int pageNumber = 0;

			fixer.setBaseCurrency(reportCurrency);
			Page<Transaction> page = transactionRepository.findAll(new PageRequest(pageNumber, pageLimit));
			while (page.hasNext()) {
				for (Transaction transaction : page.getContent()) {
					sum = sum.add(fixer.convertToBase(transaction.getCurrency(), transaction.getAmount()));
				}
				page = transactionRepository.findAll(new PageRequest(++pageNumber, pageLimit));
			}

			// process last page
			for (Transaction transaction : page.getContent()) {
				sum = sum.add(fixer.convertToBase(transaction.getCurrency(), transaction.getAmount()));
			}

			log.warn("[NewBiz] Total is " + sum + " " + reportCurrency.getISO4217() + "!");
		} catch (ExchangeRateException ere) {
			log.error("[NewBiz] Could not convert currencies!");
			throw new CalculationException();
		}
	}
}
