package com.openet.axis;

import lombok.Data;


/**
 * 
 * @author mujoko
 *
 */
@Data
public class PolicyFup {
	
	public static String template = "insert into bl_policy_fup (PACKAGE_ID,INDICATOR_0,THRES_1, " +
			"INDICATOR_1,THRES_2,INDICATOR_2,THRES_3,INDICATOR_3) values (PACKAGE_ID_VALUE,'INDICATOR_0_VALUE',THRES_1_VALUE,'INDICATOR_1_VALUE',-1,0,-1,0);";

	
	public PolicyFup(Packages paket) {
		super();
		this.paket = paket;
	}
//	private String 


	private Packages paket;
	
	public String generateSql(){
		StringBuffer sql=new StringBuffer(template);
		String sqlString=sql.toString().replace("PACKAGE_ID_VALUE", paket.getId());
		
		sqlString=sqlString.replace("INDICATOR_0_VALUE", paket.getIndicator0());
		sqlString=sqlString.replace("INDICATOR_1_VALUE", paket.getIndicator1());
		Long treshold=-1l;
		try {
			treshold = Long.parseLong(paket.getTreshold1().trim().replace("MB", ""))*1000*1024;
		} catch (Exception e) {
			treshold=-1l;
		}
		
		sqlString=sqlString.replace("THRES_1_VALUE", treshold+"");
		return sqlString.toString();
	}
	
	
}
