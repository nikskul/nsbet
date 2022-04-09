package com.project.nsbet.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

public class InvalidFileTypeException extends FileUploadException {

    public InvalidFileTypeException(String msg) {
        super(msg);
    }

}
