package se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.customervalue.cvs.abstraction.dataaccess.CurrencyRepository;
import se.customervalue.cvs.abstraction.externalservice.PDFGenerationService.PDFGenerationService;
import se.customervalue.cvs.api.representation.GennyRequestRepresentation;
import se.customervalue.cvs.common.CVSConfig;
import se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator.NewBiz.NewBizText;
import se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator.NewBiz.PageStamper;
import se.customervalue.cvs.dependency.externalservice.iTextPDFGenerator.exception.PDFGenerationException;
import se.customervalue.cvs.domain.Currency;

import java.io.FileOutputStream;

@Service
public class PDFGenerationServiceiText implements PDFGenerationService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public void generateNewBizPDF(String[] charts, GennyRequestRepresentation options, int newReportId) throws PDFGenerationException {
		Currency reportCurrency = currencyRepository.findByCurrencyId(options.getCurrencyId());

		try {
			Document document = new Document();
			document.setPageSize(PageSize.A4);
			document.setMargins(49, 49, 43, 29);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(CVSConfig.REPORTS_FS_STORE + "/" + newReportId + ".pdf"));
			writer.setPageEvent(new PageStamper());
			document.open();
			Font text = FontFactory.getFont("Helvetica", 10, Font.NORMAL);
			Font title = FontFactory.getFont("Helvetica", 11, Font.BOLD);

			// Introduction
			document.add(new Paragraph(NewBizText.paragraph1[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph2[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph3[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph4[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph5[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph6[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph7[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph8[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.newPage();

			// Nykundsackvisition
			document.add(new Paragraph(NewBizText.title1[options.getLanguageId()], title));
			document.add(new Paragraph(NewBizText.paragraph9[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph10[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			Image chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[0] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			Paragraph caption = new Paragraph("Figure 1", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[1] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 2", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);

			document.newPage();

			// Försäljning nya vs återkommande kunder per månad
			document.add(new Paragraph(NewBizText.title2[options.getLanguageId()], title));
			document.add(new Paragraph(NewBizText.paragraph11[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph12[options.getLanguageId()].replace("SEK", reportCurrency.getISO4217()), text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph13[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[2] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 3", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[3] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 4", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.newPage();

			document.add(new Paragraph(NewBizText.paragraph14[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[4] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 5", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			document.add(new Paragraph(NewBizText.paragraph15[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph16[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph17[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph18[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph19[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.newPage();

			// Försäljningsutveckling och köpbeteende
			document.add(new Paragraph(NewBizText.title3[options.getLanguageId()], title));
			document.add(new Paragraph(NewBizText.paragraph20[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph21[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph22[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[5] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 6", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			document.add(new Paragraph(NewBizText.paragraph23[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph24[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph25[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph26[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph27[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph28[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph29[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[6] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 7", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			document.add(new Paragraph(NewBizText.paragraph30[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph31[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph32[options.getLanguageId()].replace("SEK", reportCurrency.getISO4217()), text));
			document.add(Chunk.NEWLINE);
			document.newPage();

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[7] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 8", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			chart = Image.getInstance(CVSConfig.TEMP_FS_STORE + "/" + charts[8] + ".png");
			chart.scaleToFit(PageSize.A4.getWidth(), 241);
			document.add(chart);
			caption = new Paragraph("Figure 9", text);
			caption.setAlignment(Element.ALIGN_CENTER);
			document.add(caption);
			document.add(Chunk.NEWLINE);

			document.add(new Paragraph(NewBizText.paragraph33[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph34[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph35[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph36[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph37[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph38[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph39[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph40[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph41[options.getLanguageId()].replace("SEK", reportCurrency.getISO4217()), text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph42[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(NewBizText.paragraph43[options.getLanguageId()], text));
			document.add(Chunk.NEWLINE);

			document.close();
		} catch (Exception ex) {
			log.error("[PDFGenerator] Error generating PDF report!");
			throw new PDFGenerationException();
		}
	}
}
