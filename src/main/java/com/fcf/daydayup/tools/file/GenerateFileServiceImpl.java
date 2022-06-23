package com.fcf.daydayup.tools.file;

import com.deepoove.poi.XWPFTemplate;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileRequest;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileResponse;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @Override
    public DownloadTaskFileResponse generateFile(DownloadTaskFileRequest task) {

        DownloadTaskFileResponse response = new DownloadTaskFileResponse();

        // 将对应的结果落地到数据库
        try {
            if (!task.getIsSync()) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        if (task.getUseTemplate() && StringUtils.equals(task.getFileType(), "docx")) {
                            generateWordByTemplate(task.getQueryJson(), task.getTemplateName(), response);
                        }
                    }
                });
            } else {
                defaultGenerate(task.getQueryJson(), response);
            }
        } catch (Exception e) {
            log.error("generateFile error", e);
            response.setStatus("0");
            //  将对应的结果保存到数据库 save(task);
        }
        return response;
    }

    private void generateWordByTemplate(String queryJson, String templateName, DownloadTaskFileResponse response) {
        try {
            // 使用模板生成 word
            ClassPathResource resource = new ClassPathResource(File.separator + "templates" + File.separator + templateName);
            InputStream inputStream = resource.getInputStream();
            // 业务处理 根据业务处理数据 根据queryJson 决定
            Map<String, Object> data = initData();

            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024 * 8);
            template.write(byteArrayOutputStream);
            byteArrayOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            // 将对应的结果落地到数据库  和response 对应的任务id绑定
        } catch (Exception e) {
            throw new RuntimeException("生成文件失败", e);
        }
    }

    private Map<String, Object> initData() {
        Map<String, Object> data = new HashMap<>();
        data.put("title", "测试附件");
        data.put("id", "0001");
        List<Map> list = new ArrayList<>();
        {
            Map map = Maps.newHashMap();
            map.put("name", "张三");
            map.put("name2", "李四");
            list.add(map);
        }
        {
            Map map = Maps.newHashMap();
            map.put("name", "张三san");
            map.put("name2", "李四si");
            list.add(map);
        }
        data.put("list", list);
        return data;
    }

    private void defaultGenerate(String queryJson, DownloadTaskFileResponse response) {

    }
}
