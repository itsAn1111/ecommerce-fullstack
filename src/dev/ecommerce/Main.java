package dev.ecommerce;

public class Main {

    public static void main(String[] args) {
        HttpServer ecommerceServer = new HttpServer("127.0.0.1", 80, 50);
        ecommerceServer.startServer();
    }

}