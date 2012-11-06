package com.openet.axis;

import java.util.ArrayList;
import java.util.List;

public class Rollback {

	List<String> packageIdList;
	List<String> packageNames;

	final String template = "delete from TABLE_NAME where PACKAGE_ID =";
	final String templateName = "delete from TABLE_NAME where PACKAGE_NAME =";

	public Rollback(List<String> packageIdList, List<String> packageNames) {
		this.packageIdList = packageIdList;
		this.packageNames = packageNames;

	}

	public List<String> generateQuerybyID(String tableName) {
		List<String> queryList = new ArrayList<String>();
		for (String string : packageIdList) {
			if (tableName.toUpperCase().equals("BL_PACKAGE_BAL_TYPE")) {
				queryList.add( 	new StringBuffer(template.replace("TABLE_NAME", tableName)).append(string).append(" and BALANCE_TYPE='AddOnAllowance'").toString());
			} else {
				queryList.add( 	new StringBuffer(template.replace("TABLE_NAME", tableName)).append(string).toString());
			}
		}
		return queryList;
	}
	public List<String> generateQuerybyName(String tableName) {
		List<String> queryList = new ArrayList<String>();
		for (String string : packageNames) {
			queryList.add( 	new StringBuffer(templateName.replace("TABLE_NAME", tableName)).append(string).toString());
		}
		return queryList;
	}

}
