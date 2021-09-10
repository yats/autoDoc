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
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;

import net.atos.iam.utils.autodoc.common.CheckMandatoryFieldUtils;
import net.atos.iam.utils.autodoc.mswordmanagement.LivraisonProdManagement;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.PrestataireManager;

public class LivraisonProdPanel extends Panel implements GrcAutoDocPanel {

	
	private CheckBoxList<String> checkBoxList;
	private TextBox titreDocument;
	private TextBox referenceLivraison;
	private TextBox versionDocument;
	ComboBox<String> prestataire = new ComboBox<>(PrestataireManager.getEnumDescAsList());
	private List<String> checkedItems;
	
	private Panel patchSqlPanel = new Panel();
	private CheckBoxList<String> checkBoxSqlList;
	private List<String> checkedSqlItems;
	
	private Panel systemPanel = new Panel();
	private CheckBoxList<String> checkBoxSystemList;
	private List<String> checkedSystemItems;

	private Panel dbaPanel = new Panel();
	private CheckBoxList<String> checkBoxDbaList;
	private List<String> checkedDbaItems;
	
	private Panel deploiementPanel = new Panel();
	private RadioBoxList<String> checkBoxDeploiementList;
	private String checkeDeploiementItem;
	
	
	private Window window;
	private Screen screen;
	
	public LivraisonProdPanel() {
		super();
		
	}
	
	
	public void init(Window window,Screen screen) {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Livraison production");
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

        referenceLivraison = new TextBox();
        referenceLivraison.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
		this.addComponent(new Label(DocumentConstantes.REFERENCE_LIVRAISON));
		this.addComponent(referenceLivraison);
		
		this.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
		this.addComponent(new Label(DocumentConstantes.PROVIDER_PROJECT_MANAGER));
		this.addComponent(prestataire);
		
		Label label = new Label("Parties inclues ");
        this.addComponent(label);
        
		this.checkBoxList = new CheckBoxList<String>();
		this.checkBoxList.addItem("DBA",true);
		this.checkBoxList.addItem("SQL",true);
		this.checkBoxList.addItem("SYSTEME",true);
		this.checkedItems = this.checkBoxList.getCheckedItems();
		this.checkBoxList.addListener((sel, prev) ->
	    { this.checkedItems = this.checkBoxList.getCheckedItems();
	       System.out.println(this.checkBoxList);}
	);
		this.addComponent(checkBoxList);
		
		addPatchSqlPanel();
		addSystemPanel();
		addDbaPanel();
		addDeploiementPanel();
		
		
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
		
		this.checkBoxList.addListener((sel, prev) -> {
			manageCheckedChoices();
		}
	);
		
	}


	private void manageCheckedChoices() {
		this.checkedItems = this.checkBoxList.getCheckedItems();
		if (null != checkedItems && !checkedItems.contains("SQL") && this.containsComponent(patchSqlPanel)) {
			this.removeComponent(patchSqlPanel);
		} else if (null != checkedItems && checkedItems.contains("SQL") && !this.containsComponent(patchSqlPanel)) {
			this.addComponent(8, patchSqlPanel);
		}

		if (null != checkedItems && !checkedItems.contains("SYSTEME") && this.containsComponent(systemPanel)) {
			this.removeComponent(systemPanel);
		} else if (null != checkedItems && checkedItems.contains("SYSTEME") && !this.containsComponent(systemPanel)) {
			this.addComponent(8, systemPanel);
		}

		if (null != checkedItems && !checkedItems.contains("DBA") && this.containsComponent(dbaPanel)) {
			this.removeComponent(dbaPanel);
		} else if (null != checkedItems && checkedItems.contains("DBA") && !this.containsComponent(dbaPanel)) {
			this.addComponent(8, dbaPanel);
		}
	}


