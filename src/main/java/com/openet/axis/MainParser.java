package com.openet.axis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainParser {
	public static String WO=null;

	public static void main(String[] args) {
		WO=null;
		String path=new File("./").getAbsolutePath();
		path=path.replace(".","");
		List<String> packageList = FileUtil.readFileData(path, "data.csv");
		if (args.length>0) {
			 WO=args[0].toUpperCase();
			System.out.println("Start Read Data "+ WO);
			path=path+File.separator+WO+File.separator;
			new File(path).mkdirs();
		}
		List<String> pckQuery = new ArrayList<String>();
		List<String> pckGroupQuery = new ArrayList<String>();
		List<String> balType = new ArrayList<String>();
		List<String> notificationQuery = new ArrayList<String>();
		List<String> packageNameList = new ArrayList<String>();
		List<String> packageIdList = new ArrayList<String>();
		List<String> fup = new ArrayList<String>();
		Packages lastPackage=null;
		for (String data : packageList) {
			if (!FileUtil.isEmpty(data) && !data.startsWith("#") && !data.startsWith("NOTIFICATIONS")) {
				Packages paket = new Packages();
				paket.parseData(data, ",");
				lastPackage=paket;
				packageNameList.add(paket.getName());
				packageIdList.add(paket.getId());
				
				PackageBalanceType balanceType = new PackageBalanceType(paket);
				PackageGroup packageGroup = new PackageGroup(paket);
				
				List<String> queries = packageGroup.generatePckGroup();
				/**
				 * Create Package.
				 */
				pckQuery.add("/* "+lastPackage.getName()+" */");
				pckQuery.add("/* ********************** */");
				pckQuery.add(paket.generatePackageQuery());

				/**
				 * Create PackageGroup.
				 */
				pckGroupQuery.add("/* "+lastPackage.getName()+" */");
				for (String string : queries) {
					pckGroupQuery.add( string );
				}
				/**
				 * Balance Type
				 */
				balType.add(  balanceType.generateQuery());
				/**
				 * Notification.
				 */
				Notification notification = new Notification(lastPackage);
				notification.generateQueryUnlimited(notificationQuery);
				
				/**
				 * FUP
				 */
				PolicyFup policyFup=new PolicyFup(paket);
				fup.add("/* "+lastPackage.getName()+" */");
				fup.add("/* ********************** */");
				fup.add(policyFup.generateSql());
				
			} else if (data.startsWith("NOTIFICATIONS")){
				Notification notification = new Notification(lastPackage);
				notification.parseData(data, "|");
				notification.generateQuery(notificationQuery);
				
				
			}
		}
		
		FileUtil.writeNewFile(path, Packages.FILE_NAME, pckQuery);
		FileUtil.writeNewFile(path, PackageGroup.FILE_NAME, pckGroupQuery);//insertBL_PACKAGE_BAL_TYPE
		FileUtil.writeNewFile(path, "insertBL_PACKAGE_BAL_TYPE.sql", balType);//
		FileUtil.writeNewFile(path, "insertBL_NOTIFICATION.sql", notificationQuery);//
		FileUtil.writeNewFile(path, "insertBL_POLICY_FUP.sql", fup);//

		
		/**
		 * Create Verification.
		 */
		Verification ver=new Verification(packageIdList, packageNameList);
		FileUtil.writeNewFile(path+File.separator+"verification"+File.separator, "verification_db.sql", ver.getQueries());//
		/**
		 * Create Roollback query
		 */
		
		Rollback rollback=new Rollback(packageIdList, packageNameList);
		
		
		FileUtil.writeNewFile(path+File.separator+"rollback"+File.separator, "rollback_insertBL_PACKAGE.sql", rollback.generateQuerybyID("BL_PACKAGE"));//
		FileUtil.writeNewFile(path+File.separator+"rollback"+File.separator, "rollback_insertBL_PACKAGE_GROUP.sql", rollback.generateQuerybyID("BL_PACKAGE_GROUP"));//
		FileUtil.writeNewFile(path+File.separator+"rollback"+File.separator, "rollback_insertBL_POLICY_FUP.sql", rollback.generateQuerybyID("BL_POLICY_FUP"));//
		FileUtil.writeNewFile(path+File.separator+"rollback"+File.separator, "rollback_insertBL_NOTIFICATION.sql", rollback.generateQuerybyName("BL_NOTIFICATION"));//
		FileUtil.writeNewFile(path+File.separator+"rollback"+File.separator, "rollback_insertBL_PACKAGE_BAL_TYPE.sql", rollback.generateQuerybyName("BL_PACKAGE_BAL_TYPE"));//
		
		/**
		 * Backup
		 */
		if (WO!=null && WO.startsWith("WO")) {
			List<String> backup = new ArrayList<String>();
			backup.add(  "create table bl_package_"+WO + " as (select * from bl_package);");
			backup.add(  "create table bl_package_bal_type_"+WO + " as (select * from bl_package_bal_type);");
			backup.add(  "create table bl_package_group_"+WO + " as (select * from bl_package_group);");
			backup.add(  "create table bl_policy_fup_"+WO + " as (select * from bl_policy_fup);");
			backup.add(  "create table bl_notification_"+WO + " as (select * from bl_notification);");
			FileUtil.writeNewFile(path+File.separator, "backup_bl_table.sql", backup);
		}
		
		
	}


}
