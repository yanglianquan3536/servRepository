package com.quang.serv.components.mybatis.dao.service;

import com.quang.serv.components.mybatis.dao.CommonMapper;
import com.quang.serv.core.model.ServiceMethod;

import java.util.List;

public interface IServiceMethodMapper extends CommonMapper<Long, ServiceMethod> {

    int deleteByServiceId(long serviceId);

    List<ServiceMethod> selectByServiceId(long serviceId);
}
