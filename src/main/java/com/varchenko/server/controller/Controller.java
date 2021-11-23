package com.varchenko.server.controller;

import com.varchenko.server.exceptions.ServerException;
import com.varchenko.server.model.Request;
import com.varchenko.server.model.Response;

public interface Controller {
    void process(Request request, Response response) throws ServerException;
}
