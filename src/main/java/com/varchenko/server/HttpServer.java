package com.varchenko.server;

import com.varchenko.server.controller.Controller;
import com.varchenko.server.exceptions.ServerException;
import com.varchenko.server.model.Request;
import com.varchenko.server.model.Response;
import com.varchenko.server.service.RequestService;
import com.varchenko.server.service.ResponseService;
import com.varchenko.server.service.TcpConnectionService;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Getter
public class HttpServer {

    private final Map<String, Controller> controllerMap = new HashMap<>();
    private final ResponseService responseService;
    private final RequestService requestService;
    private final TcpConnectionService connector;
    private String host;
    private int port;

    public HttpServer() throws ServerException {
        this.host = "localhost";
        this.port = 8080;
        this.responseService = new ResponseService();
        this.requestService = new RequestService();
        this.connector = new TcpConnectionService(host, port);
    }

    public void setIpAddress(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void launchServer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socketRequest = connector.getSocket();
                long start = System.currentTimeMillis();
                Request request = requestService.getParsedRequest(socketRequest);
                log.info("Request: (" + request.getMethod() + ", /" + request.getRequestTarget() + ")");
                Response response = responseService.createResponse(request);
                controllerMap.get(
                        request.getKeyForControllerMap()
                ).process(request, response);
                responseService.processResponse(request, response);
                log.info("Response: (" + response.getStatus() + ", " + response.getStatusMessage() + ", " + response.getContentLength() + ")");
                long finish = System.currentTimeMillis() - start;
                log.info("Request was processed for " + finish + " ms");
            }
        } catch (ServerException e) {
            log.error("Fatal server error:", e);
        }
    }

    public void addController(String url, String method, Controller controller) {
        controllerMap.put(method + StringUtils.SPACE + url, controller);
    }
}
