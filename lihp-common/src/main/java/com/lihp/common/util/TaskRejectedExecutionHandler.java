package com.lihp.common.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.Logger;

public class TaskRejectedExecutionHandler
    implements RejectedExecutionHandler {

    private transient long rejectSleepTime;

    private transient Logger logger;

    public TaskRejectedExecutionHandler(long rejectSleepTime, Logger logger) {
        this.rejectSleepTime = rejectSleepTime;
        this.logger = logger;
    }

    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        try {
            Thread.sleep(rejectSleepTime);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        executor.execute(task);
    }
}
