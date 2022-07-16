package com.sxpcwlkj.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期类型转换 工具类
 *
 * @author wangpan
 */
public class DateUtil {
    public static void main(String[] args) {
        try {


            Date  ttt= getAddDate(new Date(),0,0,7,0,0,0,0);
            long mit=1593014592L*1000;
            mit=1584611370000L;
            Date  fistz=new Date(mit);

            System.out.println(getDateToStr(fistz,DateUtil.DATE_FORMAT_YYYY_MM_DD));


            Date sss=getStrToDate("2019-01-02 00:00:00");
            System.out.println(sss.getTime());
            Date kkk=getStrToDate("2019-01-01 00:00:00");

            System.out.println(sss.getTime()- kkk.getTime());

            Date tty=new Date(kkk.getTime()+86400000);

            System.out.println(tty);

            long timess = 1580750640226L;
            Date dated = new Date(timess);
            System.out.println("dated:"+dated);


            Date date1 = new Date();

            long times = 1564416000L;
            Date date0 = getLongToDate(DataUtil.getLong("1564416000"));
            System.out.println("1412654676572 ==》Date： " + DateUtil.getDateToStr(date0, DateUtil.DATE_FORMAT_YYYY_MM_DD));

            Date date2 = getStrToDate("2019-01-01 10:20:26");
            System.out.println("String==》Date： " + DateUtil.getDateToStr(date2, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            Date date3 = getStrToDateReturnDefault("2019-9901 120.20:26", (DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS), new Date());
            System.out.println("String==》Date 出现异常放回默认值： " + DateUtil.getDateToStr(date3, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));


            System.out.println("获取某日期的年 " + DateUtil.getYear(date1));
            System.out.println("获取某日期的月 " + DateUtil.getMonth(date1));
            System.out.println("获取某日期的日 " + DateUtil.getDay(date1));
            System.out.println("日凌晨后00:00:00：" + getDayBeginTime(new Date()));
            System.out.println("日凌晨前23:59:59：" + getDayEndTime(new Date()));

            System.out.println("Date格式化日期01 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_POINTYYYYMMDD));
            System.out.println("Date格式化日期02 " + DateUtil.getDateToStr(date1, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
            System.out.println("Date格式化日期03 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_YYYYMMDD));
            System.out.println("Date格式化日期04 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_YYYYMMDDHHmm));
            System.out.println("Date格式化日期05 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_MMDDHHMI));
            System.out.println("Date格式化日期06 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_YYYY));
            System.out.println("Date格式化日期07 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_YYYYMM));
            System.out.println("Date格式化日期08 " + DateUtil.getDateToStr(date1, DateUtil.DATE_FORMAT_YYYY_MM));

            Timestamp time1 = new Timestamp(new Date().getTime());

            System.out.println("Timestamp格式化日期01 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYY年MM月DD日));
            System.out.println("Timestamp格式化日期02 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            System.out.println("Timestamp格式化日期03 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
            System.out.println("Timestamp格式化日期04 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS));
            System.out.println("Timestamp格式化日期06 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYYMMDDHHMISS));
            System.out.println("Timestamp格式化日期07 " + DateUtil.getTimestampToStr(time1, DateUtil.DATE_TIME_FORMAT_YYYYMMDD_HH_MI));

            System.out.println("一年之中第几周 " + DateUtil.getWeekthOfYear(date1));//有问题
            System.out.println("一年之中每  周 " + DateUtil.getWeekTimeOfYear(2019));//有问题
            System.out.println("一年之中有几周 " + DateUtil.getWeekCountOfYear(2019));//有问题
            System.out.println("年第几周第一天 " + DateUtil.getFirstDayOfWeek(1, 2019));//有问题
            System.out.println("本周最后一  天 " + DateUtil.getLastDayOfWeek(new Date()));
            System.out.println("日期在地几  周 " + DateUtil.getFirstDayOfWeek(new Date()));
            System.out.println("String转日期01 " + DateUtil.getStrToDate("2019-03-09 22:55"));
            System.out.println("String转日期02 " + DateUtil.getStrToDate("2019-03-09"));
            System.out.println("String转日期03 " + DateUtil.getStrToDate("20190309"));
            System.out.println("String转日期04 " + DateUtil.getStrToDate("201903"));
            System.out.println("String转日期05 " + DateUtil.getStrToDate("2019"));
            System.out.println("String转日期06 " + DateUtil.getStrToDate("20190808200808456"));
            System.out.println("之间的所有的年 " + DateUtil.getYearListOfYears(0, 5));
            System.out.println("之间的所有的月 " + DateUtil.getMonthListOfDate("2018-08", "2019-02"));
            System.out.println("之间的所有的日 " + DateUtil.getDayListOfDate("2018-09-01", "2018-09-10"));

            System.out.println("字符串是否为日期 " + DateUtil.getStrIsDate("2018-09-01"));
            System.out.println("字符串是否为日期 " + DateUtil.getStrIsDate("2ddd2565"));
            System.out.println("日期时分秒转为 0 " + DateUtil.getHhMmSsOfDate(new Date()));

            System.out.println("日期+年/月/日/时/分/秒/毫秒" + getAddDate(new Date(), 1, 2, 2, 10, 0, 0, 0));
            System.out.println("两个日期相差几天" + getDistanceTimestamp(new Date(), getAddDate(new Date(), 0, 0, 2, 10, 50, 0, 0)));
            System.out.println("是否同年同月" + getIsSameMonth(new Date(), getAddDate(new Date(), 0, 0, 2, 10, 50, 0, 0)));

            Date one = DateUtil.getStrToDate("2019-03-09 22:55");
            Date two = DateUtil.getStrToDate("2019-03-10 23:55");
            long[] d = getDistanceTime(one, two);  //{天, 时, 分, 秒}

            System.out.println("相差：" + "天=" + d[0] + "时=" + d[1] + "分=" + d[2] + "秒=" + d[3]);
            System.out.println("相差距离多少天：" + getDistanceDays("2018-08-08 20:08:08", "2018-08-09 20:08:08"));

            String s = "1564416000";
            long it = new Long(s);
            Date date = new Date(it);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String res = simpleDateFormat.format(date);
            System.out.println(timestampToDate(1564416000));

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * 时间戳转换时间
     * @param time
     * @return
     */
    public static String timestampToDate(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }

    /**
     * 计算两个时间每隔20分钟的时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> calculateTimeTwentyMin(String startTime,String endTime,int MI){
        long  oneMI=60000L;
        Date startDate = DateUtil.getStrToDate(startTime);
        Date endDate = DateUtil.getStrToDate(endTime);
        List<String> list = new ArrayList<String>();
        list.add(DateUtil.getDateToStr(startDate,DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        list = diguTime(startDate,endDate,list,oneMI*MI);
        return list;
    }
    /**
     * 递归计算时间
     * @param startDate
     * @param endDate
     * @param list
     * @return
     */
    public static List<String> diguTime(Date startDate,Date endDate,List<String> list,long MI){
        Date afterDate = new Date(startDate .getTime() + MI);
        Date nowTime=new Date();
        long t=nowTime.getTime()-afterDate.getTime();

        if(t>0){
            list.add(DateUtil.getDateToStr(afterDate,DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        }else {
            list.add(DateUtil.getDateToStr(nowTime,DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        }

        if(afterDate.getTime() < endDate.getTime()) {
            diguTime(afterDate,endDate,list,MI);
        }
        return list;
    }

    /**
     * 计算两个时间每隔20分钟的时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> calculateTimeTwentyMinTwo(String startTime,String endTime){
        Date startDate = DateUtil.getStrToDate(startTime);
        Date endDate = DateUtil.getStrToDate(endTime);
        List<String> list = new ArrayList<String>();
        list.add(DateUtil.getDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        list = diguTimeTwo(startDate,endDate,list);
        return list;
    }
    /**
     * 递归计算时间
     * @param startDate
     * @param endDate
     * @param list
     * @return
     */
    public static List<String> diguTimeTwo(Date startDate,Date endDate,List<String> list){
        Date afterDate = new Date(startDate .getTime() - 600000);
        list.add(DateUtil.getDateToStr(afterDate,DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        if(afterDate.getTime() > endDate.getTime()) {
            diguTimeTwo(afterDate,endDate,list);
        }
        return list;
    }

    /**
     * 字符串转Date方法
     *
     * @param dateStr
     * @param format  format 如yyyy-MM-dd HH:mm:ss等
     * @return
     * @throws Exception
     */
    public static Date getStringToDate(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳 转 Date
     *
     * @param str
     * @return
     */
    public static Date getLongToDate(long str) {
        Date date = new Date(str);
        return date;
    }

    /* ************工具方法***************   */

    /**
     * 获取某日期的年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取某日期的月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某日期的日数
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);//获取日
        return day;
    }

    /**
     * 格式化Date时间
     *
     * @param time       Date类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的字符串
     */
    public static String getDateToStr(Date time, String timeFromat) {
        DateFormat dateFormat = new SimpleDateFormat(timeFromat);
        return dateFormat.format(time);
    }

    /**
     * 格式化Timestamp时间
     *
     * @param timestamp  Timestamp类型时间
     * @param timeFromat
     * @return 格式化后的字符串
     */
    public static String getTimestampToStr(Timestamp timestamp, String timeFromat) {
        SimpleDateFormat df = new SimpleDateFormat(timeFromat);
        return df.format(timestamp);
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认值为当前时间Date
     * @return 格式化后的字符串
     */
    public static String getDateToStr(Date time, String timeFromat, final Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            if (defaultValue != null)
                return getDateToStr(defaultValue, timeFromat);
            else
                return getDateToStr(new Date(), timeFromat);
        }
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认时间值String类型
     * @return 格式化后的字符串
     */
    public static String getDateToStr(Date time, String timeFromat, final String defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化String时间
     *
     * @param time       String类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的Date日期
     */
    public static Date getStrToDate(String time, String timeFromat) {
        if (time == null || time.equals("")) {
            return null;
        }

        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            date = dateFormat.parse(time);
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * 格式化String时间
     *
     * @param strTime      String类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 异常时返回的默认值
     * @return
     */
    public static Date getStrToDateReturnDefault(String strTime, String timeFromat, Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.parse(strTime);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 当strTime为2008-9时返回为2008-9-1 00:00格式日期时间，无法转换返回null.
     *
     * @param strTime
     * @return
     */
    public static Date getStrToDate(String strTime) {
        if (strTime == null || strTime.trim().length() <= 0)
            return null;

        Date date = null;
        List<String> list = new ArrayList<String>(0);

        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        list.add(DATE_FORMAT_YYYY_MM_DD);
        //list.add(DATE_FORMAT_YY_MM_DD);
        list.add(DATE_FORMAT_YYYYMMDD);
        list.add(DATE_FORMAT_YYYY_MM);
        list.add(DATE_FORMAT_YYYYMM);
        list.add(DATE_FORMAT_YYYY);


        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            String format = (String) iter.next();
            if (strTime.indexOf("-") > 0 && format.indexOf("-") < 0)
                continue;
            if (strTime.indexOf("-") < 0 && format.indexOf("-") > 0)
                continue;
            if (strTime.length() > format.length())
                continue;
            date = getStrToDate(strTime, format);
            if (date != null)
                break;
        }

        return date;
    }

    /**
     * 解析两个日期之间的所有月份
     *
     * @param beginDateStr 开始日期，至少精确到yyyy-MM
     * @param endDateStr   结束日期，至少精确到yyyy-MM
     * @return yyyy-MM日期集合
     */
    public static List<String> getMonthListOfDate(String beginDateStr, String endDateStr) {
        // 指定要解析的时间格式
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        // 返回的月份列表
        String sRet = "";

        // 定义一些变量
        Date beginDate = null;
        Date endDate = null;

        GregorianCalendar beginGC = null;
        GregorianCalendar endGC = null;
        List<String> list = new ArrayList<String>();

        try {
            // 将字符串parse成日期
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // 设置日历
            beginGC = new GregorianCalendar();
            beginGC.setTime(beginDate);

            endGC = new GregorianCalendar();
            endGC.setTime(endDate);

            // 直到两个时间相同
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
                sRet = beginGC.get(Calendar.YEAR) + "-"
                        + (beginGC.get(Calendar.MONTH) + 1);
                list.add(sRet);
                // 以月为单位，增加时间
                beginGC.add(Calendar.MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析两个日期段之间的所有日期
     *
     * @param beginDateStr 开始日期  ，至少精确到yyyy-MM-dd
     * @param endDateStr   结束日期  ，至少精确到yyyy-MM-dd
     * @return yyyy-MM-dd日期集合
     */
    public static List<String> getDayListOfDate(String beginDateStr, String endDateStr) {
        // 指定要解析的时间格式
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        // 定义一些变量
        Date beginDate = null;
        Date endDate = null;

        Calendar beginGC = null;
        Calendar endGC = null;
        List<String> list = new ArrayList<String>();

        try {
            // 将字符串parse成日期
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // 设置日历
            beginGC = Calendar.getInstance();
            beginGC.setTime(beginDate);

            endGC = Calendar.getInstance();
            endGC.setTime(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 直到两个时间相同
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

                list.add(sdf.format(beginGC.getTime()));
                // 以日为单位，增加时间
                beginGC.add(Calendar.DAY_OF_MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当下年份指定前后数量的年份集合
     *
     * @param before 当下年份前年数
     * @param behind 当下年份后年数
     * @return 集合
     */
    public static List<Integer> getYearListOfYears(int before, int behind) {
        if (before < 0 || behind < 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        Calendar c = null;
        c = Calendar.getInstance();
        c.setTime(new Date());
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        int startYear = currYear - before;
        int endYear = currYear + behind;
        for (int i = startYear; i < endYear; i++) {
            list.add(Integer.valueOf(i));
        }
        return list;
    }

    /**
     * 获取当前日期是一年中第几周
     *
     * @param date
     * @return
     */
    public static Integer getWeekthOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取某一年各星期的始终时间
     * 实例：getWeekList(2016)，第52周(从2016-12-26至2017-01-01)
     *
     * @param year 年份
     * @return
     */
    public static HashMap<Integer, String> getWeekTimeOfYear(int year) {
        HashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int count = getWeekthOfYear(c.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayOfWeekStart = "";
        String dayOfWeekEnd = "";
        for (int i = 1; i <= count; i++) {
            dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
            dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
            map.put(Integer.valueOf(i), "第" + i + "周(从" + dayOfWeekStart + "至" + dayOfWeekEnd + ")");
        }
        return map;

    }

    /**
     * 获取某一年的总周数
     *
     * @param year
     * @return
     */
    public static Integer getWeekCountOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int count = getWeekthOfYear(c.getTime());
        return count;
    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 获取某年某周的第一天
     *
     * @param year 目标年份
     * @param week 目标周数
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年某周的最后一天
     *
     * @param year 目标年份
     * @param week 目标周数
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year  目标年份
     * @param month 目标月份
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        month = month - 1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);

        int day = c.getActualMinimum(c.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  目标年份
     * @param month 目标月份
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        month = month - 1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        int day = c.getActualMaximum(c.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取某个日期为星期几
     *
     * @param date
     * @return String "星期*"
     */
    public static String getDayWeekOfDate1(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获得指定日期的星期几数
     *
     * @param date
     * @return int
     */
    public static Integer getDayWeekOfDate2(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        int weekDay = aCalendar.get(Calendar.DAY_OF_WEEK);
        return weekDay;
    }

    /**
     * 判断现在时间是早上还是下午
     * 早上还是下午  0是早上 1是下午
     * @param
     * @return
     */
    public static int  getIsAMorPM(){
        GregorianCalendar ca = new GregorianCalendar();
        int mOa = ca.get(GregorianCalendar.AM_PM);
        return mOa;
    }
    /**
     * 当前时间加1天
     *
     * @param date
     * @return
     */
    public static Date getAddNumDay(Date date,int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +num);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
    /**
     * 验证字符串是否为日期
     * 验证格式:YYYYMMDD、YYYY_MM_DD、YYYYMMDDHHMISS、YYYYMMDD_HH_MI、YYYY_MM_DD_HH_MI、YYYYMMDDHHMISSSSS、YYYY_MM_DD_HH_MI_SS
     *
     * @param strTime
     * @return null时返回false;true为日期，false不为日期
     */
    public static boolean getStrIsDate(String strTime) {
        if (strTime == null || strTime.trim().length() <= 0)
            return false;

        Date date = null;
        List<String> list = new ArrayList<String>(0);

        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        list.add(DATE_FORMAT_YYYY_MM_DD);
        //list.add(DATE_FORMAT_YY_MM_DD);
        list.add(DATE_FORMAT_YYYYMMDD);
        //list.add(DATE_FORMAT_YYYY_MM);
        //list.add(DATE_FORMAT_YYYYMM);
        //list.add(DATE_FORMAT_YYYY);

        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            String format = (String) iter.next();
            if (strTime.indexOf("-") > 0 && format.indexOf("-") < 0)
                continue;
            if (strTime.indexOf("-") < 0 && format.indexOf("-") > 0)
                continue;
            if (strTime.length() > format.length())
                continue;
            date = getStrToDate(strTime.trim(), format);
            if (date != null)
                break;
        }

        if (date != null) {
            System.out.println("生成的日期:" + DateUtil.getDateToStr(date, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS, "--null--"));
            return true;
        }
        return false;
    }

    /**
     * 将指定日期的时分秒格式为零
     *
     * @param date
     * @return
     */
    public static Date getHhMmSsOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得指定时间加减参数后的日期(不计算则输入0)
     *
     * @param date        指定日期
     * @param year        年数，可正可负
     * @param month       月数，可正可负
     * @param day         天数，可正可负
     * @param hour        小时数，可正可负
     * @param minute      分钟数，可正可负
     * @param second      秒数，可正可负
     * @param millisecond 毫秒数，可正可负
     * @return 计算后的日期
     */
    public static Date getAddDate(Date date, int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);//加减年数
        c.add(Calendar.MONTH, month);//加减月数
        c.add(Calendar.DATE, day);//加减天数
        c.add(Calendar.HOUR, hour);//加减小时数
        c.add(Calendar.MINUTE, minute);//加减分钟数
        c.add(Calendar.SECOND, second);//加减秒
        c.add(Calendar.MILLISECOND, millisecond);//加减毫秒数

        return c.getTime();
    }

    /**
     * 获得两个日期的时间戳之差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDistanceTimestamp(Date startDate, Date endDate) {
        long daysBetween = (endDate.getTime() - startDate.getTime() + 1000000) / (3600 * 24 * 1000);
        return daysBetween;
    }

    /**
     * 判断二个时间是否为同年同月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean getIsSameMonth(Date date1, Date date2) {
        boolean flag = false;
        int year1 = getYear(date1);
        int year2 = getYear(date2);
        if (year1 == year2) {
            int month1 = getMonth(date1);
            int month2 = getMonth(date2);
            if (month1 == month2) flag = true;
        }
        return flag;
    }

    /**
     * 获得两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param one 时间参数 1 格式：1990-01-01 12:00:00
     * @param two 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTime(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {

            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceDays(String str1, String str2){
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayBeginTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * date1>date2  返回 true  否则  false
     *
     * @param date1  date2
     * @return
     */
    public static Boolean getcompareTime(Date date1, Date date2) {
        long beginMillisecond = date1.getTime();
        long endMillisecond = date2.getTime();
        return beginMillisecond > endMillisecond;
    }

    /**
     * data1比data2 大 num 分钟
     * @param date1
     * @param date2
     * @param num
     * @return
     */
    public static Boolean getcompareTimeTwo(int num,Date date1, Date date2) {
        long beginMillisecond = date1.getTime();
        long endMillisecond = date2.getTime();
        long numSin=60000L;
        long end=(beginMillisecond-(numSin*num))-endMillisecond;
        if(end>=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 将Date按指定format转换为字符串
     * @param date
     * @param format
     * @return
     */
    public static String getDateStr(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    // ==格式到年==
    /**
     * 日期格式，年份，例如：2018，2019
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";

    public static final String DATE_FORMAT_MM = "MM";

    public static final String DATE_FORMAT_DD = "dd";

    public static final String DATE_FORMAT_HH = "HH";

    public static final String DATE_FORMAT_mm= "mm";

    public static final String DATE_FORMAT_HH_MM= "HH:mm";

    // ==格式到年月 ==
    /**
     * 日期格式，年份和月份，例如：201707，201808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式，年份和月份，例如：2017-07，2018-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";


    // ==格式到年月日==
    /**
     * 日期格式，年月日，例如：180630，180808
     */
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：18-12-25，18-08-08
     */
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式，年月日，例如：20150630，20180808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：20018-12-25，2018-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式，年月日，例如：2018.10.05
     */
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    /**
     * 日期格式，年月日，例如：2018年10月05日
     */
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";


    // ==格式到年月日 时分 ==

    /**
     * 日期格式，年月日时分，例如：201806301210，201808081210
     */
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    /**
     * 日期格式，年月日时分，例如：20181230 12:00，20180808 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    /**
     * 日期格式，年月日时分，例如：2018-12-30 12:00，2018-08-08 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式，年月日时分，例如：20:08:00
     */
    public static final String HH_MI_SS = "HH:mm:ss";

    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20018230120000，20180808200808
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2015-05-10 23：20：00，2018-08-08 20:08:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";


    // ==格式到年月日 时分秒 毫秒==
    /**
     * 日期格式，年月日时分秒毫秒，例如：20181230120000123，20180808200808456
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";


    // ==特殊格式==
    /**
     * 日期格式，月日时分，例如：12-05 12:00
     */
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";

    // ==特殊格式==
    /**
     * 日期格式，月日时分，例如：2018-08-08+20:08:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DDADDHH_MI_SS = "yyyy-MM-dd+HH:mm:ss";

}