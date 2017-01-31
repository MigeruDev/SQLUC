/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.command;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sqlProcess.manager.OpTable;
import sqlProcess.manager.Operation;

/**
 *
 * @author The Worst One
 */
public class Select extends CRUD{

    public Select(Operation operation) {
        super(operation);
    }

    @Override
    public void execute(String command) {
        operation.select(command);
    }
    
}
