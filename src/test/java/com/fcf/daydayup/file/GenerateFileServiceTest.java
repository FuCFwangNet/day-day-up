package com.fcf.daydayup.file;

import com.alibaba.fastjson.JSON;
import com.fcf.daydayup.DayDayUpApplication;
import com.fcf.daydayup.tools.file.GenerateFileService;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileRequest;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DayDayUpApplication.class)
public class GenerateFileServiceTest {
    @Autowired
    private GenerateFileService generateFileService;

    @Test
    public void generateFileTest() {
        DownloadTaskFileRequest downloadTaskFileRequest = new DownloadTaskFileRequest();
        downloadTaskFileRequest.setFileName("测试");
        downloadTaskFileRequest.setFileType("docx");
        downloadTaskFileRequest.setIsSync(true);
        downloadTaskFileRequest.setUseTemplate(true);
        downloadTaskFileRequest.setTemplateName("test_template.docx");

        DownloadTaskFileResponse response = generateFileService.generateFile(downloadTaskFileRequest);

        log.info("result-->>{}", JSON.toJSONString(response));
    }
}
