package se.customervalue.cvs.dependency.externalservice.ProductGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

@Service("newBiz")
public class ProductGeneratorNewBiz implements ProductGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override @Async
	public void start(GennyRequestRepresentation request) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.warn("[NewBiz] Started!");
		calculate(request);
	}

	@Override
	public void calculate(GennyRequestRepresentation request) {
		log.warn("[NewBiz] Calculating!");
	}

	//	@Override @Async @Transactional
//	public void generate(GennyRequestRepresentation report) {
//		// Update DB (create report entry as processing)
//		// TODO: Perform Calculations
//		// TODO: Generate Graphs
//		// TODO: Generate PDF
//		// TODO: Update DB (reduce owned products, update report entry as complete)
//
//		try {
//			Thread.sleep(5000);
//			log.warn("TGEQWGDV");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
////		Instant start = Instant.now();
////		System.out.println("COUNT: " + transactionRepository.countBySalesDataBatch(salesDataRepository.findBySalesDataId(2)));
////		Instant end = Instant.now();
////		System.out.println(Duration.between(start, end).getNano() / 1000000 + "ms");
////
////		final int pageLimit = 10;
////		int pageNumber = 0;
////		Page<Transaction> page = transactionRepository.findAll(new PageRequest(pageNumber, pageLimit));
////		while (page.hasNext()) {
////			processPageContent(page.getContent());
////			try {
////				Thread.sleep(2000);
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
////			page = transactionRepository.findAll(new PageRequest(++pageNumber, pageLimit));
////		}
////		// process last page
////		processPageContent(page.getContent());
//	}

//	private void processPageContent(List<Transaction> list) {
//		for (Transaction transaction : list) {
//			System.out.println(transaction.getAmount());
//		}
//	}
}
