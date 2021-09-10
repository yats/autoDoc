package net.atos.iam.utils.autodoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import net.atos.iam.utils.autodoc.common.AutoDocUtils;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentConstantes;
import net.atos.iam.utils.autodoc.mswordmanagement.constantes.DocumentTypes;
import net.atos.iam.utils.layout.GrcAutoDocPanel;

@Service
public class AutoDocApplication {
	
	static Logger log = Logger.getLogger(AutoDocApplication.class.getName());

	static Terminal terminal = null;
	static Screen screen = null;
	private static final String TMP_PATH = "C:\\tmp";
	private static final Path TMP_FOLDER = Paths.get(TMP_PATH);
	private static final Path TMP_LIV_PROD_FOLDER = Paths.get(TMP_PATH+"\\livraisonProd");
	private static final Path TMP_PATCH_SQLFOLDER = Paths.get(TMP_PATH+"\\patchSql");
	
	
	public static void main(String[] args) {
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		
		try {
			if(!Files.exists(TMP_FOLDER)) Files.createDirectory(TMP_FOLDER);
			if(!Files.exists(TMP_LIV_PROD_FOLDER)) Files.createDirectory(TMP_LIV_PROD_FOLDER);
			if(!Files.exists(TMP_PATCH_SQLFOLDER)) Files.createDirectory(TMP_PATCH_SQLFOLDER);
			
			terminal = defaultTerminalFactory.createTerminal();
			screen = new TerminalScreen(terminal);
			screen.startScreen();

			final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

			final Window window = new BasicWindow("Formulaire de génération des documents GRC ");

			Panel contentPanel = new Panel(new GridLayout(2));
			contentPanel.setPreferredSize(new TerminalSize(100,100));
			List<String> listDocuments = DocumentTypes.getEnumDescAsList();
			contentPanel.addComponent(new Label(DocumentConstantes.TYPE));
			ComboBox<String> typeDocument = new ComboBox<>(listDocuments);
			typeDocument.setReadOnly(false).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));
			contentPanel.addComponent(typeDocument);

			contentPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
			contentPanel.addComponent(new Separator(Direction.HORIZONTAL)
					.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
			contentPanel.addComponent(new Button("Valider", new Runnable() {
				@Override
				public void run() {
					
					GrcAutoDocPanel panel = new GrcAutoDocPanelFactory().createPanel(typeDocument.getText());
					panel.init(window,screen);
					panel.addComponents();
					window.setComponent((Component) panel);
					textGUI.addWindowAndWait(window);
					try {
						panel.generateDocuments();
					} catch (Exception e) {
                      log.error("Erreur lors de la creation du panel ", e);
					}
					window.close();
				}
			}).setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

			window.setComponent(contentPanel);
			window.setHints(Arrays.asList(Window.Hint.FIT_TERMINAL_WINDOW));
			textGUI.addWindowAndWait(window);

			screen.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (screen != null) {
				try {
					screen.stopScreen();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
