package se.customervalue.cvs.api.representation;

public class GennyRequestRepresentation {
	private int salesDataId;
	private int employeeId;
	private int companyId;
	private int ownedProductId;

	public GennyRequestRepresentation() {}

	public int getSalesDataId() {
		return salesDataId;
	}

	public void setSalesDataId(int salesDataId) {
		this.salesDataId = salesDataId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getOwnedProductId() {
		return ownedProductId;
	}

	public void setOwnedProductId(int ownedProductId) {
		this.ownedProductId = ownedProductId;
	}
}
