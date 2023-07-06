package blockchain;

import java.util.List;
import java.util.Random;

public class Block {

    //create a block object for use in the chain
    int id;
    int minerId;
    long timestamp;
    String previousHash;
    String hash;
    List<Transactions> blockData;
    int magicNumber;
    long generatingTime;
    String zeros;
    int N;

    public Block(int id, int minerId, long timestamp, String previousHash, String hash, int magicNumber, List<Transactions> blockData , long generatingTime, String zeros) {
        this.id = id;
        this.minerId = minerId;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.hash = hash;
        this.magicNumber = magicNumber;
        this.blockData = blockData;
        this.generatingTime = generatingTime;
        this.zeros = zeros;
        this.N = calculateN();
    }



    public int calculategeneratingTime() {
        return 10;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int calculateN() {
        int N  = zeros.length();
        return N;

    }
}
