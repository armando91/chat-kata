package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
		TextView tv = (TextView) findViewById(R.id.chatText);
		String myNick = tv.getText().toString();
		try {
			if (myNick.compareTo("") != 0) {
				SharedPreferences settings = getSharedPreferences("userInfo", MODE_PRIVATE);
				SharedPreferences.Editor mEdit= settings.edit();
				mEdit.putString("userNick", myNick);
				mEdit.commit();
				Intent loginIntent = new Intent(this, ChatActivity.class);
				startActivity(loginIntent);
			} else {
				Toast notification = Toast.makeText(getApplicationContext(), "Put your nick!", Toast.LENGTH_SHORT);
				notification.show();
			}
		} catch (NullPointerException npe) {
			Toast notification = Toast.makeText(getApplicationContext(), "Fatal error!", Toast.LENGTH_SHORT);
			notification.show();
		}
	}
}