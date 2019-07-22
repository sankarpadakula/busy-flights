package com.travix.medusa.busyflights.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelpers {

    public final static String yyyyMMddhhmmssFormat = "yyyy-MM-dd'T'hh:mm:ss";
    public final static String ddMMyyyFormat = "dd-MM-yyyy";
    

   public static LocalDate getLocalDateFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ddMMyyyFormat);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public static String getDateFromStringDateTime(String date) {
        DateFormat formatter = new SimpleDateFormat(yyyyMMddhhmmssFormat);
        try{
            return new SimpleDateFormat(ddMMyyyFormat).format(formatter.parse(date));
        }catch(ParseException e){
            e.printStackTrace();
        }
        return "";
    }

}
