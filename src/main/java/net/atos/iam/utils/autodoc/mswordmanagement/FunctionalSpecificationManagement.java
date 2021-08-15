package net.atos.iam.utils.autodoc.mswordmanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import net.atos.iam.utils.autodoc.AutoDocApplication;
import net.atos.iam.utils.autodoc.common.AutoDocUtils;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentTypes;

@Component
public class FunctionalSpecificationManagement extends AutoDocUtils{

	public void generateDocument(String titreDocument, String referenceJira,
			String versionDocument, String chefProjetSI) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

		String resourcePath = "template\\templateFC.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI())));
		this.replaceTextFor(doc, "{TITRE}", titreDocument);
		this.replaceTextFor(doc, "{ID_JIRA}", referenceJira);
		this.replaceTextFor(doc, "{CP_SI}", chefProjetSI);
		this.replaceTextFor(doc, "{CP_PRESTATAIRE}", "Y.AMRI");
		this.replaceTextFor(doc, "{DATE}", formattedDate);
		this.createTOC(doc, 18);
		this.saveDocument("C:\\tmp\\TMA_GRC_" + DocumentTypes.SPEC_GENERALE.getSigle()
				+ "_" + referenceJira + "_" + titreDocument + "_V" + versionDocument
				+ ".docx", doc);
	}

}
