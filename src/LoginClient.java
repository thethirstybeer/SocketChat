import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LoginClient extends JFrame {
    private JPanel panel1;
    private JTextField textName;
    private JTextField textPort;
    private JButton loginButton;
    private JButton clearButton;

    public LoginClient(){
        setTitle("Login");
        add(panel1);
        pack();
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textName.getText().equals("") && !textPort.getText().equals("")){
                    try {
                        setVisible(false);
                        Socket socket = new Socket("localhost", Integer.parseInt(textPort.getText()));
                        Client client = new Client(socket, textName.getText());
                        client.listenMsg();
                    }catch (IOException f){

                    }
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPort.setText("");
                textName.setText("");
            }
        });
    }

    public static void main(String[] args) {
        new LoginClient();
    }
}
