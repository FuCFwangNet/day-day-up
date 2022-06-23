package com.fcf.daydayup.tools.file.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import com.fcf.daydayup.tools.file.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@Slf4j
public enum WordFileHelperImpl implements FileHelper {
    INSTANCE;

    @Override
    public String exportToFile(String fileName, Object data) {
        return null;
    }

    @Override
    public OutputStream exportToResponse(String ftlPath, Object data) {
        String fileName = "测试.docx";
        ClassPathResource resource = new ClassPathResource(File.separator + "templates" + File.separator + "test_template.docx");
        try {
            InputStream inputStream = resource.getInputStream();
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 8);
            template.write(bos);
            bos.flush();
            PoitlIOUtils.closeQuietlyMulti(template, bos);
            return bos;
        } catch (IOException e) {
            throw new RuntimeException("生成word失败!");
        }
    }


}
