package com.fcf.daydayup.tools.file.pdf.utils;

import com.fcf.daydayup.tools.file.pdf.exception.FreeMarkerException;
import com.fcf.daydayup.tools.file.pdf.exception.PDFException;
import com.google.common.collect.Maps;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

public class FreeMarkerUtil {

    private static final String WINDOWS_SPLIT = "\\";

    private static final String UTF_8 = "UTF-8";

    private static Map<String, FileTemplateLoader> fileTemplateLoaderCache = Maps.newConcurrentMap();

    private static Map<String, Configuration> configurationCache = Maps.newConcurrentMap();

    public static Configuration getConfiguration(String templateFilePath) {

        return Optional.ofNullable(configurationCache.get(templateFilePath))
                .orElseGet(() -> {
                            Configuration config = new Configuration(Configuration.VERSION_2_3_25);
                            config.setDefaultEncoding(UTF_8);
                            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                            config.setLogTemplateExceptions(false);
                            FileTemplateLoader fileTemplateLoader = null;
                            if (null != fileTemplateLoaderCache.get(templateFilePath)) {
                                fileTemplateLoader = fileTemplateLoaderCache.get(templateFilePath);
                            } else {
                                try {
                                    fileTemplateLoader = new FileTemplateLoader(new File(templateFilePath));
                                    fileTemplateLoaderCache.put(templateFilePath, fileTemplateLoader);
                                } catch (IOException e) {
                                    throw new FreeMarkerException("fileTemplateLoader init error!", e);
                                }
                            }
                            config.setTemplateLoader(fileTemplateLoader);
                            configurationCache.put(templateFilePath, config);
                            return config;
                        }
                );
    }


    /**
     * @description ????????????
     */
    public static String getContent(String fileName, Object data) {
        String templatePath = getPDFTemplatePath(fileName);
        String templateFileName = getTemplateName(templatePath);
        String templateFilePath = getTemplatePath(templatePath);
        if (StringUtils.isEmpty(templatePath)) {
            throw new FreeMarkerException("templatePath can not be empty!");
        }
        try {
            Template template = getConfiguration(templateFilePath).getTemplate(templateFileName);
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception ex) {
            throw new FreeMarkerException("FreeMarkerUtil process fail", ex);
        }
    }


    private static String getTemplatePath(String templatePath) {
        if (StringUtils.isEmpty(templatePath)) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(0, templatePath.lastIndexOf(WINDOWS_SPLIT));
        }
        return templatePath.substring(0, templatePath.lastIndexOf("/"));
    }

    private static String getTemplateName(String templatePath) {
        if (StringUtils.isEmpty(templatePath)) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(templatePath.lastIndexOf(WINDOWS_SPLIT) + 1);
        }
        return templatePath.substring(templatePath.lastIndexOf("/") + 1);
    }

    /**
     * @param fileName PDF?????????    (hello.pdf)
     * @return ?????????????????????
     * @description ??????PDF???????????????,
     * ????????????PDF????????????????????????
     */
    public static String getPDFTemplatePath(String fileName) {
        String classpath = PDFKit.class.getClassLoader().getResource("").getPath();
        String templatePath = classpath + "/templates";
        File file = new File(templatePath);
        if (!file.isDirectory()) {
            throw new PDFException("PDF?????????????????????,?????????templates?????????!");
        }
        String pdfFileName = fileName.substring(0, fileName.lastIndexOf("."));
        File defaultTemplate = null;
        File matchTemplate = null;
        for (File f : file.listFiles()) {
            if (!f.isFile()) {
                continue;
            }
            String templateName = f.getName();
            if (templateName.lastIndexOf(".ftl") == -1) {
                continue;
            }
            if (defaultTemplate == null) {
                defaultTemplate = f;
            }
            if (StringUtils.isEmpty(fileName) && defaultTemplate != null) {
                break;
            }
            templateName = templateName.substring(0, templateName.lastIndexOf("."));
            if (templateName.toLowerCase().equals(pdfFileName.toLowerCase())) {
                matchTemplate = f;
                break;
            }
        }
        if (matchTemplate != null) {
            return matchTemplate.getAbsolutePath();
        }
        if (defaultTemplate != null) {
            return defaultTemplate.getAbsolutePath();
        }

        return null;

    }


}
