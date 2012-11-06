package com.openet.axis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Verification {
	
	
	private List<String> queries=new ArrayList<String>();

	public Verification(List<String> packageIdList, List<String> packageNames) {
		super();
		StringBuffer params=new StringBuffer("(");
		int count=0;
		for (String name : packageNames) {
			++count;
			if (count!=packageNames.size()) {
				params.append("'").append(name).append("'").append(",");
			} else {
				params.append("'").append(name).append("'");
			}
		}
		params.append(");");
		StringBuffer paramsId=new StringBuffer("(");
		int countId=0;
		for (String id : packageIdList) {
			++countId;
			if (countId!=packageIdList.size()) {
				paramsId.append(id).append(",");
			} else {
				paramsId.append(id);
			}
		}
		paramsId.append(");");
		queries.add( "select * from BL_PACKAGE where package_id in"+paramsId);
		queries.add("select * from BL_PACKAGE_GROUP where package_id in"+paramsId);
		queries.add("select * from BL_PACKAGE_BAL_TYPE where package_id in"+paramsId.toString().replace(";", "")+(" order by group_id; "));
		queries.add("select * from BL_POLICY_FUP where package_id in"+paramsId);
		queries.add("select * from BL_NOTIFICATION where package_id in"+paramsId.toString().replace(";", "")+(" order by scenario, package_name ; "));

	}
	
	public static void main(String[] args) {
		List<String> packageNames=new ArrayList<String>();
		packageNames.add("P_SMART_MON_HI"  );
		packageNames.add("P_SMART_WK_HI"  );//P_SMART_MON_LO
		packageNames.add("P_SMART_MON_LO"  );//P_SMART_MON_LO
		StringBuffer params=new StringBuffer("(");
		int count=0;
		for (String name : packageNames) {
			++count;
			if (count!=packageNames.size()) {
				params.append("'").append(name).append("'").append(",");
			} else {
				params.append("'").append(name).append("'");
			}
		}
		params.append(");");
		System.out.println("select * from BL_PACKAGE where package_id in"+params);
		
	}

}
