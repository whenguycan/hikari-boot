package com.lepus.hikariboot.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 枚举工具
 * @author wangchenyu@cit
 * @date   2017-9-19
 */
public class EnumsUtils {

	private static Map<String, Enum[]> enums = new HashMap<String, Enum[]>();
	
	public static void load(String pkg){
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> e = getDirs(pkg, cld);
		if(e != null){
			while(e.hasMoreElements()){
				URL url = e.nextElement();
				File f = new File(url.getFile());
				File[] files = f.listFiles();
				for(File file : files){
					String filename = file.getName();
					String classname = filename.replace(".class", "");
					try {
						Class<?> clazz = cld.loadClass(pkg + "." + classname);
						if(clazz.isEnum()){
							init(classname, (Class<Enum>)clazz);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	private static Enumeration<URL> getDirs(String pkg, ClassLoader cld){
		try {
			String path = pkg.replace(".", "/");
			return cld.getResources(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Set<String> keys(){
		return enums.keySet();
	}
	
	public static <T extends Enum<T>> void init(String tag, Class<T> clazz){
		enums.put(tag, clazz.getEnumConstants());
	}
	
	public static Map<String, Map<String, Object>> allMap(){
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for(String key : enums.keySet()){
			Enum[] arr = enums.get(key);
			if(arr != null){
				map.putAll(convertToMap(key, arr[0].getClass()));
			}
		}
		return map;
	}
	
	public static <T extends Enum<T>> Map<String, Map<String, Object>> convertToMap(String tag, Class<T> clazz){
		Enum[] arr = clazz.getEnumConstants();
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Map<String, Object> x = new HashMap<String, Object>();
		for(Enum e : arr){
			if(e instanceof MyEnum){
				x.put(((MyEnum) e).code(), ((MyEnum) e).text());
			}
		}
		map.put(tag, x);
		return map;
	}
	
	public static List<Select> generateSelect(String tag, String[] ignores){
		if(enums.size() == 0)
			return null;
		Enum[] arr = enums.get(tag);
		if(arr == null || arr.length == 0)
			return null;
		List<Select> list = new ArrayList<Select>();
		for(Enum e : arr){
			if(e instanceof MyEnum){
				String code = ((MyEnum) e).code();
				boolean find = false;
				if(ignores != null && ignores.length != 0){
					for(String ignore : ignores){
						if(ignore != null && ignore.equals(code)){
							find = true;
							break;
						}
					}
				}
				if(!find)
					list.add(new Select((MyEnum) e));
			}
		}
		return list;
	}
	
	public static String convert(String tag, String code){
		if(enums.size() == 0)
			return null;
		Enum[] arr = enums.get(tag);
		if(arr == null || arr.length == 0)
			return null;
		MyEnum find = null;
		for(Enum e : arr){
			if(e instanceof MyEnum)
				if(((MyEnum) e).code().equals(code)){
					find = (MyEnum) e;
					break;
				}
		}
		return find != null ? find.text() : null;
	}
	
	public static class Select{
		public String code;
		public String text;
		public Select(MyEnum me){
			this.code = me.code();
			this.text = me.text();
		}
		public Select(String code, String text){
			this.code = code;
			this.text = text;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
	
	public interface MyEnum{
		public String code();
		public String text();
		public int codeInt();
	}
	
}