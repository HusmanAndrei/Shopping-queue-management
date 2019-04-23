package models;

import java.util.concurrent.atomic.AtomicInteger;
public class Client {

    private static AtomicInteger counter = new AtomicInteger(1);
    private int id;
    private int start;
    private int serveTime;
    private int waitTime = 0;
    private boolean isServed= false;
    private boolean isWaiting = false;

    public void incrementWaitTime(){
        waitTime+=1;
    }
    public int getWaitTime(){
        return waitTime;
    }
    public boolean getIsWaiting(){
        return isWaiting;
    }
    public void setIsWaiting(){
        isWaiting = true;
    }
    public Client(int start, int serveTime){
        this.start = start;
        this.serveTime = serveTime;
        this.id = counter.getAndIncrement();
    }

    public int getId() {
        return id;
    }

    public int getStart() {
        return start;
    }
    public boolean isServed(){
        return isServed;
    }

    public void serve(){
        try {
            isWaiting = false;
            isServed = true;
            Thread.sleep(serveTime*1000);
            isServed = false;

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
