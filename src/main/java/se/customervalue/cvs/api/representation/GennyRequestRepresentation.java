package se.customervalue.cvs.api.representation;

public class GennyRequestRepresentation {
	private int salesPeriodId;
	private int EmployeeId;
	private int CompanyId;
	private String productType;

	public GennyRequestRepresentation() {}

	public int getSalesPeriodId() {
		return salesPeriodId;
	}

	public void setSalesPeriodId(int salesPeriodId) {
		this.salesPeriodId = salesPeriodId;
	}

	public int getEmployeeId() {
		return EmployeeId;
	}

	public void setEmployeeId(int employeeId) {
		EmployeeId = employeeId;
	}

	public int getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(int companyId) {
		CompanyId = companyId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}
