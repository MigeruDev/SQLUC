/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.regex;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import sqlProcess.command.CRUD;
import sqlProcess.command.Create;
import sqlProcess.command.Delete;
import sqlProcess.command.Join;
import sqlProcess.command.Select;
import sqlProcess.command.Update;
import sqlProcess.manager.OMRecord;
import sqlProcess.manager.OMTable;
import sqlProcess.manager.OperationManager;

/**
 *
 * @author The Worst One
 */
public class ContextProxy implements IRegex {

    private OperationManager manager;
    private Context context=new Context();
    
    @Override
    public void request(String command) {
        
        String createTable = ("^CREAR(\\sTABLA)\\s[a-zA-Z0-9-]+"
                + "(\\sCAMPOS)\\s([a-zA-Z0-9]+|,\\s)+\\s(CLAVE)\\s[a-zA-Z0-9]+"
                + "\\s(LONGITUD)\\s[0-9]+$");
        String updateTable = ("^MODIFICAR(\\sTABLA)"
                + "\\s[a-zA-Z0-9-]+(\\sCAMPO)\\s([a-zA-Z0-9]+)\\s(POR)\\s[a-zA-Z0-9]+$");
        String deleteTable = ("^ELIMINAR(\\sTABLA)\\s[a-zA-Z0-9-]+$");
        String createRecord = ("^CREAR(\\sREGISTRO)\\s[a-zA-Z0-9-]"
                + "+(\\sVALOR)\\s([a-zA-Z0-9]+|,\\s)+$");
        String updateRecord = ("^MODIFICAR(\\sREGISTRO)"
                + "\\s[a-zA-Z0-9-]+(\\sCLAVE)\\s([a-zA-Z0-9]+)\\s(CAMPO)\\s"
                + "[a-zA-Z0-9]+\\s(POR)\\s[a-zA-Z0-9]+$");
        String deleteRecord = ("^ELIMINAR(\\sREGISTRO)\\s"
                + "[a-zA-Z0-9-]+(\\sCLAVE)\\s([a-zA-Z0-9]+)$");
        String selectTable = ("^SELECCIONAR(\\sDE)\\s[a-zA-Z0-9-]"
                + "+(\\sDONDE)\\s([a-zA-Z0-9]+)\\s=\\s([a-zA-Z0-9]+)$");
        String joinTable = ("^UNIR\\s[a-zA-Z0-9-]+,\\s([a-zA-Z0-9]+)"
                + "(\\sPOR)\\s([a-zA-Z0-9]+)\\s=\\s([a-zA-Z0-9]+)$");
        
        if (command.matches(createTable))
        {
            manager = new OMTable();
            context.setCommand(new Create(manager.getOperation()));
            context.request(command);
        }else if (command.matches(updateTable))
        {
            manager = new OMTable();
            context.setCommand(new Update(manager.getOperation()));
            context.request(command);
        }else if (command.matches(deleteTable))
        {
            manager = new OMTable();
            context.setCommand(new Delete(manager.getOperation()));
            context.request(command);
        }else if(command.matches(createRecord))
        {
            manager = new OMRecord();
            context.setCommand(new Create(manager.getOperation()));
            context.request(command);
        }else if(command.matches(updateRecord))
        {
            manager = new OMRecord();
            context.setCommand(new Update(manager.getOperation()));
            context.request(command);
        }else if(command.matches(deleteRecord))
        {
            manager = new OMRecord();
            context.setCommand(new Delete(manager.getOperation()));
            context.request(command);
        }else if(command.matches(selectTable))
        {
            manager = new OMTable();
            context.setCommand(new Select(manager.getOperation()));
            context.request(command);            
        }else if(command.matches(joinTable))
        {
            manager = new OMTable();
            context.setCommand(new Join(manager.getOperation()));
            context.request(command); 
        }else
            JOptionPane.showMessageDialog(null, 
                            "No es un comando válido", 
                            "Error de comando", 
                            JOptionPane.WARNING_MESSAGE);
    }
    
}
