package org.ejmc.android.simplechat.model;

/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {

	private String nick;
	private String msg;

	public Message(String nick, String msg) {
		this.nick = nick;
		this.msg = msg;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}