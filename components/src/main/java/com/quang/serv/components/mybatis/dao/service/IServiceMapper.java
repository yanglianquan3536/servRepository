package com.quang.serv.components.mybatis.dao.service;

import com.quang.serv.components.mybatis.dao.CommonMapper;
import com.quang.serv.core.model.Service;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceMapper extends CommonMapper<Long, Service> {
    Service selectByName(String serviceName);
}
