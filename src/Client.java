import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends JFrame {
    public static ArrayList<String> listName = new ArrayList<>();
    private JPanel Client;
    private JTextField msgTextField;
    private JButton sendMessageButton;
    private JTextArea msg_Group;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private JScrollPane jScrollPane;

    public Client(Socket socket, String username) throws IOException {
        setTitle("Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(Client);
        pack();
        setVisible(true);
        this.socket = socket;
        this.username = username;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//      Set name
        bufferedWriter.write(username);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        sendMessageButton.addActionListener(new ActionListener() {
            String msgSend = "";
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    msgSend = msgTextField.getText();
                    bufferedWriter.write(username + ": " + msgSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    msgTextField.setText("");
                    msg_Group.setText(msg_Group.getText() + "\nYou: " + msgSend);
                    pack();
                }catch (IOException f){
                    f.printStackTrace();
                }

            }
        });
    }

    public void listenMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgGroup = "";
                while (socket.isConnected()){
                    try {
                        msgGroup = bufferedReader.readLine();
                        msg_Group.setText(msg_Group.getText() + "\n" + msgGroup);
                        pack();
                    }catch (IOException e){

                    }
                }
            }
        }).start();
    }

    public void closeClient(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
