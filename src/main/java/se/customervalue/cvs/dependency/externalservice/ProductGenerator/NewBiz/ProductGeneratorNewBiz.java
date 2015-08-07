package se.customervalue.cvs.dependency.externalservice.ProductGenerator.NewBiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.ExchangeRateService;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.exception.CalculationException;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.domain.*;

import java.util.List;

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
	private CompanyRepository companyRepository;

	@Autowired
	private ExchangeRateService fixer;

	@Override @Async
	public void start(GennyRequestRepresentation request, int newReportId) {
		try {
			log.debug("[NewBiz] Started generation!");

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
		} catch (Exception ex) {
			log.error("[NewBiz] Error Analyzing Data!");
			ex.printStackTrace();
			Report generatedReport = reportRepository.findByReportId(newReportId);
			generatedReport.setStatus(ReportStatus.ERROR);
			reportRepository.save(generatedReport);
		}
	}

	@Override
	public void calculate(GennyRequestRepresentation request) throws CalculationException {
		try {
			log.debug("[NewBiz] Calculating!");

			Company company = companyRepository.findByCompanyId(request.getCompanyId());
			String viewName = company.getName().replace(" ", "") + "Transactions";

			// Process ORD_MON
			List<Object[]> results = transactionRepository.getORD_MON(viewName);
			NewBizAnalysisData analysisData = new NewBizAnalysisData(results.size());
			analysisData.setOrd_mon(results);

			// Process ACK_OMS and ORD_OMS
			analysisData.setAck_omsOrd_oms(transactionRepository.getACK_OMS(viewName));

			// Process ACK_NYKUND, FIRST_OMS and NYKUND
			analysisData.setAck_nykundFirst_omsNykund(transactionRepository.getACK_NYKUND(viewName));

			// Process ACK_ANTTRANS and ANTTRANS
			analysisData.setAck_anttransAnttrans(transactionRepository.getACK_ANTTRANS(viewName));

			// TODO: Calculate AKTIVKUND

			// Process ACK_KUNDNUM and ANTKUND
			analysisData.setAck_kundnumAntkund(transactionRepository.getACK_KUNDNUM(viewName));

			// Process ACK_MIN_DATE
			analysisData.setAck_min_date(transactionRepository.getACK_MIN_DATE(viewName));

			// Process ACK_MAX_DATE
			analysisData.setAck_max_date(transactionRepository.getACK_MAX_DATE(viewName));

			// Process NYKUND_OMS
			analysisData.setNykund_oms(transactionRepository.getNYKUND_OMS(viewName));

			// Process OLDKUND_OMS
			analysisData.updateOldkund_oms();

			// Process NYKUND_REAL
			analysisData.setNykund_real(transactionRepository.getNYKUND_REAL(viewName));

			// Process NYKUND_NOLL
			analysisData.updateNykund_noll();

			// Process OLDKUND_REAL
			analysisData.setOldkund_real(transactionRepository.getOLDKUND_REAL(viewName));

			// Process OLDKUND_NOLL
			analysisData.setOldkund_noll(transactionRepository.getOLDKUND_NOLL(viewName));

			// Process OLDKUND
			analysisData.updateOldkund();

			// Process FIRSTTRANS
			analysisData.updateFirsttrans();

			// Process ANTRETUR
			analysisData.setAntretur(transactionRepository.getANTRETUR(viewName));

			// Process MIN_DATE
			analysisData.setMin_date(transactionRepository.getMIN_DATE(viewName));

			// Process MAX_DATE
			analysisData.setMax_date(transactionRepository.getMAX_DATE(viewName));

			// Process NYK_COHORT
			analysisData.updateNyk_cohort();

			// Process ORD_OMS0
			analysisData.updateOrd_oms0();

			// Process NYKUND_REAL0
			analysisData.updateNykund_real0();

			// Process NYKUND_NOLL0
			analysisData.updateNykund_noll0();

			// Process ANTTRANS0
			analysisData.updateAnttrans0();

			// Process ANTKUND0
			analysisData.updateAntkund0();

			// Process ANTRETUR0
			analysisData.setAntretur0(transactionRepository.getANTRETUR0(viewName));

			// TODO: Calculate ORD_OMS3
			// TODO: Calculate OLDKUND_REAL3
			// TODO: Calculate OLDKUND_NOLL3
			// TODO: Calculate ANTTRANS3
			// TODO: Calculate ANTRETUR3
			// TODO: Calculate ANTKUND3
			// TODO: Calculate ORD_OMS12
			// TODO: Calculate OLDKUND_REAL12
			// TODO: Calculate OLDKUND_NOLL12
			// TODO: Calculate ANTTRANS12
			// TODO: Calculate ANTRETUR12
			// TODO: Calculate ANTKUND12
			// TODO: Calculate ORD_OMS24
			// TODO: Calculate OLDKUND_REAL24
			// TODO: Calculate OLDKUND_NOLL24
			// TODO: Calculate ANTTRANS24
			// TODO: Calculate ANTRETUR24
			// TODO: Calculate ANTKUND24


//			res = transactionRepository.getACK_OMS("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ORD_OMS " + re[IDX.ORD_OMS]);
//				log.warn("[NewBiz] ACK_OMS " + re[IDX.ACK_OMS]);
//			}
//
//			res = transactionRepository.getORD_MON("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//			}
//
//			res = transactionRepository.getACK_ANTTRANS("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ANTTRANS " + re[IDX.ANTTRANS]);
//				log.warn("[NewBiz] ACK_ANTTRANS " + re[IDX.ACK_ANTTRANS]);
//			}
//
//			res = transactionRepository.getACK_KUNDNUM("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ANTKUND " + re[IDX.ANTKUND]);
//				log.warn("[NewBiz] ACK_KUNDNUM " + re[IDX.ACK_KUNDNUM]);
//			}
//
//			res = transactionRepository.getACK_MAX_DATE("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ACK_MAX_DATE_YEAR " + re[IDX.ACK_MAX_DATE_YEAR]);
//			}
//
//			res = transactionRepository.getACK_MIN_DATE("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] ACK_MIN_DATE_YEAR " + re[IDX.ACK_MIN_DATE_YEAR]);
//				log.warn("[NewBiz] ACK_MIN_DATE_MONTH " + re[IDX.ACK_MIN_DATE_MONTH]);
//				log.warn("[NewBiz] ACK_MIN_DATE_DAY " + re[IDX.ACK_MIN_DATE_DAY]);
//			}
//
//			res = transactionRepository.getACK_NYKUND("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ACK_NYKUND " + re[IDX.ACK_NYKUND]);
//				log.warn("[NewBiz] FIRST_OMS " + re[IDX.FIRST_OMS]);
//				log.warn("[NewBiz] NYKUND " + re[IDX.NYKUND]);
//			}
//
//			res = transactionRepository.getACK_OMS("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ORD_OMS " + re[IDX.ORD_OMS]);
//				log.warn("[NewBiz] ACK_OMS " + re[IDX.ACK_OMS]);
//			}
//
//			res = transactionRepository.getANTRETUR("CompanyATransactions");
//			for (Object[] re : res) {
//				log.warn("[NewBiz] Year " + re[IDX.YEAR]);
//				log.warn("[NewBiz] Month " + re[IDX.MONTH]);
//				log.warn("[NewBiz] ANTRETUR " + re[IDX.ANTRETUR]);
//			}
//
//			Currency reportCurrency = currencyRepository.findByCurrencyId(request.getCurrencyId());
//
//			BigDecimal sum = new BigDecimal("0.00");
//			final int pageLimit = 10;
//			int pageNumber = 0;
//
//			fixer.setBaseCurrency(reportCurrency);
//			Page<Transaction> page = transactionRepository.findAll(new PageRequest(pageNumber, pageLimit));
//			while (page.hasNext()) {
//				for (Transaction transaction : page.getContent()) {
//					sum = sum.add(fixer.convertToBase(transaction.getCurrency(), transaction.getAmount()));
//				}
//				page = transactionRepository.findAll(new PageRequest(++pageNumber, pageLimit));
//			}
//
//			// process last page
//			for (Transaction transaction : page.getContent()) {
//				sum = sum.add(fixer.convertToBase(transaction.getCurrency(), transaction.getAmount()));
//			}
//
//			log.warn("[NewBiz] Total is " + sum + " " + reportCurrency.getISO4217() + "!");
		} catch (Exception ex) {
			log.error("[NewBiz] Could not complete data analysis!");
			ex.printStackTrace();
			throw new CalculationException();
		}
	}
}
