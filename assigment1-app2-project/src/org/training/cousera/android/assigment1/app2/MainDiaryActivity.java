package org.training.cousera.android.assigment1.app2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainDiaryActivity extends Activity {

	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");

	private static String DIARY_AUTHOR_STATE = "state.author";
	private static String DIARY_MESSAGE_STATE = "state.message";
	private static String DIARY_CONTENT_STATE = "state.diaryContent";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_diary);

		//restore state is it's presented
		if (null != savedInstanceState) {
			getAuthorEditText().setText(savedInstanceState.getString(DIARY_AUTHOR_STATE));
			getMessageEditText().setText(savedInstanceState.getString(DIARY_MESSAGE_STATE));
			getDiaryContentView().setText(savedInstanceState.getString(DIARY_CONTENT_STATE));
		}
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		//save my activity state
		outState.putString(DIARY_AUTHOR_STATE, getAuthorEditText().getText().toString());
		outState.putString(DIARY_MESSAGE_STATE, getMessageEditText().getText().toString());
		outState.putString(DIARY_CONTENT_STATE, getDiaryContentView().getText().toString());
	}


	/**
	 * Save message action handler
	 *
	 * @param view
	 */
	public void saveMessageToDiary(View view) {
		String actorName = getAuthorEditText().getText().toString();
		String message = getMessageEditText().getText().toString();

		//do not process further if either actor name or message are not specified
		if (null == actorName || null == message ||
				"".equals(actorName) || "".equals(message))
			return;

		String messageText = DATE_FORMATTER.format(new Date())+" "+actorName+": "+message;
		String diaryText =
			 (null != getDiaryContentView().getText()) ?
					 getDiaryContentView().getText().toString() : "";

		diaryText = messageText + "\n\n"+ diaryText;
		getDiaryContentView().setText(diaryText);

		//clear message after adding it to the diary
		getMessageEditText().setText("");
	}

	private EditText getAuthorEditText() {
		return (EditText)findViewById(R.id.authorEditText);
	}

	private EditText getMessageEditText() {
		return (EditText)findViewById(R.id.messageEditText);
	}

	private TextView getDiaryContentView() {
		return (TextView)findViewById(R.id.diaryTextView);
	}
}
