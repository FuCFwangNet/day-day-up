package com.fcf.daydayup.face.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "file_downlad_task")
public class FileDownloadTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_title")
    private String fileTitle;

    @Column(name = "file_type")
    private String fileType;

    private String status;

    @Column(name = "blob_id")
    private Integer blobId;

    @Column(name = "create_dt")
    private Date createDt;

}