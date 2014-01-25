package org.training.coursera.android.assigment2.app1;

import android.webkit.WebView;

public class NasaActivity extends GenericWebViewActivity{

	@Override
	protected void loadViewContent(WebView webView) {
		webView.loadUrl("file:///android_asset/uofi-at-nasa.html");
	}

}
