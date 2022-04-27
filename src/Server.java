import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends JFrame {
    private JPanel Server;
    private JTextField textPort;
    private JButton startButton;
    private JButton stopButton;
    private  JList listUser;
    private ServerSocket serverSocket;
    private ArrayList<String> userOnlines;


    public Server() throws IOException {
        setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(Server);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(!textPort.getText().equals("")){
                                textPort.setEnabled(false);
                                JOptionPane.showMessageDialog(null, "Server is running!");
                                serverSocket = new ServerSocket(Integer.parseInt(textPort.getText()));
                                stopButton.setEnabled(true);
                                startButton.setEnabled(false);
                                while (true){
                                    Socket socket = serverSocket.accept();
                                    System.out.println("A new Client");
                                    ClientHandler clientHandler = new ClientHandler(socket);
                                    updateUserOnline(clientHandler.getArrayName());
                                    Thread thread = new Thread(clientHandler);
                                    thread.start();
                                }
                            }else {
                                JOptionPane.showMessageDialog(null, "Please Input Port !");
                            }
                        }catch (IOException f){

                        }
                    }
                }).start();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(null, "Server is close");
                    textPort.setEnabled(true);
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    serverSocket.close();
                    ClientHandler clientHandler = new ClientHandler("Server disconnecting please Exit Application");
                }catch (IOException f){

                }
            }
        });
        pack();
        setVisible(true);
    }


    public void updateUserOnline(ArrayList<String> arrayList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] empty = {};
                while (!serverSocket.isClosed()){
                    listUser.setListData(arrayList.toArray());
                    pack();
                    if(serverSocket.isClosed()){
                        listUser.setListData(empty);
                        break;
                    }
                }
            }
        }).start();

    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

}
