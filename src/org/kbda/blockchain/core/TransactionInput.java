package org.kbda.blockchain.core;

public class TransactionInput {
	public TransactionOutput UTXO;
	public String transactionOutputId;
	
	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
