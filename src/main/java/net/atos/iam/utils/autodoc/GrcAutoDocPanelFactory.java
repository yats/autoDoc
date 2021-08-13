package net.atos.iam.utils.autodoc;

import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentTypes;
import net.atos.iam.utils.layout.FunctionalSpecificationPanel;
import net.atos.iam.utils.layout.GrcAutoDocPanel;
import net.atos.iam.utils.layout.PatchSqlPanel;

public class GrcAutoDocPanelFactory {
	
	GrcAutoDocPanel createPanel(String typeDocument) {
		if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.PATCH_SQL)) {
			return new PatchSqlPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.SPEC_GENERALE)) {
			return new FunctionalSpecificationPanel();
		} else {
			return null;
		}
	}

}
