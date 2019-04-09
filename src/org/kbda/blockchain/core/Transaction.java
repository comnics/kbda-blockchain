package org.kbda.blockchain.core;

import java.security.PublicKey;

import org.kbda.blockchain.util.StringUtil;

public class Transaction {
	public String transactionHash;
	public PublicKey sender;
	public PublicKey recipient;
	public float value;
	public byte[] signature;
	
	public Transaction(PublicKey from, PublicKey to, float value) {
		super();
		this.sender = from;
		this.recipient = to;
		this.value = value;
	}
	
	public String toString() {
		return String.format("[Transaction Info] From : %s, To : %s, Value : %f\r\n", 
				StringUtil.getStringFromKey(sender),
				StringUtil.getStringFromKey(sender),
				value);
	}
}
