package com.fcf.daydayup.tools.file.pdf.exception;

public class BaseException extends RuntimeException {
    public int errorCode;
    public String errorMsg;

    public BaseException(){
        super("runtime exception");
    }
    public BaseException(int errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }
    public BaseException(String errorMsg){
        super(errorMsg);
        this.errorCode=500;
        this.errorMsg=errorMsg;
    }
    public BaseException(String errorMsg, Exception e){
        super(errorMsg,e);
        this.errorCode=500;
        this.errorMsg=errorMsg;
    }


}
