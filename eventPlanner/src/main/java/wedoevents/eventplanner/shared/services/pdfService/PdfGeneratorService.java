package wedoevents.eventplanner.shared.services.pdfService;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.util.List;


@Service
public class PdfGeneratorService {
    public static final String DEJAVU = "src/main/resources/fonts/DejaVuSans.ttf";

    public byte[] generateEventPdf(Event event) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);

            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            Text underlinedText = new Text("Dogadjaj:").setUnderline();
            Paragraph firstParagraph = new Paragraph().add(underlinedText);
            document.add(firstParagraph);
            document.add(new Paragraph("Naziv: " + event.getName()));
            document.add(new Paragraph("Tip dogadjaja: " + event.getEventType().getName()));
            document.add(new Paragraph("Opis dogadjaja: " + event.getDescription()));
            document.add(new Paragraph("Maksimalan broj ucesnika: " + event.getGuestCount()));
            String privacy = event.getIsPublic() ? "Otvorenog tipa"  : "Zatvorenog tipa";
            document.add(new Paragraph("Tip privatnosti: " + privacy));
            String location = event.getCity().getName() + ", " + event.getAddress();
            document.add(new Paragraph("Lokacijska ogranicenja: " + location));
            document.add(new Paragraph("Vremenska ogranicenja: " + event.getDate().toString()));

            document.add(new Paragraph().setMarginTop(20));
            Text agendaText = new Text("Agenda").setUnderline();
            Paragraph agendaParagraph = new Paragraph().add(agendaText);
            document.add(agendaParagraph);

            List<EventActivity> agenda = event.getEventActivities();
            if (agenda != null && !agenda.isEmpty()) {
                Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 4, 2})).useAllAvailableWidth();

                table.addHeaderCell("Vreme");
                table.addHeaderCell("Naziv");
                table.addHeaderCell("Opis");
                table.addHeaderCell("Lokacija");

                for (EventActivity activity : agenda) {
                    String time = activity.getStartTime() + " - " + activity.getEndTime();
                    table.addCell(time);
                    table.addCell(activity.getName());
                    table.addCell(activity.getDescription());
                    table.addCell(activity.getLocation());
                }

                document.add(table);
            } else {
                document.add(new Paragraph("Agenda nije planirana"));
            }

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
