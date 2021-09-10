package net.atos.iam.utils.layout;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.ComboBox;
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
import net.atos.iam.utils.autodoc.mswordmanagement.DemandeAccesVPNManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.FicheRelectureManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.FunctionalSpecificationManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.GuideUtilisateurManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.MTProjectManagers;

public class DemandeAccesVPNPanel extends Panel implements GrcAutoDocPanel {

	private TextBox titreDocument = new TextBox();
	private List<String> checkedItems;
	private Window window;
	private Screen screen;
	
	public DemandeAccesVPNPanel() {
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
			DemandeAccesVPNManagement docManagement = new DemandeAccesVPNManagement();
			docManagement.generateDocument(titreDocument.getText());
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

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}


}
