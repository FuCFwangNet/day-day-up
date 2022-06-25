package com.fcf.daydayup.face.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "file_download_blob")
public class FileDownloadBlob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_title")
    private String fileTitle;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "file_content")
    private byte[] fileContent;

}