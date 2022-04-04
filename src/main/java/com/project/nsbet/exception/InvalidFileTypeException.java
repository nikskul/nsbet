package com.project.nsbet.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

public class InvalidFileTypeException extends FileUploadException {
    public InvalidFileTypeException() {
    }

    public InvalidFileTypeException(String msg) {
        super(msg);
    }

    public InvalidFileTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidFileTypeException(Throwable cause) {
        super(cause);
    }
}
