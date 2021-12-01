package com.varchenko.server.service;

import com.varchenko.server.exceptions.ParseException;
import com.varchenko.server.model.Request;
import com.varchenko.server.parser.SocketRequestParser;
import lombok.extern.log4j.Log4j;

import java.net.Socket;

@Log4j
public class RequestService {

    private final SocketRequestParser socketRequestParser = new SocketRequestParser();

    public Request getParsedRequest(Socket socket) {
        try {
            return socketRequestParser.parseRequest(socket);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
