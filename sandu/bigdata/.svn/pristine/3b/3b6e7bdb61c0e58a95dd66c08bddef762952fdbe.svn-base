package com.sandu.custom.interceptor;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.sandu.custom.constant.EventLogConstants;


public class SetTimestampInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory
		      .getLogger(SetTimestampInterceptor.class);
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public void initialize() {
	}

	
	/**
	 * Modifies events in-place.
	 */
	@Override
	public Event intercept(Event event) {
		Map<String, String> headers = event.getHeaders();
		byte[] line = event.getBody();
		if(line!=null) {
			String lineStr = new String(event.getBody(), Charsets.UTF_8);
			String[] fields = lineStr.split(EventLogConstants.LOG_COLUMN_SEPARTIOR);
			if(fields.length>2) {
				String serverTimeStr = fields[1];
				try {
					Date serverTime = DateUtils.parseDate(serverTimeStr, new String[] {DATE_TIME_FORMAT});
					headers.put("timestamp", Long.toString(serverTime.getTime()));
				}catch(Exception e) {
					logger.error("日期解析异常:"+lineStr,e);
				}
				
			}
		}
		return event;
	}
	
	
public static void main(String[] args) {
	try {
		Date date = DateUtils.parseDate("2019-02-11 10:10:10", new String[] {DATE_TIME_FORMAT});
		
		System.out.println(FastDateFormat.getInstance(DATE_TIME_FORMAT).format(date));
	}catch(Exception e) {
		e.printStackTrace();
	}
}
	

	/**
	 * Delegates to {@link #intercept(Event)} in a loop.
	 *
	 * @param events
	 * @return
	 */
	public List<Event> intercept(List<Event> events) {
		List<Event> out = Lists.newArrayList();
		for (Event event : events) {
			Event outEvent = intercept(event);
			if (outEvent != null) {
				out.add(outEvent);
			}
		}
		return out;
	}

	public void close() {
	}

	/**
	 */
	public static class Builder implements Interceptor.Builder {

	
		public Interceptor build() {
			return new SetTimestampInterceptor();
		}

		public void configure(Context context) {
			
		}

	}



}