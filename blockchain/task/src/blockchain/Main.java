package blockchain;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {

        Blockchain blockchain = new Blockchain();
        blockchain.initializeBlockchain();

        // Create an ExecutorService with a fixed thread pool size
        int numMiners = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numMiners);

        for (int i = 1; i < 15; i++) {

            //get VC transaction
            DummyTransactions dummy = new DummyTransactions();
            dummy.generateTransactions();

            Transactions transaction = dummy.trasactionlist.get(i);
            //validate the transaction
            boolean signatureIsValid = transaction.verifySignature(dummy.publicKeys.get(transaction.recipient));
            boolean SenderHasEnoughCrypto = blockchain.verifyblaance(transaction.sender, transaction.amount);

            if (signatureIsValid && SenderHasEnoughCrypto) {
                Miners miner = new Miners(blockchain, transaction);
                executorService.submit(miner);
            }
        }


        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        blockchain.printBlockchain();


        //Validate the blockchain
        //boolean isValidchain = blockchain.isValid();
        //System.out.println("Blockchain is valid: " + isValidchain);
    }

}









