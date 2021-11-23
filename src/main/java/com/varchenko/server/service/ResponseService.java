package com.varchenko.server.service;

import com.varchenko.server.model.Request;
import com.varchenko.server.model.Response;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

@Log4j
public class ResponseService {

    public Response createResponse(Request request) {
        return Response.builder()
                .version(request.getVersion())
                .statusMessage("OK")
                .build();
    }

    public void processResponse(Request request, Response response) {
        try {
            request.getWriter().write((response.getVersion() + response.getStatus() + response.getStatusMessage() + StringUtils.LF));
            request.getWriter().write(StringUtils.LF);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getBody())));
            String next;
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.ready() && (next = reader.readLine()) != null) {
                stringBuilder.append(next).append(StringUtils.LF);
            }
            request.getWriter().write(stringBuilder.toString());
            request.getWriter().flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
