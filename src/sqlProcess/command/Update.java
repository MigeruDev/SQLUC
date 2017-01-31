/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.command;

import sqlProcess.manager.Operation;

/**
 *
 * @author The Worst One
 */
public class Update extends CRUD{

    public Update(Operation operation) {
        super(operation);
    }

    @Override
    public void execute(String command) {
        operation.update(command);
    }
    
}
