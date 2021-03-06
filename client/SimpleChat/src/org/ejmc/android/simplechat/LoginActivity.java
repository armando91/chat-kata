package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

	private Button loginButton;
	private TextView loginText;
	private TextView urlText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginButton = (Button) findViewById(R.id.submitLoginButton);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginChat(v);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void loginChat(View view) {
		loginText = (TextView) findViewById(R.id.loginText);
		String myNick = loginText.getText().toString();
		urlText = (TextView) findViewById(R.id.urlField);
		String url = urlText.getText().toString();
		try {
			if (myNick.compareTo("") != 0) {
				SharedPreferences settings = getSharedPreferences("userInfo",
						MODE_PRIVATE);
				SharedPreferences.Editor mEdit = settings.edit();
				mEdit.putString("userNick", myNick);
				mEdit.putString("url", url);
				mEdit.commit();
				Intent loginIntent = new Intent(this, ChatActivity.class);
				startActivity(loginIntent);
			} else {
				Toast notification = Toast.makeText(getApplicationContext(),
						"Nick needed!!", Toast.LENGTH_SHORT);
				notification.show();
			}
		} catch (NullPointerException npe) {
			Toast notification = Toast.makeText(getApplicationContext(),
					"Fatal error!", Toast.LENGTH_SHORT);
			notification.show();
		}
	}

	public void salir(View view) {
		Toast toast = Toast.makeText(getApplicationContext(), "Bye",
				Toast.LENGTH_SHORT);
		toast.show();
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//Solamente usar como ultimo recurso.
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//	}

	@Override
	protected void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		String nick = loginText.getText().toString();
		String url = urlText.getText().toString();
		if (nick != null) {
			savedState.putString("nick", nick);
		}
		if (url != null) {
			savedState.putString("url", url);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		if (savedState != null) {
			String nick = savedState.getString("nick");
			String url = savedState.getString("url");
			loginText.setText(nick);
			urlText.setText(url);
		}
	}
}