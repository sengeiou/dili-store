package com.dili.alioss.sdk.exception;

/**
 * 七牛云上传异常
 */
public class QiniuUploadException extends RuntimeException {

    public QiniuUploadException(){
        super();
    }

    public QiniuUploadException(String msg){
        super(msg);
    }
}
