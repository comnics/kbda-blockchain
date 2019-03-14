package org.kbda.blockchain;

import org.kbda.bockchain.core.Block;

public class KbdaBlockchain {

	public static int difficulty = 5;

	public static void main(String[] arg) {

		//블럭을 만듭니다.
		Block block = new Block("Genesis block", "0");
		block.mineBlock(difficulty);

	}

}
