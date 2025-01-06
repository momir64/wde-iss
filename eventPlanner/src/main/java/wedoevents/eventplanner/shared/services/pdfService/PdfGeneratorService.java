package wedoevents.eventplanner.shared.services.pdfService;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.Event;
import wedoevents.eventplanner.eventManagement.models.EventActivity;
import com.itextpdf.layout.properties.UnitValue;
import wedoevents.eventplanner.productManagement.dtos.CatalogueProductDTO;
import wedoevents.eventplanner.serviceManagement.dtos.CatalogueServiceDTO;
import wedoevents.eventplanner.userManagement.dtos.ReviewDistributionDTO;
import wedoevents.eventplanner.userManagement.models.userTypes.Guest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;


@Service
public class PdfGeneratorService {
    public static final String DEJAVU = "src/main/resources/fonts/DejaVuSans.ttf";

    public byte[] generateEventPdf(Event event, List<Guest> invitedGuests, List<Guest> acceptedGuests, ReviewDistributionDTO reviewDistribution) {
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

            if (!acceptedGuests.isEmpty()) {
                Text acceptedText = new Text("Potvrdjeni gosti:").setUnderline();
                Paragraph acceptedParagraph = new Paragraph().add(acceptedText);
                document.add(acceptedParagraph);

                for (Guest guest : acceptedGuests) {
                    document.add(new Paragraph(guest.getName() + " " + guest.getSurname()));
                }
            }


            if (!invitedGuests.isEmpty()) {
                Text invitedText = new Text("Gosti koji jos nisu potvrdili dolazak:").setUnderline();
                Paragraph invitedParagraph = new Paragraph().add(invitedText);
                document.add(invitedParagraph);

                for (Guest guest : invitedGuests) {
                    document.add(new Paragraph(guest.getName() + " " + guest.getSurname()));
                }
            }
            if (reviewDistribution != null && (reviewDistribution.getOne() > 0 || reviewDistribution.getTwo() > 0 || reviewDistribution.getThree() > 0 || reviewDistribution.getFour() > 0 || reviewDistribution.getFive() > 0)) {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                dataset.addValue(reviewDistribution.getOne(), "Reviews", "1");
                dataset.addValue(reviewDistribution.getTwo(), "Reviews", "2");
                dataset.addValue(reviewDistribution.getThree(), "Reviews", "3");
                dataset.addValue(reviewDistribution.getFour(), "Reviews", "4");
                dataset.addValue(reviewDistribution.getFive(), "Reviews", "5");

                JFreeChart chart = ChartFactory.createBarChart(
                        "Recenzije",
                        "Rating",
                        "Count",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,
                        false,
                        false
                );

                BufferedImage chartImage = chart.createBufferedImage(500, 300);
                ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
                ImageIO.write(chartImage, "PNG", chartOut);

                byte[] chartImageData = chartOut.toByteArray();
                Image chartPdfImage = new Image(ImageDataFactory.create(chartImageData));

                document.add(new Paragraph("Recenzije:"));
                document.add(chartPdfImage);

            }


            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public byte[] generateServicesCatalogue(List<CatalogueServiceDTO> catalogueServicesFromSeller) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);

            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            Text underlinedText = new Text("Cenovnik usluga").setUnderline().setBold().setFontSize(35);
            Paragraph firstParagraph = new Paragraph().add(underlinedText).setTextAlignment(TextAlignment.CENTER);
            document.add(firstParagraph);

            document.add(new Paragraph().setMarginTop(20));

            Table table = new Table(UnitValue.createPercentArray(new float[]{5, 5})).useAllAvailableWidth();

            table.addHeaderCell("Usluga");
            table.addHeaderCell(new Cell().add(new Paragraph("Trenutna cena")).setTextAlignment(TextAlignment.RIGHT));

            for (CatalogueServiceDTO cataloguedService : catalogueServicesFromSeller) {
                table.addCell(cataloguedService.getName());

                Double price = cataloguedService.getSalePercentage() == null ?
                        cataloguedService.getPrice() : cataloguedService.getPrice() * (1 - cataloguedService.getSalePercentage());

                table.addCell(new Cell().add(new Paragraph(String.format("%.2f€/hr", price)).setTextAlignment(TextAlignment.RIGHT)));
            }

            document.add(table);

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public byte[] generateProductsCatalogue(List<CatalogueProductDTO> catalogueProductsFromSeller) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);

            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            Text underlinedText = new Text("Cenovnik proizvoda").setUnderline().setBold().setFontSize(35);
            Paragraph firstParagraph = new Paragraph().add(underlinedText).setTextAlignment(TextAlignment.CENTER);
            document.add(firstParagraph);

            document.add(new Paragraph().setMarginTop(20));

            Table table = new Table(UnitValue.createPercentArray(new float[]{5, 5})).useAllAvailableWidth();

            table.addHeaderCell("Proizvod");
            table.addHeaderCell(new Cell().add(new Paragraph("Trenutna cena")).setTextAlignment(TextAlignment.RIGHT));

            for (CatalogueProductDTO cataloguedProduct : catalogueProductsFromSeller) {
                table.addCell(cataloguedProduct.getName());

                Double price = cataloguedProduct.getSalePercentage() == null ?
                        cataloguedProduct.getPrice() : cataloguedProduct.getPrice() * (1 - cataloguedProduct.getSalePercentage());

                table.addCell(new Cell().add(new Paragraph(String.format("%.2f€", price)).setTextAlignment(TextAlignment.RIGHT)));
            }

            document.add(table);

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
