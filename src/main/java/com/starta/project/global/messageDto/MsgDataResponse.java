package com.starta.project.global.messageDto;

import lombok.Getter;

@Getter
public class MsgDataResponse {
    private String msg;
    private Object data;

    public MsgDataResponse(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
}
