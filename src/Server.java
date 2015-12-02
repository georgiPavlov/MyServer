import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by georgipavlov on 01.12.15.
 */
public class Server {
    private String in;
    private String out;
    ServerSocket server;
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private int key;

    public Server(){
        runTheServer();
    }

    public void runTheServer(){
        try {
            server = new ServerSocket(6666);
            socket = server.accept();
            //socket = new Socket("localhost" ,66666); client !!!!
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started");

        while (!in.equals("stop")){
            try {
                in = inputStream.readUTF();
                in = decode(in , true);
                System.out.println("Client says"  + in);
                out = scanner.nextLine();
                out = decode(out, false);
                outputStream.writeUTF(out);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.close();
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private  String decode( String in , boolean decode){
        if(!decode){
            key *=-1;
        }
        StringBuilder tempB = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            if(Character.isWhitespace(tempB.charAt(i))){
                tempB.append(in.charAt(i));
                continue;
            }
            tempB.append((char)(((int)in.charAt(i))+key));
        }
        return tempB.toString();
    }
}
