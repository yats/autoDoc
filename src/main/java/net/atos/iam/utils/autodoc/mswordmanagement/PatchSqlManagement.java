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

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;

import net.atos.iam.utils.autodoc.common.AutoDocUtils;

@Component
public class PatchSqlManagement extends AutoDocUtils {

	
	private static final String PATCH_DESTINATION_FOLDER = "C:\\tmp\\patchSql\\GRC_PATCH_";
	private static final String PATCH_SOURCE_FOLDER = "template\\GRC_PATCH_ANO_TEMPLATE";
	private static final int PARAGRAPH_FIXE_START = 56;
	private static final int PARAGRAPH_FIXE_END = 83;
	
	private static final int PARAGRAPH_MOBILE_START = 72;
	private static final int PARAGRAPH_MOBILE_END = 99;

	/**
	 * 
	 * Add a mapping of the paragraphs to remove
	 * 
	 * @param doc
	 * @param paragraphTitle
	 */
	
	
	public void generateDocument(ComboBox<String> typeDocument, TextBox titreDocument, TextBox referenceJira,
			TextBox versionDocument, ComboBox<String> chefProjetSI,List<String> checkedItems,Window window) throws FileNotFoundException, IOException, URISyntaxException {

		String formattedDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
		FileUtils.copyDirectory(new File(ClassLoader.getSystemResource(PATCH_SOURCE_FOLDER).toURI()), new File(PATCH_DESTINATION_FOLDER+referenceJira.getText()));
		
		// Generer le manuel 
		generateManuel(referenceJira, versionDocument, checkedItems, formattedDate);
		// Renommer les fichiers 
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceJira.getText()+"\\Script";
		Path scriptSourceFolderFixe = Paths.get(manuelTmpPath+"\\LIVR_FX");
		Path scriptSourceFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\GRC_BD_FX.sql");
		Path scriptSourceSanityFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\GRC_BD_FX_SANITY_TESTS.sql");
		Path scriptSourceChapeauFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\sql_chapeau.sql");
		Path scriptSourceSanityChapeauFileFixe = Paths.get(manuelTmpPath+"\\LIVR_FX\\sql\\sql_sanity.sql");
		
		Path scriptSourceFolderMobile = Paths.get(manuelTmpPath+"\\LIVR_MB");
		Path scriptSourceFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\GRC_BD_MB.sql");
		Path scriptSourceSanityFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\GRC_BD_MB_SANITY_TESTS.sql");
		Path scriptSourceChapeauFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\sql_chapeau.sql");
		Path scriptSourceSanityChapeauFileMobile = Paths.get(manuelTmpPath+"\\LIVR_MB\\sql\\sql_sanity.sql");
		
		if(null!= checkedItems && !checkedItems.contains("FIXE")) {
			FileUtils.deleteDirectory(new File(manuelTmpPath+"\\LIVR_FX")); 
		} else {
			replaceTextInFile(scriptSourceFileFixe,"{ID_JIRA}",referenceJira.getText());
			replaceTextInFile(scriptSourceChapeauFileFixe,"{ID_JIRA}",referenceJira.getText());
			replaceTextInFile(scriptSourceSanityChapeauFileFixe,"{ID_JIRA}",referenceJira.getText());
			// TODO remplacer avec la bonne valeur
			replaceTextInFile(scriptSourceFileFixe,"{CP_PRESTATAIRE}","Y.AMRI");
			replaceTextInFile(scriptSourceFileFixe,"{DATE}",formattedDate);
			Files.move(scriptSourceFileFixe, scriptSourceFileFixe.resolveSibling("GRC_BD_FX" + "_"+referenceJira.getText()+".sql"));
			Files.move(scriptSourceSanityFileFixe, scriptSourceSanityFileFixe.resolveSibling("GRC_BD_FX_SANITY_TESTS" + "_"+referenceJira.getText()+".sql"));
			Files.move(scriptSourceFolderFixe, scriptSourceFolderFixe.resolveSibling("LIVR_FX" + "_"+referenceJira.getText()));
		}
		
		
		if(null!= checkedItems && !checkedItems.contains("MOBILE")) {
			FileUtils.deleteDirectory(new File(manuelTmpPath+"\\LIVR_MB")); 
		} else {
			replaceTextInFile(scriptSourceFileMobile,"{ID_JIRA}",referenceJira.getText());
			replaceTextInFile(scriptSourceChapeauFileMobile,"{ID_JIRA}",referenceJira.getText());
			replaceTextInFile(scriptSourceSanityChapeauFileMobile,"{ID_JIRA}",referenceJira.getText());
			// TODO remplacer avec la bonne valeur
			replaceTextInFile(scriptSourceFileMobile,"{CP_PRESTATAIRE}","Y.AMRI");
			replaceTextInFile(scriptSourceFileMobile,"{DATE}",formattedDate);
			Files.move(scriptSourceFileMobile, scriptSourceFileMobile.resolveSibling("GRC_BD_MB" + "_"+referenceJira.getText()+".sql"));
			Files.move(scriptSourceSanityFileMobile, scriptSourceSanityFileMobile.resolveSibling("GRC_BD_MB_SANITY_TESTS" + "_"+referenceJira.getText()+".sql"));
			Files.move(scriptSourceFolderMobile, scriptSourceFolderMobile.resolveSibling("LIVR_MB" + "_"+referenceJira.getText()));
		}
		
		window.close();
		//TODO  Factoriser ce qui peut etre factorisé 
	}

	private void generateManuel(TextBox referenceJira, TextBox versionDocument, List<String> checkedItems,
			String formattedDate) throws IOException, FileNotFoundException {
		String manuelTmpPath = PATCH_DESTINATION_FOLDER+referenceJira.getText()+"\\Manuel\\GRC_OPM_Manuel_d_installation_SQL.docx";
		XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(manuelTmpPath)));
		this.replaceTextFor(doc, "{ID_JIRA}", referenceJira.getText());
		this.replaceTextFor(doc, "{CP_PRESTATAIRE}", "Y.AMRI");
		this.replaceTextFor(doc, "{DATE}", formattedDate);
		
		logParagraphs(doc);		
		if(null!= checkedItems && !checkedItems.contains("FIXE")) {
			this.removeParagrahRange(doc, PARAGRAPH_FIXE_START,PARAGRAPH_FIXE_END); 
		}
		
		if(null!= checkedItems && !checkedItems.contains("MOBILE")) {
			this.removeParagrahRange(doc, PARAGRAPH_MOBILE_START,PARAGRAPH_MOBILE_END); 
		}
		
		this.createTOC(doc, 38);
		logParagraphs(doc);
		this.saveDocument(PATCH_DESTINATION_FOLDER+referenceJira.getText()+"\\Manuel\\GRC_OPM_Manuel_d_installation_SQL"
				+ "_" + referenceJira.getText() + "_V" + versionDocument.getText()
				+ ".docx", doc);
		this.deleteDocument(manuelTmpPath);
	}

}
