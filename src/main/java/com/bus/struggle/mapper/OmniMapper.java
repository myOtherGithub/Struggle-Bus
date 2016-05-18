package com.bus.struggle.mapper;

import java.lang.reflect.Method;

public class OmniMapper {

	public Object omniMapper(Object firstObject, Object secondObject){
		Method[] methods = firstObject.getClass().getMethods();
		for(Method currentMethod : methods){
			String methodName = currentMethod.getName();
			if(methodName.contains("get")){
				Method setterMethod = grabMethod(methodName.replace("get", "set"), secondObject);
				try{
					Object parameter = currentMethod.invoke(firstObject);
					setterMethod.invoke(secondObject, parameter);
				}catch(Exception e){
					
				}
			}
		}
		return secondObject;
	}
	
	private Method grabMethod(String methodName, Object object){
		Method setterMethod;
		try{
			setterMethod = object.getClass().getMethod(methodName);
			return setterMethod;
		}catch(Exception e){
			Method[] methods = object.getClass().getMethods();
			for(Method thisMethod : methods){
				if(thisMethod.getName().equals(methodName)){
					return thisMethod;
				}
			}
			return null;
		}
	}
}
