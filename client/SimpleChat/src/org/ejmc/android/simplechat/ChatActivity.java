package org.ejmc.android.simplechat;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	private String userNick;
	private String url;
	private TextView sendText;
	private NetRequests nRequests;
	private NetResponseHandler<Message> netResponseHandlerSend;
	private NetResponseHandler<ChatList> netResponseHandlerGet;
	private Timer timer;

	private ScrollView scrollChat;
	private TextView chat;

	private Handler handlerSend = new Handler() {
		public void handleMessage(android.os.Message notification) {
			Toast toast = Toast.makeText(getApplicationContext(),
					notification.obj.toString(), Toast.LENGTH_SHORT);
			toast.show();
		}
	};

	private Handler handlerGet = new Handler() {
		public void handleMessage(android.os.Message chatList) {
			ArrayList<Message> messages = ((ChatList) chatList.obj)
					.getMessagessList();
			for (Message _msg : messages) {
				chat.append("[" + _msg.getNick() + "]: " + _msg.getMsg() + "\n");
				scrollChat.fullScroll(View.FOCUS_DOWN);
			}
		}
	};

	private TimerTask tareaTimer = new TimerTask() {
		int sequence = 0;

		@Override
		public void run() {
			netResponseHandlerGet = new NetResponseHandler<ChatList>();
			new NetRequests().chatGET(sequence, url, netResponseHandlerGet);
			android.os.Message chatlist = new android.os.Message();
			chatlist.obj = netResponseHandlerGet.getDatos();
			sequence = ((ChatList) chatlist.obj).getSequence();
			handlerGet.sendMessage(chatlist);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		new Thread(new Runnable() {

			@Override
			public void run() {
				timer = new Timer();
				timer.schedule(tareaTimer, 0, 1000);
			}
		}).start();

		this.userNick = getSharedPreferences("userInfo", MODE_PRIVATE)
				.getString("userNick", "unKnown");
		this.url = getSharedPreferences("userInfo", MODE_PRIVATE).getString(
				"url", "Wrong Server");
		// Show the Up button in the action bar.
		setupActionBar();
		sendText = (TextView) findViewById(R.id.inputChat);
		nRequests = new NetRequests();
		netResponseHandlerSend = new NetResponseHandler<Message>();
		chat = (TextView) findViewById(R.id.chatText);
		scrollChat = (ScrollView) findViewById(R.id.scrollChat);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

	public void sendMessage(View view) {
		final String text = sendText.getText().toString();
		sendText.setText("");
		new Thread(new Runnable() {
			@Override
			public void run() {
				nRequests.chatPOST(new Message(userNick, text), url,
						netResponseHandlerSend);
				String controlSend = netResponseHandlerSend.getDatos() != null ? "Message Sent"
						: "Error";
				android.os.Message notification = new android.os.Message();
				notification.obj = controlSend;
				handlerSend.sendMessage(notification);
			}
		}).start();

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
		 timer.cancel();
	}

	@Override
	protected void onStop() {
		super.onStop();
		 timer.cancel();
	}

	@Override
	protected void onRestart() {
		 timer.schedule(tareaTimer, 0, 1000);
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		 timer.cancel();
		super.onDestroy();
	}
}