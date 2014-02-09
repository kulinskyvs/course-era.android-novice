package org.training.coursera.android.assigment3.app1;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class IdeaSaverActivity extends Activity implements TextWatcher {

	private static final String PREF_NAME_KEY = "name";

	private static final String BUNDLE_KEY_NAME = "name";
	private static final String BUNDLE_KEY_THOUGHT = "thought";
	private static final String BUNDLE_KEY_SHARE_TYPE = "sharingType";

    private EditText mNameEditText;
    private EditText mThoughtsEditText;
    private TextView mMessageTextView;

    private Button mClearButton;
    private Button mShareButton;
    private Spinner mShareTypeSpinner;

    private DateFormat mLocalDateTimeFormat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_saver);

		mLocalDateTimeFormat = DateFormat.getDateTimeInstance();

		mShareTypeSpinner = (Spinner) findViewById(R.id.shareTypeSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.share_options, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mShareTypeSpinner.setAdapter(adapter);

		mNameEditText = (EditText)findViewById(R.id.nameEditText);
		mThoughtsEditText = (EditText)findViewById(R.id.thoughsEditText);
		mMessageTextView = (TextView)findViewById(R.id.messageTextView);
		mClearButton = (Button)findViewById(R.id.buttonClear);
		mShareButton = (Button)findViewById(R.id.buttonShareWith);

		mShareButton.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						//store name in this case in preferences
						getPreferences(MODE_PRIVATE).edit().
						   putString(PREF_NAME_KEY, mNameEditText.getText().toString()).
						   commit();

						int selectedItemId = (int)mShareTypeSpinner.getSelectedItemId();
						switch (selectedItemId) {
						   case 0:
							   shareByEmail(mMessageTextView.getText().toString());
							   return;
						   case 1:
							   shareBySms(mMessageTextView.getText().toString());
							   return;
							default:
								Toast.makeText(IdeaSaverActivity.this,
										R.string.select_share_type, Toast.LENGTH_LONG).show();
						}

					}
				});

		mNameEditText.addTextChangedListener(this);
		mThoughtsEditText.addTextChangedListener(this);

		//restore name from preferences
		mNameEditText.setText(getPreferences(MODE_PRIVATE).getString(PREF_NAME_KEY, ""));

		//restore previous state
		if (null != savedInstanceState) {

			if (savedInstanceState.containsKey(BUNDLE_KEY_NAME))
				mNameEditText.setText(savedInstanceState.getString(BUNDLE_KEY_NAME));

			if (savedInstanceState.containsKey(BUNDLE_KEY_THOUGHT))
				mThoughtsEditText.setText(savedInstanceState.getString(BUNDLE_KEY_THOUGHT));

			mShareTypeSpinner.setSelection(savedInstanceState.getInt(BUNDLE_KEY_SHARE_TYPE));
		}

		//rebuild message
		rebuildMessage();
	}


	@Override
	protected void onResume() {
		super.onResume();
		rebuildMessage();
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (!isEmpty(mNameEditText.getText().toString()))
			outState.putCharSequence(BUNDLE_KEY_NAME, mNameEditText.getText());
		if (!isEmpty(mThoughtsEditText.getText().toString()))
			outState.putCharSequence(BUNDLE_KEY_THOUGHT, mThoughtsEditText.getText());
		outState.putInt(BUNDLE_KEY_SHARE_TYPE, mShareTypeSpinner.getSelectedItemPosition());
	}


	public void onClearButtonClicked(View view) {
		mNameEditText.getText().clear();
		mThoughtsEditText.getText().clear();
		mNameEditText.setText(getPreferences(MODE_PRIVATE).getString(PREF_NAME_KEY, ""));
		rebuildMessage();
	}


	private void rebuildMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		String name = mNameEditText.getText().toString();
		String thought = mThoughtsEditText.getText().toString();

		if (!isEmpty(name))
			messageBuilder.append(name + " wants to share with you his(her) idea that came to mind on ").
			append(mLocalDateTimeFormat.format(new Date())).append(" :");

		if (!isEmpty(thought))
			messageBuilder.append("\n \"").append(thought).append("\" ");

		String newMessage = messageBuilder.toString();
		if (!isEmpty(newMessage)) {
			mMessageTextView.setText(newMessage);
			mClearButton.setVisibility(View.VISIBLE);
		} else {
			mMessageTextView.setText("");
			mClearButton.setVisibility(View.INVISIBLE);
			mShareButton.setVisibility(View.INVISIBLE);
			mShareTypeSpinner.setVisibility(View.INVISIBLE);
		}

		if (!isEmpty(name) && !isEmpty(thought)) {
			mShareButton.setVisibility(View.VISIBLE);
			mShareTypeSpinner.setVisibility(View.VISIBLE);
		} else {
			mShareButton.setVisibility(View.INVISIBLE);
			mShareTypeSpinner.setVisibility(View.INVISIBLE);
		}
	}


	@Override
	public void afterTextChanged(Editable s) {
        rebuildMessage();
    }


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		//just do nothing
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//just do nothing
	}

	private static final boolean isEmpty(String value) {
		return null == value || value.isEmpty();
	}


	private void shareBySms(String message) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("sms:1234567890"));
		smsIntent.putExtra("sms_body", message);
		try {
			startActivity(smsIntent);
		} catch(ActivityNotFoundException ex) {
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}

	}

	private void shareByEmail(String message) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		try {
			startActivity(emailIntent);
		} catch(ActivityNotFoundException ex) {
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

}
