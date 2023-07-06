package blockchain;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Miners implements Runnable {

    private static int counter = 0;
    private int minerId;
    private Blockchain blockchain;
    private Transactions transaction;

    public Miners(Blockchain blockchain, Transactions transaction) {
        this.transaction= transaction;
        Random random = new Random();
        this.minerId = random.nextInt(9) + 1;
        this.blockchain = blockchain;
    }

    @Override
    public void run()  {

        blockchain.generateBlock(minerId, transaction); // Use the provided blockchain object

    }
}
