package com.lineage.server.thread;

import java.util.concurrent.RejectedExecutionException;

/**
 * 
 * 類名稱：CustomRejectedExecutionHandler<br>
 * 類描述：取自JDK的RejectedExecutionHandler<br>
 * 創建人:Agui512<br>
 * 修改時間：2016年7月14日 下午12:03:19<br>
 * 修改人:QQ:1479366584<br>
 * 修改備註:<br>
 * @version<br>
 */
public interface CustomRejectedExecutionHandler {

    /**
     * Method that may be invoked by a {@link CustomThreadPoolExecutor} when
     * {@link CustomThreadPoolExecutor#execute execute} cannot accept a
     * task.  This may occur when no more threads or queue slots are
     * available because their bounds would be exceeded, or upon
     * shutdown of the Executor.
     *
     * <p>In the absence of other alternatives, the method may throw
     * an unchecked {@link RejectedExecutionException}, which will be
     * propagated to the caller of {@code execute}.
     *
     * @param r the runnable task requested to be executed
     * @param executor the executor attempting to execute this task
     * @throws RejectedExecutionException if there is no remedy
     */
    void rejectedExecution(Runnable r, CustomThreadPoolExecutor executor);
}
