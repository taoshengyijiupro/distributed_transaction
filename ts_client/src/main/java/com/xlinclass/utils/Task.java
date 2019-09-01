package com.xlinclass.utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:15
 */
public class Task {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    //等待
    public void waitTask() {
        try {
            lock.lock();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //唤醒
    public void signalTask() {
        lock.lock();
        condition.signal();
        lock.unlock();
    }

}
