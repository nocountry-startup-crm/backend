package com.nocountry.crm.dto.request;

public class RequestPubSubNotification {
    public PubSubMessage message;

    public static class PubSubMessage{
        public String data;
        public String messageId;
    }
}
