package net.atos.iam.utils.layout;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;

import net.atos.iam.utils.autodoc.common.CheckMandatoryFieldUtils;
import net.atos.iam.utils.autodoc.mswordmanagement.DemandeAccesUnixManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;

public class DemandeAccesUnixPanel extends Panel implements GrcAutoDocPanel {

	private TextBox titreDocument = new TextBox();
	private TextBox nomDemandeur = new TextBox();
	private TextBox prenomDemandeur = new TextBox();
	private TextBox mailDemandeur = new TextBox();
	private TextBox environnement = new TextBox();
	private List<String> checkedItems;
	private Window window;
	private Screen screen;
	
	public DemandeAccesUnixPanel() {
		super();
		
	}
	
	public void init(Window window,Screen screen) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Demande Acces unix - Remplacer le texte en surbrillance jaune sur le document généré");
        this.addComponent(label);
        this.window = window;
        this.screen = screen;
	}
	
	public void addComponents() {
	
		this.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));

        this.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        
		titreDocument.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.TITLE));
		this.addComponent(titreDocument);
		
		nomDemandeur.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.NOM_DEMANDEUR));
		this.addComponent(nomDemandeur);
		
		prenomDemandeur.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.PRENOM_DEMANDEUR));
		this.addComponent(prenomDemandeur);
		
		mailDemandeur.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.MAIL_DEMANDEUR));
		this.addComponent(mailDemandeur);
		
		environnement.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.ENVIRONNEMENT));
		this.addComponent(environnement);
		
		
		this.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));

        this.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        
		this.addComponent(new Button("Valider", new Runnable() {
			public void run() {
				if (validateForm()) window.close();
			}
		}));
	}
	
	public boolean validateForm() {
		 CheckMandatoryFieldUtils test = new CheckMandatoryFieldUtils();
	        test.isNullOrEmpty(titreDocument.getText(), "Le titre est  obligatoire");
	        test.isNullOrEmpty(nomDemandeur.getText(), "Le nom du demander est obligatoire");
	        test.isNullOrEmpty(prenomDemandeur.getText(), "Le prénom du demander est obligatoire");
	        test.isNullOrEmpty(mailDemandeur.getText(), "Le mail du demander est obligatoire");
	        test.isNullOrEmpty(environnement.getText(), "L'environnement du demander est obligatoire");

	        if (!test.isCheckOk()) {
	        	final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
				MessageDialog.showMessageDialog(textGUI, "Validation", test.getMessage().toString());
				return false;
	        } else {
	        	return true;
	        }
	}

	public List<String> getCheckedItems() {
		return checkedItems;
	}


	public void setCheckedItems(List<String> ckeckedItems) {
		this.checkedItems = ckeckedItems;
	}


	@Override
	public void generateDocuments() {
		try {
			DemandeAccesUnixManagement docManagement = new DemandeAccesUnixManagement();
			docManagement.generateDocument(titreDocument.getText(), nomDemandeur.getText(), prenomDemandeur.getText(), mailDemandeur.getText(), environnement.getText());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public TextBox getTitreDocument() {
		return titreDocument;
	}

	public void setTitreDocument(TextBox titreDocument) {
		this.titreDocument = titreDocument;
	}

	public TextBox getNomDemandeur() {
		return nomDemandeur;
	}

	public void setNomDemandeur(TextBox nomDemandeur) {
		this.nomDemandeur = nomDemandeur;
	}

	public TextBox getPrenomDemandeur() {
		return prenomDemandeur;
	}

	public void setPrenomDemandeur(TextBox prenomDemandeur) {
		this.prenomDemandeur = prenomDemandeur;
	}

	public TextBox getMailDemandeur() {
		return mailDemandeur;
	}

	public void setMailDemandeur(TextBox mailDemandeur) {
		this.mailDemandeur = mailDemandeur;
	}

	public TextBox getEnvironnement() {
		return environnement;
	}

	public void setEnvironnement(TextBox environnement) {
		this.environnement = environnement;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}


}
