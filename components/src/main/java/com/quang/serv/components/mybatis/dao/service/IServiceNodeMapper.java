package com.quang.serv.components.mybatis.dao.service;

import com.quang.serv.components.mybatis.dao.CommonMapper;
import com.quang.serv.core.model.ServiceNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IServiceNodeMapper extends CommonMapper<Long, ServiceNode> {

    int deleteByServiceId(long serviceId);

    ServiceNode selectByHost(@Param("serviceId") long serviceId, @Param("host") String host);

    List<ServiceNode> selectByServiceId(long serviceId);
}
