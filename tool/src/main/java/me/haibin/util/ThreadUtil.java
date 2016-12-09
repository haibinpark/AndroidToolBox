package me.haibin.util;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 */
public class ThreadUtil {
	private ThreadUtil(){
		num = Runtime.getRuntime().availableProcessors();
		/**
		 * 进行优先级处理 
		 */
		Comparator<? super Runnable> comparator=new Comparator<Runnable>() {
			@Override
			public int compare(Runnable lhs, Runnable rhs) {
				return lhs.hashCode()>rhs.hashCode()? 1:-1;
			}
		};
		workQueue = new PriorityBlockingQueue<Runnable>(num*10, comparator);
		executor = new ThreadPoolExecutor(num*2, num*2, 8, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	private static final ThreadUtil manager= new ThreadUtil();
	public int num;
	private ThreadPoolExecutor executor;
	private PriorityBlockingQueue<Runnable> workQueue;
	
	public ExecutorService getService() {
		return executor;
	}
	public static ThreadUtil getInstance(){
		return manager;
	}
	/**
	 * 方法描述：关闭线程池不再接受新的任务
	 */
	public void stopReceiveTask(){
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
	}
	/**
	 * 方法描述：停止所有线程,包括等待
	 */
	public void stopAllTask(){
		if (!executor.isShutdown()) {
			executor.shutdownNow();
		}
	}
	/**
	 * 添加一个新任务
	 * @param runnable 任务的Runnable对象
	 */
	public void addTask(Runnable runnable){
		if(executor.isShutdown()){
			executor = new ThreadPoolExecutor(num*2, num*2, 8, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());
		}
		executor.execute(runnable);
	}
}
