package org.pdfHtmlConverter.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.System.Logger;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.pdfHtmlConverter.Utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class HtmlToPdf implements Converter {
    private String arg = null;
    private static final Logger LOGGER = System.getLogger(HtmlToPdf.class.getName());

    public HtmlToPdf(String argString) {
        arg = argString;
    }

    private org.w3c.dom.Document html5ParseDocument(String urlStr, int timeoutMs) throws IOException {
		URL url = new URL(urlStr);
		Document doc;
		
		if (url.getProtocol().equalsIgnoreCase("file")) {
			doc = Jsoup.parse(new File(url.getPath()), "UTF-8");
		}
		else {
			doc = Jsoup.parse(url, timeoutMs);	
		}

		return new W3CDom().fromJsoup(doc);
	}

    private org.w3c.dom.Document parseRawHtml5String(String html) {
        org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(html, "UTF-8");
        jsoupDoc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        return new W3CDom().fromJsoup(jsoupDoc);
    }

    @Override
    public void convert() throws IllegalArgumentException {
        if (arg == null) throw new IllegalArgumentException("Expected file path or HTML");

        Boolean isHtml = Utils.isHtml(arg);
        String outputPath = isHtml ? "./pdfFromRawHtml.pdf" : "./" + Utils.getFileName(arg) + ".pdf";
        try (OutputStream os = new FileOutputStream(outputPath)) {
            File inputHtml = new File(arg);
            org.w3c.dom.Document doc = isHtml ? parseRawHtml5String(arg) : html5ParseDocument(arg, 10000);
            
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withW3cDocument(doc, isHtml ? "/" : inputHtml.toURI().toString());
            builder.toStream(os);
            builder.run();
            
            LOGGER.log(System.Logger.Level.INFO, () -> "Conversion complete: " + outputPath);
        } catch (Exception e) {
            LOGGER.log(System.Logger.Level.ERROR, "Unable to convert to PDF", e);
        }
    }
    
}