package com.varchenko.server.service;

import com.varchenko.server.exceptions.ServerException;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j
public class TcpConnectionService {

    private static final Object MONITOR = new Object();
    private volatile ServerSocket serverSocket;

    public TcpConnectionService(String host, int port) throws ServerException {
        this.serverSocket = getSingletonServerSocket(port, host);
    }

    private ServerSocket getSingletonServerSocket(int port, String host) throws ServerException {
        ServerSocket localServerSocket;
        try {
            localServerSocket = serverSocket;
            if (localServerSocket == null) {
                synchronized (MONITOR) {
                    localServerSocket = serverSocket;
                    if (localServerSocket == null) {
                        localServerSocket = new ServerSocket();
                        InetSocketAddress ip = new InetSocketAddress(host, port);
                        localServerSocket.bind(ip);
                        serverSocket = localServerSocket;
                        log.info("Server has been started at " + host + ":" + port);
                    }
                }
            }
        } catch (IOException e) {
            throw new ServerException("Can't initialize ServerSocket: " + e);
        }
        return localServerSocket;
    }

    public Socket getSocket() throws ServerException {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new ServerException("Socket is closed/is not bound " + e);
        }
    }
}
