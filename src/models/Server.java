package models;

import simulation.Simulation;

//import javax.swing.*;
import java.util.ArrayDeque;
//import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

//import static java.lang.Thread.sleep;

public class Server implements Runnable{

    private Simulation simulation;
    private static AtomicInteger counter = new AtomicInteger(1);
    private int id;
    private int peakSecond = 0;



    private boolean done = false;


    private Queue<Client> clients = new ArrayDeque<>();

    public Server(Simulation simulation){
        this.simulation = simulation;
        id = counter.getAndIncrement();
    }

    public void setPeakSecond(int newPeak){
        this.peakSecond = newPeak;
    }
    public int getPeakSecond(){
        return peakSecond;
    }

    @Override
    public synchronized void run() {
        try {
            while (!done) {
                Client c = clients.peek();
                if (c != null) {
                    c.serve();
                    removeTop();
                    simulation.update();
                    Thread.sleep(10);
                    //System.out.println(this);
                }
                else{
                    //System.out.println(this);
                    //wait();
                    Thread.sleep(20);
                }
            }
            //wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void addClient(Client c){
        simulation.update();
        clients.add(c);
    }
    public synchronized void removeTop(){
        clients.remove();
    }

    public void setDone(){
        done = true;
    }

    public int getQueueSize(){
        return clients.size();
    }

    public String toString(){
        StringBuilder repr = new StringBuilder("Server: " + id + ": [");
        for (Client c: clients){
            repr.append("C" + c.getId() +" ,");
        }
        repr.append("]");
        return repr.toString();
    }

    public int getId() {
        return id;
    }
}
