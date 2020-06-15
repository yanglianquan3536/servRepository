pipeline-管道
提供netty服务器，用于接收不同来源的netty数据并转发，比如：
* 接收集群状态上报
* 接收链接访问数据上报
* ……
根据不同的上报类型，调用不同的API处理

提供Redis管道
支持Redis读写通用API