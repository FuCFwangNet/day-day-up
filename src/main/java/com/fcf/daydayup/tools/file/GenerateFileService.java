package com.fcf.daydayup.tools.file;

import com.fcf.daydayup.tools.file.model.DownloadTaskFileRequest;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileResponse;

public interface GenerateFileService {
    /**
     * 生成文件
     *
     * @param task 生成任务
     * @return 任务信息
     */
    DownloadTaskFileResponse generateFile(DownloadTaskFileRequest task);
}
