package se.customervalue.cvs.dependency.externalservice.ProductGenerator.NewBiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.ExchangeRateService;
import se.customervalue.cvs.abstraction.externalservice.ExchangeRateService.exception.ExchangeRateException;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.AnalysisData;
import se.customervalue.cvs.domain.Currency;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;


public class AnalysisDataNewBiz extends AnalysisData {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static final int THREE_MONTH_PERIOD = 0;
	public static final int TWELVE_MONTH_PERIOD = 1;
	public static final int TWENTY_FOUR_MONTH_PERIOD = 2;
	public static final int INITIAL_PERIOD = 3;

	private Map<Date, Integer> ord_mon;
	private BigDecimal[] ack_oms;
	private int[] ack_nykund;
	private int[] ack_anttrans;
	private int[] aktivkund;
	private int[] ack_kundnum;
	private Date[] ack_min_date;
	private Date[] ack_max_date;
	private BigDecimal[] ord_oms;
	private BigDecimal[] first_oms;
	private BigDecimal[] nykund_oms;
	private BigDecimal[] oldkund_oms;
	private int[] nykund;
	private int[] nykund_real;
	private int[] nykund_noll;
	private int[] oldkund;
	private int[] oldkund_real;
	private int[] oldkund_noll;
	private int[] anttrans;
	private int[] firsttrans;
	private int[] antretur;
	private int[] antkund;
	private Date[] min_date;
	private Date[] max_date;
	private Date[] nyk_cohort;
	private BigDecimal[] ord_oms0;
	private int[] nykund_real0;
	private int[] nykund_noll0;
	private int[] anttrans0;
	private int[] antretur0;
	private int[] antkund0;
	private BigDecimal[] ord_oms3;
	private int[] oldkund_real3;
	private int[] oldkund_noll3;
	private int[] anttrans3;
	private int[] antretur3;
	private int[] antkund3;
	private BigDecimal[] ord_oms12;
	private int[] oldkund_real12;
	private int[] oldkund_noll12;
	private int[] anttrans12;
	private int[] antretur12;
	private int[] antkund12;
	private BigDecimal[] ord_oms24;
	private int[] oldkund_real24;
	private int[] oldkund_noll24;
	private int[] anttrans24;
	private int[] antretur24;
	private int[] antkund24;

