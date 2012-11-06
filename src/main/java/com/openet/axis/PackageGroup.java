package com.openet.axis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author mujoko
 * 
 */
@Data
public class PackageGroup {
	
	

	/**
	 */
	final static String sqlPcgGroup ="insert into bl_package_group (GROUP_ID,PACKAGE_ID) values (GROUP_ID_VALUE,PACKAGE_ID_VALUE);";

	/**
	 * @param paket.
	 */
	public PackageGroup(Packages paket) {
		super();
		this.paket = paket;
		if (paket!=null){
			packageId = paket.getId();
			packageName = paket.getName();
		}
	}

	/**
	 */
	private Packages paket;

	/**
	 */
	private String packageName="";

	/**
	 */
	private String groupId="";

	/**
	 */
	private String packageId="";

	/**
	 */
	private List<String> queryPackageGroup = new ArrayList<String>();
	
	static String FILE_NAME="insertBL_PACKAGE_GROUP.sql";
	/**
	 * Default
	 * @return
	 */
	public List<String>  generatePckGroup(){
		String sqlInsert = new StringBuffer(sqlPcgGroup).toString();
		queryPackageGroup.add("/*######### "+packageName +" ######### */");
		queryPackageGroup.add("/***************************************/");
		
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "1"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "2"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "3"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "4"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "8"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "9"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "10"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "14"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "16"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "17"));
		queryPackageGroup.add(sqlInsert.replace("PACKAGE_ID_VALUE", packageId).replace("GROUP_ID_VALUE", "18"));
		
		return queryPackageGroup;
	}

	
	/**
	 * @param args.
	 */
	public static void main(String[] args) {
		PackageGroup p = new PackageGroup(null);
		p.generatePckGroup();
		for (String q : p.queryPackageGroup) {
			System.out.println(q);
		}
	}

}
