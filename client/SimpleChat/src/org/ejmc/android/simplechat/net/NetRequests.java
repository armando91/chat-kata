package org.ejmc.android.simplechat.net;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */
	public void chatGET(int seq, String url, NetResponseHandler<ChatList> handler) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				url+"?seq=" + seq);
		try {
			HttpResponse response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				handler.onSuccess(parseJSON(EntityUtils.toString(response
						.getEntity())));
			}
		} catch (ClientProtocolException e) {
			handler.onNetError();
		} catch (IOException e) {
			// TODO Gestionar este error
			handler.onNetError();
		}
	}

	private ChatList parseJSON(String stringJSON) {
		int sequence = 0;
		ChatList chatlist = null;
		ArrayList<Message> messagesList = new ArrayList<Message>();
		try {
			JSONObject jsonObject = new JSONObject(stringJSON);
			JSONArray jsonarray = jsonObject.getJSONArray("messages");
			int index = jsonarray.length();
			JSONObject jsonMessage;
			for(int i=0; i<index; i++){
				jsonMessage = jsonarray.getJSONObject(i);
				Message message = new Message(jsonMessage.get("nick")
						.toString(), jsonMessage.get("message").toString());
				messagesList.add(message);
			}
			sequence = Integer.parseInt(jsonObject.get("last_seq").toString());
			chatlist = new ChatList(sequence, messagesList);
		} catch (JSONException e) {
		}
		return chatlist;
	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	public void chatPOST(Message message, String url, NetResponseHandler<Message> handler) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setHeader("content-type", "application/json");
			String json;
			if ((json = buildJSON(message)) != null) {
				httppost.setEntity(new StringEntity(json));
				HttpResponse response = httpclient.execute(httppost);
				if (response.getStatusLine().getStatusCode() == 201) {
					handler.onSuccess(message);
				}
			}
		} catch (ClientProtocolException e) {
			Log.d("CPE:chatPOST", "Error ClientProtocol");
		} catch (IOException e) {
			Log.d("IO:chatPOST", "Error Input/Output");
		} catch (NullPointerException npe) {
			Log.d("NPE:chatPOST", "Error Null Pointer");
		}

	}

	private String buildJSON(Message msg) {
		String val = null;
		JSONObject dats = new JSONObject();
		try {
			dats.put("nick", msg.getNick());
			dats.put("message", msg.getMsg());
			val = dats.toString();
		} catch (JSONException e) {
			Log.d("JSON", "Error building JSON");
		}
		return val;
	}

}