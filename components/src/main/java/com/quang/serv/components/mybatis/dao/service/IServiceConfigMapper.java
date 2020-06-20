package com.quang.serv.components.mybatis.dao.service;

import com.quang.serv.components.mybatis.dao.CommonMapper;
import com.quang.serv.core.model.ServiceConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceConfigMapper extends CommonMapper<Long, ServiceConfig> {
    int deleteByServiceId(long serviceId);

    ServiceConfig selectByServiceId(long serviceId);
}
