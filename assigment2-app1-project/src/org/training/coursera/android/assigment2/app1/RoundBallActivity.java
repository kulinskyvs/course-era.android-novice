package org.training.coursera.android.assigment2.app1;

import android.webkit.WebView;

public class RoundBallActivity extends GenericWebViewActivity{

	@Override
	protected void loadViewContent(WebView webView) {
		webView.loadUrl("file:///android_asset/roundball/roundball.html");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);

	}


}
