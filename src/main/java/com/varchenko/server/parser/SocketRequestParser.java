package com.varchenko.server.parser;

import com.varchenko.server.exceptions.ParseException;
import com.varchenko.server.model.Request;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
public class SocketRequestParser {

    private static final String REQUEST_LINE = "([A-Z]+)\\s/([a-z]+)\\s([A-Z]+/[0-9].[0-9])";
    private static final String SLASH = "/";
    private static final String SPLITTER = ": ";

    public Request parseRequest(Socket socket) throws ParseException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Matcher m = getRequestLine(reader);
            return Request.builder()
                    .socket(socket)
                    .reader(reader)
                    .writer(writer)
                    .method(m.group(1))
                    .requestTarget(m.group(2))
                    .version(m.group(3))
                    .headers(getHeaders(reader))
                    .keyForControllerMap(m.group(1) + StringUtils.SPACE + SLASH + m.group(2))
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ParseException(e.getMessage());
        }
    }

    private Map<String, String> getHeaders(BufferedReader reader) throws ParseException {
        Map<String, String> map = new HashMap<>();
        String[] next;
        try {
            while (reader.ready()) {
                next = reader.readLine().split(SPLITTER);
                map.put(next[0], next[next.length - 1]);
            }
        } catch (IOException e) {
            throw new ParseException();
        }
        return map;
    }

    private Matcher getRequestLine(BufferedReader reader) throws ParseException {
        Pattern pattern1 = Pattern.compile(REQUEST_LINE);
        try {
            String head = reader.readLine();
            Matcher m = pattern1.matcher(head);
            if (m.matches()) {
                return m;
            }
            throw new ParseException("Request can't be parsed");
        } catch (ParseException | IOException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
