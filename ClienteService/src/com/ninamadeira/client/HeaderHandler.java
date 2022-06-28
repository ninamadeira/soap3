package com.ninamadeira.client;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext contex) {
        Boolean outboundProperty = (Boolean) contex.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if(outboundProperty.booleanValue()){
            try {
                SOAPEnvelope envelope = contex.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();

                SOAPElement security = header.addChildElement("Security","wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

                SOAPElement usernameToken = security.addChildElement("UsernameToken","wsse");

                SOAPElement username = usernameToken.addChildElement("Username","wsse");
                username.addTextNode("nina");

                SOAPElement password = usernameToken.addChildElement("Password","wsse");
                password.addTextNode("123");

            } catch (SOAPException e){
                e.printStackTrace();
            }
        }
        return outboundProperty;
    }

    @Override
    public boolean handleFault(SOAPMessageContext soapMessageContext) {
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
