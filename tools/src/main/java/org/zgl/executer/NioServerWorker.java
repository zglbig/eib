package org.zgl.executer;
import org.zgl.executer.pool.NioSelectorRunnablePool;
import org.zgl.executer.pool.Worker;

import java.util.concurrent.Executor;

public class NioServerWorker extends AbstractNioSelector implements Worker {

	public NioServerWorker(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
		super(executor, threadName, selectorRunnablePool);
	}
	@Override
	public void registerNewChannelTask(Runnable runnable) {
		registerTask(runnable);
	}
}