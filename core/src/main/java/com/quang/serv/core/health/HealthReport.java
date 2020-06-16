package com.quang.serv.core.health;

import com.quang.serv.core.components.cache.CacheSerializable;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author Lianquan Yang
 */
@Data
public class HealthReport implements CacheSerializable {
    private String serviceName;
    private String ip;
    private Date currentTime;
    private int lostTimes;

    @Override
    public String getKey() {
        return serviceName + "_" + ip;
    }
}
