package com.ninamadeira.client;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

public class HeaderHandlerResolver implements HandlerResolver {
    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {

        List<Handler> handlerChain = new ArrayList<Handler>();

        HeaderHandler hh = new HeaderHandler();

        handlerChain.add(hh);

        return handlerChain;
    }
}