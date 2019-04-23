package view;
import javax.swing.*;
public class Main {

    public static void main(String[] args) {
        //View view = new View();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Model model = new Model();
                View view = new View();
                //new Controller(model, view);
            }
        });
        //view.setVisible(true);
    }

}
