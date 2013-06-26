package org.ejmc.android.simplechat.model;

import java.util.ArrayList;

/**
 * List off chat messages..
 * 
 * @author startic
 * 
 */
public class ChatList {

	private int sequence; 
	private ArrayList<Message> messagessList;

	public ChatList(int sequence, ArrayList<Message> messagessList) {
		this.sequence = sequence;
		this.messagessList = messagessList;
	}
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public ArrayList<Message> getMessagessList() {
		return messagessList;
	}

	public void setMessagessList(ArrayList<Message> messagessList) {
		this.messagessList = messagessList;
	}

}