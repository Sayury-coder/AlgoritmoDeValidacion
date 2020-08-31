package tesoreriaCDMX;

import java.util.HashMap;

public class main {
	public static void main(String[] args) {
		String reference = "8345701300714BYYHF46";
		int amount = 454;
		TreasuryCdmxService t = new TreasuryCdmxService();
		t.processCDMX(reference, amount);
	}

/*	public HashMap verifyPayment(boolean processCDMX, String lc) {
		HashMap info = new HashMap();
		int amount = 174;
		String reference = "9417200725497BY589F3";
		TreasuryCdmxService t = new TreasuryCdmxService();
		
		if (t.processCDMX(reference, amount)) {
			
			info.put("lc", reference );
			info.put("isvalid", true);
			t.processCDMX(reference, amount);
		} else {
			System.out.println("Is invalid ");
		}
		return info;
	}*/

}
