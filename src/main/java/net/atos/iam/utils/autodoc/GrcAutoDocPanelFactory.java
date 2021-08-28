package net.atos.iam.utils.autodoc;

import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentTypes;
import net.atos.iam.utils.layout.CahierTestPanel;
import net.atos.iam.utils.layout.ChiffrageDIPanel;
import net.atos.iam.utils.layout.ChiffrageFCPanel;
import net.atos.iam.utils.layout.CompteRenduReunionPanel;
import net.atos.iam.utils.layout.DemandeAccesUnixPanel;
import net.atos.iam.utils.layout.DemandeAccesVPNPanel;
import net.atos.iam.utils.layout.FicheRelecturePanel;
import net.atos.iam.utils.layout.FunctionalSpecificationPanel;
import net.atos.iam.utils.layout.GrcAutoDocPanel;
import net.atos.iam.utils.layout.GrilleEstimationPanel;
import net.atos.iam.utils.layout.GuideUtilisateurPanel;
import net.atos.iam.utils.layout.LivraisonProdPanel;
import net.atos.iam.utils.layout.MacroChiffragePanel;
import net.atos.iam.utils.layout.ManuelExploitationPanel;
import net.atos.iam.utils.layout.PatchSqlPanel;
import net.atos.iam.utils.layout.SpecificationTechniqueWsSoapPanel;

public class GrcAutoDocPanelFactory {
	
	GrcAutoDocPanel createPanel(String typeDocument) {
		if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.LIVRAISON_PROD)) {
			return new LivraisonProdPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.PATCH_SQL)) {
			return new PatchSqlPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.SPEC_GENERALE)) {
			return new FunctionalSpecificationPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.GUIDE_UTILISATEUR)) {
			return new GuideUtilisateurPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.FICHE_RELECTURE)) {
			return new FicheRelecturePanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.DEMANDE_ACCES_UNIX)) {
			return new DemandeAccesUnixPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.MANUEL_EXPLOITATION)) {
			return new ManuelExploitationPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.DEMANDE_ACCES_VPN)) {
			return new DemandeAccesVPNPanel();
		} else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.COMPTE_RENDU_REUNION)) {
			return new CompteRenduReunionPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.SPEC_TECHNIQUE_WS_SOAP)) {
			return new SpecificationTechniqueWsSoapPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.CAHIER_TEST)) {
			return new CahierTestPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.GRILLE_ESTIMATION)) {
			return new GrilleEstimationPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.CHIFFRAGE_FC)) {
			return new ChiffrageFCPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.CHIFFRAGE_DI)) {
			return new ChiffrageDIPanel();
		}else if(DocumentTypes.getDocumentTypeFromDesc(typeDocument).equals(DocumentTypes.MACRO_CHIFFRAGE)) {
			return new MacroChiffragePanel();
		}
		else {
			return null;
		}
	}

}
