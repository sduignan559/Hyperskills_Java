package blockchain;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;



public class Transactions {

    String sender;
    String recipient;
    int amount;
    byte[] signature;

    public Transactions(String sender, String recipient, int amount, PrivateKey privateKey) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.signature = sign(privateKey);
    }

    public Transactions(String recipient, int amount) {
        this.recipient = recipient;
        this.amount = amount;
    }

    private byte[] sign(PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update((sender + recipient + amount).getBytes());
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public boolean verifySignature(PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update((sender + recipient + amount).getBytes());
            return signature.verify(this.signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }
}