package lib;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    public static HashMap parse(LinkedList list) {
        HashMap info = new HashMap();
        String query = list.removeFirst().toString();
        query = query.substring(1, query.length() - 1);
        query = query.replace("\"", "");
        query = query.replace(",", "");
        query = query.replace(":", "");
        //System.out.println(query);
        String[] values = query.split(" ");

        for (int x = 0; x < values.length - 1; x++) {
            String value = values[x + 1];
            info.put(values[x], value);
            x++;
        }

        return info;
    }


}
