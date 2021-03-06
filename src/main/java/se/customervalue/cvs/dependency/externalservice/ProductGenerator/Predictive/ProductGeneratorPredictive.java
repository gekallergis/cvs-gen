package se.customervalue.cvs.dependency.externalservice.ProductGenerator.Predictive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.AnalysisData;
import se.customervalue.cvs.abstraction.externalservice.ProductGenerator.ProductGenerator;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

@Service("predictive")
public class ProductGeneratorPredictive implements ProductGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override @Async
	public void start(GennyRequestRepresentation request, int newReportId) {
		log.warn("[Predictive] Started!");
	}

	@Override
	public AnalysisData calculate(GennyRequestRepresentation request) {
		log.warn("[Predictive] Calculating!");
		return null;
	}
}
