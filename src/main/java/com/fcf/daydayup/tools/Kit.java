package com.fcf.daydayup.tools;

import com.fcf.daydayup.tools.file.FileHelper;
import com.fcf.daydayup.tools.file.pdf.PdfFileHelperImpl;
import com.fcf.daydayup.tools.file.word.WordFileHelperImpl;
import com.fcf.daydayup.tools.mail.MailHelper;
import com.fcf.daydayup.tools.mail.MailHelperImpl;

public enum Kit {
    INSTANCE;

    public static Kit help() {
        return INSTANCE;
    }

    public FileHelper pdfFileHelper() {
        return PdfFileHelperImpl.INSTANCE;
    }

    public FileHelper wordFileHelper() {
        return WordFileHelperImpl.INSTANCE;
    }

    public MailHelper mailHelper() {
        return MailHelperImpl.INSTANCE;
    }
}
