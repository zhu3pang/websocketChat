package com.example.websocket.websocket;

import com.alibaba.fastjson.JSON;
import com.example.websocket.entity.Message;
import com.example.websocket.entity.MessageType;
import com.example.websocket.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zhuji
 * @date 2017/11/2 11:13
 */

@ServerEndpoint(value = "/websocket")
//@ServerEndpoint(value = "/websocket", encoders = {ServerEncoder.class})
@Component
@Slf4j
public class WebSocketDemo implements Serializable {
    //    private static final Logger logger = Logger.getLogger(WebSocketDemo.class);
    private static final String LOGGER_HEADER = "[WebSocket服务端消息]";


    private static ConcurrentHashMap<String, Session> nameMapToSession = new ConcurrentHashMap<>();

    private String userName;

    /**
     * 有新连接到达时,群发上线通知
     *
     * @param session
     * @throws Exception
     */
    @OnOpen
    public void onOpen(Session session) throws Exception {
        userName = session.getQueryString().split("=")[1];
        nameMapToSession.put(userName, session);
        log.info(LOGGER_HEADER + "有新连接加入！name-->" + userName);
        log.info(LOGGER_HEADER + "websocket连接数" + nameMapToSession.size() + "个");

        /*群发上线通知*/
        List<String> userNames = new ArrayList<>(nameMapToSession.keySet());
        Message message = Message.of().setSender(userName)
            .setMessageType(MessageType.BROADCAST_NOTICE)
            .setContent("您的好友" + userName + "上线了," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-mm HH:mm:ss")))
            .setReceivers(userNames)
            .setOnlineUsers(userNames);

        broadcast(new ArrayList<>(nameMapToSession.values()), message);

    }

    /**
     * 有连接断开时,群发离线通知
     *
     * @throws Exception
     */
    @OnClose
    public void onClose() throws Exception {
        log.info(LOGGER_HEADER + "websocket关闭！name-->" + userName);
        nameMapToSession.remove(userName);
        log.info(LOGGER_HEADER + "websocket连接数" + nameMapToSession.size() + "个");

        /*群发离线通知*/
        List<String> userNames = new ArrayList<>(nameMapToSession.keySet());
        Message message = Message.of().setSender(userName)
            .setMessageType(MessageType.BROADCAST_NOTICE)
            .setContent("您的好友" + userName + "离线了," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-mm HH:mm:ss")))
            .setReceivers(userNames)
            .setOnlineUsers(userNames);

        broadcast(new ArrayList<>(nameMapToSession.values()), message);

    }

    /**
     * 有消息到达时,广播消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info(LOGGER_HEADER + "收到消息-->" + message);
        Message msg = JSON.parseObject(message, Message.class);
        msg.setContent("[" + msg.getSender() + "]   "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-HH-mm HH:mm:ss"))
            + "</br>" + "    " + msg.getContent() + "</br></br>")
            .setMessageType(MessageType.CHAT)
            .setOnlineUsers(new ArrayList<>(nameMapToSession.keySet()));

        broadcast(msg.getReceivers().stream()
            .map(name -> nameMapToSession.get(name))
            .collect(Collectors.toList()), msg);
    }


    /**
     * 广播消息
     *
     * @param sessions
     * @param msg
     * @throws RuntimeException
     */
    private void broadcast(List<Session> sessions, Message msg) throws RuntimeException {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(JsonUtil.bean2str(msg));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
