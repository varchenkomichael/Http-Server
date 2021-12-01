package com.varchenko.server.model;

import lombok.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Request {

    private Map<String, String> headers = new HashMap<>();
    private String keyForControllerMap;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String requestTarget;
    private String version;
    private String method;
    private Socket socket;

}
