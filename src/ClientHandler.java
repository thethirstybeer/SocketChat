import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    public static ArrayList<String> usersName = new ArrayList<String>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String usernameClient;

    public ClientHandler(String msg) throws IOException{
        sendMsg(msg);
        clientHandlers.removeAll(clientHandlers);
    }

    public ClientHandler(){

    }

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.usernameClient = bufferedReader.readLine();
        clientHandlers.add(this);
        usersName.add(this.usernameClient);
        sendMsg("Server: " + usernameClient + " đã vào phòng.");
    }


    public void sendMsg(String msg){
        for(int i = 0; i < clientHandlers.size(); i++){
            if(!clientHandlers.get(i).usernameClient.equals(usernameClient)){
                try {
                    clientHandlers.get(i).bufferedWriter.write(msg);
                    clientHandlers.get(i).bufferedWriter.newLine();
                    clientHandlers.get(i).bufferedWriter.flush();
                }catch (IOException e){
                    closeClient(socket, bufferedReader, bufferedWriter);
                }
            }
        }
    }

    public void sendFile(){

    }

    public void closeClient(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClient();
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

    public void removeClient(){
        clientHandlers.remove(this);
        usersName.remove(usernameClient);
        sendMsg("Server: " + usernameClient + " đã thoát khỏi phòng!");
    }

    public String getName(){
        return usernameClient;
    }

    @Override
    public void run() {
        String msgGroup = "";
        while (socket.isConnected()){
            try {
                msgGroup = bufferedReader.readLine();
                sendMsg(msgGroup);
            }catch (IOException e){
                closeClient(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public ArrayList<ClientHandler> clientHandlers(){
        return  clientHandlers;
    }

    public ArrayList<String> getArrayName(){
        return usersName;
    }


}
