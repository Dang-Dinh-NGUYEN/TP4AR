import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerSPool {
    public static void main(String[] args) throws IOException {
        int port;
        int nbThreads;

        if(args.length == 0 || args.length > 3){
            System.out.println("Usage: java EchoServerDPool -t nb port " + "\n" +
                    "port : n° de port utilisé par le serveur " + "\n" +
                    "nb : nombre de threads associés au serveur, si non défini alors le pool est dynamique");
            return;
        }

        // analyse de la ligne de commande
        nbThreads = Integer.parseInt(args[1]);
        port = Integer.parseInt(args[2]);


        // vérification de la validité du n° de port
        if (port <= 1024) {
            System.err.println("Port non valide");
            System.exit(1);
        }

        // création du pool de threads
        ExecutorService pool;
        if (nbThreads <= 0) {
            System.err.println("nombre de threads non valide");
            System.exit(1);
        }
        pool = Executors.newFixedThreadPool(nbThreads);


        // création et démarrage du serveur
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Serveur démarré");
            while (true) {
                Socket connection = server.accept();
                pool.submit(new ClientHandler(connection));
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        }
    }

}
