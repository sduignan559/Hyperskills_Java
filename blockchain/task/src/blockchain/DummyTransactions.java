package blockchain;

import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
public class DummyTransactions {

    ArrayList<Transactions> trasactionlist= new ArrayList<>();
    HashMap<String, PublicKey> publicKeys = new HashMap<>();

    public void generateTransactions() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();
        keyPairGenerator.initialize(2048, secureRandom);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        Transactions transaction1 = new Transactions("1","2",50,privateKey);
        Transactions transaction2 = new Transactions("1","2",5,privateKey);
        Transactions transaction3 = new Transactions("1","2",6,privateKey);
        Transactions transaction4 = new Transactions("1","2",4,privateKey);
        Transactions transaction5 = new Transactions("1","2",3,privateKey);
        Transactions transaction6 = new Transactions("1","2",3,privateKey);
        Transactions transaction7 = new Transactions("1","2",3,privateKey);
        Transactions transaction8 = new Transactions("1","2",3,privateKey);
        Transactions transaction9 = new Transactions("1","2",3,privateKey);
        Transactions transaction10 = new Transactions("1","2",4,privateKey);
        Transactions transaction11 = new Transactions("1","2",3,privateKey);
        Transactions transaction12 = new Transactions("1","2",3,privateKey);
        Transactions transaction13 = new Transactions("1","2",6,privateKey);
        Transactions transaction14 = new Transactions("1","2",3,privateKey);
        Transactions transaction15 = new Transactions("1","2",3,privateKey);


        trasactionlist.add(transaction1);
        trasactionlist.add(transaction2);
        trasactionlist.add(transaction3);
        trasactionlist.add(transaction4);
        trasactionlist.add(transaction5);
        trasactionlist.add(transaction6);
        trasactionlist.add(transaction7);
        trasactionlist.add(transaction8);
        trasactionlist.add(transaction9);
        trasactionlist.add(transaction10);
        trasactionlist.add(transaction11);
        trasactionlist.add(transaction12);
        trasactionlist.add(transaction13);
        trasactionlist.add(transaction14);
        trasactionlist.add(transaction15);

        publicKeys.put(transaction1.recipient, publicKey);
    }
}
