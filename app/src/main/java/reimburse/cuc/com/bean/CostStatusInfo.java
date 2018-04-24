package reimburse.cuc.com.bean;

public class CostStatusInfo {
	private String budget_type;
	private String budget_paper_passed_amount;
	
	public CostStatusInfo(){
		
		
	}

	
	
	public CostStatusInfo(String budget_type, String budget_paper_passed_amount) {
		this.budget_type = budget_type;
		this.budget_paper_passed_amount = budget_paper_passed_amount;
	}



	public String getBudget_type() {
		return budget_type;
	}

	public void setBudget_type(String budget_type) {
		this.budget_type = budget_type;
	}

	public String getBudget_paper_passed_amount() {
		return budget_paper_passed_amount;
	}

	public void setBudget_paper_passed_amount(String budget_paper_passed_amount) {
		this.budget_paper_passed_amount = budget_paper_passed_amount;
	}

}
