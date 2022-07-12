package com.sist.nono.model;

import javax.websocket.Session;

public class ChatSession {
	Session session;
	int cr_no;
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public int getCr_no() {
		return cr_no;
	}
	public void setCr_no(int cr_no) {
		this.cr_no = cr_no;
	}
	
}
