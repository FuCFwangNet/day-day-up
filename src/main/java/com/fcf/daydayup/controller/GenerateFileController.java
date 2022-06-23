package com.fcf.daydayup.controller;

import com.fcf.daydayup.tools.file.GenerateFileService;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileRequest;
import com.fcf.daydayup.tools.file.model.DownloadTaskFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/file")
public class GenerateFileController {
    @Autowired
    private GenerateFileService genFileService;

    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public DownloadTaskFileResponse generateFile(@RequestBody DownloadTaskFileRequest request) {
        return genFileService.generateFile(request);
    }
}
