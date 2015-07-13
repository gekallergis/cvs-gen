package se.customervalue.cvs.abstraction.externalservice.ProductGenerator;

import org.springframework.scheduling.annotation.Async;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;

public interface ProductGenerator {
	@Async
	void start(GennyRequestRepresentation request);
	void calculate(GennyRequestRepresentation request);
}
