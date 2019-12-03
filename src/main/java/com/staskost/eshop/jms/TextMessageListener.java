package com.staskost.eshop.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TextMessageListener {
	
	@JmsListener(destination = "text.messagequeue")
	public void onMessage(String msg) {
		System.out.println(msg);
	}

}
