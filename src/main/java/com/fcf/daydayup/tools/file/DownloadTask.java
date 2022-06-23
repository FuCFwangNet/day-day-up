package com.fcf.daydayup.tools.file;

import java.sql.Timestamp;

public interface DownloadTask {
    public String getId();

    public String getName();

    public String getStatus();

    public String getAdmBlobDataId();

    public Timestamp getStartTime();

    public Timestamp getCompleteTime();

    public String getMemo();

    public String getCreateBy();

    public String getReqParams();

    public String getBeanName();

    public int getProgress();
}
