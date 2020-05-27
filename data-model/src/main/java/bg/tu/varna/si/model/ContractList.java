package bg.tu.varna.si.model;

import java.util.LinkedList;
import java.util.List;

public class ContractList {

	private List<Contract> contracts = new LinkedList<>();

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

}
