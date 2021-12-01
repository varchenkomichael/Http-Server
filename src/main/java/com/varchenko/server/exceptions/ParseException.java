package com.varchenko.server.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParseException extends ServerException {

    public ParseException(String s) {
        super(s);
    }
}

