package simulation;

import models.Client;
import models.Server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import view.View;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation implements Runnable {

    private int simulationTime;
    private int tick = 0;
    private View v;
    private int noServers;
    private int noClients;
    private int minServeTime;
    private int maxServeTime;
    private int emptyTime = 0;
    private int serveTime = 0;

    private int minArrival;
    private int maxArrival;

    private List<Server> serverList = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private Map<Server, Integer> maxClients = new HashMap<>();

    public Simulation(View v,int simulationTime,int noClients, int noServers, int minServeTime, int maxServeTime, int minArrival, int maxArrival) {
        this.simulationTime = simulationTime;
        this.v = v;
        this.noServers = noServers;
        this.noClients  = noClients;
        this.minServeTime = minServeTime;
        this.maxServeTime = maxServeTime;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        try {
            System.setOut(new PrintStream("output.txt"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void createServers(){

        for (int i = 0 ; i < noServers; ++i){
            Server s = new Server(this);
            serverList.add(s);
            maxClients.put(s, 0);
        }
    }

    private static int getRandom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void createClients(){
        for(int i = 0; i < noClients; i++){
            int start = getRandom(minArrival, maxArrival);
            int serve = getRandom(minServeTime, maxServeTime);
            clientList.add(new Client(start, serve));
        }
    }

    public void update(){

        StringBuilder repr = new StringBuilder("");
        for (Server s : serverList){
            repr.append(s.getId()).append(" : ").append( s.toString()).append('\n');
        }
        System.out.println( repr.toString());
        v.updatev(repr.toString());

    }

    public Server getLeastEmpty(){
        /*return serverList.sort((s1, s2) -> {
            if(s1.getQueueSize()< s2.getQueueSize())
                return
        });*/
        Server smin= serverList.get(0);
        for (Server s : serverList){
            if (s.getQueueSize()<smin.getQueueSize()){
                smin=s;
            }
        }
        return smin;
    }

    @Override
    public synchronized void run(){
        createClients();
        createServers();
        for (int i = 0 ; i < serverList.size(); i++){
            new Thread(serverList.get(i)).start();
        }
        for(tick = 1 ; tick <= simulationTime; tick++){
            for (Client c : clientList){
                if (c.getStart()==tick){
                    c.setIsWaiting();
                    getLeastEmpty().addClient(c);
                }
                if (c.getIsWaiting()){
                    c.incrementWaitTime();
                }
                if (c.isServed()){
                    serveTime += 1;
                }
            }
            for (Server s : serverList){
                int nrClients = s.getQueueSize();
                if (maxClients.get(s) < nrClients){
                    maxClients.put(s, nrClients);
                    s.setPeakSecond(tick);
                }
                if (nrClients == 0){
                    emptyTime += 1;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Server server : serverList){
            server.setDone();
        }
        setStatistics();
    }

    public void setStatistics(){
        int avgServe = serveTime/ noClients;
        StringBuilder peak = new StringBuilder();
        Map<Server, Integer> peakTime = getPeakServers();
        for (Server s: peakTime.keySet()){
            peak.append("Server[" ).append(s.getId()).append("] -> ").append(peakTime.get(s)).append(" ");
        }
        String finalString = "Average serve time: " + avgServe + "Empty queue time:   " + emptyTime + " Average wait time: " + getAvgWaitTime() + " " + peak.toString();
        v.updateOut2(finalString);
    }

    public int getAvgWaitTime(){
        int sum = 0;
        for(Client c: clientList){
            sum += c.getWaitTime();
        }
        return sum / noClients;
    }

    public Map<Server, Integer> getPeakServers(){
        Map<Server, Integer> waitTime = new HashMap<>();
        for (Server s: serverList){
            waitTime.put(s, s.getPeakSecond());
        }
        return waitTime;
    }


}
