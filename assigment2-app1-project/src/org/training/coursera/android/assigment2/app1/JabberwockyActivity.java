package org.training.coursera.android.assigment2.app1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;

public class JabberwockyActivity extends GenericWebViewActivity {

	private MediaPlayer player;

	@Override
	protected int getContentViewId() {
		return R.layout.jabberwocky_activity;
	}

	@Override
	protected void loadViewContent(WebView webView) {
		webView.loadUrl("file:///android_asset/jabberwocky.html");
	}

	@Override
	protected void onResume() {
		super.onResume();
		player = MediaPlayer.create(this, R.raw.bumer_ringtone);
		player.setLooping(true);
		player.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		player.stop();
		player.release();
	}

	public void onWikiButtonClicked(View view) {
		Intent myIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://en.wikipedia.org/wiki/Jabberwocky"));
        startActivity(myIntent);
	}

	public void onImageButtonClicked(View view) {
		webView.loadUrl("file:///android_asset/bumer_image.jpg");
	}

}
