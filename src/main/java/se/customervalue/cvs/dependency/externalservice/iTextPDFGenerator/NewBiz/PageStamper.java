package se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator.NewBiz;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PageStamper extends PdfPageEventHelper {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		final int currentPageNumber = writer.getCurrentPageNumber();

		try {
			if (currentPageNumber == 1 || currentPageNumber == 2) {
				return;
			}

			final Rectangle pageSize = document.getPageSize();
			final PdfContentByte directContent = writer.getDirectContent();

			directContent.setColorFill(BaseColor.GRAY);
			directContent.setFontAndSize(BaseFont.createFont(), 10);

			directContent.setTextMatrix(pageSize.getRight(40), pageSize.getBottom(30));
			directContent.showText(String.valueOf(currentPageNumber));
		} catch (Exception ex) {
			log.error("[PDFGenerator] Error numbering report pages!");
		}
	}
}