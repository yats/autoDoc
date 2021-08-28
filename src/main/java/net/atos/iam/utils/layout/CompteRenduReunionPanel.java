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

import net.atos.iam.utils.autodoc.mswordmanagement.CompteRenduReunionManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.DemandeAccesUnixManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.FicheRelectureManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.FunctionalSpecificationManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.GuideUtilisateurManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.MTProjectManagers;

public class CompteRenduReunionPanel extends Panel implements GrcAutoDocPanel {

	private TextBox titreDocument = new TextBox();
	private TextBox heureDebut = new TextBox();
	private TextBox heureFin = new TextBox();
	private TextBox ordreDuJour = new TextBox();
	private TextBox environnement = new TextBox();
	private List<String> checkedItems;
	private Window window;
	
	public CompteRenduReunionPanel() {
		super();
		
	}
	
	public void init(Window window) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Compte rendu - Remplacer les actions renseignées sur le tableau");
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
		
		heureDebut.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.HEURE_DEBUT));
		this.addComponent(heureDebut);
		
		heureFin.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.HEURE_FIN));
		this.addComponent(heureFin);
		
		ordreDuJour.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.ORDRE_DU_JOUR));
		this.addComponent(ordreDuJour);
		
		
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

	public List<String> getCheckedItems() {
		return checkedItems;
	}


	public void setCheckedItems(List<String> ckeckedItems) {
		this.checkedItems = ckeckedItems;
	}


	@Override
	public void generateDocuments() {
		try {
			CompteRenduReunionManagement docManagement = new CompteRenduReunionManagement();
			docManagement.generateDocument(titreDocument.getText(), heureDebut.getText(), heureFin.getText(), ordreDuJour.getText());
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

	public TextBox getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(TextBox heureDebut) {
		this.heureDebut = heureDebut;
	}

	public TextBox getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(TextBox heureFin) {
		this.heureFin = heureFin;
	}

	public TextBox getOrdreDuJour() {
		return ordreDuJour;
	}

	public void setOrdreDuJour(TextBox ordreDuJour) {
		this.ordreDuJour = ordreDuJour;
	}


}
