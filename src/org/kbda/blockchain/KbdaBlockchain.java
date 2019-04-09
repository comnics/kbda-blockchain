package org.kbda.blockchain;

import java.util.ArrayList;

import org.kbda.blockchain.core.Block;
import org.kbda.blockchain.core.Transaction;
import org.kbda.blockchain.core.Wallet;

import com.google.gson.GsonBuilder;

public class KbdaBlockchain {

	//blockchain ArrayList
	public static ArrayList<Block> blockchain = new ArrayList<Block>();

	public static int difficulty = 3;

	public static void main(String[] arg) {
		//Wallet 생성
		Wallet w1 = new Wallet();
		Wallet w2 = new Wallet();
		w1.generateKeyPair();
		w2.generateKeyPair();
		
		Transaction t1 = new Transaction(w1.publicKey, w2.publicKey, 100);
		
/*
		//초기 블럭을 만듭니다.
		blockchain.add(new Block("Genesis block", "0"));
		System.out.println("\nTrying to Mine Genesis block!");
		blockchain.get(0).mineBlock(difficulty);
		//이후 블럭을 생성합니다.
		for(int i = 1 ; i <= 10 ; i++){
			blockchain.add(new Block("block " + i, blockchain.get(blockchain.size()-1).hash));
			System.out.printf("\nTrying to Mine block #%d", i+1 );
			blockchain.get(i).mineBlock(difficulty);
		}
		//전체 blockchain이 정상인지 체크합니다.
		System.out.println("\nBlockchain is Valid : " + isChainValid());
		
		//전체 블럭을 출력합니다.
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nOpenchain Block list : ");
		System.out.println(blockchainJson);
*/
	}
	
	/**
	 * blockchain이 유효한지 체크합니다.
	 *  - 현재 블럭의 hash가 유효한 값인지 체크한다.
	 *  - 이전 블럭의 hash값과 동일한지 체크한다.
	 * @return
	 */
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;

		//전체 블럭을 체크합니다.
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			//현재 블럭의 hash가 맞는지 체크합니다.
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			
			//이전 블럭의 hash값과 동일한지 체크합니다.
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
