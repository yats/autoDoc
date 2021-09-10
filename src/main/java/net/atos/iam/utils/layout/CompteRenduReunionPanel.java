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
import net.atos.iam.utils.autodoc.mswordmanagement.CompteRenduReunionManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;

public class CompteRenduReunionPanel extends Panel implements GrcAutoDocPanel {

	private TextBox titreDocument = new TextBox();
	private TextBox heureDebut = new TextBox();
	private TextBox heureFin = new TextBox();
	private TextBox ordreDuJour = new TextBox();
	private TextBox environnement = new TextBox();
	private List<String> checkedItems;
	private Window window;
	private Screen screen;
	
	public CompteRenduReunionPanel() {
		super();
		
	}
	
	public void init(Window window,Screen screen) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Compte rendu - Remplacer les actions renseignées sur le tableau");
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
				if (validateForm()) window.close();
			}
		}));
	}
	
	public boolean validateForm() {
		 CheckMandatoryFieldUtils test = new CheckMandatoryFieldUtils();
	        test.isNullOrEmpty(titreDocument.getText(), "Le titre est  obligatoire");
	        test.isNullOrEmpty(heureDebut.getText(), "L'heure de début est obligatoire");
	        test.isNullOrEmpty(heureFin.getText(), "L'heure de fin est obligatoire");
	        
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
