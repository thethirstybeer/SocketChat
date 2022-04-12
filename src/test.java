import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class test extends JFrame {

    private ArrayList<String> arrayList = new ArrayList<String>();
    public JList jList;
    public test(){
        arrayList.add("Hai Ninh");
        arrayList.add("HaiNinh");
        jList = new JList(arrayList.toArray());
        setTitle("A");
        add(jList);
        pack();
        setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        new test();
    }
}
