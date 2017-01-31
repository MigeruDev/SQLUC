/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.regex;

import sqlProcess.command.CRUD;
import sqlProcess.command.Create;

/**
 *
 * @author The Worst One
 */
public class Context implements IRegex{

    private CRUD crud;
    
    @Override
    public void request(String command) {
        crud.execute(command);
    }
    
    public void setCommand(CRUD crud){
        this.crud = crud;
    }
    
}
