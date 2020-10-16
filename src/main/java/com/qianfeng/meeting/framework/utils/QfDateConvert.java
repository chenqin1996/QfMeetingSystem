package com.qianfeng.meeting.framework.utils;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QfDateConvert implements Converter {
    @Override
    public Object convert(Class aClass, Object value) {
        if(value == null){
            return null;
        }
        if(!(value instanceof String)){
            return value;
        }
        String val = (String)value;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
