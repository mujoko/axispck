package com.openet.axis;

import java.io.Serializable;
import java.util.StringTokenizer;

import lombok.Data;

/**
 * @author mujoko
 */
@Data
public class Packages implements Serializable {


	static String FILE_NAME = "insertBL_PACKAGE.sql";

	final static StringBuffer sqlInsertPackage=new StringBuffer("insert into bl_package (PACKAGE_ID,PACKAGE_NAME,PACKAGE_ALIAS_EN,PACKAGE_ALIAS_ID,PACKAGE_TYPE,PRIORITY,OFFER_START_DATE,OFFER_END_DATE,VALIDITY,VALIDITY_UNIT,MAX_RECURRING_TIMES,VALID_FOR_H,VALID_FOR_N,VALID_FOR_I,DEFAULT_QUOTA,EXPIRY_TIME,EXPIRY_REMINDER,EXPIRY_REMINDER_TIME,RESTRICTED,PREFERENCE) values (" +
			"PACKAGE_ID_VALUE," +
			"'PACKAGE_NAME_VALUE'," +
			"'PACKAGE_ALIAS_EN_VALUE'," +
			"'PACKAGE_ALIAS_ID_VALUE'," +
			"'PACKAGE_TYPE_VALUE'," +
			"PRIORITY_VALUE," +
			"'OFFER_START_DATE_VALUE'," +
			"'OFFER_END_DATE_VALUE'," +
			"VALIDITY_VALUE," +
			"'VALIDITY_UNIT_VALUE'," +
			"'MAX_RECURRING_TIMES_VALUE'," +
			"'VALID_FOR_H_VALUE'," +
			"'VALID_FOR_N_VALUE'," +
			"'VALID_FOR_I_VALUE'," +
			"DEFAULT_QUOTA_VALUE," +
			" EXPIRY_TIME_VALUE," +
			"'EXPIRY_REMINDER_VALUE'," +
			"EXPIRY_REMINDER_TIME_VALUE," +
			"'RESTRICTED_VALUE'," +
			"PREFERENCE_VALUE);");


	//	final static String sqlPcgGroup ="insert into bl_package_group (GROUP_ID,PACKAGE_ID) values (GROUP_ID_VALUE,PACKAGE_ID_VALUE);";
	//	
	//	
	//	final static StringBuffer sqlBalType = new StringBuffer(
	//"insert into bl_package_bal_type (PACKAGE_ID,SERVICE_NAME,BALANCE_TYPE,ALLOWANCE,THRESHOLD1,THRESHOLD2,THRESHOLD3,THRESHOLD4) values (PACKAGE_ID_VALUE,'GPRS','AddOnAllowance',ALLOWANCE_VALUE,0,0,0,0);");
	/**
	 * 
	 */
	private static final long serialVersionUID = -3728773147030653305L;

	private String id;

	private String name;

	private String packageAliasEn;

	private String packageAliasId;

	private String packageType = "O";

	private String priority;

	private String offerStartDate = "10-OCT-1980";

	private String offerEndDate = "10-OCT-2080";

	private String validity = "30";

	private String validityUnit = "days";

	private String maxRecuringTime = "0";

	private String validForH = "N";

	private String validForN = "N";

	private String validForI = "N";

	private String defaultQuota = "1024000";

	private String expireTime = "null";

	private String expireReminder = "0";

	private String expireReminderTime = "null";

	private String restricted = "0";

	private String preference = "0";

	private String quota = "0";

