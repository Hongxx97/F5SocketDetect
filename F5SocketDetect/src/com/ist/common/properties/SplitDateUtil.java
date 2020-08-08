package com.ist.common.properties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author maxiaoming
 * 
 */
public class SplitDateUtil {

	/**
	 * 根据一段时间区间，按月份拆分成多个时间段
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static List<KeyValueForDate> getKeyValueForDate(String startDate,
			String endDate) {
		List<KeyValueForDate> list = null;
		try {
			list = new ArrayList<KeyValueForDate>();

			String firstDay = "";
			String lastDay = "";
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);// 定义起始日期

			Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);// 定义结束日期

			Calendar dd = Calendar.getInstance();// 定义日期实例
			dd.setTime(d1);// 设置日期起始时间
			Calendar cale = Calendar.getInstance();

			Calendar c = Calendar.getInstance();
			c.setTime(d2);

			int startDay = d1.getDate();
			int endDay = d2.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			KeyValueForDate keyValueForDate = null;

			while (dd.getTime().before(d2)) {// 判断是否到结束日期
				keyValueForDate = new KeyValueForDate();
				cale.setTime(dd.getTime());

				if (dd.getTime().equals(d1)) {
					cale.set(Calendar.DAY_OF_MONTH,
							dd.getActualMaximum(Calendar.DAY_OF_MONTH));

					lastDay = sdf.format(cale.getTime());

					keyValueForDate.setStartDate(sdf.format(d1));

					keyValueForDate.setEndDate(lastDay);

				} else if (dd.get(Calendar.MONTH) == d2.getMonth()
						&& dd.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
					cale.set(Calendar.DAY_OF_MONTH, 1);// 取第一天
					firstDay = sdf.format(cale.getTime());

					keyValueForDate.setStartDate(firstDay);
					keyValueForDate.setEndDate(sdf.format(d2));

				} else {
					cale.set(Calendar.DAY_OF_MONTH, 1);// 取第一天
					firstDay = sdf.format(cale.getTime());

					cale.set(Calendar.DAY_OF_MONTH,
							dd.getActualMaximum(Calendar.DAY_OF_MONTH));
					lastDay = sdf.format(cale.getTime());

					keyValueForDate.setStartDate(firstDay);
					keyValueForDate.setEndDate(lastDay);

				}
				list.add(keyValueForDate);
				dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1

			}

			if (endDay < startDay) {
				keyValueForDate = new KeyValueForDate();

				cale.setTime(d2);
				cale.set(Calendar.DAY_OF_MONTH, 1);// 取第一天
				firstDay = sdf.format(cale.getTime());

				keyValueForDate.setStartDate(firstDay);
				keyValueForDate.setEndDate(sdf.format(d2));
				list.add(keyValueForDate);
			}
		} catch (ParseException e) {
			return null;
		}

		return list;
	}

	/**
	 * 根据一段时间区间，按月份拆分成多个时间段
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	public static List<KeyValueForDate> getKeyValueForDate2(String startDate,
			String endDate) throws ParseException {
		KeyValueForDate keyValueForDate = null;
		List<KeyValueForDate> list = new ArrayList<KeyValueForDate>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date begin = new Date();
		Date end = new Date();
		begin = sdf.parse(startDate);
		end = sdf.parse(endDate);
		// 开始日期不能大于结束日期
		if (!begin.before(end)) {
			return null;
		}

		Calendar cal_begin = Calendar.getInstance();
		cal_begin.setTime(begin);
		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(end);
		while (true) {
			keyValueForDate = new KeyValueForDate();
			if (cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR)
					&& cal_begin.get(Calendar.MONTH) == cal_end
							.get(Calendar.MONTH)) {
				keyValueForDate.setStartDate(sdf.format(cal_begin.getTime()));
				keyValueForDate.setEndDate(sdf.format(cal_end.getTime()));
				list.add(keyValueForDate);
				break;
			}
			String str_begin = sdf.format(cal_begin.getTime());
			String str_end = getMonthEnd(cal_begin.getTime());
			cal_begin.add(Calendar.MONTH, 1);
			cal_begin.set(Calendar.DAY_OF_MONTH, 1);
			keyValueForDate.setStartDate(str_begin);
			keyValueForDate.setEndDate(str_end);
			list.add(keyValueForDate);
		}

		return list;
	}

	private static String getDateInterval(Date begin, Date end) {
		// 开始日期不能大于结束日期
		if (!begin.before(end)) {
			return null;
		}
		Calendar cal_begin = Calendar.getInstance();
		cal_begin.setTime(begin);
		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer strbuf = new StringBuffer();
		while (true) {
			if (cal_begin.get(Calendar.YEAR) == cal_end.get(Calendar.YEAR)
					&& cal_begin.get(Calendar.MONTH) == cal_end
							.get(Calendar.MONTH)) {
				strbuf.append(sdf.format(cal_begin.getTime())).append("~")
						.append(sdf.format(cal_end.getTime())).append("\r\n");
				break;
			}
			String str_begin = sdf.format(cal_begin.getTime());
			String str_end = getMonthEnd(cal_begin.getTime());
			strbuf.append(str_begin).append("~").append(str_end).append("\r\n");
			cal_begin.add(Calendar.MONTH, 1);
			cal_begin.set(Calendar.DAY_OF_MONTH, 1);
			// String str_end =;
		}
		return strbuf.toString();
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(Date date) {
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDateByFormat(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
