package net.atos.iam.utils.layout;

import java.util.List;

import com.googlecode.lanterna.gui2.Window;

public interface GrcAutoDocPanel {
	
	public void init(Window window);
	public void addComponents();
	public List<String> getCheckedItems();
	public void generateDocuments();
	public void validateForm();

}
