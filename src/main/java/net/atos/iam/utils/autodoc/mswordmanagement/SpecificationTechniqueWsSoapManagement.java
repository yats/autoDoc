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
public class SpecificationTechniqueWsSoapManagement extends AutoDocUtils{

	public void generateDocument(String titreDocument, String referenceJira,
			 String chefProjetSI,String prestataire,String nomWebservice) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

		String resourcePath = "template\\specTechniqueWebservice.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI())));
		this.replaceTextFor(doc, "{TITRE}", titreDocument);
		this.replaceTextFor(doc, "{ID_JIRA}", referenceJira);
		this.replaceTextFor(doc, "{CP_SI}", chefProjetSI);
		this.replaceTextFor(doc, "{CP_PRESTATAIRE}", prestataire);
		this.replaceTextFor(doc, "{NOM_WEBSERVICE}", nomWebservice);
		this.replaceTextFor(doc, "{DATE}", formattedDate);
		this.saveDocument("C:\\tmp\\TMA_GRC_" + DocumentTypes.SPEC_TECHNIQUE_WS_SOAP.getSigle()
				+ "_" + referenceJira + "_" + titreDocument + "_V_1.0.docx", doc);
	}

}
