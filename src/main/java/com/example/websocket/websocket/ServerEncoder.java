package com.example.websocket.websocket;

import com.example.websocket.entity.Message;
import com.example.websocket.util.JsonUtil;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * 使用session.getBasicRemote().sendObject(obj)方法发送消息时,需要本类解码
 *
 * @author zhuji
 * @date 2017/11/2 16:22
 */


public class ServerEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message object) throws EncodeException {
        try {
            return JsonUtil.bean2str(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
