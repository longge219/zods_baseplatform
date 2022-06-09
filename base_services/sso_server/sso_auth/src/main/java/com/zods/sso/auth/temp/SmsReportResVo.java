package com.zods.sso.auth.temp;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsReportResVo implements Serializable {
    
    private static final long serialVersionUID = 87545735604362361L;

    private Integer result;
    private String out;
}
