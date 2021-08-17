package net.atos.iam.utils.autodoc.mswordmanagement.constantes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DocumentTypes {

	SPEC_GENERALE("SPEC_GENERALE","Spécification générale","SFD"),
	GUIDE_UTILISATEUR("GUIDE_UTILISATEUR","Guide utilisateur","GU"),
	FICHE_RELECTURE("FICHE_RELECTURE","Fiche de relecture","FR"),
	PATCH_SQL("PATCH_SQL","Patch SQL","PQ"),
	MANUEL_EXPLOITATION("MANUEL_EXPLOITATION","Manuel d'exploitation","ME");

	private final String documentCd;
	private final String documentDesc;
	private final String sigle;
	
	DocumentTypes(String documentCd,String documentDesc,String sigle) {
		this.documentCd = documentCd;
		this.documentDesc = documentDesc;
		this.sigle = sigle;
	}

	public String getDocumentCd() {
		return documentCd;
	}

	public String getDocumentDesc() {
		return documentDesc;
	}
	
    public static DocumentTypes getDescFromCode(final String code)  {
        
        if(null == code || code.length()==0) return null;
        
        for(DocumentTypes element:  DocumentTypes.values()) {
            if(element.getDocumentCd().equalsIgnoreCase(code)) {
                return element;
            }
        }
        return null;
    }
    
    public static DocumentTypes getDocumentTypeFromDesc(final String desc)  {
        
        if(null == desc || desc.length()==0) return null;
        
        for(DocumentTypes element:  DocumentTypes.values()) {
            if(element.getDocumentDesc().equalsIgnoreCase(desc)) {
                return element;
            }
        }
        return null;
    }
    
    public static String[] getEnumDescAsArray() {
    	return  (String[]) Arrays.stream(DocumentTypes.values()).map(element -> {
    		return element.getDocumentDesc();
    	}).toArray();
    }

    public static List<String> getEnumDescAsList() {
    	return  Arrays.stream(DocumentTypes.values()).map(element -> {
    		return element.getDocumentDesc();
    	}).collect(Collectors.toList());
    }

	public String getSigle() {
		return sigle;
	}
	
}
