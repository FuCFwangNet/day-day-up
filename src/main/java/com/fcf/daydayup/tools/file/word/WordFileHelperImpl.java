package com.fcf.daydayup.tools.file.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import com.fcf.daydayup.tools.file.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public enum WordFileHelperImpl implements FileHelper {
    INSTANCE;

    @Override
    public String exportToFile(String fileName, Object data) {
        return null;
    }

    @Override
    public OutputStream exportToResponse(String ftlPath, Object data, HttpServletResponse response) {
        String fileName = "测试.docx";
        ClassPathResource resource = new ClassPathResource(File.separator + "templates" + File.separator + "test_template.docx");
        try {
            InputStream inputStream = resource.getInputStream();
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream out = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            template.write(bos);
            bos.flush();
            out.flush();
            PoitlIOUtils.closeQuietlyMulti(template, bos, out);
            return bos;
        } catch (IOException e) {
            throw new RuntimeException("生成word失败!");
        }
    }


}
