/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2014, ticktick <lujun.hust@gmail.com>
 *  http://ticktick.blog.51cto.com/
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    SignatureGen.java
 *  @brief   Implement a java class for jni signature generate
 *  
 *  @version 1.0     
 *  @author  ticktick
 *  @date    2014/12/15  
 * 
 */
package com.ticktick.library;
 
import java.util.HashMap;
 
public class SignatureGen {
 
    public static final HashMap<String,String> Primitives = new HashMap<String, String>();
     
    static {
        Primitives.put(Void.class.getName(),"V");
        Primitives.put(Boolean.class.getName(),"Z");
        Primitives.put(Byte.class.getName(),"B");
        Primitives.put(Character.class.getName(),"C");
        Primitives.put(Short.class.getName(),"S");
        Primitives.put(Integer.class.getName(),"I");
        Primitives.put(Long.class.getName(),"J");
        Primitives.put(Float.class.getName(),"F");
        Primitives.put(Double.class.getName(),"D");            
    }
             
    public static String getSignature( Class ret, Class...params ) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for( Class param : params ) {
            builder.append(getSignature(param));   
        }      
        builder.append(")");
        builder.append(getSignature(ret));
        return builder.toString();
    }
         
    protected static String getSignature( Class param ) {
         
        StringBuilder builder = new StringBuilder();
        String name = "";
        if( param.isArray() ) {
            name = param.getComponentType().getName();
            builder.append("[");
        }
        else {
            name = param.getName();              
        }  
             
        if( Primitives.containsKey(name) ) {
        builder.append(Primitives.get(name));
        }
        else {        
            builder.append("L"+name.replace(".","/")+";");
        }  
       
        return builder.toString();
    }
}