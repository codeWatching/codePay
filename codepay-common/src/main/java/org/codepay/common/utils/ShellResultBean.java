package org.codepay.common.utils;

import org.apache.commons.lang3.StringUtils;

public class ShellResultBean {

    public static final int EXIT_VALUE_TIMEOUT = -1;

    public static final int EXIT_CODE_SUCCESS = 0;

    private String output; // shell 标准输出
    private int exitValue; // shell 命令退出状态号
    private String error; // shell 标准错误输出

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getExitValue() {
        return exitValue;
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        String codeMsg = "code:" + getExitValue();
        if (StringUtils.isNotBlank(getError())) {
            return codeMsg + " error:" + getError();
        } else {
            return codeMsg;
        }
    }

    public boolean isSuccess() {
        if (0 == this.getExitValue()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ShellResultBean [output=" + output + ", exitValue=" + exitValue + ", error=" + error + "]";
    }

}