	private void addPatchSqlPanel() {
		patchSqlPanel.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
		
        patchSqlPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
        patchSqlPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
		Label labelPatchSql = new Label("Patch SQL ");
		patchSqlPanel.addComponent(labelPatchSql);
        
		this.checkBoxSqlList = new CheckBoxList<String>();
		this.checkBoxSqlList.addItem("FIXE",true);
		this.checkBoxSqlList.addItem("MOBILE",true);
		this.checkedSqlItems = this.checkBoxSqlList.getCheckedItems();
		this.checkBoxSqlList.addListener((sel, prev) ->
		    { this.checkedSqlItems = this.checkBoxSqlList.getCheckedItems();
		       System.out.println(this.checkedSqlItems);}
		);
		
		patchSqlPanel.addComponent(checkBoxSqlList);
		this.addComponent(patchSqlPanel);
	}
	
	
	private void addSystemPanel() {
		systemPanel.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
		
        systemPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
        systemPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
		Label labelSystemPanel = new Label("Partie système ");
		systemPanel.addComponent(labelSystemPanel);
        
		this.checkBoxSystemList = new CheckBoxList<String>();
		this.checkBoxSystemList.addItem("PROPERTIES",true);
		this.checkBoxSystemList.addItem("CERTIFICAT_WS",true);
		this.checkBoxSystemList.addItem("BATCH",true);
		this.checkBoxSystemList.addItem("CERTIFICAT_JDK",true);
		this.checkedSystemItems = this.checkBoxSystemList.getCheckedItems();
		this.checkBoxSystemList.addListener((sel, prev) ->
		    { this.checkedSystemItems = this.checkBoxSystemList.getCheckedItems();}
		);
		
		systemPanel.addComponent(checkBoxSystemList);
		this.addComponent(systemPanel);
	}
	
	
	private void addDbaPanel() {
		dbaPanel.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
		
        dbaPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
        dbaPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
		Label labelDbaPanel = new Label("Partie DBA");
		dbaPanel.addComponent(labelDbaPanel);
        
		this.checkBoxDbaList = new CheckBoxList<String>();
		this.checkBoxDbaList.addItem("DEMANDES D'ACCES DEPLOY",true);
		this.checkBoxDbaList.addItem("DEMANDES D'ACCES NOMINATIFS",true);
		this.checkedDbaItems = this.checkBoxDbaList.getCheckedItems();
		this.checkBoxDbaList.addListener((sel, prev) ->
		    { this.checkedDbaItems = this.checkBoxDbaList.getCheckedItems();}
		);
		
		dbaPanel.addComponent(checkBoxDbaList);
		this.addComponent(dbaPanel);
	}
	
	
	private void addDeploiementPanel() {
		deploiementPanel.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
		
        deploiementPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
        deploiementPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
		
		Label labelDeploiementPanel = new Label("Déploiement");
		deploiementPanel.addComponent(labelDeploiementPanel);
        
		this.checkBoxDeploiementList = new RadioBoxList<String>();
		this.checkBoxDeploiementList.addItem("ARRET_DEPLOIEMENT_RELANCE_IN_ON_RUN");
		this.checkBoxDeploiementList.addItem("ARRET_DEPLOIEMENT_RELANCE_IN_SEPARATE_RUNS");
		this.checkBoxDeploiementList.addItem("ARRET_RELANCE");
		this.checkeDeploiementItem = this.checkBoxDeploiementList.getCheckedItem();
		this.checkBoxDeploiementList.addListener((sel, prev) ->
		    { this.checkeDeploiementItem = this.checkBoxDeploiementList.getCheckedItem();}
		);
		
		deploiementPanel.addComponent(checkBoxDeploiementList);
		this.addComponent(deploiementPanel);
	}
	
