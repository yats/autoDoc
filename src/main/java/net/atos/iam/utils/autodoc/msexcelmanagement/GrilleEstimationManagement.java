package net.atos.iam.utils.autodoc.msexcelmanagement;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.aspose.cells.Workbook;

import net.atos.iam.utils.autodoc.AutoDocApplication;
import net.atos.iam.utils.autodoc.common.AutoDocUtils;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentTypes;

@Component
public class GrilleEstimationManagement extends AutoDocUtils{

	public void generateDocument(String titreDocument, String referenceJira,String description) throws Exception {

		String resourcePath = "template\\grilleEstimation.xlsm";
		
		Workbook  doc = new Workbook(Files.newInputStream(Paths.get(AutoDocApplication.class.getClassLoader().getResource(resourcePath).toURI())));
		this.replaceTextInExcel(doc, "{TITRE}", titreDocument);
		this.replaceTextInExcel(doc, "{ID_JIRA}", referenceJira);
		this.replaceTextInExcel(doc, "{DESCRIPTION}", description);
		doc.save("C:\\tmp\\TMA_GRC_" + DocumentTypes.GRILLE_ESTIMATION.getSigle()+ "_" + referenceJira + "_" + titreDocument + "_V_1.0.xlsm");
	}

}
