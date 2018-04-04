package ChatServer;

import java.util.Stack;

public class CheckOutput {

    private static NetworkConnection connection = Client.getInstance();
    static Stack<String> messages = new Stack<>();

    public boolean checkValid() {
        return true;
    }

}
