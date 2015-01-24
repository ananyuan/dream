package com.dream.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ThreadTask implements Runnable {
    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public final void run() {
        try {
            execute();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    public abstract void execute();

}
