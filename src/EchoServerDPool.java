import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerDPool {
    public static void main(String[] args) throws IOException {
        int port = 0;
        int nbThreads = 0;

        // analyse de la ligne de commande
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t")) {
                nbThreads = Integer.parseInt(args[++i]);
            } else {
                port = Integer.parseInt(args[i]);
            }
        }

        // vérification de la validité du n° de port
        if (port <= 1024) {
            System.err.println("Port non valide");
            System.exit(1);
        }

        // création du pool de threads
        ExecutorService pool;
        if (nbThreads > 0) {
            pool = Executors.newFixedThreadPool(nbThreads);
        } else {
            pool = Executors.newCachedThreadPool();
        }

        // création et démarrage du serveur
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serveur démarré");
            while (true) {
                Socket connection = server.accept();
                Runnable task = new EchoTask(connection);
                pool.execute(task);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        }
    }
}

class EchoTask implements Runnable {
    private Socket connection;

    public EchoTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        ) {
            while (true) {
                String request = in.readLine();
                if (request == null) {
                    break;
                }
                System.out.println("Reçu :" + request);
                out.println(request);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                System.err.println("Erreur : " + e);
            }
        }
    }
}