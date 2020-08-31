package tesoreriaCDMX;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlgorithmCdmx {
	private final int SECRET_KEY = 89475338; // 12357113 ;
	private final String ALPHABET = "0123456789ABCDEFHJKMNPQRTUVWXY";
	private final String ALPHABET2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final int SERIE[] = { 11, 13, 17, 19, 23 };
	private final int CURRENT_YEAR = 2020;
	private final int MOD = 887;

	private String getDecodedReference(String reference, int amount) {
		int subCadenaB = 0;
		int beta = beta(reference);
		// System.out.println("Count: " + count);

		System.out.println("Reference: " + reference);
		// System.out.println("Beta: " + beta);
		// System.out.println("Amount: " + amount);
		int potencia = (int) (Math.pow(30, 5));
		subCadenaB = (beta - SECRET_KEY - amount) % potencia;
		if (subCadenaB < 0) {
			subCadenaB = subCadenaB + potencia;
		}

		// System.out.println("Alfa: " + subCadenaB);
		String subCadenaA = transformBase10ToN(String.valueOf(subCadenaB), 10, 30);
		// System.out.println("SubcadenaA: " + subCadenaA);
		String lineaCapturaBase = reference.substring(0, 13) + subCadenaA + reference.substring(18, 20);
		System.out.println("The line of capture is: " + lineaCapturaBase);
		return reference.substring(0, 13) + subCadenaA;

	}

	public boolean checkVerifyCharacters(String reference, int amount) {

		if (reference.length() == 20) {
			String lineaCapturaDecodificada = getDecodedReference(reference, amount);
			String valueChar = "";
			int acumulador = 0;
			int j = 0;
			for (int i = lineaCapturaDecodificada.length() - 1; i >= 0; i--) {
				valueChar = Character.toString(lineaCapturaDecodificada.charAt(i));
				if (j <= 4) {
					int aux = convertCharToBase10(valueChar) * SERIE[j];
					acumulador += (int) aux;
					j++;
				} else {
					j = 0;
					int aux = convertCharToBase10(valueChar) * SERIE[j];
					acumulador += (int) aux;
					j++;
				}
			}

			int suma = acumulador + CURRENT_YEAR;
			int resultMod = suma % MOD;
			String resultModBase30 = transformBase10ToBase30(Integer.toString(resultMod), 10, 30);
			String referenceLast = reference.substring(18, 20);
			System.out.println("Reference last: " + referenceLast + "\t Final Reference: " + resultModBase30);

			/*
			 * if (referenceLast.equals(resultModBase30)) { if (getDueDate(reference,"f")) {
			 * return true; } else { return false; } } else { return false; }
			 */

			return true;
		} else {
			return false;
		}

	}

	private int beta(String reference) {

		String SubCadenaB = reference.substring(13, 18);
		int result = 0;
		String reverseSubCadenaB = "";
		for (int x = SubCadenaB.length() - 1; x >= 0; x--) {
			reverseSubCadenaB = reverseSubCadenaB + SubCadenaB.charAt(x);
		}

		reverseSubCadenaB.toUpperCase();
		for (int i = reverseSubCadenaB.length() - 1; i >= 0; i--) { // i=4
			for (int j = 0; j < ALPHABET.length(); j++) {
				if (reverseSubCadenaB.charAt(i) == ALPHABET.charAt(j)) {
					double aux = (double) Math.pow(30, i);
					result += (j * aux);
				}
			}
		}
		return result;
	}

	public String getDueDate(String reference, int amount) {
		String encodedDueDate = getDecodedReference(reference, amount).substring(13, 15);
		int day = 0;
		int month = 0;

		String[][] matrixCondensedDate = {
				{ "AA", "AB", "AC", "AD", "AE", "AF", "AH", "AJ", "AK", "AM", "AN", "AP", "AQ", "AR", "AT", "AU", "AV",
						"AW", "AX", "AY", "BA", "BB", "BC", "BD", "BE", "BF", "BH", "BJ", "BK", "BM", "BN" },
				{ "BP", "BQ", "BR", "BT", "BU", "BV", "BW", "BX", "BY", "CA", "CB", "CC", "CD", "CE", "CF", "CH", "CJ",
						"CK", "CM", "CN", "CP", "CQ", "CR", "CT", "CU", "CV", "CW", "CX", "CY", "", "" },
				{ "DC", "DD", "DE", "DF", "DH", "DJ", "DK", "DM", "DN", "DP", "DQ", "DR", "DT", "DU", "DV", "DW", "DX",
						"DY", "EA", "EB", "EC", "ED", "EE", "EF", "EH", "EJ", "EK", "EM", "EN", "EP", "EQ" },
				{ "ER", "ET", "EU", "EV", "EW", "EX", "EY", "FA", "FB", "FC", "FD", "FE", "FF", "FH", "FJ", "FK", "FM",
						"FN", "FP", "FQ", "FR", "FT", "FU", "FV", "FW", "FX", "FY", "HA", "HB", "HC", "" },
				{ "HE", "HF", "HH", "HJ", "HK", "HM", "HN", "HP", "HQ", "HR", "HT", "HU", "HV", "HW", "HX", "HY", "JA",
						"JB", "JC", "JD", "JE", "JF", "JH", "JJ", "JK", "JM", "JN", "JP", "JQ", "JR", "JT" },
				{ "JU", "JV", "JW", "JX", "JY", "KA", "KB", "KC", "KD", "KE", "KF", "KH", "KJ", "KK", "KM", "KN", "KP",
						"KQ", "KR", "KT", "KU", "KV", "KW", "KX", "KY", "MA", "MB", "MC", "MD", "ME", "" },
				{ "MH", "MJ", "MK", "MM", "MN", "MP", "MQ", "MR", "MT", "MU", "MV", "MW", "MX", "MY", "NA", "NB", "NC",
						"ND", "NE", "NF", "NH", "NJ", "NK", "NM", "NN", "NP", "NQ", "NR", "NT", "NU", "NV" },
				{ "NW", "NX", "NY", "PA", "PB", "PC", "PD", "PE", "PF", "PH", "PJ", "PK", "PM", "PN", "PP", "PQ", "PR",
						"PT", "PU", "PV", "PW", "PX", "PY", "QA", "QB", "QC", "QD", "QE", "QF", "QH", "QJ" },
				{ "QK", "QM", "QN", "QP", "QQ", "QR", "QT", "QU", "QV", "QW", "QX", "QY", "RA", "RB", "RC", "RD", "RE",
						"RF", "RH", "RJ", "RK", "RM", "RN", "RP", "RQ", "RR", "RT", "RU", "RV", "RW", "" },
				{ "RY", "TA", "TB", "TC", "TD", "TE", "TF", "TH", "TJ", "TK", "TM", "TN", "TP", "TQ", "TR", "TT", "TU",
						"TV", "TW", "TX", "TY", "UA", "UB", "UC", "UD", "UE", "UF", "UH", "UJ", "UK", "UM" },
				{ "UN", "UP", "UQ", "UR", "UT", "UU", "UV", "UW", "UX", "UY", "VA", "VB", "VC", "VD", "VE", "VF", "VH",
						"VJ", "VK", "VM", "VN", "VP", "VQ", "VR", "VT", "VU", "VV", "VW", "VX", "VY", "" },
				{ "WB", "WC", "WD", "WE", "WF", "WH", "WJ", "WK", "WM", "WN", "WP", "WQ", "WR", "WT", "WU", "WV", "WW",
						"WX", "WY", "XA", "XB", "XC", "XD", "XE", "XF", "XH", "XJ", "XK", "XM", "XN", "XP" } };

		for (int k = 0; k < matrixCondensedDate.length; k++) {
			for (int l = 0; l < matrixCondensedDate[k].length; l++) {
				if (matrixCondensedDate[k][l].equals(encodedDueDate)) {
					month = k;
					day = l;
				}
			}
		}

		String finalMonth = "";
		month = month + 1;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (Integer.toString(month).length() == 1) {
			finalMonth = "0" + Integer.toString(month);
		} else {
			finalMonth = Integer.toString(month);
		}

		String finalDay = "";
		day = day + 1;
		if (Integer.toString(day).length() == 1) {
			finalDay = "0" + Integer.toString(day);
		} else {
			finalDay = Integer.toString(day);
		}
		// System.out.println("encodedDueDate: " + encodedDueDate);
		// System.out.println("day: " + finalDay + " month: " + finalMonth);

		return (finalDay) + "/" + (finalMonth) + "/" + year;
	}

	public boolean isValidDueDate(String dueDate) {

		try {
			Date now = new Date();
			Date dueDateInDate;
			now = resetDate(now);
			dueDateInDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
			System.out.println("now: " + now + "\t dueDateInDate: " + dueDateInDate);
			if (now.getTime() < dueDateInDate.getTime() || now.getTime() == dueDateInDate.getTime()) {
				// System.out.println("procede ...");
				return true;
			} else {
				// System.out.println("no procede...");
				return false;
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return false;
	}

	private Date resetDate(Date target) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			target = sdf.parse(sdf.format(target));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return target;
	}

	private String transformBase10ToN(String numero, int baseOrigen, int baseDestino) {

		int numeroBase10 = Integer.parseInt(numero, baseOrigen);
		String numeroBaseB = Integer.toString(numeroBase10, baseDestino);
		numeroBaseB = numeroBaseB.toUpperCase();
		String result = "";
		for (int i = 0; i < numeroBaseB.length(); i++) {
			for (int j = 0; j < ALPHABET2.length(); j++)
				if (numeroBaseB.charAt(i) == ALPHABET2.charAt(j)) {
					if (j < 15) {
						result += numeroBaseB.charAt(i);
					} else if (j <= 29) {
						result += ALPHABET.charAt(j);
					} else {
						result += ALPHABET2.charAt(j);
					}
				}
		}
		return result;
	}

	private int convertCharToBase10(String c) {
		int base10 = 0;

		for (int i = 0; i < ALPHABET2.length(); i++) {
			if (c.charAt(0) == ALPHABET2.charAt(i)) {
				base10 = i;
			}
		}
		return base10;
	}

	private String transformBase10ToBase30(String numero, int baseOrigen, int baseDestino) {
		int numeroBase10 = Integer.parseInt(numero, baseOrigen);
		String numeroBaseB = Integer.toString(numeroBase10, baseDestino);
		numeroBaseB = numeroBaseB.toUpperCase();
		String result = "";
		for (int i = 0; i < numeroBaseB.length(); i++) {
			for (int j = 0; j < ALPHABET2.length(); j++)
				if (numeroBaseB.charAt(i) == ALPHABET2.charAt(j)) {
					if (j < 15) {
						result += numeroBaseB.charAt(i);
					} else if (j <= 29) {
						result += ALPHABET.charAt(j);
					} else {
						result += ALPHABET2.charAt(j);
					}
				}
		}
		return result;
	}
}
