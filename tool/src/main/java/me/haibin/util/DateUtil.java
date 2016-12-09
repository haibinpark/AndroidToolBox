package me.haibin.util;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期处理类
 */
public class DateUtil {
    /*java Data、String、Long三种日期类型之间的相互转换*/


    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    /**
     * 根据年月获取对应的月份天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    //比较日期
    public static boolean isMaxOrEqualDateNow(String date) {
        try {
            Date ds = new Date();
            LocalDate today = new LocalDate(ds);

            LocalDate selectDay = new LocalDate(date);
            return (selectDay.isAfter(today) || selectDay.isEqual(today));
        } catch (Exception e) {
            return false;
        }
    }

    //获取年
    public static int getYearNow() {
        Date ds = new Date();
        LocalDate today = new LocalDate(ds);
        return today.getYear();
    }

    //获取月
    public static int getMonthNow() {

        Date ds = new Date();
        LocalDate today = new LocalDate(ds);
        return today.getMonthOfYear();
    }

    //获取
    public static int getDayNow() {
        Date ds = new Date();
        LocalDate today = new LocalDate(ds);
        return today.getDayOfMonth();
    }

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return 输出格式 {@link #FORMAT_DEFAULT}
     */
    public static String getDate()
    {
        return format(Calendar.getInstance().getTime(),FORMAT_DEFAULT);
    }

    /**
     * 获取东八区时间(北京时间)
     * @return 输出格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getChinaDate()
    {
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(tz, Locale.CHINA);
        StringBuilder date = new StringBuilder();

        date.append(calendar.get(Calendar.YEAR));
        date.append("-");
        date.append(calendar.get(Calendar.MONTH)+1);//月份从0开始所以需要加1
        date.append("-");
        date.append(calendar.get(Calendar.DAY_OF_MONTH));
        date.append(" ");
        date.append(calendar.get(Calendar.HOUR_OF_DAY));
        date.append(":");
        date.append(calendar.get(Calendar.MINUTE));
        date.append(":");
        date.append(calendar.get(Calendar.SECOND));

        return date.toString();
    }

    /**
     * 格式化时间
     * @param date {@link Date}
     * @param formatStr 如 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date,String formatStr)
    {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(date);
    }


    /**
     * 设置默认时区，影响全局
     * @param id 时区ID,可以通过{@link TimeZone#getAvailableIDs()}遍历所有支持的ID，
     *           也可以输入这样的 "GMT+8"
     */
    public static void setDefaultTimeZone(String id)
    {
        TimeZone.setDefault(TimeZone.getTimeZone(id));
    }


}
