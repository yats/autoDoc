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
import net.atos.iam.utils.autodoc.mswordmanagement.PatchSqlManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;

public class PatchSqlPanel extends Panel implements GrcAutoDocPanel {

	
	private CheckBoxList<String> checkBoxList;
	private TextBox titreDocument;
	private TextBox referenceJira;
	private TextBox versionDocument;
	private ComboBox<String> chefProjetSI;
	private List<String> checkedItems;
	private Window window;
	private Screen screen;
	
	public PatchSqlPanel() {
		super();
		
	}
	
	
	public void init(Window window,Screen screen) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Patch SQL");
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

        referenceJira = new TextBox();
        referenceJira.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.REFERENCE_JIRA));
		this.addComponent(referenceJira);
		
		Label label = new Label("Paragraphes à inclure :");
        this.addComponent(label);
        
		this.checkBoxList = new CheckBoxList<String>();
		this.checkBoxList.addItem("FIXE",true);
		this.checkBoxList.addItem("MOBILE",true);
		this.checkBoxList.addListener((sel, prev) ->
		    { this.checkedItems = this.checkBoxList.getCheckedItems();
		       System.out.println(this.getCheckBoxList());}
		);
		
		this.addComponent(checkBoxList);
		
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
	
	public void generateDocuments() {
		
		try {
			PatchSqlManagement docManagement = new PatchSqlManagement();
			docManagement.generateDocument(referenceJira.getText(), "1.0", this.getCheckedItems());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public boolean validateForm() {
		 CheckMandatoryFieldUtils test = new CheckMandatoryFieldUtils();
	        test.isNullOrEmpty(referenceJira.getText(), "La référence JIRA est obligatoire");
	        test.isListEmpty(checkedItems,"Choisir au moins un produit FIXE ou MOBILE");

	        if (!test.isCheckOk()) {
	        	final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
				MessageDialog.showMessageDialog(textGUI, "Validation", test.getMessage().toString());
				return false;
	        } else {
	        	return true;
	        }
	        
	}
	


	public CheckBoxList<String> getCheckBoxList() {
		return checkBoxList;
	}


	public void setCheckBoxList(CheckBoxList<String> checkBoxList) {
		this.checkBoxList = checkBoxList;
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


	public TextBox getVersionDocument() {
		return versionDocument;
	}


	public void setVersionDocument(TextBox versionDocument) {
		this.versionDocument = versionDocument;
	}


	public ComboBox<String> getChefProjetSI() {
		return chefProjetSI;
	}


	public void setChefProjetSI(ComboBox<String> chefProjetSI) {
		this.chefProjetSI = chefProjetSI;
	}


	public List<String> getCheckedItems() {
		return checkedItems;
	}


	public void setCheckedItems(List<String> checkedItems) {
		this.checkedItems = checkedItems;
	}


	public Window getWindow() {
		return window;
	}


	public void setWindow(Window window) {
		this.window = window;
	}
	
	
	

	
}
