package com.lepus.hikariboot.framework.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author whenguycan
 * @date 2017年11月6日 下午8:59:27
 */
public class Hql {
	
	private String originHql;
	private List<String> orderList = new ArrayList<String>();
	
	public String getQueryHql(){
		String origin = this.originHql;
		if(orderList.size() != 0){
			origin += " order by " + orderList.get(0);
			if(orderList.size() > 1){
				for(int i=1, len=orderList.size(); i<len; i++){
					origin += "," + orderList.get(i);
				}
			}
		}
		return origin;
	}
	
	public String getCountHql(){
		String origin = this.originHql;
		return "select count(*) " + origin;
	}
	
	private Hql(){
		
	}
	
	public static Hql build(String originHql){
		if(originHql == null || "".equals(originHql))
			return null;
		Hql hql = new Hql();
		hql.originHql = originHql;
		return hql;
	}
	
	public static Hql build(Class<?> clazz, Map<String, String> params){
		String className = clazz.getName();
		String entityName = className.substring(className.lastIndexOf(".") + 1);
		String rootAlias = entityName.substring(0, 1).toLowerCase();
		List<String> andList = new ArrayList<String>();
		List<String> orderList = new ArrayList<String>();
		if(params != null){
			for(Entry<String, String> e : params.entrySet()){
				String cnd = e.getKey();
				String val = e.getValue();
				String[] arr = cnd.split("_");
				if(arr.length > 3){
					String operator = arr[1];
					String dataType = arr[2];
					String fields = arr[3];
					boolean resolve = resolve(rootAlias, fields, operator, val, dataType, andList, orderList);
					if(!resolve)
						System.out.println("resolve failed : " + cnd);
				}else{
					System.out.println("error cnd ignored : " + cnd);
				}
			}
		}
		String origin = "from " + entityName + " " + rootAlias;
		if(andList.size() != 0){
			origin += " where " + andList.get(0);
			if(andList.size() > 1){
				for(int i=1, len=andList.size(); i<len; i++){
					origin += " and " + andList.get(i);
				}
			}
		}
		Hql hql = new Hql();
		hql.originHql = origin;
		hql.orderList.addAll(orderList);
		return hql;
	}
	private static boolean resolve(String rootAlias, String fields, String operator, String val, String dataType, List<String> andList, List<String> orderList){
		OperateType ot = OperateType.parse(operator);
		DataType dt = DataType.parse(dataType);
		String alias = rootAlias + "." + fields;
		if(ot == null || dt == null)
			return false;
		if(ot == OperateType.order){
			orderList.add(" " + alias + " " + (dt==DataType.asc?"asc":"desc"));
		}else if(ot.name().indexOf("like") != -1){
			if(ot == OperateType.slike)
				andList.add(" " + alias + " " + ot.linker + " '" + val + "%'");
			else if(ot == OperateType.likes)
				andList.add(" " + alias + " " + ot.linker + " '%" + val + "'");
			else
				andList.add(" " + alias + " " + ot.linker + " '%" + val + "%'");
		}else{
			if(dt == DataType.i)
				andList.add(" " + alias + " " + ot.linker + " " + val);
			else
				andList.add(" " + alias + " " + ot.linker + " '" + val + "'");
		}
		return true;
	}
	
	public static enum OperateType{
		eq("="),
		ne("<>"),
		lt("<"),
		le("<="),
		gt(">"),
		ge(">="),
		like("like"),
		slike("like"),
		likes("like"),
		order("order");
		private String linker;
		private OperateType(String linker){
			this.linker = linker;
		}
		public static OperateType parse(String input){
			OperateType[] arr = OperateType.values();
			for(OperateType o : arr){
				if(o.name().equalsIgnoreCase(input))
					return o;
			}
			return null;
		}
	}
	
	public static enum DataType{
		i,s,asc,desc;
		public static DataType parse(String input){
			DataType[] arr = DataType.values();
			for(DataType o : arr){
				if(o.name().equalsIgnoreCase(input))
					return o;
			}
			return null;
		}
	}
	
}
