package org.training.coursersa.android.assigment2.app2;

import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClockActivity extends Activity {

	private Lock clockTimerLock = new ReentrantLock();
	private Timer clockTimer;

	private TextView clockTextView;
	private Button clockActionButton;

	private ClockTimerHandler clockHandler = new ClockTimerHandler() {
		@Override
		public void changeClockText(final String time) {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					clockTimerLock.lock();
					try {
						clockTextView.setText(time);
					} finally {
						clockTimerLock.unlock();
					}
				}
			});
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock);
		clockTextView = (TextView) findViewById(R.id.clockTextView);
		clockActionButton = (Button) findViewById(R.id.clockActionButton);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null == clockTimer)
			startClock();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (null != clockTimer)
			stopClock();
	}

	public void onTimerActionChanged(View view) {
		if (null != clockTimer) {
			stopClock();
		} else {
			startClock();
		}
	}

	private void startClock() {
		clockTimerLock.lock();
		try {
			clockActionButton.setText(getString(R.string.stop));
			clockTimer = new Timer();
			clockTimer.scheduleAtFixedRate(new ClockTimerTask(clockHandler), 0,
					1000);
		} finally {
			clockTimerLock.unlock();
		}
	}

	private void stopClock() {
		clockTimerLock.lock();
		try {
			clockActionButton.setText(getString(R.string.start));
			clockTimer.cancel();
			clockTimer = null;
		} finally {
			clockTimerLock.unlock();
		}
	}

}
