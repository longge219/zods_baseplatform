package com.zods.sso.auth.temp;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsSendResVo implements Serializable {

    private static final long serialVersionUID = -324475185463865210L;

    private Integer result;
    private String description;
    private String faillist;
    private String taskid;
    private String task_id;
}
