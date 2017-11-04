package com.example.websocket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 消息实体
 *
 * @author zhuji
 * @date 2017/11/2 12:57
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "of")
public class Message implements Serializable {

    /**
     * 发送人
     */
    private String sender;
    /**
     * 消息类型
     */
    private MessageType messageType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 接收人
     */
    private List<String> receivers;
    /**
     * 在线用户
     */
    private List<String> onlineUsers;

}
