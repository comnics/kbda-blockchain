package org.kbda.blockchain.core;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.kbda.blockchain.util.StringUtil;

public class Wallet {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	

	public void generateKeyPair() {
		try {
			Security.addProvider( new BouncyCastleProvider() );
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			keyGen.initialize(ecSpec, random);
			
			KeyPair keyPair = keyGen.generateKeyPair();
			
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		System.out.printf("Gen Key Pair : [%s, %s]\n", 
				StringUtil.getStringFromKey(privateKey),
				StringUtil.getStringFromKey(publicKey)
				);
	}
}