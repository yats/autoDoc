package net.atos.iam.utils.layout;
import java.util.List;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;

public class FunctionalSpecificationPanel extends Panel implements GrcAutoDocPanel {

	
	private CheckBoxList<String> checkBoxList;
	private List<String> ckeckedItems;
	
	public FunctionalSpecificationPanel() {
		super();
		
	}
	
	
	public void init() {
		this.setLayoutManager(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)this.getLayoutManager();
        gridLayout.setHorizontalSpacing(1);
        Label label = new Label("Specification générale");
        this.addComponent(label);
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

		
		Label label = new Label("Paragraphes à inclure :");
        this.addComponent(label);
        
		this.checkBoxList = new CheckBoxList<String>();
		this.checkBoxList.addItem("Existant",true);
		this.checkBoxList.addItem("Permissions rajoutées dans le cadre de l'évolution",true);
		this.checkBoxList.addItem("Produits concernés",true);
		this.checkBoxList.addListener((sel, prev) ->
		    { this.ckeckedItems = this.checkBoxList.getCheckedItems();
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
        
		Button button = new Button("Done");
		this.addComponent(button);
	}


	public CheckBoxList<String> getCheckBoxList() {
		return checkBoxList;
	}


	public void setCheckBoxList(CheckBoxList<String> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}


	public List<String> getCkeckedItems() {
		return ckeckedItems;
	}


	public void setCkeckedItems(List<String> ckeckedItems) {
		this.ckeckedItems = ckeckedItems;
	}
	
	
	

	
}
