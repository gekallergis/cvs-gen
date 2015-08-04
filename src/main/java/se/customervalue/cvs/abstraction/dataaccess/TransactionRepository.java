package se.customervalue.cvs.abstraction.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.customervalue.cvs.domain.SalesData;
import se.customervalue.cvs.domain.Transaction;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(value = "call ord_mon(?1)", nativeQuery = true)
	List<Object[]> getORD_MON(String companyViewName);

	@Query(value = "call ack_oms(?1);", nativeQuery = true)
	List<Object[]> getACK_OMS(String companyViewName);

	@Query(value = "call ack_nykund(?1);", nativeQuery = true)
	List<Object[]> getACK_NYKUND(String companyViewName);

	@Query(value = "call ack_anttrans(?1);", nativeQuery = true)
	List<Object[]> getACK_ANTTRANS(String companyViewName);

	@Query(value = "call ack_kundnum(?1);", nativeQuery = true)
	List<Object[]> getACK_KUNDNUM(String companyViewName);

	@Query(value = "call ack_min_date(?1);", nativeQuery = true)
	List<Object[]> getACK_MIN_DATE(String companyViewName);

	@Query(value = "call ack_max_date(?1);", nativeQuery = true)
	List<Object[]> getACK_MAX_DATE(String companyViewName);

	@Query(value = "call antretur(?1);", nativeQuery = true)
	List<Object[]> getANTRETUR(String companyViewName);

	@Query(value = "call max_date(?1);", nativeQuery = true)
	List<Object[]> getMAX_DATE(String companyViewName);

	@Query(value = "call min_date(?1);", nativeQuery = true)
	List<Object[]> getMIN_DATE(String companyViewName);

	@Query(value = "call nykund_real(?1);", nativeQuery = true)
	List<Object[]> getNYKUND_REAL(String companyViewName);

	@Query(value = "call nykund_noll(?1);", nativeQuery = true)
	List<Object[]> getNYKUND_NOLL(String companyViewName);

	@Query(value = "call nykund_oms(?1);", nativeQuery = true)
	List<Object[]> getNYKUND_OMS(String companyViewName);

	@Query(value = "call oldkund_real(?1);", nativeQuery = true)
	List<Object[]> getOLDKUND_REAL(String companyViewName);

	@Query(value = "call oldkund_noll(?1);", nativeQuery = true)
	List<Object[]> getOLDKUND_NOLL(String companyViewName);
}
