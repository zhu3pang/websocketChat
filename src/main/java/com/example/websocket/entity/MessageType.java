package com.example.websocket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 消息类型
 *
 * @author zhuji
 * @date 2017/11/2 15:10
 */


@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MessageType implements Serializable {

    /**
     * 聊天
     */
    CHAT("001"),

    /**
     * 群发通知
     */
    BROADCAST_NOTICE("002");

    /**
     * 消息类型码
     */
    @Getter
    private String typeCode;

}
