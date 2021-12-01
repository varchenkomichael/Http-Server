package com.varchenko.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class Response {

    private Map<String, String> headers;
    private String statusMessage;
    private long contentLength;
    private String version;
    private byte[] body;
    private int status;
}