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
public class DemandeAccesVPNManagement extends AutoDocUtils{

	public void generateDocument(String titreDocument) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

		String resourcePath = "template\\demandeAccesVpn.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI())));
		this.replaceTextFor(doc, "{TITRE}", titreDocument);
		this.replaceTextFor(doc, "{DATE}", formattedDate);
		this.saveDocument("C:\\tmp\\TMA_GRC_" + DocumentTypes.DEMANDE_ACCES_VPN.getSigle()
				+ "_" + titreDocument + "_V1.0" 
				+ ".docx", doc);
	}

}
