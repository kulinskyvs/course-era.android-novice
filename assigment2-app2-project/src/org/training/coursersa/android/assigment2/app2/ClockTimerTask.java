package org.training.coursersa.android.assigment2.app2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class ClockTimerTask extends TimerTask {

	private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private ClockTimerHandler clockHandler;
	private DateFormat clockFormat;


	public ClockTimerTask(ClockTimerHandler clockHandler) {
		this(clockHandler, DEFAULT_TIME_FORMAT);
	}

	public ClockTimerTask(ClockTimerHandler clockHandler, String timeFormat) {
		this.clockHandler = clockHandler;
		setTimeFormat(timeFormat);
	}

	@Override
	public void run() {
    	clockHandler.changeClockText(clockFormat.format(new Date()));
	}

	public void setTimeFormat(String timeFormat) {
    	clockFormat = new SimpleDateFormat(timeFormat);
	}
}
