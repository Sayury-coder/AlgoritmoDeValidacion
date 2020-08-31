package tesoreriaCDMX;

import java.util.HashMap;

public class TreasuryCdmxService {

	public boolean processCDMX(String reference, int amount) {
		AlgorithmCdmx a = new AlgorithmCdmx();
		boolean checkVerifyCharacters = a.checkVerifyCharacters(reference, amount);

		String dueDate = a.getDueDate(reference, amount);
		System.out.println("Due Date: " + dueDate);
		boolean isValidDueDate = a.isValidDueDate(dueDate);

		if (checkVerifyCharacters && isValidDueDate) {
			System.out.println("Is valid ");
			return true;
		} else {
			System.out.println("Is not valid ");
			return false;
		}

	}
}
