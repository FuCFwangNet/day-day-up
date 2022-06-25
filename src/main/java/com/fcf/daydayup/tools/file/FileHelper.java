package com.fcf.daydayup.tools.file;

import java.io.OutputStream;

public interface FileHelper {
    /**
     * 根据文件名称以及动态参数将内容输出到文本
     *
     * @param fileName 文件名称 ***模板名称需保持一致
     * @param data     数据
     * @return 文件地址
     */
    String exportToFile(String fileName, Object data);

    /**
     * 根据文件名称，将生成文本以数据流的形式输出
     *
     * @param ftlPath 模板路径
     * @param data    数据
     * @return 输出流
     */
    OutputStream exportToResponse(String ftlPath, Object data);

    byte[] export2File(String templateName,Object data);
}
