package com.vkulinsky.coursera.android.novice.week1_project;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(AppConfigurator.COMMON_LOG_TAG, "main activity is created");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		Log.e(AppConfigurator.BANANA_LOG_TAG, "Just a simple error message for test purposes");
		return true;
	}

}
