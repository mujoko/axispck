package com.openet.axis;

import java.io.Serializable;

import lombok.Data;

/**
 * @author mujoko
 */
@Data
public class PackageBalanceType implements Serializable {
	

	final static StringBuffer sqlBalType = new StringBuffer(
			"insert into bl_package_bal_type (PACKAGE_ID,SERVICE_NAME,BALANCE_TYPE,ALLOWANCE,THRESHOLD1,THRESHOLD2,THRESHOLD3,THRESHOLD4) values (" +
			"PACKAGE_ID_VALUE,'GPRS','BALANCE_TYPE_VALUE',ALLOWANCE_VALUE,THRESHOLD1_VALUE,THRESHOLD2_VALUE,THRESHOLD3_VALUE,THRESHOLD4_VALUE);");

	/**
	1. PACKAGE_ID
	2. SERVICE_NAME
	3. BALANCE_TYPE
	4. ALLOWANCE
	5. THRESHOLD1
	6. THRESHOLD2
	7. THRESHOLD3
	8. THRESHOLD4
	
	 */
	private static final long serialVersionUID = 9176778328159521964L;
	/**
	 */
	private Packages paket;

	/**
	 */
//	@Column(name = "PACKAGE_ID")
	private String packageId="";
	

//	@Column(name = "ALLOWANCE")
	private String allowance="";


//	@Column(name = "SERVICE_NAME")
	private String serviceName;


//	@Column(name = "BALANCE_TYPE")
	private String balanceType="AddOnAllowance";

	
//	@Column(name = "THRESHOLD1")
	private String threshold1="0";


//	@Column(name = "THRESHOLD2")
	private String threshold2="0";


//	@Column(name = "THRESHOLD3")
	private String threshold3="0";

//	@Column(name = "THRESHOLD4")
	private String threshold4="0";
	/**
	 * @return
	 */
	public String generateQuery() {
		String sqlInsert = new StringBuffer(sqlBalType).toString();
		sqlInsert = sqlInsert.replace("PACKAGE_ID_VALUE", packageId);
		sqlInsert = sqlInsert.replace("THRESHOLD1_VALUE", threshold1);
		sqlInsert = sqlInsert.replace("THRESHOLD2_VALUE", threshold2);
		sqlInsert = sqlInsert.replace("THRESHOLD3_VALUE", threshold3);
		sqlInsert = sqlInsert.replace("THRESHOLD4_VALUE", threshold4);//ALLOWANCE
		sqlInsert = sqlInsert.replace("ALLOWANCE_VALUE", allowance);//ALLOWANCE
		sqlInsert = sqlInsert.replace("BALANCE_TYPE_VALUE", balanceType);//ALLOWANCE
		return sqlInsert;
	}
	
	
	public static void main(String[] args) {
		PackageBalanceType n=new PackageBalanceType(null);
		System.out.println(n.generateQuery());
	}
	
	

	public PackageBalanceType(Packages paket) {
		super();
		this.paket = paket;
		if (paket!=null){
			packageId=paket.getId();
			allowance=paket.getQuota();
			if (paket.getPackageType().toLowerCase().equals("p")){
				balanceType="AxisUsage";
			}
			
		}
	}
	
}
