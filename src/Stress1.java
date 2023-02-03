import java.io.*;
import java.net.Socket;

public class Stress1 {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.printf("Usage: java Stress1 n");
            return;
        }

        int n = Integer.parseInt(args[0]);

        long[] responseTimes = new long[n];

        for(int i = 0; i < n; i++){
            Socket s = new Socket("localhost", 1234);

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            long startTime = System.nanoTime();
            dout.writeUTF("(LINUX) client stress1 n° " + i);
            dout.flush();
            dout.close();
            /*fermeture de la connexion*/
            s.close();
            long endTime = System.nanoTime();
            responseTimes[i] = endTime - startTime;
        }

        FileWriter writer = new FileWriter("response_times_" + n + ".csv");
        for (int i = 0; i < n; i++) {
            writer.append(responseTimes[i] + ";");
        }
        writer.flush();
        writer.close();
    }
}
