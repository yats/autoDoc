package net.atos.iam.utils.layout;

import java.util.List;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;

public interface GrcAutoDocPanel {
	
	public void init(Window window,Screen screeen);
	public void addComponents();
	public List<String> getCheckedItems();
	public void generateDocuments();
	public boolean validateForm();

}