	public AnalysisDataNewBiz(int size) {
		this.ord_mon = new HashMap<>(size);
		this.ack_oms = new BigDecimal[size];
		Arrays.fill(this.ack_oms, new BigDecimal("0.00"));
		this.ack_nykund = new int[size];
		Arrays.fill(this.ack_nykund, 0);
		this.ack_anttrans = new int[size];
		Arrays.fill(this.ack_anttrans, 0);
		this.aktivkund = new int[size];
		Arrays.fill(this.aktivkund, 0);
		this.ack_kundnum = new int[size];
		Arrays.fill(this.ack_kundnum, 0);
		this.ack_min_date = new Date[size];
		this.ack_max_date = new Date[size];
		this.ord_oms = new BigDecimal[size];
		Arrays.fill(this.ord_oms, new BigDecimal("0.00"));
		this.first_oms = new BigDecimal[size];
		Arrays.fill(this.first_oms, new BigDecimal("0.00"));
		this.nykund_oms = new BigDecimal[size];
		Arrays.fill(this.nykund_oms, new BigDecimal("0.00"));
		this.oldkund_oms = new BigDecimal[size];
		Arrays.fill(this.oldkund_oms, new BigDecimal("0.00"));
		this.nykund = new int[size];
		Arrays.fill(this.nykund, 0);
		this.nykund_real = new int[size];
		Arrays.fill(this.nykund_real, 0);
		this.nykund_noll = new int[size];
		Arrays.fill(this.nykund_noll, 0);
		this.oldkund = new int[size];
		Arrays.fill(this.oldkund, 0);
		this.oldkund_real = new int[size];
		Arrays.fill(this.oldkund_real , 0);
		this.oldkund_noll = new int[size];
		Arrays.fill(this.oldkund_noll, 0);
		this.anttrans = new int[size];
		Arrays.fill(this.anttrans , 0);
		this.firsttrans = new int[size];
		Arrays.fill(this.firsttrans, 0);
		this.antretur = new int[size];
		Arrays.fill(this.antretur, 0);
		this.antkund = new int[size];
		Arrays.fill(this.antkund, 0);
		this.min_date = new Date[size];
		this.max_date = new Date[size];
		this.nyk_cohort = new Date[size];
		this.ord_oms0 = new BigDecimal[size];
		Arrays.fill(this.ord_oms0 , new BigDecimal("0.00"));
		this.nykund_real0 = new int[size];
		Arrays.fill(this.nykund_real0, 0);
		this.nykund_noll0 = new int[size];
		Arrays.fill(this.nykund_noll0, 0);
		this.anttrans0 = new int[size];
		Arrays.fill(this.anttrans0, 0);
		this.antretur0 = new int[size];
		Arrays.fill(this.antretur0, 0);
		this.antkund0 = new int[size];
		Arrays.fill(this.antkund0, 0);
		this.ord_oms3 = new BigDecimal[size];
		Arrays.fill(this.ord_oms3, new BigDecimal("0.00"));
		this.oldkund_real3 = new int[size];
		Arrays.fill(this.oldkund_real3, 0);
		this.oldkund_noll3 = new int[size];
		Arrays.fill(this.oldkund_noll3, 0);
		this.anttrans3 = new int[size];
		Arrays.fill(this.anttrans3, 0);
		this.antretur3 = new int[size];
		Arrays.fill(this.antretur3, 0);
		this.antkund3 = new int[size];
		Arrays.fill(this.antkund3, 0);
		this.ord_oms12 = new BigDecimal[size];
		Arrays.fill(this.ord_oms12, new BigDecimal("0.00"));
		this.oldkund_real12 = new int[size];
		Arrays.fill(this.oldkund_real12, 0);
		this.oldkund_noll12 = new int[size];
		Arrays.fill(this.oldkund_noll12, 0);
		this.anttrans12 = new int[size];
		Arrays.fill(this.anttrans12, 0);
		this.antretur12 = new int[size];
		Arrays.fill(this.antretur12, 0);
		this.antkund12 = new int[size];
		Arrays.fill(this.antkund12, 0);
		this.ord_oms24 = new BigDecimal[size];
		Arrays.fill(this.ord_oms24, new BigDecimal("0.00"));
		this.oldkund_real24 = new int[size];
		Arrays.fill(this.oldkund_real24, 0);
		this.oldkund_noll24 = new int[size];
		Arrays.fill(this.oldkund_noll24, 0);
		this.anttrans24 = new int[size];
		Arrays.fill(this.anttrans24, 0);
		this.antretur24 = new int[size];
		Arrays.fill(this.antretur24, 0);
		this.antkund24 = new int[size];
		Arrays.fill(this.antkund24, 0);
	}

