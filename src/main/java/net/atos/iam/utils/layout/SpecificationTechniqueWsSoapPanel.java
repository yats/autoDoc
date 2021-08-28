package net.atos.iam.utils.layout;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;

import net.atos.iam.utils.autodoc.mswordmanagement.FunctionalSpecificationManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.SpecificationTechniqueWsSoapManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.MTProjectManagers;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.PrestataireManager;

public class SpecificationTechniqueWsSoapPanel extends Panel implements GrcAutoDocPanel {

	private TextBox titreDocument = new TextBox();
	private TextBox referenceJira = new TextBox();
	private TextBox nomWebservice = new TextBox();
	ComboBox<String> chefProjetSI = new ComboBox<>(MTProjectManagers.getEnumDescAsList());
	ComboBox<String> prestataire = new ComboBox<>(PrestataireManager.getEnumDescAsList());
	private CheckBoxList<String> checkBoxList;
	private List<String> checkedItems;
	private Window window;
	
	public SpecificationTechniqueWsSoapPanel() {
		super();
		
	}
	
	public void init(Window window) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Specification technique : Modifier le texte en surbrillance sur le document généré");
        this.addComponent(label);
        this.window = window;
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
		
		titreDocument.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.REFERENCE_JIRA));
		this.addComponent(referenceJira);
		
		this.addComponent(new Label(DocumentConstantes.MT_PROJECT_MANAGER));
		this.addComponent(chefProjetSI);
		
		this.addComponent(new Label(DocumentConstantes.PROVIDER_PROJECT_MANAGER));
		this.addComponent(prestataire);
		
		this.addComponent(new Label(DocumentConstantes.NOM_WEBSERVICE));
		this.addComponent(nomWebservice);
		
		
		
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
				window.close();
			}
		}));
	}
	
	public void validateForm() {
		// TODO add implementation
	}


	public CheckBoxList<String> getCheckBoxList() {
		return checkBoxList;
	}


	public void setCheckBoxList(CheckBoxList<String> checkBoxList) {
		this.checkBoxList = checkBoxList;
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
			SpecificationTechniqueWsSoapManagement docManagement = new SpecificationTechniqueWsSoapManagement();
			docManagement.generateDocument(titreDocument.getText(),referenceJira.getText(),chefProjetSI.getText(),prestataire.getText(),nomWebservice.getText());
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

	public TextBox getReferenceJira() {
		return referenceJira;
	}

	public void setReferenceJira(TextBox referenceJira) {
		this.referenceJira = referenceJira;
	}

	public ComboBox<String> getChefProjetSI() {
		return chefProjetSI;
	}

	public void setChefProjetSI(ComboBox<String> chefProjetSI) {
		this.chefProjetSI = chefProjetSI;
	}

	public TextBox getNomWebservice() {
		return nomWebservice;
	}

	public void setNomWebservice(TextBox nomWebservice) {
		this.nomWebservice = nomWebservice;
	}

	public ComboBox<String> getPrestataire() {
		return prestataire;
	}

	public void setPrestataire(ComboBox<String> prestataire) {
		this.prestataire = prestataire;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
	
}
