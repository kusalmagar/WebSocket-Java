import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class WebSocketDemo {
    private static okhttp3.WebSocket webSocket;
    private static String SERVER_PATH = "ws://192.168.1.64:3000";
    private static String message;
    private String sentMessage;
    private String receivedMessage;

//    public static void initializeSocketConnectionVaiHttpUrlConnection() throws IOException {
//        URL url = new URL("http://192.168.1.65");
//        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);
//        httpURLConnection.connect();
//        String user_name = "Hari Bahadur";
//        String password = "Ma hari bahadur";
//        OutputStream outputStream = httpURLConnection.getOutputStream();
//        BufferedWriter bufferedWriter = new BufferedWriter((new OutputStreamWriter(outputStream, "UTF-8")));
//        String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
//        bufferedWriter.write(post_data);
//        bufferedWriter.flush();
//        bufferedWriter.close();
//        outputStream.close();
//
//
//    }

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
                System.out.println("Message Received in WebSocketDemo: " + text);
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
//        try {
//            initializeSocketConnectionVaiHttpUrlConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        getImput();
    }

}


