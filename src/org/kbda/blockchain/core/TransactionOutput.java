package org.kbda.blockchain.core;

import java.security.PublicKey;

import org.kbda.blockchain.util.StringUtil;

public class TransactionOutput {
	public String id;
	public PublicKey reciepient;
	public float value;
	public String parentTransactionId;
	
	public TransactionOutput(PublicKey reciepient, float value, String pratentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		
		this.id = StringUtil.applySha256(
						StringUtil.getStringFromKey(reciepient) + 
						Float.toString(value) + 
						parentTransactionId
				);
	}
	
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == this.reciepient);
	}
}
