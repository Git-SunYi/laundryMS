package com.sunyi.laundryms.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtils {

    /**
     * 用SimpleDateFormat计算时间差
     * @throws ParseException
     */
    public static String calculateTimeDifferenceBySimpleDateFormat(Date date) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        /*月数差*/
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(simpleFormat.parse(simpleFormat.format(date)));
        c2.setTime(simpleFormat.parse(simpleFormat.format(new Date())));
        int i = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        int month = 0;
        if (i<0) {
            month = -i*12;
        }else if(i>0) {
            month =  i*12;
        }
        int month2 = (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)) + month;
        log.info("两个时间之间的月份差为：" + month2);

        /*天数差*/
        Date fromDate1 = simpleFormat.parse(simpleFormat.format(date));
        Date toDate1 = simpleFormat.parse(simpleFormat.format(new Date()));
        long from1 = fromDate1.getTime();
        long to1 = toDate1.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        log.info("两个时间之间的天数差为：" + days);

        /*小时差*/
        Date fromDate2 = simpleFormat.parse(simpleFormat.format(date));
        Date toDate2 = simpleFormat.parse(simpleFormat.format(new Date()));
        long from2 = fromDate2.getTime();
        long to2 = toDate2.getTime();
        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
        log.info("两个时间之间的小时差为：" + hours);

        /*分钟差*/
        Date fromDate3 = simpleFormat.parse(simpleFormat.format(date));
        Date toDate3 = simpleFormat.parse(simpleFormat.format(new Date()));
        long from3 = fromDate3.getTime();
        long  to3 = toDate3.getTime();
        int minutes = (int) ((to3 - from3) / (1000 * 60));
        log.info("两个时间之间的分钟差为：" + minutes);

        if (month2>=12){
            return month2/12+"年之前";
        }else if (days>=30){
            return month2+"个月之前";
        }else if (hours>=24){
            return days+"天之前";
        }else if (minutes>=60){
            return hours+"小时之前";
        }else {
            return minutes+"分钟之前";
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = simpleDateFormat.parse("2021-10-24 15:00");
        String s = calculateTimeDifferenceBySimpleDateFormat(date);
        System.out.println(s);
    }

}
