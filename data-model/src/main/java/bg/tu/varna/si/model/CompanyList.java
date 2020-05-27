package bg.tu.varna.si.model;

import java.util.LinkedList;
import java.util.List;

public class CompanyList {
	
	private List <Company> listCompany = new LinkedList<Company>();

	public List<Company> getListCompany() {
		return listCompany;
	}

	public void setListCompany(List<Company> listCompany) {
		this.listCompany = listCompany;
	}
	
	

}
