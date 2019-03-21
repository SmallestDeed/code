package com.sandu.im.service.handlermsg;

public interface HandlerMsgProvider {

    HistoryMessageHandler produce(String produceType);
}