	private String indicator0="";
	
	
	public void parseData(String data, String token) {

		String toke = ",";
		if (token != null) {
			toke = token;
		}
		StringTokenizer dataList = new StringTokenizer(data, toke);
		// Start get Data
		if (dataList.hasMoreElements()) {
			id = (String) dataList.nextElement();
		}

		if (dataList.hasMoreElements()) {
			name = (String) dataList.nextElement();
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue)) {
				packageAliasEn = newValue;
			}
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue)) {
				packageAliasId = newValue;

			}
		}

		//		if (dataList.hasMoreElements()) {
		//String newValue = (String) dataList.nextElement();
		//if (!FileUtil.isEmpty(newValue)) {
		//	packageType = newValue;
		//}
		//		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue)) {
				priority = newValue;
			}
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue))
				if (newValue.endsWith("MB")){
					Integer d=new Integer(newValue.trim().replace("MB", " ").trim());
					Long quotaInByte=(d * 1000 * 1024L);
					quota = String.valueOf(quotaInByte);
				} else if (newValue.endsWith("GB")){
					Integer d=new Integer(newValue.trim().replace("GB", " ").trim());
					Long quotaInByte=(d * 1000 * 1000 *  1024L );
					quota = String.valueOf(quotaInByte);
				} else if (newValue.toLowerCase().trim().contains("unlimited")){
					quota = ""+ 614400000000L; //100 Giga for unlimited
				}
		}


		//		if (dataList.hasMoreElements()) {
		//String newValue = (String) dataList.nextElement();
		//if (!FileUtil.isEmpty(newValue))
		//	offerStartDate = newValue;
		//		}
		//
		//		if (dataList.hasMoreElements()) {
		//String newValue = (String) dataList.nextElement();
		//if (!FileUtil.isEmpty(newValue))
		//	offerEndDate = newValue;
		//		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue)){
				validity = newValue.split(" ")[0];
				validityUnit = newValue.split(" ")[1];

			}
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue))
				maxRecuringTime = newValue;
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue))
				validForH = newValue;
		}

		if (dataList.hasMoreElements()) {
			String newValue = (String) dataList.nextElement();
			if (!FileUtil.isEmpty(newValue))
				indicator0 = newValue;
			System.out.println(indicator0);
		}


		//		if (dataList.hasMoreElements()) {
		//String newValue = (String) dataList.nextElement();
		//if (!FileUtil.isEmpty(newValue))
		//	validityUnit = newValue;
		//		}

	}

	/**
	 * 1. PACKAGE_ID 2. PACKAGE_NAME 3. PACKAGE_ALIAS_EN 4. PACKAGE_ALIAS_ID 5.
	 * PACKAGE_TYPE 6. PRIORITY 7. OFFER_START_DATE 8. OFFER_END_DATE 9.
	 * VALIDITY 10. VALIDITY_UNIT 11. MAX_RECURRING_TIMES 12. VALID_FOR_H 13.
	 * VALID_FOR_N 14. VALID_FOR_I 15. DEFAULT_QUOTA 16. EXPIRY_TIME 17.
	 * EXPIRY_REMINDER 18. EXPIRY_REMINDER_TIME 19. RESTRICTED 20. PREFERENCE
	 * 21. INLINE_RESET
	 */

	public String generatePackageQuery() {
		String sqlInsert = new StringBuffer(sqlInsertPackage).toString();

		sqlInsert = sqlInsert.replace("PACKAGE_ID_VALUE", id);
		sqlInsert = sqlInsert.replace("PACKAGE_NAME_VALUE", name);

		sqlInsert = sqlInsert.replace("PACKAGE_ALIAS_EN_VALUE", packageAliasEn);
		sqlInsert = sqlInsert.replace("PACKAGE_ALIAS_ID_VALUE", packageAliasId);

		sqlInsert = sqlInsert.replace("PACKAGE_TYPE_VALUE", packageType);
		sqlInsert = sqlInsert.replace("PRIORITY_VALUE", priority);

		sqlInsert = sqlInsert.replace("OFFER_START_DATE_VALUE", offerStartDate);
		sqlInsert = sqlInsert.replace("OFFER_END_DATE_VALUE", offerEndDate);


		sqlInsert = sqlInsert.replace("VALIDITY_VALUE", validity);
		sqlInsert = sqlInsert.replace("VALIDITY_UNIT_VALUE", validityUnit);


		sqlInsert = sqlInsert.replace("MAX_RECURRING_TIMES_VALUE",	maxRecuringTime);
		sqlInsert = sqlInsert.replace("VALID_FOR_H_VALUE", validForH);
		sqlInsert = sqlInsert.replace("VALID_FOR_N_VALUE", validForN);
		sqlInsert = sqlInsert.replace("VALID_FOR_I_VALUE", validForI);
		sqlInsert = sqlInsert.replace("DEFAULT_QUOTA_VALUE", defaultQuota);
		sqlInsert = sqlInsert.replace(" EXPIRY_TIME_VALUE", expireTime);
		sqlInsert = sqlInsert.replace("EXPIRY_REMINDER_VALUE", expireReminder);
		sqlInsert = sqlInsert.replace("EXPIRY_REMINDER_TIME_VALUE",	expireReminderTime);
		sqlInsert = sqlInsert.replace("RESTRICTED_VALUE", restricted);
		sqlInsert = sqlInsert.replace("PREFERENCE_VALUE", preference);
		return sqlInsert;
	}

	//
	//	
	//	/**
	//	 * Default
	//	 * @return
	//	 */
	//	public String generatePckGroup(){
	//		System.out.println("/* "+name +"*/");
	//		System.out.println("/* ********************** */");
	//		String sqlInsert = new StringBuffer(sqlPcgGroup).toString();
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "1"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "2"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "3"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "4"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "8"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "9"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "10"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "14"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "16"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "17"));
	//		System.out.println(sqlInsert.replace("PACKAGE_ID_VALUE", id).replace("GROUP_ID_VALUE", "18"));
	//		
	//		return sqlPcgGroup;
	//	}


}
