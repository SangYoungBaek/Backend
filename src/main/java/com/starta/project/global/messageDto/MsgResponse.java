package com.starta.project.global.messageDto;

import lombok.Getter;

@Getter
public class MsgResponse {
    private String msg;

    public MsgResponse(String msg) {
        this.msg = msg;
    }
}