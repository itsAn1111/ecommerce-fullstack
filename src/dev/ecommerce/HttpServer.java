package dev.ecommerce;

import java.io.*;
import java.net.*;

public class HttpServer {
    private ServerSocket serverSocket;
    private InetAddress serverAddress;
    private String templateJson = """
                        {
                                "status": "success",
                                "products": [
                                    {"id": 1, "name": "MacbookAirM5", "price": 1099.99},
                                    {"id": 2, "name": "ThinkpadX1", "price": 1499.99}
                                ]
                        }
                        """;
    private String templateHtml = "<h1> Welcome to my E-commerce shop! </h1>";

    public HttpServer(String serverIPAddress, int serverPort, int serverBackLog) {
        try {
            serverAddress = InetAddress.getByName(serverIPAddress);
        } catch (UnknownHostException u) {
            System.out.println("Binding IP address to server failed. " + u);
            System.exit(1);
        }

        try {
            serverSocket = new ServerSocket(serverPort, serverBackLog, serverAddress);
            System.out.println("Server is running on: " + serverAddress.getHostAddress() + ":" + serverPort);
        } catch (IOException i) {
           System.out.println("Create server socket failed. " + i);
           System.exit(1);
        }
    }

    public String generateHttpResponse(String httpBody, String type) {
        String contentType = "";
        if ("Json".equals(type)) {
            contentType = "Content-Type: application/json\r\n";
        } else if ("Text".equals(type)) {
            contentType = "Content-Type: text/html";
        }
        return "HTTP/1.1 200 OK\r\n" + contentType +
                "Content-Length: " + httpBody.getBytes().length + "\r\n" +
                "\r\n" + httpBody;
    }


    public void startServer() {
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();

                InputStream input = clientSocket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String requestLine = reader.readLine();

                // Client request details
                // TODO: Handle request base on routes
                System.out.println("Client is requesting " + requestLine);

                OutputStream output = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(generateHttpResponse(templateJson,"Json"));
                clientSocket.close();

            } catch (IOException i) {
                System.out.println("Server running process failed! " + i);
                System.exit(2);
            }
        }
    }


}
