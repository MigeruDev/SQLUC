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
public class OMTable extends OperationManager {

    @Override
    public Operation getOperation() {
        OpTable opTable=null;
        try {
            opTable = new OpTable();
        } catch (IOException ex) {
            Logger.getLogger(OMTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opTable;
    }
    
}
