package net.atos.iam.utils.autodoc.mswordmanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import net.atos.iam.utils.autodoc.common.AutoDocUtils;

@Component
public class LivraisonProdManagement extends AutoDocUtils {

	
	private static final String DATE = "{DATE}";
	private static final String CP_PRESTATAIRE = "{CP_PRESTATAIRE}";
	private static final String REFERENCE_LIVRAISON = "{REF_LIVRAISON}";
	private static final String PATCH_DESTINATION_FOLDER = "C:\\tmp\\livraisonProd\\GRC_";
	private static final String PATCH_SOURCE_FOLDER = "template\\GRC_MEP_PROD";
	
	private static final int PARAGRAPH_FIXE_START = 56;
	private static final int PARAGRAPH_FIXE_END = 83;
	private static final int PARAGRAPH_MOBILE_START = 72;
	private static final int PARAGRAPH_MOBILE_END = 99;

	
	private static final int PARAGRAPH_PROPERTIES_START = 18;
	private static final int PARAGRAPH_PROPERTIES_END = 32;
	private static final int PARAGRAPH_CERTIFICAT_WS_START = 33;
	private static final int PARAGRAPH_CERTIFICAT_WS_END = 55;
	private static final int PARAGRAPH_BATCH_START = 56;
	private static final int PARAGRAPH_BATCH_END = 75;
	private static final int PARAGRAPH_CERTIFICAT_JDK_START = 76;
	private static final int PARAGRAPH_CERTIFICAT_JDK_END = 91;
	

	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_1_START = 30;
	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_1_END = 35;
	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_2_START = 36;
	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_2_END = 47;
	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_3_START = 48;
	private static final int PARAGRAPH_DEPLOIEMENT_TYPE_3_END = 53;
	
	/**
	 * 
	 * @param doc
	 * @param paragraphTitle
	 */
	
	
	public void generateDocument(String referenceLivraison,
			String versionDocument,String cpPrestataire,List<String> checkedSqlItems,List<String> checkedDbaItems,List<String> checkedSystemItems,String checkedDeploiementItem) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
		
		FileUtils.copyDirectory(new File(ClassLoader.getSystemResource(PATCH_SOURCE_FOLDER).toURI()), new File(PATCH_DESTINATION_FOLDER+referenceLivraison));
		generateManuelSql(referenceLivraison, versionDocument,cpPrestataire, checkedSqlItems, formattedDate);
		generateSqlFiles(referenceLivraison, checkedSqlItems,cpPrestataire, formattedDate);
		
		generateManuelDBA(referenceLivraison, versionDocument);
		generateDbaFiles(referenceLivraison,cpPrestataire,formattedDate);
		generateDemandeAcces(referenceLivraison, versionDocument,formattedDate,"Demande_de _droits_accès_Production_GRC_Comptes_nominatifs"); 
		generateDemandeAcces(referenceLivraison, versionDocument,formattedDate,"Demande_de _droits_accès_Production_GRC_DEPLOY");
		
		generateDemandeSystem(referenceLivraison,versionDocument,cpPrestataire,formattedDate,checkedSystemItems);
		
