package edu.ilstu.chtmltranscriptor.models;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileUploadBean {

    private MultipartFile[] files;

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public MultipartFile[] getFiles() {
        return files;
    }
}