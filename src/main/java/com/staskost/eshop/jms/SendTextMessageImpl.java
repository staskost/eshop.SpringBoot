package com.staskost.eshop.jms;

import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendTextMessageImpl implements SendTextMessage {

	private Queue textMessageQueue;

	private JmsTemplate jmsTemplate;

	public SendTextMessageImpl(Queue textMessageQueue, JmsTemplate jmsTemplate) {
		this.textMessageQueue = textMessageQueue;
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendTextMessage(String msg) {
		this.jmsTemplate.convertAndSend(this.textMessageQueue, msg);
	}

}
