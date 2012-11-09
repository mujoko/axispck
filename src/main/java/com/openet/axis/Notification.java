package com.openet.axis;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

import lombok.Data;

/**
 * @author mujoko
 */
@Data
public class Notification implements Serializable {

	final static String sqlNoti = "insert into bl_notification (SCENARIO, TEXT_ENG, TEXT_BAH, ENABLED, PACKAGE_NAME) values ("
			+ "'SCENARIO_VALUE', 'TEXT_ENG_VALUE','TEXT_BAH_VALUE', 'ENABLED_VALUE', 'PACKAGE_NAME_VALUE');";
	
	
	final static String unlimited ="insert into bl_notification (SCENARIO, TEXT_ENG, TEXT_BAH, ENABLED, PACKAGE_NAME) values ('QUERY_HAS_PKG'," +
			" 'Hello! Your active package is $PKG_BASENAME valid until $END_DATE. Thank you for using AXIS'," +
			" 'Halo! Paket internetan kamu yg aktif adalah $PKG_BASENAME, berlaku s.d $END_DATE." +
			" Terima kasih telah menggunakan AXIS', 'Y', 'PACKAGE_NAME_VALUE');";
	
	final static String unlimited2 ="insert into bl_notification (SCENARIO, TEXT_ENG, TEXT_BAH, ENABLED, PACKAGE_NAME) values ('QUERY_HAS_1PKG'," +
			" 'Hello! Your active package is $PKG_BASENAME valid until $END_DATE. Thank you for using AXIS', 'Halo! Paket internetan kamu yg aktif adalah $PKG_BASENAME, berlaku s.d $END_DATE. Terima kasih telah menggunakan AXIS', 'Y', 'PACKAGE_NAME_VALUE');";

	public Notification(Packages paket) {
		super();
		if (paket != null) {
			this.paket = paket;
			this.packageName = paket.getName();
		}
	}

	/**
	 * 
	 1. SCENARIO 5. PACKAGE_NAME 2. TEXT_ENG 3. TEXT_BAH 4. ENABLED
	 */
	private static final long serialVersionUID = 4164012988932601659L;

	private Packages paket;
	/**
	 */
	private String scenario = "AllowanceFinished";
	/**
	 */
	private String packageName = "";

	/**
	 */
	// @Column(name="TEXT_ENG")
	private String textEnglish = "";

	/**
	 */
	// @Column(name="TEXT_BAH")
	private String textBahasa = "TEST";

	/**
	 */
	// @Column(name="ENABLED")
	private String enabled = "Y";

	/**
	 * @return
	 */
	public List<String> generateQuery(List<String> queryUnlimited) {

		queryUnlimited.add("/* "+paket.getName()+" */");
		queryUnlimited.add("/* ********************** */");

		
		/**
		 * AllowanceFinished
		 * AllowanceThreshold1
		 */
		String sqlInsert = new StringBuffer(sqlNoti).toString();
		sqlInsert = sqlInsert.replace("PACKAGE_NAME_VALUE", packageName);
		sqlInsert = sqlInsert.replace("ENABLED_VALUE", enabled);
		sqlInsert = sqlInsert.replace("SCENARIO_VALUE", scenario);
		sqlInsert = sqlInsert.replace("TEXT_ENG_VALUE", textEnglish);
		sqlInsert = sqlInsert.replace("TEXT_BAH_VALUE", textBahasa);
		queryUnlimited.add(sqlInsert);

//		if (!paket.getQuota().equals("614400000000")){
//			String sqlInsert2 = new StringBuffer(sqlNoti).toString();
//			sqlInsert2 = sqlInsert2.replace("PACKAGE_NAME_VALUE", packageName);
//			sqlInsert2 = sqlInsert2.replace("ENABLED_VALUE", enabled);
//			sqlInsert2 = sqlInsert2.replace("SCENARIO_VALUE", "AllowanceFinished");
////			sqlInsert2 = sqlInsert2.replace("TEXT_ENG_VALUE", textEnglish);
////			sqlInsert2 = sqlInsert2.replace("TEXT_BAH_VALUE", textBahasa);
//			queryUnlimited.add(sqlInsert2);
//			
//			
//		} else {
//			String sqlInsert2 = new StringBuffer(sqlNoti).toString();
//			sqlInsert2 = sqlInsert2.replace("PACKAGE_NAME_VALUE", packageName);
//			sqlInsert2 = sqlInsert2.replace("ENABLED_VALUE", enabled);
//			sqlInsert2 = sqlInsert2.replace("SCENARIO_VALUE", "AllowanceThreshold2");
////			sqlInsert2 = sqlInsert2.replace("TEXT_ENG_VALUE", textEnglish);
////			sqlInsert2 = sqlInsert2.replace("TEXT_BAH_VALUE", textBahasa);
//			queryUnlimited.add(sqlInsert2);
//			generateQueryUnlimited(queryUnlimited);
//		}
		
		return queryUnlimited;
	}
	
	public List<String> generateQueryUnlimited(List<String> queryUnlimited) {
		if (paket.getQuota().equals("614400000000") && !paket.getInlineResetValue().equals("N")){
//			or 
			/**
			 * AllowanceThreshold1 90%
			 * AllowanceThreshold2 FUP limit
			 */
			queryUnlimited.add("/* "+paket.getName()+" */");
			queryUnlimited.add("/* ********************** */");
			String sqlInsert = new StringBuffer(unlimited).toString();
			sqlInsert = sqlInsert.replace("PACKAGE_NAME_VALUE", packageName);
			queryUnlimited.add(sqlInsert);
			
			String sqlInsert2 = new StringBuffer(unlimited2).toString();
			sqlInsert2 = sqlInsert2.replace("PACKAGE_NAME_VALUE", packageName);
			queryUnlimited.add(sqlInsert2);
		}
		
		return queryUnlimited;
	}

	public void parseData(String data, String token) {
		String toke = "|\\";
//		if (token != null) {
			toke = token;
//		}
		StringTokenizer dataList = new StringTokenizer(data, toke);
		dataList.nextElement();
		String scenarioTemp="";
		if (dataList.hasMoreElements()) {
			scenarioTemp = (String) dataList.nextElement();
		}
		
		if (scenarioTemp.toUpperCase().startsWith("BEFORE")){
			scenario="AllowanceThreshold1";
		} else if (scenarioTemp.toUpperCase().startsWith("WHEN")){
			scenario="AllowanceThreshold2";
		} else if (scenarioTemp.toUpperCase().startsWith("QUOTA")){
			scenario="AllowanceFinished";
		}
		
		if (dataList.hasMoreElements()) {
			packageName = (String) dataList.nextElement();
		}
		if (dataList.hasMoreElements()) {
			textBahasa = (String) dataList.nextElement();
		}
		if (dataList.hasMoreElements()) {
			textEnglish = (String) dataList.nextElement();
		}
		
	}

	public static void main(String[] args) {
		
		String path=new File("./").getAbsolutePath();
		path=path.replace(".","");
		List<String> packageList = FileUtil.readFileData(path, "NOTIFICATIONS - New Combo Blackberry Packages (PCC).csv");

		for (String data : packageList) {
			System.out.println("====================");
			System.out.println(data);
		}

	}

}
