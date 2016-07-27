/*
 * Copyright 2013 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package org.codepay.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zhuhui
 * @Descriptions 提供执行shell命令API
 * @date 2014-10-30
 */
public class ExecuteShellCommandUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteShellCommandUtil.class);

    /**
     * @param command
     * @return shell命令的结果Bean
     * @description 执行shell命令
     */
    public static ShellResultBean exec(String command) {
        LOG.info("exec shell command:" + command);
        ShellResultBean result = new ShellResultBean();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            wait(process, result);
            if (process != null) {
                process.destroy();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            result.setExitValue(-1);
            result.setError(e.getMessage());
        }
        return result;
    }

    public static ShellResultBean exec(String[] command) {
        LOG.info("exec shell command:{}", FastJsonUtil.toJSONStr(command));
        ShellResultBean result = new ShellResultBean();
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            wait(process, result);
            if (process != null) {
                process.destroy();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            result.setExitValue(-1);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * @param process
     * @return shell命令的结果Bean
     * @description 执行命令，读取错误输出，标准输出，命令退出码封装成shell命令的结果Bean
     */
    private static void wait(Process process, ShellResultBean result) {
        BufferedReader errorStreamReader = null; // 进程的错误输出流
        BufferedReader inputStreamReader = null; // 进程的标准输出流
        try {
            errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            result.setExitValue(process.waitFor()); // 阻塞直到命令执行完毕

            if (inputStreamReader.ready()) { // 封装进程正确输出流信息
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = errorStreamReader.readLine()) != null) {
                    buffer.append(line);
                }
                result.setOutput(buffer.toString());
            }

            if (errorStreamReader.ready()) { // 封装进程错误输出流信息
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = errorStreamReader.readLine()) != null) {
                    buffer.append(line);
                }
                result.setError(buffer.toString());
            }
        } catch (Throwable e) { // 发生异常时，返回-1
            result.setExitValue(ShellResultBean.EXIT_VALUE_TIMEOUT);
            result.setOutput("Command process timeout");
        } finally {
            if (errorStreamReader != null) { // 关闭进程错误输出流
                try {
                    errorStreamReader.close();
                } catch (IOException e) {
                    LOG.warn("close process error stream happend exception", e);
                }
            }

            if (inputStreamReader != null) { // 关闭进程标准输出流
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    LOG.warn("close process input stream happend exception", e);
                }
            }
        }
    }

}
