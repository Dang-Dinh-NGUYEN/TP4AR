import java.io.*;
import java.net.Socket;

public class Stress1 {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.printf("Usage: java Stress1 n");
            return;
        }

        int n = Integer.parseInt(args[0]);

        for(int i = 0; i < n; i++){
            Socket s = new Socket("localhost", 1234);

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            dout.writeUTF("(LINUX) client stress1 n° " + i);
            dout.flush();
            dout.close();
            //s.close();
            //System.out.println("connection closed");

        }
    }
}
