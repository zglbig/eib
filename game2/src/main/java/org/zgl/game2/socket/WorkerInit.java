package org.zgl.game2.socket;

import org.zgl.executer.pool.NioSelectorRunnablePool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class WorkerInit {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static WorkerInit instance = new WorkerInit();
    }
    public static WorkerInit getInstance() {
        return SingletonHolder.instance;
    }
    private final NioSelectorRunnablePool pool;

    private WorkerInit() {
        pool = new NioSelectorRunnablePool(new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>()), 1);
    }

    public NioSelectorRunnablePool getPool() {
        return pool;
    }
}
