package com.varchenko.server;


import com.varchenko.server.controller.IndexController;
import com.varchenko.server.exceptions.ServerException;

public class Client {

    public static void main(String[] args) {
        try {
            final HttpServer httpServer = new HttpServer();
            IndexController controller = new IndexController();
            httpServer.setIpAddress("localhost");
            httpServer.setPort(8080);
            httpServer.addController("/index", "GET", controller);
            httpServer.launchServer();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
