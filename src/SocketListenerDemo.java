import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;


public class SocketListenerDemo {
    private static okhttp3.WebSocket webSocket;
    private static String SERVER_PATH = "ws://192.168.1.64:3000";
    private static String message;
    private String sentMessage;
    private String receivedMessage;


    public static void initializeSocketConnection(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(SERVER_PATH).build();
            webSocket = client.newWebSocket(request, new SocketListener());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(okhttp3.WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            System.out.println("Socket Connection Successful!");
        }
        @Override
        public void onMessage(okhttp3.WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            if (!text.isEmpty()) {
                System.out.println("Message Received in SocketListenerDemo: " + text);
                getImput();
            }

        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            System.out.println("Failure occured during websocket execution");
        }

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
            System.out.println("WebSocket Closed");

        }
    }


    public static void getImput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message:");
        message = scanner.nextLine();
        if (!message.isEmpty()){
            webSocket.send(message);
        }
    }





    public static void main(String[] args) {
        initializeSocketConnection();
        SocketListener socketListener = new SocketListener();
        getImput();
    }

}


