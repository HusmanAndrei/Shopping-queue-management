package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulation.Simulation;

public class View {
    private ArrayList<JTextField> textArr;
    private ArrayList<JLabel> labelArr;
    private ArrayList<JLabel> labelArr2;

    private JButton b;
    private JLabel outl, out2;
    private JFrame f;
    private JPanel inputpanel1;
    private JPanel inputpanel2;
    private JPanel inputpanel0;
    private JPanel simulationpanel;
    //private JPanel testpanel;

    private Simulation simul;
    int mina;
    int maxa;

    int mins;
    int maxs;
    int nrc;
    int nrs;
    int simt;

    public View(){
        f= new JFrame();
        //f.setLayout(new BoxLayout(f,BoxLayout.Y_AXIS));
        textArr = new ArrayList<JTextField>();
        labelArr = new ArrayList<JLabel>();
        labelArr2 = new ArrayList<JLabel>(20);
        b = new JButton("Start");
        inputpanel1 = new JPanel();
        inputpanel0 = new JPanel();
        //testpanel = new JPanel();
        //testpanel.setLayout(new BoxLayout(testpanel, BoxLayout.Y_AXIS));
        //inputpanel0.setLayout(new BoxLayout(inputpanel0, BoxLayout.PAGE_AXIS));
        inputpanel1.setLayout(new BoxLayout(inputpanel1, BoxLayout.Y_AXIS));
        inputpanel2 = new JPanel();
        inputpanel2.setLayout(new BoxLayout(inputpanel2, BoxLayout.Y_AXIS));
        textArr.add(new JTextField(""));//nr c
        textArr.add(new JTextField(""));//nr s
        outl = new JLabel("Txt initial");
        out2 = new JLabel("      ");
        textArr.add(new JTextField(""));//min st
        textArr.add(new JTextField(""));//max st
        textArr.add(new JTextField(""));//min at
        textArr.add(new JTextField(""));//max at
        textArr.add(new JTextField(""));//simulation time

        labelArr.add(new JLabel("Number of Clients"));
        labelArr.add(new JLabel("Number of Servers"));
        labelArr.add(new JLabel("Min service time"));
        labelArr.add(new JLabel("Max service time"));
        labelArr.add(new JLabel("Min arrival time"));
        labelArr.add(new JLabel("Max arrival time"));
        labelArr.add(new JLabel("Simulation interval"));

        f.setSize(800,500);
        textArr.get(0).setPreferredSize(new Dimension(100,20));
        labelArr.get(0).setPreferredSize(new Dimension(30,30));
        labelArr.get(0).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(1).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(2).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(3).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(4).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(5).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        labelArr.get(6).setBorder(BorderFactory.createEmptyBorder(0,0,6,10));
        inputpanel1.add(labelArr.get(0));
        inputpanel2.add(textArr.get(0));
        inputpanel1.add(labelArr.get(1));
        inputpanel2.add(textArr.get(1));
        inputpanel1.add(labelArr.get(2));
        inputpanel2.add(textArr.get(2));
        inputpanel1.add(labelArr.get(3));
        inputpanel2.add(textArr.get(3));
        inputpanel1.add(labelArr.get(4));
        inputpanel2.add(textArr.get(4));
        inputpanel1.add(labelArr.get(5));
        inputpanel2.add(textArr.get(5));
        inputpanel1.add(labelArr.get(6));
        inputpanel2.add(textArr.get(6));
        inputpanel0.add(inputpanel1);
        inputpanel0.add(inputpanel2);
        //inputpanel2.setAlignmentY(inputpanel2.BOTTOM_ALIGNMENT);
        inputpanel0.add(b);
        b.addActionListener((arg0) -> {


                nrc= Integer.parseInt(textArr.get(0).getText());
                nrs = Integer.parseInt(textArr.get(1).getText());
                mins = Integer.parseInt(textArr.get(2).getText());
                maxs = Integer.parseInt(textArr.get(3).getText());
                mina = Integer.parseInt(textArr.get(4).getText());
                maxa = Integer.parseInt(textArr.get(5).getText());
                simt = Integer.parseInt(textArr.get(6).getText());
                simulationpanel = new JPanel();

                simulationpanel.setLayout(new BoxLayout(simulationpanel, BoxLayout.Y_AXIS));
                //outl.setAlignmentX((outl.BOTTOM_ALIGNMENT));
                //out2.setAlignmentX((out2.BOTTOM_ALIGNMENT));
                simulationpanel.add(outl);
                simulationpanel.add(out2);

                //testpanel.add(inputpanel0);
                //testpanel.add(simulationpanel);

                f.getContentPane().remove(inputpanel0);
                f.getContentPane().add(simulationpanel);
                f.validate();

                simul = (new Simulation(this,simt,nrc,nrs,mins,maxs,mina,maxa));
                new Thread(simul).start();

        });



        //testpanel.add(inputpanel0);
        //testpanel.add(simulationpanel);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setContentPane(inputpanel0);
        f.setVisible(true);
    }

    public void updateOut2(String content){
        out2.setText(content);
    }
    public void updatev(String s){
        //labelArr2.get(idQ-1).setText(s);
        outl.setText(s);
    }
}
