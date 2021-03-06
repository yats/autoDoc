package net.atos.iam.utils.autodoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.atos.iam.utils.autodoc.mswordmanagement.FunctionalSpecificationManagement;

@SpringBootApplication
public class AutoDocApplication {

	public static void main(String[] args)  throws URISyntaxException, IOException {

		FunctionalSpecificationManagement fsm = new FunctionalSpecificationManagement();

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dt.format(new Date());
		
		String resourcePath = "templateFC.docx";
        Path templatePath = Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI());
        XWPFDocument doc =  new XWPFDocument(Files.newInputStream(templatePath));
        fsm.replaceTextFor(doc, "{TITRE}", "Resiliation FO");
        fsm.replaceTextFor(doc, "{ID_JIRA}", "12036");
        fsm.replaceTextFor(doc, "{CP_SI}", "ALLAM AOUATIF");
        fsm.replaceTextFor(doc, "{CP_PRESTATAIRE}", "Y.AMRI");
        fsm.replaceTextFor(doc, "{DATE}",formattedDate);
        
        
        fsm.removeParagrah(doc, "DESCRIPTION DE L’EXISTANT");
        
        fsm.createTOC(doc, 18);
        fsm.saveDocument("C:\\tmp\\document.docx", doc);
		SpringApplication.run(AutoDocApplication.class, args);
	}

}
