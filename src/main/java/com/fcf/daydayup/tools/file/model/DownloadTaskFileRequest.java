package com.fcf.daydayup.tools.file.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DownloadTaskFileRequest implements Serializable {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型 xls xlsx doc docx
     */
    private String fileType;
    /**
     * 入参json
     */
    private String queryJson;
    /**
     * 是否同步生成
     */
    private Boolean isSync;
    /**
     * 是否使用模板
     */
    private Boolean useTemplate;
    /**
     * 模板名称
     */
    private String templateName;
}
