package org.zgl.error;

import org.zgl.JsonUtils;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
public class AppGeneralError extends RuntimeException {
    private final long id = -46518231654416754L;
    private int errorCode;
    private String errorMsg;

    public AppGeneralError() {
        throw this;
    }

    public AppGeneralError(int errorCode) {
        this.errorCode = errorCode;
        throw this;
    }
    public AppGeneralError(int errorCode,Object...arg) {
        this.errorCode = errorCode;
        if(arg.length > 0){
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<arg.length;i++){
                sb.append(arg[i]);
                if (i != arg.length - 1) {
                    sb.append(",");
                }
            }
            errorMsg = sb.toString();
        }
        throw this;
    }
    public AppGeneralError(String errorMsg) {
        this.errorMsg = errorMsg;
        throw this;
    }

    public AppGeneralError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        throw this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsgArgs(){
        Object[] args = new Object[]{errorCode,errorMsg};
        return JsonUtils.jsonSerialize(args);
    }
}
