package com.fcf.daydayup.tools.file.pdf.utils;

import com.fcf.daydayup.tools.file.pdf.builder.HeaderFooterBuilder;
import com.fcf.daydayup.tools.file.pdf.builder.PDFBuilder;
import com.fcf.daydayup.tools.file.pdf.exception.PDFException;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

@Slf4j
@Setter
@Getter
public class PDFKit {

    /**
     * PDF页眉、页脚定制工具
     */
    private HeaderFooterBuilder headerFooterBuilder;
    private String saveFilePath;

    /**
     * @param fileName 输出PDF文件名
     * @param data     模板所需要的数据
     * @description 导出pdf到文件
     */
    public String exportToFile(String fileName, Object data) {

        String htmlData = FreeMarkerUtil.getContent(fileName, data);
        if (StringUtils.isEmpty(saveFilePath)) {
            saveFilePath = getDefaultSavePath(fileName);
        }
        File file = new File(saveFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            //设置输出路径
            outputStream = new FileOutputStream(saveFilePath);
            //设置文档大小
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            //设置页眉页脚
            PDFBuilder builder = new PDFBuilder(headerFooterBuilder, data);
            builder.setPresentFontSize(10);
            writer.setPageEvent(builder);

            //输出为PDF文件
            convertToPDF(writer, document, htmlData);
        } catch (Exception ex) {
            throw new PDFException("PDF export to File fail", ex);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return saveFilePath;

    }


    /**
     * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
     *
     * @param ftlPath  ftl模板文件的路径（不含文件名）
     * @param data     输入到FTL中的数据
     * @param response OutputStream
     * @return
     */
    public OutputStream exportToResponse(String ftlPath, Object data,
                                         OutputStream response) {

        String html = FreeMarkerUtil.getContent(ftlPath, data);

        try {
            //设置文档大小
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response);
            //设置页眉页脚
            PDFBuilder builder = new PDFBuilder(headerFooterBuilder, data);
            writer.setPageEvent(builder);
            //输出为PDF文件
            convertToPDF(writer, document, html);
            return response;
        } catch (Exception ex) {
            throw new PDFException("PDF export to response fail", ex);
        }

    }

    /**
     * @description PDF文件生成
     */
    private void convertToPDF(PdfWriter writer, Document document, String htmlString) {
        //获取字体路径
        String fontPath = getFontPath();
        document.open();
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(htmlString.getBytes()),
                    XMLWorkerHelper.class.getResourceAsStream("/default.css"),
                    Charset.forName("UTF-8"), new XMLWorkerFontProvider(fontPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new PDFException("PDF文件生成异常", e);
        } finally {
            document.close();
        }

    }

    /**
     * @description 创建默认保存路径
     */
    private String getDefaultSavePath(String fileName) {
        String classpath = PDFKit.class.getClassLoader().getResource("").getPath();
        String saveFilePath = classpath + "pdf/" + fileName;
        File f = new File(saveFilePath);
        if (!f.getParentFile().exists()) {
            f.mkdirs();
        }
        return saveFilePath;
    }

    /**
     * @description 获取字体设置路径
     */
    public static String getFontPath() {
        String classpath = PDFKit.class.getClassLoader().getResource("").getPath();
        return classpath + "fonts";
    }

}