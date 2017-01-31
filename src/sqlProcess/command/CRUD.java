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
public abstract class CRUD {
    protected Operation operation;

    public CRUD(Operation operation) {
        this.operation = operation;
    }
    
    public abstract void execute(String command);
    
}