	public void setOrd_mon(List<Object[]> dbData) {
		int i = 0;
		for (Object[] row : dbData) {
			ord_mon.put(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]), i++);
		}
	}

	public void setAck_omsOrd_oms(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			ack_oms[i] = new BigDecimal((double)row[IDX.ACK_OMS]).setScale(2, BigDecimal.ROUND_HALF_UP);
			ord_oms[i] = (BigDecimal)row[IDX.ORD_OMS];
		}
	}

	public void setAck_nykundFirst_omsNykund(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			ack_nykund[i] = ((Double)row[IDX.ACK_NYKUND]).intValue();
			first_oms[i] = (BigDecimal)row[IDX.FIRST_OMS];
			nykund[i] = ((BigInteger)row[IDX.NYKUND]).intValue();
		}
	}

	public void setAck_anttransAnttrans(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			anttrans[i] = ((BigInteger)row[IDX.ANTTRANS]).intValue();
			ack_anttrans[i] = ((Double)row[IDX.ACK_ANTTRANS]).intValue();
		}
	}

	public void setAck_kundnumAntkund(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			antkund[i] = ((BigInteger)row[IDX.ANTKUND]).intValue();
			ack_kundnum[i] = ((Double)row[IDX.ACK_KUNDNUM]).intValue();
		}
	}

	public void setAck_min_date(List<Object[]> dbData) {
		Date date = toDate(((BigInteger) dbData.get(0)[IDX.ACK_MIN_DATE_YEAR]).intValue(), ((BigInteger) dbData.get(0)[IDX.ACK_MIN_DATE_MONTH]).intValue(), ((BigInteger) dbData.get(0)[IDX.ACK_MIN_DATE_DAY]).intValue());
		for (int i = 0; i < ack_min_date.length; i++) {
			ack_min_date[i] = date;
		}
	}

	public void setAck_max_date(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			ack_max_date[i] = toDate(((BigInteger) row[IDX.ACK_MAX_DATE_YEAR]).intValue(), ((BigInteger) row[IDX.ACK_MAX_DATE_MONTH]).intValue(), ((BigInteger) row[IDX.ACK_MAX_DATE_DAY]).intValue());
		}
	}

	public void setNykund_oms(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			nykund_oms[i] = (BigDecimal)row[IDX.NYKUND_OMS];
		}
	}

	public void updateOldkund_oms() {
		for(int i = 0; i < oldkund_oms.length; i++) {
			oldkund_oms[i] = ord_oms[i].subtract(nykund_oms[i]);
		}
	}

	public void setNykund_real(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			nykund_real[i] = ((BigInteger)row[IDX.NYKUND_REAL]).intValue();
		}
	}

	public void updateNykund_noll() {
		for(int i = 0; i < nykund_noll.length; i++) {
			nykund_noll[i] = nykund[i] - nykund_real[i];
		}
	}

	public void setOldkund_real(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			oldkund_real[i] = ((BigInteger)row[IDX.OLDKUND_REAL]).intValue();
		}
	}

	public void setOldkund_noll(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			oldkund_noll[i] = ((BigInteger)row[IDX.OLDKUND_NOLL]).intValue();
		}
	}

	public void updateOldkund() {
		for(int i = 0; i < oldkund.length; i++) {
			oldkund[i] = oldkund_real[i] + oldkund_noll[i];
		}
	}

	public void updateFirsttrans() {
		for(int i = 0; i < firsttrans.length; i++) {
			firsttrans[i] = nykund[i];
		}
	}

	public void setAntretur(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			antretur[i] = ((BigInteger)row[IDX.ANTRETUR]).intValue();
		}
	}

	public void setMin_date(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			min_date[i] = toDate(((BigInteger) row[IDX.MIN_DATE_YEAR]).intValue(), ((BigInteger) row[IDX.MIN_DATE_MONTH]).intValue(), ((BigInteger) row[IDX.MIN_DATE_DAY]).intValue());
		}
	}

	public void updateNyk_cohort() {
		ord_mon.forEach((date, index) -> nyk_cohort[index.intValue()] = date);
	}

	public void updateOrd_oms0() {
		for(int i = 0; i < ord_oms0.length; i++) {
			ord_oms0[i] = first_oms[i];
		}
	}

	public void updateNykund_real0() {
		for(int i = 0; i < nykund_real0.length; i++) {
			nykund_real0[i] = nykund_real[i];
		}
	}

	public void updateNykund_noll0() {
		for(int i = 0; i < nykund_noll0.length; i++) {
			nykund_noll0[i] = nykund_noll[i];
		}
	}

	public void updateAnttrans0() {
		for(int i = 0; i < anttrans0.length; i++) {
			anttrans0[i] = firsttrans[i];
		}
	}

	public void updateAntkund0() {
		for(int i = 0; i < antkund0.length; i++) {
			antkund0[i] = firsttrans[i];
		}
	}

	public void setMax_date(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate((int) row[IDX.YEAR], (int) row[IDX.MONTH]));
			max_date[i] = toDate(((BigInteger) row[IDX.MAX_DATE_YEAR]).intValue(), ((BigInteger) row[IDX.MAX_DATE_MONTH]).intValue(), ((BigInteger) row[IDX.MAX_DATE_DAY]).intValue());
		}
	}

	public void setAntretur0(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((BigInteger)row[IDX.YEAR]).intValue(), ((BigInteger)row[IDX.MONTH]).intValue()));
			String[] splitAmounts = ((String)row[IDX.ANTRETUR0_AMOUNTS]).split(",");
			//log.warn("[] " + (String)row[IDX.ANTRETUR0_AMOUNTS]);
			BigDecimal[] amounts = convertAmounts(splitAmounts);
			if(initTransactionHasReturns(amounts)) {
				antretur0[i]++;
			}
		}
	}

	public void setOrd_oms3(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			ord_oms3[i] = ((BigDecimal)row[IDX.ORD_OMSN]);
		}
		zeroNonApplicableDates(3, ord_oms3);
	}

	public void setOldkund_real3(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_real3[i] = ((BigInteger)row[IDX.OLDKUND_REALN]).intValue();
		}
		zeroNonApplicableDates(3, oldkund_real3);
	}

	public void setOldkund_noll3(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_noll3[i] = ((int)row[IDX.OLDKUND_NOLLN]);
		}
		zeroNonApplicableDates(3, oldkund_noll3);
	}

	public void setAnttrans3(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			anttrans3[i] = ((BigDecimal)row[IDX.ANTTRANSN]).intValue();
		}
		zeroNonApplicableDates(3, anttrans3);
	}

	public void setAntretur3(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			antretur3[i] = ((BigDecimal)row[IDX.ANTRETURN]).intValue();
		}
		zeroNonApplicableDates(3, antretur3);
	}

	public void updateAntkund3() {
		for(int i = 0; i < antkund3.length; i++) {
			antkund3[i] = oldkund_real3[i] + oldkund_noll3[i];
		}
	}

	public void setOrd_oms12(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			ord_oms12[i] = ((BigDecimal)row[IDX.ORD_OMSN]);
		}
		zeroNonApplicableDates(12, ord_oms12);
	}

	public void setOldkund_real12(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_real12[i] = ((BigInteger)row[IDX.OLDKUND_REALN]).intValue();
		}
		zeroNonApplicableDates(12, oldkund_real12);
	}

	public void setOldkund_noll12(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_noll12[i] = ((int)row[IDX.OLDKUND_NOLLN]);
		}
		zeroNonApplicableDates(12, oldkund_noll12);
	}

	public void setAnttrans12(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			anttrans12[i] = ((BigDecimal)row[IDX.ANTTRANSN]).intValue();
		}
		zeroNonApplicableDates(12, anttrans12);
	}

	public void setAntretur12(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			antretur12[i] = ((BigDecimal)row[IDX.ANTRETURN]).intValue();
		}
		zeroNonApplicableDates(12, antretur12);
	}

	public void updateAntkund12() {
		for(int i = 0; i < antkund12.length; i++) {
			antkund12[i] = oldkund_real12[i] + oldkund_noll12[i];
		}
	}

	public void setOrd_oms24(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			ord_oms24[i] = ((BigDecimal)row[IDX.ORD_OMSN]);
		}
		zeroNonApplicableDates(24, ord_oms24);
	}

	public void setOldkund_real24(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_real24[i] = ((BigInteger)row[IDX.OLDKUND_REALN]).intValue();
		}
		zeroNonApplicableDates(24, oldkund_real24);
	}

	public void setOldkund_noll24(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			oldkund_noll24[i] = ((int)row[IDX.OLDKUND_NOLLN]);
		}
		zeroNonApplicableDates(24, oldkund_noll24);
	}

	public void setAnttrans24(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			anttrans24[i] = ((BigDecimal)row[IDX.ANTTRANSN]).intValue();
		}
		zeroNonApplicableDates(24, anttrans24);
	}

	public void setAntretur24(List<Object[]> dbData) {
		for (Object[] row : dbData) {
			int i = getDateIndex(toDate(((int) row[IDX.COHORT_YEAR]), ((int) row[IDX.COHORT_MONTH])));
			antretur24[i] = ((BigDecimal)row[IDX.ANTRETURN]).intValue();
		}
		zeroNonApplicableDates(24, antretur24);
	}

	public void updateAntkund24() {
		for(int i = 0; i < antkund24.length; i++) {
			antkund24[i] = oldkund_real24[i] + oldkund_noll24[i];
		}
	}

	public String[] getPrettyMonthList() {
		String[] dateList = new String[ord_mon.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-yy");
		ord_mon.forEach((date, index) -> dateList[index.intValue()] = sdf.format(date));
		return dateList;
	}

	public double[] getNykundAsDouble() {
		double[] array = new double[nykund.length];
		for(int i = 0; i < nykund.length; i++) {
			array[i] = (double)nykund[i];
		}
		return array;
	}

	public double[] getOldkundAsDouble() {
		double[] array = new double[oldkund.length];
		for(int i = 0; i < oldkund.length; i++) {
			array[i] = (double)oldkund[i];
		}
		return array;
	}

	public double[] getFirstOmsAsDouble() {
		double[] array = new double[first_oms.length];
		for(int i = 0; i < first_oms.length; i++) {
			array[i] = first_oms[i].doubleValue();
		}
		return array;
	}

	public double[] getAntkund0AsDouble() {
		double[] array = new double[antkund0.length];
		for(int i = 0; i < antkund0.length; i++) {
			array[i] = (double)antkund0[i];
		}
		return array;
	}

	public double[] getOrdOms0AsDouble() {
		double[] array = new double[ord_oms0.length];
		for(int i = 0; i < ord_oms0.length; i++) {
			array[i] = ord_oms0[i].doubleValue();
		}
		return array;
	}

	public double[] getProportionOfNewCustomers() {
		double[] array = new double[nykund.length];
		for(int i = 0; i < nykund.length; i++) {
			array[i] = ((double)nykund[i] / (double)antkund[i]) * 100.0f;
		}
		return array;
	}

	public double[] getNewCustomersAdditionalSales() {
		double[] array = new double[nykund_oms.length];
		for(int i = 0; i < nykund_oms.length; i++) {
			array[i] = nykund_oms[i].subtract(first_oms[i]).doubleValue();
		}
		return array;
	}

	public double[] getRecurringCustomersSales() {
		double[] array = new double[ord_oms.length];
		for(int i = 0; i < ord_oms.length; i++) {
			array[i] = ord_oms[i].subtract(first_oms[i]).doubleValue();
		}
		return array;
	}

	public double[] getNewCustomersShareOnSales() {
		double[] array = new double[nykund_oms.length];
		for(int i = 0; i < nykund_oms.length; i++) {
			array[i] = nykund_oms[i].divide(first_oms[i].add(oldkund_oms[i]), 2, RoundingMode.HALF_UP).doubleValue();
		}
		return array;
	}

	public double[] getAverageSalesPerNewCustomer() {
		double[] array = new double[nykund_oms.length];
		for(int i = 0; i < nykund_oms.length; i++) {
			if(nykund[i] == 0) {
				array[i] = 0.0f;
			} else {
				array[i] = nykund_oms[i].divide(new BigDecimal(nykund[i]), 2, RoundingMode.HALF_UP).doubleValue();
			}
		}
		return array;
	}

	public double[] getAverageSalesPerRecurringCustomer() {
		double[] array = new double[nykund_oms.length];
		for(int i = 0; i < nykund_oms.length; i++) {
			if(antkund[i] - nykund[i] == 0) {
				array[i] = 0.0f;
			} else {
				array[i] = ord_oms[i].subtract(nykund_oms[i]).divide(new BigDecimal(antkund[i] - nykund[i]), 2, RoundingMode.HALF_UP).doubleValue();
			}
		}
		return array;
	}

	public double[] getProportionOfNewCustomersRepurchasing(int periodLength) {
		double[] array = new double[antkund0.length];
		switch(periodLength) {
			case THREE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)(antkund3[i]/antkund0[i]);
					}
				}
				break;
			case TWELVE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)(antkund12[i]/antkund0[i]);
					}
				}
				break;
			case TWENTY_FOUR_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)(antkund24[i]/antkund0[i]);
					}
				}
				break;
		}
		return array;
	}

	public double[] getRepurchasePercentageOfInitialSales(int periodLength) {
		double[] array = new double[ord_oms0.length];
		switch(periodLength) {
			case THREE_MONTH_PERIOD:
				for(int i = 0; i < ord_oms0.length; i++) {
					if(ord_oms0[i].compareTo(BigDecimal.ZERO) == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms3[i].divide(ord_oms0[i], 2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
				break;
			case TWELVE_MONTH_PERIOD:
				for(int i = 0; i < ord_oms0.length; i++) {
					if(ord_oms0[i].compareTo(BigDecimal.ZERO) == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms12[i].divide(ord_oms0[i], 2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
				break;
			case TWENTY_FOUR_MONTH_PERIOD:
				for(int i = 0; i < ord_oms0.length; i++) {
					if(ord_oms0[i].compareTo(BigDecimal.ZERO) == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms24[i].divide(ord_oms0[i], 2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
				break;
		}
		return array;
	}

	public double[] getAverageNumberOfPurchasesPerNewCustomer(int periodLength) {
		double[] array = new double[antkund0.length];
		switch(periodLength) {
			case INITIAL_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)(anttrans0[i] / antkund0[i]);
					}
				}
				break;
			case THREE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)((anttrans0[i] + anttrans3[i]) / antkund0[0]);
					}
				}
				break;
			case TWELVE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)((anttrans0[i] + anttrans12[i]) / antkund0[0]);
					}
				}
				break;
			case TWENTY_FOUR_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = (double)((anttrans0[i] + anttrans24[i]) / antkund0[0]);
					}
				}
				break;
		}
		return array;
	}

	public double[] getAverageSalesPerNewCustomerCohort(int periodLength) {
		double[] array = new double[antkund0.length];
		switch(periodLength) {
			case INITIAL_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms0[i].divide(new BigDecimal(antkund0[i]), 2, RoundingMode.HALF_UP).doubleValue();
					}
				}
				break;
			case THREE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms0[i].add(ord_oms3[i]).divide(new BigDecimal(antkund0[i]), 2, RoundingMode.HALF_UP).doubleValue();
					}
				}
				break;
			case TWELVE_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0 || ord_oms12[i].compareTo(BigDecimal.ZERO) == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms0[i].add(ord_oms12[i]).subtract(ord_oms3[i]).divide(new BigDecimal(antkund0[i]), 2, RoundingMode.HALF_UP).doubleValue();
					}
				}
				break;
			case TWENTY_FOUR_MONTH_PERIOD:
				for(int i = 0; i < antkund0.length; i++) {
					if(antkund0[i] == 0 || ord_oms24[i].compareTo(BigDecimal.ZERO) == 0) {
						array[i] = 0.0f;
					} else {
						array[i] = ord_oms0[i].add(ord_oms24[i]).subtract(ord_oms12[i]).divide(new BigDecimal(antkund0[i]), 2, RoundingMode.HALF_UP).doubleValue();
					}
				}
				break;
		}
		return array;
	}

	public void convertCurrenciesTo(Currency newCurrency, ExchangeRateService converter) throws ExchangeRateException{
		Currency defaultCurrency = new Currency("Swedish krona/kronor", "SEK", "752");
		converter.setBaseCurrency(newCurrency);
		for(int i = 0; i < ack_oms.length; i++) {
			ack_oms[i] = converter.convertToBase(defaultCurrency, ack_oms[i]);
			ord_oms[i] = converter.convertToBase(defaultCurrency, ord_oms[i]);
			first_oms[i] = converter.convertToBase(defaultCurrency, first_oms[i]);
			nykund_oms[i] = converter.convertToBase(defaultCurrency, nykund_oms[i]);
			oldkund_oms[i] = converter.convertToBase(defaultCurrency, oldkund_oms[i]);
			ord_oms0[i] = converter.convertToBase(defaultCurrency, ord_oms0[i]);
			ord_oms3[i] = converter.convertToBase(defaultCurrency, ord_oms3[i]);
			ord_oms12[i] = converter.convertToBase(defaultCurrency, ord_oms12[i]);
			ord_oms24[i] = converter.convertToBase(defaultCurrency, ord_oms24[i]);
		}
	}

	public Map<Date, Integer> getOrd_mon() {
		return ord_mon;
	}

	public BigDecimal[] getAck_oms() {
		return ack_oms;
	}

	public int[] getAck_nykund() {
		return ack_nykund;
	}

	public int[] getAck_anttrans() {
		return ack_anttrans;
	}

	public int[] getAktivkund() {
		return aktivkund;
	}

	public int[] getAck_kundnum() {
		return ack_kundnum;
	}

	public Date[] getAck_min_date() {
		return ack_min_date;
	}

	public Date[] getAck_max_date() {
		return ack_max_date;
	}

	public BigDecimal[] getOrd_oms() {
		return ord_oms;
	}

	public BigDecimal[] getFirst_oms() {
		return first_oms;
	}

	public BigDecimal[] getNykund_oms() {
		return nykund_oms;
	}

	public BigDecimal[] getOldkund_oms() {
		return oldkund_oms;
	}

	public int[] getNykund() {
		return nykund;
	}

	public int[] getNykund_real() {
		return nykund_real;
	}

	public int[] getNykund_noll() {
		return nykund_noll;
	}

	public int[] getOldkund() {
		return oldkund;
	}

	public int[] getOldkund_real() {
		return oldkund_real;
	}

	public int[] getOldkund_noll() {
		return oldkund_noll;
	}

	public int[] getAnttrans() {
		return anttrans;
	}

	public int[] getFirsttrans() {
		return firsttrans;
	}

	public int[] getAntretur() {
		return antretur;
	}

	public int[] getAntkund() {
		return antkund;
	}

	public Date[] getMin_date() {
		return min_date;
	}

	public Date[] getMax_date() {
		return max_date;
	}

	public Date[] getNyk_cohort() {
		return nyk_cohort;
	}

	public BigDecimal[] getOrd_oms0() {
		return ord_oms0;
	}

	public int[] getNykund_real0() {
		return nykund_real0;
	}

	public int[] getNykund_noll0() {
		return nykund_noll0;
	}

	public int[] getAnttrans0() {
		return anttrans0;
	}

	public int[] getAntretur0() {
		return antretur0;
	}

	public int[] getAntkund0() {
		return antkund0;
	}

	public BigDecimal[] getOrd_oms3() {
		return ord_oms3;
	}

	public int[] getOldkund_real3() {
		return oldkund_real3;
	}

	public int[] getOldkund_noll3() {
		return oldkund_noll3;
	}

	public int[] getAnttrans3() {
		return anttrans3;
	}

	public int[] getAntretur3() {
		return antretur3;
	}

	public int[] getAntkund3() {
		return antkund3;
	}

	public BigDecimal[] getOrd_oms12() {
		return ord_oms12;
	}

	public int[] getOldkund_real12() {
		return oldkund_real12;
	}

	public int[] getOldkund_noll12() {
		return oldkund_noll12;
	}

	public int[] getAnttrans12() {
		return anttrans12;
	}

	public int[] getAntretur12() {
		return antretur12;
	}

	public int[] getAntkund12() {
		return antkund12;
	}

	public BigDecimal[] getOrd_oms24() {
		return ord_oms24;
	}

	public int[] getOldkund_real24() {
		return oldkund_real24;
	}

	public int[] getOldkund_noll24() {
		return oldkund_noll24;
	}

	public int[] getAnttrans24() {
		return anttrans24;
	}

	public int[] getAntretur24() {
		return antretur24;
	}

	public int[] getAntkund24() {
		return antkund24;
	}

	private void zeroNonApplicableDates(int duration, int[] array) {
		if(array.length < duration) {
			Arrays.fill(array, 0);
		} else {
			Arrays.fill(array, nyk_cohort.length - (duration - 1), nyk_cohort.length, 0);
		}
	}

	private void zeroNonApplicableDates(int duration, Object[] array) {
		if(array.length < duration) {
			Arrays.fill(array, new BigDecimal("0.00"));
		} else {
			Arrays.fill(array, nyk_cohort.length - (duration - 1), nyk_cohort.length, new BigDecimal("0.00"));
		}
	}

	private boolean initTransactionHasReturns(BigDecimal[] amounts) {
		boolean initHasReturns = false;
		boolean foundCompleteRefund = false;
		for(int i = amounts.length - 1; i > 0; i--) {
			if(amounts[i].compareTo(BigDecimal.ZERO) < 0) {
				for(int j = i - 1; j >= 0; j--) {
					if(amounts[i].abs().compareTo(amounts[j]) == 0) {
						if(j == 0) {
							initHasReturns = true;
						} else {
							foundCompleteRefund = true;
							break;
						}
					}
				}

				if(!foundCompleteRefund) {
					for(int j = i - 1; j >= 0; j--) {
						if (amounts[i].abs().compareTo(amounts[j]) < 0) {
							if (j == 0) {
								initHasReturns = true;
							} else {
								break;
							}
						}
					}
				}
				foundCompleteRefund = false;
			}
		}

		return initHasReturns;
	}

	private BigDecimal[] convertAmounts(String[] stringAmounts) {
		BigDecimal[] amounts = new BigDecimal[stringAmounts.length];
		for(int i = 0; i < stringAmounts.length; i++) {
			amounts[i] = new BigDecimal(stringAmounts[i]);
		}

		return amounts;
	}

	private Date toDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	private Date toDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	private int getDateIndex(Date date) {
		return ord_mon.get(date);
	}
}
