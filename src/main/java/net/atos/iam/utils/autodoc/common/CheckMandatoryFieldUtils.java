/*
 * @author Capgemini
 */
package net.atos.iam.utils.autodoc.common;

import java.util.List;

import org.springframework.util.StringUtils;


/**
 * The Class AccessKeyBean.
 */
/**
 * @author ha.slimani
 * 
 */
public class CheckMandatoryFieldUtils {

    /** The params voucherId. */
    private  Boolean checkOk = Boolean.TRUE;
    
    private  StringBuilder message = new StringBuilder("");

    
    
    public  Boolean isNullOrEmpty(String valueToCheck, String errorMessage) {
        Boolean returnValue = Boolean.FALSE; 
        
        if(!StringUtils.hasText(valueToCheck)) {
             checkOk= Boolean.FALSE;
             message.append(" | ").append(errorMessage);
             returnValue = Boolean.TRUE;
         }
        return returnValue;
    }


    public  Boolean inList(String valueToCheck, List<String> list, String errorMessage) {
        Boolean returnValue = Boolean.FALSE; 
        
        if(!StringUtils.hasText(valueToCheck) && list!=null && !list.isEmpty() && list.contains(valueToCheck) ) {
             returnValue = Boolean.TRUE; 
         } else {
             checkOk= Boolean.FALSE;
             message.append(" | ").append(errorMessage); 
         }
        return returnValue;
    }
    
    public  Boolean isListEmpty(List<String> list, String errorMessage) {
        Boolean returnValue = Boolean.FALSE; 
        
        if(list!=null && !list.isEmpty() ) {
             returnValue = Boolean.TRUE; 
         } else {
             checkOk= Boolean.FALSE;
             message.append(" | ").append(errorMessage); 
         }
        return returnValue;
    }

    public Boolean isCheckOk() {
        return checkOk;
    }



    public void setCheckOk(Boolean checkOk) {
        this.checkOk = checkOk;
    }



    public StringBuilder getMessage() {
        return message;
    }



    public void setMessage(StringBuilder message) {
        this.message = message;
    }
    
   

}
