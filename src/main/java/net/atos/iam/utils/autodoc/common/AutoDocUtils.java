package net.atos.iam.utils.autodoc.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.springframework.stereotype.Component;

import com.aspose.cells.ReplaceOptions;
import com.aspose.cells.Workbook;

@Component
public class AutoDocUtils {
	
	static Logger log = Logger.getLogger(AutoDocUtils.class.getName());

	public void logParagraphs(XWPFDocument doc) {
		doc.getParagraphs().forEach(p -> {
			System.out.println("###########################################");
			System.out.println("--------- Paragraph position ---------");
			System.out.println(doc.getPosOfParagraph(p));
			System.out.println("--------- Paragraph text ---------");
			System.out.println(p.getParagraphText());
			System.out.println("--------- Paragraph body ---------");
			System.out.println(p.getBody());
		});
	}
	
	public void removeParagrahRange(XWPFDocument doc, int start, int end) {

		logParagraphs(doc);

		for(int i = start;i<=end;i++) {
			doc.removeBodyElement(start);
		}

	}

	public void removeParagrah(XWPFDocument doc, int paragraphNumber) {

		logParagraphs(doc);
     	doc.removeBodyElement(paragraphNumber);

	}
	
	public void createTOC(XWPFDocument doc, int paragraphPosition) {

		CTP ctP = doc.getParagraphArray(paragraphPosition).getCTP();
		CTSimpleField toc = ctP.addNewFldSimple();
		toc.setInstr("TOC \\o \"1-2\" \\h \\z \\u");
		toc.setDirty(STOnOff.ON);

	}

	public void replaceTextFor(XWPFDocument doc, String findText, String replaceText) {

		replaceTextInParagraphs(doc, findText, replaceText);

		replaceTextInTables(doc, findText, replaceText);

		replaceTextinHeader(doc, findText, replaceText);
		
		replaceTextinFooter(doc, findText, replaceText);

	}
	
	private void replaceTextinHeader(XWPFDocument doc, String findText, String replaceText) {
		
		doc.getHeaderList().forEach(header -> {
			header.getParagraphs().forEach(p -> {
				p.getRuns().forEach(run -> {
					String text = run.text();
					if (text.contains(findText)) {
						run.setText(text.replace(findText, replaceText), 0);
					}
				});
			});

			header.getTables().forEach(t -> {
				t.getRows().forEach(row -> {
					row.getTableCells().forEach(cell -> {
						cell.getParagraphs().forEach(p -> {
							p.getRuns().forEach(run -> {
								String text = run.text();
								if (text.contains(findText)) {
									run.setText(text.replace(findText, replaceText), 0);
								}
							});
						});
					});
				});
			});

		});
	}
	
	private void replaceTextinFooter(XWPFDocument doc, String findText, String replaceText) {
		
		doc.getFooterList().forEach(header -> {
			header.getParagraphs().forEach(p -> {
				p.getRuns().forEach(run -> {
					String text = run.text();
					if (text.contains(findText)) {
						run.setText(text.replace(findText, replaceText), 0);
					}
				});
			});

			header.getTables().forEach(t -> {
				t.getRows().forEach(row -> {
					row.getTableCells().forEach(cell -> {
						cell.getParagraphs().forEach(p -> {
							p.getRuns().forEach(run -> {
								String text = run.text();
								if (text.contains(findText)) {
									run.setText(text.replace(findText, replaceText), 0);
								}
							});
						});
					});
				});
			});

		});
	}

	protected void replaceTextInTables(XWPFDocument doc, String findText, String replaceText) {
		doc.getTables().forEach(t -> {
			t.getRows().forEach(row -> {
				row.getTableCells().forEach(cell -> {
					cell.getParagraphs().forEach(p -> {
						p.getRuns().forEach(run -> {
							String text = run.text();
							if (text.contains(findText)) {
								run.setText(text.replace(findText, replaceText), 0);
							}
						});
					});
				});
			});
		});
	}
	

	private void replaceTextInParagraphs(XWPFDocument doc, String findText, String replaceText) {
		doc.getParagraphs().forEach(p -> {
			p.getRuns().forEach(run -> {
				String text = run.text();
				System.out.println("--PARAGRAPH " + run.text());
				if (text.contains(findText)) {
					run.setText(text.replace(findText, replaceText), 0);
				}
			});
		});
	}

	public void saveDocument(String filePath, XWPFDocument doc) throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
    protected static void showTablesInfo( XWPFDocument document ) {
        List<XWPFTable> tables = document.getTables();
        System.out.println( "\n document has " + tables.size() + " table(s)." );

        for ( XWPFTable table : tables ) {
            System.out.println( "\t table with position #" + document.getPosOfTable( table ) + " has "
                    + table.getRows().size() + " rows" );
        }
    }
    

    protected  void removeRow( XWPFDocument document, int tableNumberInDocument,int rowNumber) {
        List<XWPFTable> tables = document.getTables();
        XWPFTable theTable = tables.get( tableNumberInDocument );
        theTable.removeRow(rowNumber);
    }
	
	
	public void replaceTextInExcel(Workbook doc, String findText, String replaceText) {
		
		ReplaceOptions replace = new ReplaceOptions();
		replace.setCaseSensitive(false);
		replace.setMatchEntireCellContents(false);
		doc.replace(findText,replaceText, replace);

	}
	
	public void deleteDocument(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			try {
				closeQuietly(new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			file.delete();
		}
	}

	public void closeQuietly(FileOutputStream out) {
	    try { out.flush(); out.close(); } catch(Exception e) {} 
	}

	
	protected void replaceTextInFile(Path path,String textToReplace, String newText) {

		String content;
		try {
			content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			content = content.replace(textToReplace, newText);
			Files.write(path, content.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
