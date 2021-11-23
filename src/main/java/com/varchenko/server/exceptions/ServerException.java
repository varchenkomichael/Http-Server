package com.varchenko.server.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }
}
