package com.quang.serv.components.mybatis.dao.service;

import com.quang.serv.components.mybatis.dao.CommonMapper;
import com.quang.serv.core.model.ServiceNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IServiceNodeMapper extends CommonMapper<Long, ServiceNode> {

    int deleteByServiceId(long serviceId);

    ServiceNode selectByNode(@Param("host") String host, @Param("port") int port);

    List<ServiceNode> selectByServiceId(long serviceId);
}