	public void generateDocuments() {
		
		try {
			LivraisonProdManagement docManagement = new LivraisonProdManagement();
			docManagement.generateDocument(referenceLivraison.getText(), "1.0",prestataire.getText(),this.checkedItems,this.checkedSqlItems, this.checkedDbaItems, this.checkedSystemItems, this.checkeDeploiementItem);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public boolean validateForm() {
		 CheckMandatoryFieldUtils test = new CheckMandatoryFieldUtils();
			test.isNullOrEmpty(referenceLivraison.getText(), "La référence de la livraison est obligatoire");
			test.isNullOrEmpty(prestataire.getText(), "Le prestataire est obligatoire");
			test.isNullOrEmpty(checkeDeploiementItem, "Selectionner au moins un mode de déploiement");
			if (null != checkedItems && checkedItems.contains("SQL")) {
				test.isListEmpty(checkedSqlItems, "Selectionner au moins un produit pour les patchs SQL");	
			}
			if (null != checkedItems && checkedItems.contains("SYSTEME")) {
				test.isListEmpty(checkedSystemItems, "Selectionner au moins un champ système");	
			}

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
		return referenceLivraison;
	}


	public void setReferenceJira(TextBox referenceJira) {
		this.referenceLivraison = referenceJira;
	}


	public TextBox getVersionDocument() {
		return versionDocument;
	}


	public void setVersionDocument(TextBox versionDocument) {
		this.versionDocument = versionDocument;
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


	public TextBox getReferenceLivraison() {
		return referenceLivraison;
	}


	public void setReferenceLivraison(TextBox referenceLivraison) {
		this.referenceLivraison = referenceLivraison;
	}


	public CheckBoxList<String> getCheckBoxSqlList() {
		return checkBoxSqlList;
	}


	public void setCheckBoxSqlList(CheckBoxList<String> checkBoxSqlList) {
		this.checkBoxSqlList = checkBoxSqlList;
	}


	public List<String> getCheckedSqlItems() {
		return checkedSqlItems;
	}


	public void setCheckedSqlItems(List<String> checkedSqlItems) {
		this.checkedSqlItems = checkedSqlItems;
	}


	public Panel getPatchSqlPanel() {
		return patchSqlPanel;
	}


	public void setPatchSqlPanel(Panel patchSqlPanel) {
		this.patchSqlPanel = patchSqlPanel;
	}


	public Panel getSystemPanel() {
		return systemPanel;
	}


	public void setSystemPanel(Panel systemPanel) {
		this.systemPanel = systemPanel;
	}


	public CheckBoxList<String> getCheckBoxSystemList() {
		return checkBoxSystemList;
	}


	public void setCheckBoxSystemList(CheckBoxList<String> checkBoxSystemList) {
		this.checkBoxSystemList = checkBoxSystemList;
	}


	public List<String> getCheckedSystemItems() {
		return checkedSystemItems;
	}


	public void setCheckedSystemItems(List<String> checkedSystemItems) {
		this.checkedSystemItems = checkedSystemItems;
	}


	public Panel getDbaPanel() {
		return dbaPanel;
	}


	public void setDbaPanel(Panel dbaPanel) {
		this.dbaPanel = dbaPanel;
	}


	public CheckBoxList<String> getCheckBoxDbaList() {
		return checkBoxDbaList;
	}


	public void setCheckBoxDbaList(CheckBoxList<String> checkBoxDbaList) {
		this.checkBoxDbaList = checkBoxDbaList;
	}


	public List<String> getCheckedDbaItems() {
		return checkedDbaItems;
	}


	public void setCheckedDbaItems(List<String> checkedDbaItems) {
		this.checkedDbaItems = checkedDbaItems;
	}


	public ComboBox<String> getPrestataire() {
		return prestataire;
	}


	public void setPrestataire(ComboBox<String> prestataire) {
		this.prestataire = prestataire;
	}


	public Panel getDeploiementPanel() {
		return deploiementPanel;
	}


	public void setDeploiementPanel(Panel deploiementPanel) {
		this.deploiementPanel = deploiementPanel;
	}


	public RadioBoxList<String> getCheckBoxDeploiementList() {
		return checkBoxDeploiementList;
	}


	public void setCheckBoxDeploiementList(RadioBoxList<String> checkBoxDeploiementList) {
		this.checkBoxDeploiementList = checkBoxDeploiementList;
	}


	public String getCheckeDeploiementItem() {
		return checkeDeploiementItem;
	}


	public void setCheckeDeploiementItem(String checkeDeploiementItem) {
		this.checkeDeploiementItem = checkeDeploiementItem;
	}

}
