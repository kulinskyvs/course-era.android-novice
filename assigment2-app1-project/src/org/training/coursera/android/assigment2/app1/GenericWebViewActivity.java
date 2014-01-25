package org.training.coursera.android.assigment2.app1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public abstract class GenericWebViewActivity extends Activity {

	protected WebView webView;

	/**
	 * Load web view content
	 *
	 * @param webView
	 */
	protected abstract void loadViewContent(WebView webView) ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentViewId());

		webView = getWebViewControl();
		webView.getSettings().setBuiltInZoomControls(true);
		loadViewContent(webView);
	}


	/**
	 * Return default content view id
	 * @return default content view
	 */
	protected int getContentViewId() {
		return R.layout.simple_web_view_activity;
	}

	protected WebView getWebViewControl() {
		return (WebView)findViewById(R.id.mainWebView);
	}


}
