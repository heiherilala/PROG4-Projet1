package com.hei.project2p1.controller.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;

public class CustomResponse {

    public static void convertHtmlToPdf(String filename, String html, HttpServletResponse response)  {
        String outputFileName = filename+".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + outputFileName);
        try {
            OutputStream outputStream = response.getOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
