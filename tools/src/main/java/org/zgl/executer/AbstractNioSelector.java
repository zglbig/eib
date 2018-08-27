package org.zgl.executer;

import org.zgl.executer.pool.NioSelectorRunnablePool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractNioSelector implements Runnable {

    /**
     * 线程池
     */
    private final Executor executor;


    /**
     * 选择器wakenUp状态标记
     */
    protected final AtomicBoolean wakenUp = new AtomicBoolean();

    /**
     * 任务队列
     */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
    /**
     * 线程锁
     */
    private final ReentrantReadWriteLock lock;
    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 线程管理对象
     */
    protected NioSelectorRunnablePool selectorRunnablePool;


    AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.selectorRunnablePool = selectorRunnablePool;
        this.lock = new ReentrantReadWriteLock();
        openSelector();
    }

    /**
     * 获取selector并启动线程
     */
    private void openSelector() {
        executor.execute(this);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        processTaskQueue();
    }

    /**
     * 注册一个任务并激活selector
     *
     * @param task
     */
    protected final void registerTask(Runnable task) {
        try {
            taskQueue.add(task);
            this.lock.writeLock().lock();
            try {
                if (!wakenUp.get()) {
                    wakenUp.set(true);
                    openSelector();
                }
            } finally {
                this.lock.writeLock().unlock();
            }
        }catch (Exception throwable){
            throwable.printStackTrace();
        }

    }

    /**
     * 执行队列里的任务
     */
    private void processTaskQueue() {
        for (; ; ) {
            try {
                this.lock.writeLock().lock();
                try {
                    final Runnable task = taskQueue.poll();
                    if (task == null) {
                        wakenUp.set(false);
                        break;
                    }
                    task.run();
                } finally {
                    this.lock.writeLock().unlock();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取线程管理对象
     *
     * @return
     */
    public NioSelectorRunnablePool getSelectorRunnablePool() {
        return selectorRunnablePool;
    }
}