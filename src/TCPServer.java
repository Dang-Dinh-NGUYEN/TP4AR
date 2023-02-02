import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("listening ...");

        while(true) {
            Socket s = ss.accept();
            ClientHandler clientHandler = new ClientHandler(s);
            clientHandler.start();
        }
    }

    private static class ClientHandler extends Thread{
        private final Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream din = null;
                try {
                    din = new DataInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                String str = "", str2 = "";
                while (true) {
                    str = din.readUTF();
                    System.out.println("client says: " + str);
                    str2 = br.readLine();
                    dout.writeUTF(str2);
                    dout.flush();
                }
            }catch(IOException e){e.printStackTrace();}
        }
    }
}
