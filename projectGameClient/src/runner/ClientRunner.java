
package runner;

import java.awt.EventQueue;
import main.*;

public class ClientRunner {
    
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run(){
                new Game();
            }
        });
    }
    
}
