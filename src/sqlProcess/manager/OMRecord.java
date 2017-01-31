/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.manager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author The Worst One
 */
public class OMRecord extends OperationManager {

    @Override
    public Operation getOperation() {
        OpRecord opRecord = null;
        
        try {
            opRecord = new OpRecord();
        } catch (IOException ex) {
            Logger.getLogger(OMRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opRecord;
    }
    
}
