import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerWStealPool {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);

        // Créer un pool de threads "voleur"
        ExecutorService executorService = Executors.newWorkStealingPool();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré, en écoute sur le port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket);

                // Soumettre la tâche de traitement de la requête client à notre pool de threads
                executorService.submit(new ClientHandler(clientSocket));
            }
        }
    }
}
