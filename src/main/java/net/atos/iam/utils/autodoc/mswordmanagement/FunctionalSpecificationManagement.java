package net.atos.iam.utils.autodoc.mswordmanagement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.TOC;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.springframework.stereotype.Component;

@Component
public class FunctionalSpecificationManagement {


    /**
     * 
     * Add a mapping of the paragraphs to remove
     * 
     * @param doc
     * @param paragraphTitle
     */
    public void removeParagrah(XWPFDocument doc, String paragraphTitle) {

    	doc.getParagraphs().forEach(p -> {
    		System.out.println("--------- Paragraph text ---------");
    		System.out.println(p.getParagraphText());
    		System.out.println("--------- Paragraph body ---------");
    		System.out.println(p.getBody());
    		System.out.println("--------- Paragraph position ---------");
    		System.out.println(doc.getPosOfParagraph(p));
    		
    	});
    	
    	//28, 29, 30, 31
    	
    	doc.removeBodyElement(24);
    	doc.removeBodyElement(25);
    	
    }
    
    
   public void createTOC(XWPFDocument doc,int paragraphPosition) {
	   
	   CTP ctP = doc.getParagraphArray(paragraphPosition).getCTP();
	   CTSimpleField toc = ctP.addNewFldSimple();
	   toc.setInstr("TOC \\o \"1-2\" \\h \\z \\u");
	   toc.setDirty(STOnOff.ON);
	   
   }

    public void replaceTextFor(XWPFDocument doc, String findText, String replaceText){

        replaceTextInParagraphs(doc, findText, replaceText);

        replaceTextInTables(doc, findText, replaceText);

        replaceTextinHeader(doc, findText, replaceText);

    }


	private void replaceTextinHeader(XWPFDocument doc, String findText, String replaceText) {
		doc.getHeaderList().forEach(header -> {
            header.getParagraphs().forEach(p ->{
                p.getRuns().forEach(run -> {
                    String text = run.text();
                    if(text.contains(findText)) {
                        run.setText(text.replace(findText, replaceText), 0);
                    } 
                });
            });

            header.getTables().forEach(t-> {
                t.getRows().forEach(row -> {
                    row.getTableCells().forEach(cell -> {
                        cell.getParagraphs().forEach( p -> {
                            p.getRuns().forEach(run -> {
                                String text = run.text();
                                if(text.contains(findText)) {
                                    run.setText(text.replace(findText, replaceText), 0);
                                } 
                            });  
                        });
                    });
                });
            });

        });
	}


	private void replaceTextInTables(XWPFDocument doc, String findText, String replaceText) {
		doc.getTables().forEach(t-> {
            t.getRows().forEach(row -> {
                row.getTableCells().forEach(cell -> {
                    cell.getParagraphs().forEach( p -> {
                        p.getRuns().forEach(run -> {
                            String text = run.text();
                            if(text.contains(findText)) {
                                run.setText(text.replace(findText, replaceText), 0);
                            } 
                        });  
                    });
                });
            });
        });
	}


	private void replaceTextInParagraphs(XWPFDocument doc, String findText, String replaceText) {
		doc.getParagraphs().forEach(p ->{
            p.getRuns().forEach(run -> {
                String text = run.text();
                if(text.contains(findText)) {
                    run.setText(text.replace(findText, replaceText), 0);
                } 
            });
        });
	}

	public void saveDocument(String filePath, XWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filePath);
            doc.write(out);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally{
            out.close();
        }
    }

}