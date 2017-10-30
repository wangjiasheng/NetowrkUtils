package com.wjs.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ParamsUtil {
	public static String parseParams(Map<String,String> params)
	{
		StringBuilder builder = new StringBuilder(); 
		Set<Map.Entry<String, String>> set = params.entrySet();
		 Iterator<Map.Entry<String, String>> iterator = set.iterator();
         while (iterator.hasNext()) {
             Map.Entry<String, String> entry = iterator.next();
             String key = entry.getKey();
             String value = entry.getValue();
            
             if (builder.length() == 0) {
                 builder.append(key);
                 builder.append("=");
                 builder.append(value);
             } else {
                 builder.append("&");
                 builder.append(key);
                 builder.append("=");
                 builder.append(value);
             }
         }
         return builder.toString();
	}
}
