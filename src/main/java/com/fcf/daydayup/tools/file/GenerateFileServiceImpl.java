package com.fcf.daydayup.tools.file;

import com.fcf.daydayup.face.model.FileDownloadBlob;
import com.fcf.daydayup.face.model.FileDownloadTask;
import com.fcf.daydayup.service.dao.FileDownloadBlobMapper;
import com.fcf.daydayup.service.dao.FileDownloadTaskMapper;
import com.fcf.daydayup.tools.Kit;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileRequest;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileResponse;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("generateFileService")
public class GenerateFileServiceImpl implements GenerateFileService {

    private ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10,
                    100,
                    60,
                    TimeUnit.MINUTES,
                    new SynchronousQueue<Runnable>());

    private FileDownloadBlobMapper fileDownloadBlobMapper;
    private FileDownloadTaskMapper taskDownloadTaskMapper;

    @Autowired
    public GenerateFileServiceImpl(FileDownloadBlobMapper fileDownloadBlobMapper, FileDownloadTaskMapper taskDownloadTaskMapper) {
        this.fileDownloadBlobMapper = fileDownloadBlobMapper;
        this.taskDownloadTaskMapper = taskDownloadTaskMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DownloadTaskFileResponse generateFile(DownloadTaskFileRequest task) {

        DownloadTaskFileResponse response = new DownloadTaskFileResponse();
        FileDownloadTask downloadTask = new FileDownloadTask();
        downloadTask.setFileTitle(task.getFileName());
        downloadTask.setFileType(task.getFileType());
        downloadTask.setCreateDt(new Date());
        downloadTask.setStatus("0");

        int i = taskDownloadTaskMapper.insertSelective(downloadTask);


        // ????????????????????????????????????
        try {
            if (!task.getIsSync()) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        if (task.getUseTemplate() && StringUtils.equals(task.getFileType(), "docx")) {
                            Integer integer = generateWordByTemplate(task.getQueryJson(), task.getTemplateName(), response);
                            downloadTask.setBlobId(integer);
                            downloadTask.setStatus("1");
                            taskDownloadTaskMapper.updateByPrimaryKeySelective(downloadTask);
                        }
                    }
                });
            } else {
                Integer integer = generateWordByTemplate(task.getQueryJson(), task.getTemplateName(), response);
                downloadTask.setBlobId(integer);
                downloadTask.setStatus("1");
                taskDownloadTaskMapper.updateByPrimaryKeySelective(downloadTask);
            }
        } catch (Exception e) {
            log.error("generateFile error", e);
            response.setStatus("0");
            //  ????????????????????????????????????
            taskDownloadTaskMapper.updateByPrimaryKeySelective(downloadTask);
        }
        return response;
    }

    private Integer generateWordByTemplate(String queryJson, String templateName, DownloadTaskFileResponse response) {
        try {

            // ???????????? ???????????????????????? ??????queryJson ??????
            Map<String, Object> data = initData();
            // ?????????????????? word
            byte[] bytes = Kit.help().wordFileHelper().export2File(templateName, data);
            // ????????????????????????????????????  ???response ???????????????id??????
            FileDownloadBlob downloadBlob = new FileDownloadBlob();
            downloadBlob.setFileContent(bytes);
            downloadBlob.setCreateDt(new Date());
            downloadBlob.setFileTitle(templateName);
            downloadBlob.setFileType("docx");
            fileDownloadBlobMapper.insertSelective(downloadBlob);

            return downloadBlob.getId();
        } catch (Exception e) {
            throw new RuntimeException("??????????????????", e);
        }
    }

    private Map<String, Object> initData() {
        Map<String, Object> data = new HashMap<>();
        data.put("title", "????????????");
        data.put("id", "0001");
        List<Map> list = new ArrayList<>();
        {
            Map map = Maps.newHashMap();
            map.put("name", "??????");
            map.put("name2", "??????");
            list.add(map);
        }
        {
            Map map = Maps.newHashMap();
            map.put("name", "??????san");
            map.put("name2", "??????si");
            list.add(map);
        }
        data.put("list", list);
        return data;
    }

    private void defaultGenerate(String queryJson, DownloadTaskFileResponse response) {

    }
}
