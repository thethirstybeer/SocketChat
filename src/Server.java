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
    public static ArrayList<String> userOnlines = new ArrayList<String>();

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
                            if(textPort.getText().equals("")){
                                JOptionPane.showMessageDialog(null, "Please input Port ?");
                            }
                            JOptionPane.showMessageDialog(null, "Server is running!");
                            serverSocket = new ServerSocket(Integer.parseInt(textPort.getText()));
                            while (true){
                                Socket socket = serverSocket.accept();
                                System.out.println("A new Client");
                                ClientHandler clientHandler = new ClientHandler(socket);
                                updateUserOnline(clientHandler.getArrayName());
                                Thread thread = new Thread(clientHandler);
                                thread.start();
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
                    serverSocket.close();
                    JOptionPane.showMessageDialog(null, "Server is close");
                    System.exit(1);
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
                while (!serverSocket.isClosed()){
                    listUser.setListData(arrayList.toArray());
                    if(serverSocket.isClosed()){
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
