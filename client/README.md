嵌入到客户端应用中，用于调用方
1. 服务转换（基础服务，必须实现）
    假设存在一个服务地址为http://agent.quang.com/xxx/lists
    该服务在管理平台上配置如下：
    name:agent
    protocol:http
    host:agent.quang.com
    context:xxx
    httpMethod:get
    该服务可以通过如下方式调用：
    HttpProxy agentProxy = Proxy.create("agent");
    Proxy.run("lists", ReturnClass.class);
    将agent解析为http://agent.quang.com/xxx/ 并直接将返回赋值给ReturnClass
2. 校验权限
    因为调用者有key，初始化时可根据该key获取当前调用端下有权限访问的service
    如果service列表中不包含agent，应给出提示。
2. 注册nacos以完成服务配置文件的动态更新（基础服务，必须实现）
3. 权重请求
    根据服务管理平台设置的提供服务IP权重分配流量（可暂不实现）
4. 请求收集并上报
    将所有Proxy.run的请求收集起来发送到monitor，以达到数据监控的目的（可暂不实现）
