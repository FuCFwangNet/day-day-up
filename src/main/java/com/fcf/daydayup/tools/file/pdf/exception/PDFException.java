package com.fcf.daydayup.tools.file.pdf.exception;

public class PDFException extends BaseException {

    public PDFException() {
        super("PDF run error");
    }

    public PDFException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public PDFException(String errorMsg) {
        super(errorMsg);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

    public PDFException(String errorMsg, Exception e) {
        super(errorMsg, e);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }


}
