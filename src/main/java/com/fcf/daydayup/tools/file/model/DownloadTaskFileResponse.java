package com.fcf.daydayup.tools.file.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DownloadTaskFileResponse implements Serializable {
    private String id;

    private String name;

    private String status;

    private String dataId;

    private byte[] content;

}
