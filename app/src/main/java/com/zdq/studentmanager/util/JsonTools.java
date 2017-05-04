package com.zdq.studentmanager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zdq.studentmanager.bean.ClassFrom;

public class JsonTools {

    public static String createJsonString(Object value) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String string = gson.toJson(value);
        return string;
    }
    
    
    public static <T> T jsonStringTo(String jsonString){
    	Gson gson = new Gson();
    	return gson.fromJson(jsonString,new TypeToken<T>(){}.getType());
    }



}
