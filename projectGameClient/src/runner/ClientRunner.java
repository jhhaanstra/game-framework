
package runner;

import java.awt.EventQueue;
import javafx.application.Application;
import main.*;

public class ClientRunner {
    
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run(){
                Application.launch(Game.class);
                new Game();               
            }
        });
    }
    
}
