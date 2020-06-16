package com.quang.serv.register.health;

import com.quang.serv.core.health.HealthReport;

import java.util.*;
import java.util.concurrent.RecursiveTask;

/**
 * 心跳检测
 * ForkJoin根据阈值拆分任务
 * @author Lianquan Yang
 */
public class HealthReportDisconnectDetector extends RecursiveTask<Set<HealthReport>> {

    /*默认每个线程处理n个任务*/
    private static final int THRESHOLD = 500;

    /*默认认定为断线的时间间隔为20S*/
    private static final int DEFAULT_LOST_MILLS = 20 * 1000;

    /*默认被认定为断线3次即停止该服务器，除非重新响应*/
    private static final int DEFAULT_MAX_LOST_TIMES = 3;

    private List<HealthReport> items;

    public HealthReportDisconnectDetector(List<HealthReport> items){
        this.items = items;
    }

    @Override
    protected Set<HealthReport> compute() {
        boolean mustDiv = items.size() <= THRESHOLD;
        if(mustDiv){
            Set<HealthReport> healthReports = new HashSet<>();
            // 分裂
            int mid = items.size() / 2;

            HealthReportDisconnectDetector leftTask = new HealthReportDisconnectDetector(items.subList(0, mid));
            HealthReportDisconnectDetector rightTask = new HealthReportDisconnectDetector(items.subList(mid, items.size()));

            //执行子任务
            leftTask.fork();
            rightTask.fork();

            //等待子任务执行完，并得到结果
            healthReports.addAll(leftTask.join());
            healthReports.addAll(rightTask.join());
            return healthReports;
        }else{
            Set<HealthReport> healthReports = new HashSet<>();
            // 不需分裂，直接执行即可
            for (HealthReport item : items) {
                // 检查item的上报时间跟当前时间的差
                if(item.getCurrentTime().getTime() - new Date().getTime() > DEFAULT_LOST_MILLS){
                    item.setLostTimes(item.getLostTimes() + 1);
                    if(item.getLostTimes() > DEFAULT_MAX_LOST_TIMES){
                        healthReports.add(item);
                    }
                }
            }
            return healthReports;
        }
    }

}
