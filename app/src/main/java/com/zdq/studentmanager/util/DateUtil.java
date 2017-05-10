package com.zdq.studentmanager.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 一个时间处理的工具类。
 * 
 * @author 何俊松
 * @version 0.1
 */
public class DateUtil {
	/**
	 * yyyy-MM-dd
	 */
	public static final String FORMAT_1 = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss.sss
	 */
	public static final String FORMAT_10 = "yyyy-MM-dd HH:mm:ss.sss";
	/**
	 * yyyy
	 */
	public static final String FORMAT_3 = "yyyy";
	/**
	 * MM
	 */
	public static final String FORMAT_4 = "MM";
	/**
	 * dd
	 */
	public static final String FORMAT_5 = "dd";
	/**
	 * yyyyMMdd
	 */
	public static final String FORMAT_6 = "yyyyMMdd";
	/**
	 * 
	 * yyyy年MM月
	 */
	public static final String ForMat_7 = "yyyy年MM月";
	/**
	 * 
	 */
	public static final String ForMat_8 = "yyyy-MM";
	/**
	 * 
	 */
	public static final String ForMat_9 = "yyyyMMddhhmmss";

	/**
	 * 获取当前时间
	 * 
	 * @return 一个Date
	 */
	public static Date now() {
		Date now = new Date();
		return now;
	}


	public static boolean isDouble(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch(NumberFormatException ex){}
		return false;
	}

	/**
	 * 根据格式获取当前时间的字符串
	 * 
	 * @param format
	 *            时间格式
	 * @return 格式化后的字符串
	 */
	public static String now(String format) {
		return dateFormat(new Date(), format);
	}

	/**
	 * 根据格式获取所给时间的字符串
	 * 
	 * @param date
	 *            所给时间
	 * @param format
	 *            时间格式
	 * @return 格式化后的字符串
	 */
	public static String dateFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 时间戳转字符串
	 * @param timestamp
     * @return
     */
	public static String dateFormat(Timestamp timestamp){
		String tsStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_1);
		try {
			//方法一
			tsStr = sdf.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return tsStr;
	}



	/**
	 * 日期格式字符串转换成时间戳
	 * @return
	 */
	public static Timestamp date2TimeStamp(String date_str,String formats) throws ParseException {
		DateFormat format = new SimpleDateFormat(formats);
		format.setLenient(false);
		String str_test =date_str;
			Timestamp ts = new Timestamp(format.parse(str_test).getTime());
			System.out.println(ts.toString());
		return ts;
	}

	/**
	 * 比较两个时间戳谁大
	 * @param t1
	 * @param t2
     * @return false，t1大，true，t2大
     */
	public static boolean isBig(Timestamp t1,Timestamp t2){
		boolean i=false;
		i = t1.before(t2);
		return i;
	}


	/**
	 * 比较两个时间戳谁大
	 * @param t1
	 * @param t2
	 * @return false，t1大，true，t2大
	 */
	public static boolean isEq(Timestamp t1,Timestamp t2){
		boolean i=false;
		i = t1.equals(t2);
		return i;
	}

	/**
	 * 把规定格式的日期字符串转换成时间的毫秒数
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @return 转换后的时间毫秒数
	 */
	public static String dateForTimeMillis(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_2);
		String result = "";
		try {
			result = sdf.parse(dateStr).getTime() + "";
		} catch (ParseException e) {
			System.out.println("日期字符串格式不对，要遵循格式：" + FORMAT_2);
		}
		return result;
	}

	/**
	 * 根据所给时间计算所给时间所在季度。
	 * 
	 * @param date
	 *            所给时间
	 * @return int季度
	 */
	public static int quarter(Date date) {
		int qat = -1;
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		int mon = cld.get(Calendar.MONTH) + 1;
		switch (mon) {
		case 1:
		case 2:
		case 3:
			qat = 1;
			break;
		case 4:
		case 5:
		case 6:
			qat = 2;
			break;
		case 7:
		case 8:
		case 9:
			qat = 3;
			break;
		case 10:
		case 11:
		case 12:
			qat = 4;
			break;
		default:

			break;
		}

		return qat;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] getNYs(String start, String end) {
		String[] str;
		if (start.equals("") || end.equals("")) {
			str = new String[0];
			return str;
		}
		int nf = Integer.parseInt(start.split("-")[0]);// 开始的年份
		int yf = Integer.parseInt(start.split("-")[1]);// 开始的月份
		int ys = (Integer.parseInt(end.split("-")[0]) * 12 + Integer
				.parseInt(end.split("-")[1])) - (nf * 12 + yf) + 1;
		if (ys <= 0) {
			str = new String[0];
			return str;
		}

		System.out.println(ys);
		str = new String[ys];
		for (int i = 0; i < ys; i++) {
			if (yf <= 12) {
				str[i] = nf + "-" + yf;
			} else {
				nf++;
				yf = 1;
				str[i] = nf + "-" + yf;
			}
			yf++;
		}
		return str;
	}

	public static void main(String[] args) {
		// System.out.println(DateUtil.now(FORMAT_2));;
		System.out.println(getNYs("2013-9", "2014-04"));
		System.out.println(DateUtil.now(ForMat_9));
	}
}
