package com.xhy.wblog.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class test {

    @Test
    public void testDate(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(calendar.getTime()));
    }
}
