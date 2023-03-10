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

        if(args.length == 0 || args.length > 2){
            System.out.println("Usage: java EchoServerDPool [-t nb] port " + "\n" +
                    "port : n° de port utilisé par le serveur " + "\n" +
                    "nb (optionnel) : nombre de threads associés au serveur, si non défini alors le pool est dynamique");
            return;
        }

        // analyse de la ligne de commande
        if(args.length == 1){
            port = Integer.parseInt(args[0]);
        }

        if(args.length == 2){
            nbThreads = Integer.parseInt(args[0]);
            port = Integer.parseInt(args[1]);
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
            System.out.println("Serveur DPool démarré");
            while (true) {
                Socket connection = server.accept();
                pool.submit(new ClientHandler(connection));
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        }
    }

}
