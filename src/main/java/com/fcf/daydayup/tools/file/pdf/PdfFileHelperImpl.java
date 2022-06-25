package com.fcf.daydayup.tools.file.pdf;

import com.fcf.daydayup.tools.file.FileHelper;
import com.fcf.daydayup.tools.file.pdf.builder.PDFHeaderFooter;
import com.fcf.daydayup.tools.file.pdf.utils.PDFKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Slf4j
public enum PdfFileHelperImpl implements FileHelper {
    INSTANCE;

    @Override
    public String exportToFile(String fileName, Object data) {
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter = new PDFHeaderFooter();
            PDFKit kit = new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("D:\\other\\pdf-kit-master\\pdf-kit-master\\src\\test\\resources\\templates\\changeRecord.pdf");

            return kit.exportToFile(fileName, data);
        } catch (Exception e) {
            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }

    @Override
    public OutputStream exportToResponse(String ftlPath, Object data) {
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter = new PDFHeaderFooter();
            PDFKit kit = new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("D:\\other\\pdf-kit-master\\pdf-kit-master\\src\\test\\resources\\templates\\changeRecord.pdf");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024 * 8);

            return kit.exportToResponse(ftlPath, data, byteArrayOutputStream);
        } catch (Exception e) {
            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }

    @Override
    public byte[] export2File(String templateName, Object data) {
        return new byte[0];
    }
}
