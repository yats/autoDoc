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
public class CompteRenduReunionManagement extends AutoDocUtils{

	public void generateDocument(String titreDocument, String heureDebut, String heureFin, String ordreDuJour) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

		String resourcePath = "template\\compteRenduReunion.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI())));
		this.replaceTextFor(doc, "{TITRE}", titreDocument);
		this.replaceTextFor(doc, "{HEURE_DEBUT}", heureDebut);
		this.replaceTextFor(doc, "{HEURE_FIN}", heureFin);
		this.replaceTextFor(doc, "{ORDRE_DU_JOUR}", ordreDuJour);
		this.replaceTextFor(doc, "{DATE}", formattedDate);
		this.saveDocument("C:\\tmp\\TMA_GRC_" + DocumentTypes.COMPTE_RENDU_REUNION.getSigle()
				+ "_" + titreDocument + "_V1.0" 
				+ ".docx", doc);
	}

}
