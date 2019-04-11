package org.kbda.blockchain.core;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import org.kbda.blockchain.KbdaBlockchain;
import org.kbda.blockchain.util.StringUtil;

public class Transaction {
	public String transactionHash;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	
	public String signData;
	public byte[] signature;

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private static int sequence = 0;

	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		super();
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
		
		this.signData = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
	}

	private String calculateHash() {
		sequence++;
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence);
	}

	// transaction에 서명한다.
	public void generateSignature(PrivateKey privateKey) {
		signature = StringUtil.applyECDSASig(privateKey, this.signData);
	}

	// transaction이 조작없이 맞는지 검증한다.
	public boolean verifySignature() {
		return StringUtil.verifyECDSASig(sender, this.signData, signature);
	}
	
	public boolean processTransaction() {
		if(verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		
		for(TransactionInput i : inputs) {
			i.UTXO = KbdaBlockchain.UTXOs.get(i.transactionOutputId);
		}
		
		if(getInputsValue() < KbdaBlockchain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small: " + getInputsValue());
			return false;
		}
		
		//generate transaction outputs:
		float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
		transactionHash = calculateHash();
		outputs.add(new TransactionOutput( this.reciepient, value, transactionHash)); //send value to recipient
		outputs.add(new TransactionOutput( this.sender, leftOver, transactionHash)); //send the left over 'change' back to sender		
				
		//add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			KbdaBlockchain.UTXOs.put(o.id , o);
		}
		
		//remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			KbdaBlockchain.UTXOs.remove(i.UTXO.id);
		}
		
		return true;		
	}
	
	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			total += i.UTXO.value;
		}
		return total;
	}

//returns sum of outputs:
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}

	public String toString() {
		return String.format("[Transaction Info] From : %s, To : %s, Value : %f\r\n",
				StringUtil.getStringFromKey(sender), StringUtil.getStringFromKey(reciepient), value);
	}
}
