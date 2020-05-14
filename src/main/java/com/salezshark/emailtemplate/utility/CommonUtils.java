package com.salezshark.emailtemplate.utility;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Nibhash
 * 
 */
@Component
public class CommonUtils {

	/**
	 * getDsmId
	 * 
	 * @param encryptedDsmId
	 * @return
	 */
	public static Long getDsmId(String encryptedDsmId) {
		try {
			String decryptedDsmId = DESedeEncryption.decipher(DESedeEncryption.secretKey, encryptedDsmId);
			String dsmIdAndUserId[] = decryptedDsmId.split("_");
			return Long.parseLong(dsmIdAndUserId[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @AmitGera
	 * Method to convert String Array after spliting into List of Long
	 */

	public static List<Long> getListInLong(String idList) {

		String[] splittedIdListArray = idList.split(",");
		Long[] arrayAfterSplit = new Long[splittedIdListArray.length];
		for (int i = 0; i < splittedIdListArray.length; i++) {
			arrayAfterSplit[i] = Long.parseLong(splittedIdListArray[i]);
		}
		List<Long> idListinLongType = Arrays.asList(arrayAfterSplit);
		return idListinLongType;
	}
}
