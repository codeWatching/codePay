package org.codepay.common.utils;

import org.codepay.common.exception.ExecuteException;
import org.codepay.common.exception.InvalidedSateException;
import org.codepay.common.exception.RowUpdatedException;
import org.codepay.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExecutorUtils {
    protected final static transient Logger __logger = LoggerFactory.getLogger(ExecutorUtils.class);

    /**
     * 特别注意重复执行的幂等性
     * 
     * 执行操作,如果执行过程中抛出{@link org.hibernate.StaleObjectStateException}异常,会尝试重新执行. 如果尝试执行maxTryTimes次都没有成功,将抛出
     * {@link com.huicai.tarro.common.exception.ticket.lottery.support.ExecuteException}
     * 
     * @param executable
     *            操作对象
     * @param maxTryTimes
     *            最大尝试执行次数
     * @throws ExecuteException
     *             执行异常
     */
    public static void exec(Executable executable, int maxTryTimes) throws ExecuteException {
        if (maxTryTimes <= 0)
            throw new ExecuteException("最大尝试执行次数不能小于或等于0.");
        int tryTimes = 0;
        boolean run = true;
        while (run) {
            try {
                executable.run();
                run = false;
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                tryTimes++;
                run = tryTimes <= maxTryTimes;
                try {
                    Thread.sleep(1000 * tryTimes);
                } catch (InterruptedException e1) {
                }
                if (!run) {
                    __logger.error(e.getMessage(), e);
                    throw new ExecuteException("系统繁忙，请稍候再试.");
                } else {
                    __logger.warn(e.getMessage());
                    __logger.warn("尝试重新执行--第" + tryTimes + "次");
                }
            }
        }
    }

    /**
     * @param executable
     * @param maxTryTimes
     *            最大尝试次数
     * @param delay
     *            每次尝试延迟步长
     * @throws ServiceException
     */
    public static void exec(TransactionExecutable executable, int maxTryTimes, long delay) throws ServiceException {
        if (maxTryTimes <= 0) {
            throw new ServiceException("尝试次数必须大于0");
        }
        if (delay <= 0) {
            throw new ServiceException("尝试延迟必须大于0");
        }
        boolean run = true;
        int tryTimes = 1;
        while (run) {
            try {
                executable.run();
                run = false;
            } catch (RowUpdatedException e) {
                tryTimes++;
                run = tryTimes <= maxTryTimes;
                try {
                    Thread.sleep(delay * (tryTimes - 1));
                } catch (InterruptedException e1) {
                }
                if (!run) {
                    __logger.error(e.getMessage());
                } else {
                    __logger.warn(e.getMessage() + " 尝试重新执行...第" + tryTimes + "次");
                }
            } catch (InvalidedSateException e) {
                // 状态不对不处理
                run = false;
            } catch (ServiceException e) {
                __logger.error(e.getMessage());
                throw e;
            }
        }
    }

}