		generateInstallationManual(referenceLivraison,versionDocument,cpPrestataire,formattedDate,checkedDeploiementItem);
		
	}
	
	private void generateDbaFiles(String referenceLivraison, String cpPrestataire,String formattedDate) throws IOException {
		String manuelTmpPath = PATCH_DESTINATION_FOLDER + referenceLivraison + "\\DBA";

		Path scriptSourceFileFixe = Paths.get(manuelTmpPath + "\\DDL_GRC_FX.sql");
		Path scriptSourceFileMobile = Paths.get(manuelTmpPath + "\\DDL_GRC_MB.sql");

		for (Path path : new Path[] { scriptSourceFileFixe, scriptSourceFileMobile }) {
			replaceTextInFile(path, REFERENCE_LIVRAISON, referenceLivraison);
			replaceTextInFile(path, CP_PRESTATAIRE, cpPrestataire);
			replaceTextInFile(path, DATE, formattedDate);
		}

		Files.move(scriptSourceFileFixe,
				scriptSourceFileFixe.resolveSibling("DDL_GRC_FX" + "_" + referenceLivraison + ".sql"));
		Files.move(scriptSourceFileMobile,
				scriptSourceFileMobile.resolveSibling("DDL_GRC_MB" + "_" + referenceLivraison + ".sql"));

	}

	private void generateSqlFiles(String referenceJira, List<String> checkedItems, String cpPrestataire,String formattedDate) throws IOException {
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceJira+"\\Script";
		
		if(null!= checkedItems && !checkedItems.contains("FIXE")) {
			FileUtils.deleteDirectory(new File(manuelTmpPath+"\\LIVR_FX")); 
		} else {
			Path scriptSourceFolderFixe = Paths.get(manuelTmpPath+"\\LIVR_FX");
			Path scriptSourceFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\GRC_BD_FX.sql");
			Path scriptSourceSanityFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\GRC_BD_FX_SANITY_TESTS.sql");
			Path scriptSourceChapeauFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\sql_chapeau.sql");
			Path scriptSourceSanityChapeauFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\sql_sanity.sql");
			
			replaceTextInFile(scriptSourceFileFixe,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceChapeauFileFixe,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceSanityChapeauFileFixe,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceFileFixe,CP_PRESTATAIRE,cpPrestataire);
			replaceTextInFile(scriptSourceFileFixe,DATE,formattedDate);
			Files.move(scriptSourceFileFixe, scriptSourceFileFixe.resolveSibling("GRC_BD_FX" + "_"+referenceJira+".sql"));
			Files.move(scriptSourceSanityFileFixe, scriptSourceSanityFileFixe.resolveSibling("GRC_BD_FX_SANITY_TESTS" + "_"+referenceJira+".sql"));
			Files.move(scriptSourceFolderFixe, scriptSourceFolderFixe.resolveSibling("LIVR_FX" + "_"+referenceJira));
		}
		
		
		if(null!= checkedItems && !checkedItems.contains("MOBILE")) {
			FileUtils.deleteDirectory(new File(manuelTmpPath+"\\LIVR_MB")); 
		} else {
			Path scriptSourceFolderMobile = Paths.get(manuelTmpPath+"\\LIVR_MB");
			Path scriptSourceFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\GRC_BD_MB.sql");
			Path scriptSourceSanityFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\GRC_BD_MB_SANITY_TESTS.sql");
			Path scriptSourceChapeauFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\sql_chapeau.sql");
			Path scriptSourceSanityChapeauFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\sql_sanity.sql");
			
			replaceTextInFile(scriptSourceFileMobile,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceChapeauFileMobile,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceSanityChapeauFileMobile,REFERENCE_LIVRAISON,referenceJira);
			replaceTextInFile(scriptSourceFileMobile,CP_PRESTATAIRE,cpPrestataire);
			replaceTextInFile(scriptSourceFileMobile,DATE,formattedDate);
			Files.move(scriptSourceFileMobile, scriptSourceFileMobile.resolveSibling("GRC_BD_MB" + "_"+referenceJira+".sql"));
			Files.move(scriptSourceSanityFileMobile, scriptSourceSanityFileMobile.resolveSibling("GRC_BD_MB_SANITY_TESTS" + "_"+referenceJira+".sql"));
			Files.move(scriptSourceFolderMobile, scriptSourceFolderMobile.resolveSibling("LIVR_MB" + "_"+referenceJira));
		}
	}

	private void generateManuelSql(String referenceLivraion, String versionDocument,String cpPrestataire, List<String> checkedItems,
			String formattedDate) throws IOException, FileNotFoundException {
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraion+"\\Manuel\\GRC_OPM_Manuel_d_installation_SQL.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(manuelTmpPath)));
		this.replaceTextFor(doc, REFERENCE_LIVRAISON, referenceLivraion);
		this.replaceTextFor(doc, CP_PRESTATAIRE, cpPrestataire);
		this.replaceTextFor(doc, DATE, formattedDate);
		
		logParagraphs(doc);		
		if(null!= checkedItems && !checkedItems.contains("FIXE")) {
			this.removeParagrahRange(doc, PARAGRAPH_FIXE_START,PARAGRAPH_FIXE_END); 
		}
		
		if(null!= checkedItems && !checkedItems.contains("MOBILE")) {
			this.removeParagrahRange(doc, PARAGRAPH_MOBILE_START,PARAGRAPH_MOBILE_END); 
		}
		
		this.createTOC(doc, 38);
		logParagraphs(doc);
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraion+"\\Manuel\\GRC_OPM_Manuel_d_installation_SQL"
				+ "_" + referenceLivraion + "_V" + versionDocument
				+ ".docx", doc);
		this.deleteDocument(manuelTmpPath);
	}
	
	
	private void generateManuelDBA(String referenceLivraion, String versionDocument) throws IOException, FileNotFoundException {
		String manuelFXTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\GRC_DBA_FX.docx";
		XWPFDocument docFx = new XWPFDocument(Files.newInputStream(Paths.get(manuelFXTmpPath)));
		this.replaceTextFor(docFx, REFERENCE_LIVRAISON, referenceLivraion);
		
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\GRC_DBA_FX"
				+ "_" + referenceLivraion + "_V" + versionDocument
				+ ".docx", docFx);
		this.deleteDocument(manuelFXTmpPath);
		
		String manuelMBTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\GRC_DBA_MB.docx";
		XWPFDocument docMb = new XWPFDocument(Files.newInputStream(Paths.get(manuelMBTmpPath)));
		this.replaceTextFor(docMb, REFERENCE_LIVRAISON, referenceLivraion);
		
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\GRC_DBA_MB"
				+ "_" + referenceLivraion + "_V" + versionDocument
				+ ".docx", docMb);
		this.deleteDocument(manuelMBTmpPath);
	}
	
	private void generateDemandeAcces(String referenceLivraion, String versionDocument,String formattedDate,String refDocument) throws IOException, FileNotFoundException {
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\"+refDocument+".docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(manuelTmpPath)));
		this.replaceTextFor(doc, REFERENCE_LIVRAISON, referenceLivraion);
		this.replaceTextFor(doc, DATE, referenceLivraion);
		
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraion+"\\DBA\\"+refDocument
				+ "_" + referenceLivraion + "_V" + versionDocument
				+ ".docx", doc);
		this.deleteDocument(manuelTmpPath);
	}
	
	private void generateDemandeSystem(String referenceLivraison, String versionDocument,String cpPrestataire, String formattedDate,List<String> checkedSystemItems) throws IOException, FileNotFoundException {
	
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraison+"\\GRC_TNT_Configuration_WAS.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(manuelTmpPath)));
		this.replaceTextFor(doc, REFERENCE_LIVRAISON, referenceLivraison);
		this.replaceTextFor(doc, CP_PRESTATAIRE, cpPrestataire);
		this.replaceTextFor(doc, DATE, formattedDate);
	
		
		logParagraphs(doc);
		
		if (null != checkedSystemItems && !checkedSystemItems.contains("CERTIFICAT_JDK")) {
			this.removeParagrahRange(doc, PARAGRAPH_CERTIFICAT_JDK_START,
					PARAGRAPH_CERTIFICAT_JDK_END);
		}
		
		if (null != checkedSystemItems && !checkedSystemItems.contains("BATCH")) {
			this.removeParagrahRange(doc, PARAGRAPH_BATCH_START, PARAGRAPH_BATCH_END);
		}
		
		if (null != checkedSystemItems && !checkedSystemItems.contains("CERTIFICAT_WS")) {
			this.removeParagrahRange(doc, PARAGRAPH_CERTIFICAT_WS_START, PARAGRAPH_CERTIFICAT_WS_END);
		}
		
		if (null != checkedSystemItems && !checkedSystemItems.contains("PROPERTIES")) {
			this.removeParagrahRange(doc, PARAGRAPH_PROPERTIES_START, PARAGRAPH_PROPERTIES_END);
		}

		this.createTOC(doc,11);
		
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraison+"\\GRC_TNT_Configuration_WAS"
				+ "_" + referenceLivraison + "_V" + versionDocument
				+ ".docx", doc);
		this.deleteDocument(manuelTmpPath);
		
	}
	
	private void generateInstallationManual(String referenceLivraison, String versionDocument,String cpPrestataire, String formattedDate, String checkedItem) throws IOException, FileNotFoundException {
		
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceLivraison+"\\GRC_OPM_Manuel_d_installation_GRC.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(manuelTmpPath)));
		this.replaceTextFor(doc, REFERENCE_LIVRAISON, referenceLivraison);
		this.replaceTextFor(doc, CP_PRESTATAIRE, cpPrestataire);
		this.replaceTextFor(doc, DATE, formattedDate);
		
		logParagraphs(doc);
		
		if ("ARRET_DEPLOIEMENT_RELANCE_IN_ON_RUN".equals(checkedItem)) {
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_3_START,PARAGRAPH_DEPLOIEMENT_TYPE_3_END);
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_2_START,PARAGRAPH_DEPLOIEMENT_TYPE_2_END);
		}
		
		if ("ARRET_DEPLOIEMENT_RELANCE_IN_SEPARATE_RUNS".equals(checkedItem)) {
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_3_START,PARAGRAPH_DEPLOIEMENT_TYPE_3_END);
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_1_START,PARAGRAPH_DEPLOIEMENT_TYPE_1_END);
		}
		
		if ("ARRET_RELANCE".equals(checkedItem)) {
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_2_START,PARAGRAPH_DEPLOIEMENT_TYPE_2_END);
			this.removeParagrahRange(doc, PARAGRAPH_DEPLOIEMENT_TYPE_1_START,PARAGRAPH_DEPLOIEMENT_TYPE_1_END);
		}
		
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceLivraison+"\\GRC_OPM_Manuel_d_installation_GRC"
				+ "_" + referenceLivraison + "_V" + versionDocument
				+ ".docx", doc);
		this.deleteDocument(manuelTmpPath);
		
	}

}
