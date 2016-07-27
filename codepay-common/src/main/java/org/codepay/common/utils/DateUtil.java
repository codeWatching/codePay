package org.codepay.common.utils;

/**
 * 日期类
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_00 = "yyyy-MM-dd";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYYMMDDHHMMSS = "yyyyMMdd HHmmss";

	public static final String YYYYMMDDHHMM = "yyyyMMdd HHmm";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String HH_MM = "HH:mm";

	public static final String HH_MM_SS = "HH:mm:ss";
	
	public static final String YYYY_MM_DD_HH_mm_ss = "yyyyMMddHHmmss";
	/**
	 * 返回当前时间长整型
	 * 
	 * @return
	 */
	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回当前时间字符型
	 * 
	 * @return
	 */
	public static String getLongDate() {
		long d = System.currentTimeMillis();
		return String.valueOf(d);
	}

	/**
	 * 格式化日期,格式化后可直接insert into [DB]
	 * 
	 * @return
	 * 
	 */
	public static String dateToStr(Date date) {

		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrYyyyMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrYyyyMM(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	/**
	 * 返回 yyyy-MM-dd HH:mm:ss 格式时间字符串
	 */
	public static String dateToStrYMDHMS(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("MMdd", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static Date getDBDefaultTime() {
		return DateUtil.strToDate("1970-01-01 00:00:00", YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 格式化DATE
	 * 
	 * @param date
	 * @return 20140526173815
	 */
	public static Long dateToLong(Date date) {
		String dateStr = dateToStr(date, YYYY_MM_DD_HH_mm_ss);
		if (StringUtils.isNotBlank(dateStr)) {
			return Long.valueOf(dateStr);
		} else {
			return null;
		}
	}

	public static Date longToDate(Long date) {
		if (null == date) {
			return null;
		}
		String dateStr = date.toString();
		return strToDate(dateStr, "yyyyMMddHHmmss");
	}
	
    public static Integer dateToInteger(Date date, String format) {
        String dateStr = dateToStr(date, format);
        if (StringUtils.isNotBlank(dateStr)) {
            return Integer.valueOf(dateStr);
        } else {
            return null;
        }
    }
    
    public static Long dateToLong(Date date, String format) {
        String dateStr = dateToStr(date, format);
        if (StringUtils.isNotBlank(dateStr)) {
            return Long.valueOf(dateStr);
        } else {
            return null;
        }
    }

	public static String dateToStrHHmmss(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStr(Date date, String format) {

		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	/**
	 * 反向格式化日期
	 * 
	 * @return
	 * 
	 */
	public static Date strToDate(String str) {
		if (str == null)
			return null;
		// DateFormat defaultDate = DateFormat.getDateInstance();
		// 细化日期的格式
		SimpleDateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = null;
		try {
			date = defaultDate.parse(str);
		} catch (ParseException pe) {
		}
		return date;
	}

	 
	/**
	 * 修改时间 format = yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            原日期 2013-04-11 11:11:11
	 * @param match
	 *            修改内容 如 yyyy-MM
	 * @param value
	 *            修改值 2011-11
	 * @return 修改后日期 2011-11-11 11:11:11
	 */
	public static Date formatDate(Date date, String match, String value) {
		String format = "yyyy-MM-dd HH:mm:ss";
		int index = format.indexOf(match);
		int length = match.length();
		if (index >= 0 && length == value.length()) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String dateStr = sdf.format(date);
			dateStr = dateStr.substring(0, index) + value + dateStr.substring(index + length);
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 反向格式化日期
	 * 
	 * @param str
	 *            要格式化字符串
	 * @param formatStr
	 *            字符串的日期格式
	 * @return
	 */
	public static Date strToDate(String str, String formatStr) {
		try {
			if (str == null)
				return null;
			if (formatStr == null) {
				formatStr = "yyyy-MM-dd hh:mm";
			}

			SimpleDateFormat defaultDate = new SimpleDateFormat(formatStr);

			Date date = null;
			try {
				date = defaultDate.parse(str);
			} catch (ParseException pe) {
			}
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反向格式化"yyyyMMdd"格式的日期
	 * 
	 * @param str
	 *            要格式化字符串
	 * @return
	 */
	public static Date strToDateyyyyMMdd(String str) {
		try {
			if (str == null)
				return null;

			SimpleDateFormat defaultDate = new SimpleDateFormat(YYYYMMDD);

			Date date = null;
			try {
				date = defaultDate.parse(str);
			} catch (ParseException pe) {
			}
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 *            起始日期
	 * @param yearNum
	 *            年增减数
	 * @param monthNum
	 *            月增减数
	 * @param dateNum
	 *            日增减数
	 */
	public static String calDate(String date, int yearNum, int monthNum, int dateNum) {
		String result = "";
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sd.parse(date));
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = sd.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date calDate(Date date, int yearNum, int monthNum, int dateNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 *            起始日期
	 * @param yearNum
	 *            年增减数
	 * @param monthNum
	 *            月增减数
	 * @param dateNum
	 *            日增减数
	 * @param hourNum
	 *            小时增减数
	 * @param minuteNum
	 *            分钟增减数
	 * @param secondNum
	 *            秒增减数
	 * @return
	 */
	public static Date calDate(Date date, int yearNum, int monthNum, int dateNum, int hourNum, int minuteNum,
			int secondNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			cal.add(Calendar.HOUR_OF_DAY, hourNum);
			cal.add(Calendar.MINUTE, minuteNum);
			cal.add(Calendar.SECOND, secondNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回当前时间，格式'yyyy-mm-dd HH:mm:ss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getLocalDate() {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	public static String getLocalDate(String f) {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(f);
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'yyyyMMddHHmmss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getSimpleDate() {
		//
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'f' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getFormatDate(String f) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		Date dt = new Date();
		return df.format(dt);
	}

	public static String getFormatDate(String f, Date dt) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		return df.format(dt);

	}

	/**
	 * 得到当天凌晨的6点，包括明天凌晨6点前的
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String getTodaySixStr() {
		Date date = new Date();
		Date toDaySix = strToDate(dateToStr(date, "yyyy-MM-dd") + " 06:00");
		if (date.getTime() - toDaySix.getTime() > 0) {
			// //大于6点
			return dateToStr(date, "yyyy-MM-dd");
		} else {
			return dateToStr(calDate(date, 0, 0, -1), "yyyy-MM-dd");
		}
	}

	/**
	 * 得到当天日期和之前日期列表
	 * 
	 * @param orlTime
	 * @return
	 */
	public static List<String> getSixList() {
		Date date = new Date();
		Date toDaySix = strToDate(dateToStr(date, "yyyy-MM-dd") + " 06:00");
		Date daySix = null;
		if (date.getTime() - toDaySix.getTime() > 0) {
			// //大于6点
			daySix = date;
		} else {
			daySix = calDate(date, 0, 0, -1);
		}
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			l.add(dateToStr(calDate(daySix, 0, 0, -i), "yyyy-MM-dd"));
		}
		return l;
	}

	/**
	 * 得到当天凌晨的6点
	 * 
	 * @param orlTime
	 * @return
	 */
	public static Date getTodaySixDate(String dateStr) {
		Date toDaySix = strToDate(dateStr + " 06:00");
		return toDaySix;
	}

	/**
	 * 得到明天凌晨的6点
	 * 
	 * @param todaysix
	 *            今天6点
	 * @return
	 */
	public static Date getNextdaySixDate(Date todaysix) {
		return calDate(todaysix, 0, 0, 1);
	}

	/**
	 * 格式化日期为“2004年9月13日”
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String parseCnDate(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}

		if (orlTime.length() < 10) {
			return "";
		}
		String sYear = orlTime.substring(0, 4);
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sYear + "年" + sMonth + "月" + sDay + "日";
	}

	public static String parseCnDateNoYear(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}

		if (orlTime.length() < 10) {
			return "";
		}
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sMonth + "月" + sDay + "日";
	}

	/**
	 * 格式化日期为"9.13"
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String parsePointDate(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sMonth + "." + sDay;
	}

	/**
	 * 取整函数
	 * 
	 * @param mord
	 * @return
	 */
	public static String delFrontZero(String mord) {
		int im = -1;
		try {
			im = Integer.parseInt(mord);
			return String.valueOf(im);
		} catch (Exception e) {
			return mord;
		}
	}

	public static String getWeekStr(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return getWeekStr(c);
	}

	public static String getWeekStr(Calendar c) {
		return new String[] { "", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }[c.get(Calendar.DAY_OF_WEEK)];
	}

	public static String getWeekStrZhou(Calendar c) {
		return new String[] { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" }[c.get(Calendar.DAY_OF_WEEK)];
	}

	public static Integer getCNDayOfWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int a = c.get(Calendar.DAY_OF_WEEK);
		switch (a) {
		case 1:
			return 7;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		default:
			return 1;
		}
	}

	public static Integer getWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public static Integer getWeekday(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	public static String getYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	public static Integer getYear(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR);
	}

	public static Integer getMonth(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.MONTH) + 1;
	}

	public static Integer getQuarter(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int month = c.get(Calendar.MONTH);
		if (month < 3) {
			return 1;
		} else if (month >= 3 && month < 6) {
			return 2;
		} else if (month >= 6 && month < 9) {
			return 3;
		} else if (month >= 9 && month < 12) {
			return 4;
		} else {
			return null;
		}
	}

	public static Date getdecDateOfMinute(Date time, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, -minute);
		return c.getTime();
	}

	public static Date getaddDateOfMinute(Date time, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	public static Date getaddDateOfMinute(String time, int minute) {
		SimpleDateFormat df = new SimpleDateFormat(HH_MM);
		Date date = null;
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	public static Date getdecDateOfDate(Date time, int date) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DATE, -date);
		return c.getTime();
	}

	public static Date getaddDateOfDate(Date time, int date) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DATE, date);
		return c.getTime();
	}

	/**
	 * 构造函数
	 */
	private DateUtil() {
	}

	public static String getStringMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.SECOND, 59);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	public static Date getTodayDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getYesterDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	// 获取本周 第一天
	public static Date getFirstDateOfWeek(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c.getTime();
	}

	// 获取本周 第几天
	public static Date getDateOfWeek(Date date, int dayOfWeek) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c.getTime();
	}

	// 获取前几个周几
	// 前50个周二
	public static Date getDateOfWeeks(Date date, int dayOfWeek, int total) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);

		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		c.add(Calendar.WEDNESDAY, total);
		return c.getTime();
	}

	public static Date getMonthFirstDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 当前日期格式化成字符串，例20090812
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate(new Date());
	}

	public static String getDate(Date date) {
		SimpleDateFormat SDF_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		return SDF_yyyyMMdd.format(date);
	}

	public static String getDateTime() {
		return getDateTime(new Date());
	}

	public static String getDateTime(Date date) {
		return dateToStr(date, "yyyyMMddHHmmss");
	}

	public static Date getPastMonthDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -30);
		return c.getTime();
	}

	public static Date getPastWeekDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -7);
		return c.getTime();
	}

	/**
	 * Long型日期转为字符串格式的日期，格式（yyyy-MM-dd HH:mm:ss），如：20140716134421转为2014-07-16
	 * 13:44:21;
	 * 
	 * @param date
	 * @return
	 */
	public static String longDate2Str(long date) {
		String s = String.valueOf(date);
		String[] spe = { "", "-", "-", " ", ":", ":", "" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i += 2) {
			sb.append(s.charAt(i)).append(s.charAt(i + 1)).append(spe[i / 2]);
		}
		return sb.toString();
	}

	/**
	 * @return 获取近三天的日期
	 */
	public static List<String> getThreeDay() {
		List<String> dateStr = new ArrayList<String>();
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, 0)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -1)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -2)));
		return dateStr;

	}

	/**
	 * @return 获取近7天的日期
	 */
	public static List<String> getWeekDay() {
		List<String> dateStr = new ArrayList<String>();
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, 0)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -1)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -2)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -3)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -4)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -5)));
		dateStr.add(getFormatDate("yyyy-MM-dd", calDate(new Date(), 0, 0, -6)));
		return dateStr;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static int getWeekOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		w = w < 0 ? 0 : w;
		w = w > 6 ? 6 : w;

		return w;
	}

	public static long dateStringToLong(String date) {
		return DateUtil.dateToLong(DateUtil.strToDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/*
	 * public static String[] combineDate(String[] date){ String[] dateArray =
	 * {"00:00-02:00","02:00-04:00","04:00-06:00","08:00-10:00","12:00-14:00",
	 * "14:00-16:00","16:00-18:00","18:00-20:00","20:00-22:00","22:00-00:00"};
	 * Map<String,Integer> map = new HashMap<String,Integer>(); for (int
	 * i=0;i<dateArray.length;i++) { map.put(dateArray[i],i ); }
	 * 
	 * List<String> combineList = new ArrayList<String>();
	 * 
	 * String last=null; for(String temp: date){ if(null==last){ last = temp;
	 * break; } if(map.get(temp)-map.get(last)==1){ String con =
	 * last.split("-")[0]+temp.split("-")[1]; combineList.add(con); }else {
	 * combineList.add(temp); }
	 * 
	 * }
	 * 
	 * }
	 */

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static long getSecDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long diff = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// return day + "天" + hour + "小时" + min + "分" + sec + "秒";
		return diff / 1000;
	}

	/**
	 * 获取当天前n天日期
	 * 
	 * @param n
	 * @return
	 */
	public static Date getDayBefore(int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -n);
		return cal.getTime();
	}

	/**
	 * 获取当前时间前n小时的时间
	 * 
	 * @param n
	 * @return
	 */
	public static Date getTimeBeforeHour(int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, -n);
		return cal.getTime();
	}

	/**
	 * 获取当前时间前n分钟的时间
	 * 
	 * @param n
	 * @return
	 */
	public static Date getTimeBeforeMinutes(int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -n);
		return cal.getTime();
	}

	public static String strFormat(String dateStr, String format) {
		try {
			SimpleDateFormat defaultDate = new SimpleDateFormat(format);
			Date date = defaultDate.parse(dateStr);
			SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale.getDefault());
			return sdFormat.format(date);
		} catch (ParseException pe) {
			return StringUtils.EMPTY;
		}
	}

	/** 计算时间是不是在今天，本周，本月，本年-----------start---- **/

	/**
	 * 判断选择的日期是否是今天
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isToday(long time) {
		return isThisTime(time, "yyyy-MM-dd");
	}

	/**
	 * 判断选择的日期是否是本周
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isThisWeek(long time) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(new Date(time));
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	/**
	 * 判断选择的日期是否是本月
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isThisMonth(long time) {
		return isThisTime(time, "yyyy-MM");
	}

	/**
	 * 判断选择的日期是否是本年
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isThisYear(long time) {
		return isThisTime(time, "yyyy");
	}

	/**
	 * 是不是当前时间
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	private static boolean isThisTime(long time, String pattern) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String param = sdf.format(date);
		// 参数时间
		String now = sdf.format(new Date());
		// 当前时间
		if (param.equals(now)) {
			return true;
		}
		return false;
	}

	/** 计算时间是不是在今天，本周，本月，本年-----------end---- **/
}
