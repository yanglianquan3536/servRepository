package com.quang.serv.core.health;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author Lianquan Yang
 */
@Data
public class HealthReport {
    private String serviceName;
    private String ip;
    private Date currentTime;
}
