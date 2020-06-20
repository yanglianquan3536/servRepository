package com.quang.serv.core.health;

import com.quang.serv.common.utils.StringUtils;
import com.quang.serv.core.components.CacheSerializable;
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
    private int port;
    private Date currentTime;
    private int lostTimes;

    public boolean equals(Object other){
        if(other == null) return false;
        if(!(other instanceof HealthReport)) return false;
        HealthReport another = (HealthReport)other;
        if(StringUtils.isBlank(this.serviceName) && StringUtils.isNotBlank(another.getServiceName())) return false;
        if(StringUtils.isNotBlank(this.serviceName) && StringUtils.isBlank(another.getServiceName())) return false;
        if(StringUtils.isNotBlank(this.serviceName) && StringUtils.isNotBlank(another.getServiceName()) && !this.serviceName.equals(another.getServiceName())) return false;
        if(StringUtils.isBlank(this.ip) && StringUtils.isNotBlank(another.getIp())) return false;
        if(StringUtils.isNotBlank(this.ip) && StringUtils.isBlank(another.getIp())) return false;
        return !StringUtils.isNotBlank(this.ip) || !StringUtils.isNotBlank(another.getIp()) || this.ip.equals(another.getIp());
    }

    @Override
    public String getKeyForHash() {
        return serviceName + "_" + ip;
    }

    @Override
    public String getKeyForSet() {
        return serviceName;
    }
}
