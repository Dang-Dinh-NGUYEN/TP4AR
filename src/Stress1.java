import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Stress1 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.printf("Usage: java Stress1 n");
            return;
        }

        int n = Integer.parseInt(args[0]);

        long startTime = System.nanoTime();
        //ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < n; i++) {
            Socket s = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Client stress1 n° " + i);

            System.out.println(in.readLine());
            /*fermeture de la connexion*/
            out.close();
            in.close();
            s.close();

        }
        //executor.shutdown();
        //while (!executor.isTerminated()) {
        //}
        long endTime = System.nanoTime();
        long responseTimes = endTime - startTime;


        FileWriter writer = new FileWriter("response_times_" + n + ".csv");
        writer.append(responseTimes + ";");
        writer.flush();
        writer.close();
    }
}
