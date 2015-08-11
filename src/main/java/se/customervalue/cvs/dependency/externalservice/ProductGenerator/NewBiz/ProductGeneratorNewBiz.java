package se.customervalue.cvs.dependency.externalservice.ProductGenerator.NewBiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.*;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.ExchangeRateService;
import se.customervalue.cvs.abstraction.externalservice.GraphGenerationService.GraphGenerationService;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.AnalysisData;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.exception.CalculationException;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.domain.*;

import java.awt.*;
import java.util.ArrayList;
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
	private CompanyRepository companyRepository;

	@Autowired
	private ExchangeRateService fixer;

	@Autowired
	private GraphGenerationService jfchart;

	@Override @Async
	public void start(GennyRequestRepresentation request, int newReportId) {
		try {
			log.debug("[NewBiz] Started generation!");

			// Perform analysis
			AnalysisDataNewBiz analysisData = (AnalysisDataNewBiz)calculate(request);

			// Generate graphs
			List<Color> colors = new ArrayList<>();
			Color colorBlue = new Color(83, 130, 187);
			Color colorRed = new Color(189, 80, 79);
			Color colorYellow = new Color(252, 191, 42);
			Color colorGreen = new Color(155, 186, 94);
			Color colorPurple = new Color(128, 101, 159);
			colors.add(colorRed);
			colors.add(colorGreen);
			colors.add(colorPurple);
			colors.add(colorBlue);
			colors.add(colorYellow);

			// Chart #1 - Number of unique customers that have purchased per month
			String chartTitle = "Antal unika köpande kunder per månad";
			String xAxisTitle = "Μånad";
			String yAxisTitle = "Antal unika kundnummer";
			String[] rowKeys = {"Antal nya kunder", "Antal återkommande kunder"};
			String[] columnKeys = analysisData.getPrettyMonthList();
			double[][] plotData = new double[2][];
			plotData[0] = analysisData.getNykundAsDouble();
			plotData[1] = analysisData.getOldkundAsDouble();

			String chartfileName = jfchart.createStackedBarChart(chartTitle, xAxisTitle, yAxisTitle, rowKeys, columnKeys, plotData, colors);

			// TODO: Chart #2 - Proportion new customers that have purchased per month

			// TODO: Chart #3 - Sales per month

			// TODO: Chart #4 - The new customer’s share of the month's sales

			// TODO: Chart #5 - Average sales per customer for the new and recurring customers

			// TODO: Chart #6 - Proportion of the new customers that have repurchased (once or more) within 3, 12 and 24 months, and number of new customers

			// TODO: Chart #7 - Repurchase (accumulative) percentage of initial sales after 3, 12 and 24 months, and the initial sales per new customer group

			// TODO: Chart #8 - Average number of purchases per new customer, initial and accumulated over 3, 12 and 24 months

			// TODO: Chart #9 - Average sales of purchases per new customers, initially and accumulated over 3, 12 and 24 months

//			String[] rowKeys = {"Antal nya kunder", "Antal återkommande kunder", "Antal återkommande kunder 2", "Antal återkommande kunder 3"};
//
//			double[] data = {1.2f, 2.3f, 1.8f, 2.0f};
//			String name = jfchart.createBarChart("Test Chart", "X Axis Test Title", "Y Axis Test Title", rowKeys[0], rowKeys, data, colorBlue);
//			log.warn("[DE] " + name);
//
//			double[][] data2D = {{1.2f, 2.3f, 1.8f, 2.0f}, {2.2f, 1.3f, 2.8f, 1.0f}};
//
//			name = jfchart.createDualLineBarChart("Test Chart", "X Axis Test Title", "Y1 Axis Test Title", "Y2 Axis Test Title", rowKeys, rowKeys, data2D, colors);
//			log.warn("[DE] " + name);
//
//			name = jfchart.createLineChart("Test Chart", "X Axis Test Title", "Y1 Axis Test Title", rowKeys, rowKeys, data2D, colors);
//			log.warn("[DE] " + name);
//
//			double[][] data2Dn = {{1.2f, 2.3f, 1.8f, 2.0f}, {2.2f, 1.3f, 2.8f, 1.0f}, {3.2f, 5.3f, 3.8f, 4.0f}, {1.2f, 4.3f, 6.8f, 7.0f}};
//			name = jfchart.createStackedBarChart("Test Chart", "X Axis Test Title", "Y1 Axis Test Title", rowKeys, rowKeys, data2Dn, colors);
//			log.warn("[DE] " + name);

			// TODO: Generate PDF

			// TODO: Cleanup temp files

			// Update report status to Ready and reduce owned products
			Report generatedReport = reportRepository.findByReportId(newReportId);
			generatedReport.setStatus(ReportStatus.READY);
			reportRepository.save(generatedReport);

			OwnedProduct requestOwnedProduct = ownedProductRepository.findByOwnedProductId(request.getOwnedProductId());
			requestOwnedProduct.setQuantity(requestOwnedProduct.getQuantity() - 1);
			ownedProductRepository.save(requestOwnedProduct);

			log.debug("[NewBiz] Generation Complete!");
		} catch (Exception ex) {
			log.error("[NewBiz] Error Generating Report!");
			ex.printStackTrace();
			Report generatedReport = reportRepository.findByReportId(newReportId);
			generatedReport.setStatus(ReportStatus.ERROR);
			reportRepository.save(generatedReport);
		}
	}

	@Override
	public AnalysisData calculate(GennyRequestRepresentation request) throws CalculationException {
		try {
			log.debug("[NewBiz] Calculating!");

			Company company = companyRepository.findByCompanyId(request.getCompanyId());
			String viewName = company.getName().replace(" ", "") + "Transactions";

			// Process ORD_MON
			List<Object[]> results = transactionRepository.getORD_MON(viewName);
			AnalysisDataNewBiz analysisData = new AnalysisDataNewBiz(results.size());
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

			// Process ORD_OMS3
			analysisData.setOrd_oms3(transactionRepository.getORD_OMSN(viewName, "3"));

			// Process OLDKUND_REAL3
			analysisData.setOldkund_real3(transactionRepository.getOLDKUND_REALN(viewName, "3"));

			// Process OLDKUND_NOLL3
			analysisData.setOldkund_noll3(transactionRepository.getOLDKUND_NOLLN(viewName, "3"));

			// Process ANTTRANS3
			analysisData.setAnttrans3(transactionRepository.getANTTRANSN(viewName, "3"));

			// Process ANTRETUR3
			analysisData.setAntretur3(transactionRepository.getANTRETURN(viewName, "3"));

			// Process ANTKUND3
			analysisData.updateAntkund3();

			// Process ORD_OMS12
			analysisData.setOrd_oms12(transactionRepository.getORD_OMSN(viewName, "12"));

			// Process OLDKUND_REAL12
			analysisData.setOldkund_real12(transactionRepository.getOLDKUND_REALN(viewName, "12"));

			// Process OLDKUND_NOLL12
			analysisData.setOldkund_noll12(transactionRepository.getOLDKUND_NOLLN(viewName, "12"));

			// Process ANTTRANS12
			analysisData.setAnttrans12(transactionRepository.getANTTRANSN(viewName, "12"));

			// Process ANTRETUR12
			analysisData.setAntretur12(transactionRepository.getANTRETURN(viewName, "12"));

			// Process ANTKUND12
			analysisData.updateAntkund12();

			// Process ORD_OMS24
			analysisData.setOrd_oms24(transactionRepository.getORD_OMSN(viewName, "24"));

			// Process OLDKUND_REAL24
			analysisData.setOldkund_real24(transactionRepository.getOLDKUND_REALN(viewName, "24"));

			// Process OLDKUND_NOLL24
			analysisData.setOldkund_noll24(transactionRepository.getOLDKUND_NOLLN(viewName, "24"));

			// Process ANTTRANS24
			analysisData.setAnttrans24(transactionRepository.getANTTRANSN(viewName, "24"));

			// Process ANTRETUR24
			analysisData.setAntretur24(transactionRepository.getANTRETURN(viewName, "24"));

			// Process ANTKUND24
			analysisData.updateAntkund24();

			return analysisData;
		} catch (Exception ex) {
			log.error("[NewBiz] Could not complete data analysis!");
			ex.printStackTrace();
			throw new CalculationException();
		}
	}
}
