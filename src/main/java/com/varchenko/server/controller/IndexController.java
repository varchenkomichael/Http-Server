package com.varchenko.server.controller;

import com.varchenko.server.exceptions.ServerException;
import com.varchenko.server.model.Request;
import com.varchenko.server.model.Response;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IndexController implements Controller {

    @Override
    public void process(Request request, Response response) throws ServerException {
        byte[] file;
        try {
            if (request.getRequestTarget().equals("index")) {
                response.setStatus(200);
                file = IOUtils.toByteArray(new FileInputStream("src/main/resources/index.html"));
            } else {
                throw new ServerException("Url " + request.getRequestTarget() + "is incorrect");
            }
        } catch (IOException e) {
            throw new ServerException();
        }
        response.setBody(file);
        response.setContentLength(file.length);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-length: ", String.valueOf(file.length));
        response.setHeaders(headers);
    }
}
