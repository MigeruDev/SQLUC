/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.manager;

import sqlDB.MetaDB;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JOptionPane;

/**
 *
 * @author The Worst One
 */
public class OpRecord implements Operation {
    private static final String ruta="DATABASE\\";
    MetaDB metaDB;

    public OpRecord() throws IOException {
        this.metaDB = MetaDB.getMetaDB();
    }
    
    @Override
    public void create(String command)//Validado (Pasa la Prueba)
    {  
        String [] info = command.split(" ");
        String name = info[2];
        List<String> vCampos = new ArrayList<>(); //Contiene los valores de los campos
        for (int i=4;i<info.length;i++){
            if (i!=info.length-1)
                vCampos.add(info[i].substring(0, info[i].length()-1));
            else
                vCampos.add(info[i].substring(0, info[i].length()));
        }
        
        try {
            if(metaDB.existTableName(name)){
                if (metaDB.checkRecordField(name, vCampos))
                {
                    if(metaDB.checkRecordKey(name, vCampos))
                    {
                        int length = metaDB.getTableLength(name);

                        File table = new File(ruta.concat(name).concat(".csv"));
                        CsvWriter recordTable = new CsvWriter(new FileWriter(table,true),',');
                        for (String value: vCampos){
                            recordTable.write(String.format("%1$-"+(length-1)+"s", value).concat("*"));
                        }
                        recordTable.endRecord();
                        recordTable.close();
                        metaDB.addRecordTable(name);
                    }else
                        JOptionPane.showMessageDialog(null, 
                                "No se puede ingresar registros con Claves iguales.", 
                                "Registros repetidos", 
                                JOptionPane.WARNING_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, 
                                "Longitud de registro o cantidad de valores incorrectos.", 
                                "Error en el ingreso de datos", 
                                JOptionPane.WARNING_MESSAGE);
                }                
            }else{
                JOptionPane.showMessageDialog(null, 
                                "La tabla no existe", 
                                "Tabla inexistente", 
                                JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
    }

    @Override
    public void delete(String command) //Validado (Pasa la Prueba)
    {
        String name = command.split(" ")[2];
        String clave = command.split(" ")[4];
        
        try
        {
            if (metaDB.existTableName(name))
            {
                File table = new File(ruta.concat(name).concat(".csv"));
                File aux = new File(ruta.concat("auxiliar.csv"));
            
                CsvReader original = new CsvReader(new FileReader(table),',');
                CsvWriter auxiliar = new CsvWriter(new FileWriter(aux),',');

                while(original.readRecord()){
                    if (!original.getRawRecord().contains(clave)){
                        auxiliar.writeRecord(original.getRawRecord().split(","));
                    }else
                        metaDB.removeRecordTable(name);
                }
                original.close();
                auxiliar.close();
                original = new CsvReader(new FileReader(aux),',');
                auxiliar = new CsvWriter(new FileWriter(table),',');

                while(original.readRecord()){
                    auxiliar.writeRecord(original.getRawRecord().split(","));
                }
                original.close();
                auxiliar.close();
                aux.delete();
            }else
                JOptionPane.showMessageDialog(null, 
                                "La tabla no existe", 
                                "Tabla inexistente", 
                                JOptionPane.WARNING_MESSAGE);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(OpRecord.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OpRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }

    
    @Override
    public void update(String command) //Validado (Pasa la Prueba)
    {
        String [] info = command.split(" ");
        String name = info[2];
        int length;
        try {
            length = metaDB.getTableLength(name);
            String fieldKey = String.format("%1$-"+(length-1)+"s", 
                metaDB.getRecordKey(name)).concat("*");
            String vClave = String.format("%1$-"+(length-1)+"s", 
                info[4]).concat("*");
            String campo = String.format("%1$-"+(length-1)+"s", 
                info[6]).concat("*");
            String vNuevo = String.format("%1$-"+(length-1)+"s", 
                info[8]).concat("*");
            
            if (vClave.length()<=length)
            {
                CsvReader tableAux = new CsvReader(new FileReader(ruta.concat(name).concat(".csv")),',');
                
                RandomAccessFile table = new RandomAccessFile(ruta.concat(name).concat(".csv"),"rw");
                String linea = table.readLine();
                long pos = table.getFilePointer();
                tableAux.readHeaders();
                List<String> Campos = new ArrayList<>();
                Campos = Arrays.asList(tableAux.getHeaders());
                int indexField = Campos.indexOf(campo);
                if (indexField!=-1){
                    while(tableAux.readRecord()){
                        if (tableAux.get(fieldKey).equals(vClave)){
                            table.seek(pos + (length*indexField)
                            + indexField);
                            table.writeBytes(vNuevo);
                            break;
                        }           
                        linea=table.readLine();
                        pos = table.getFilePointer();
                    }
                }else
                    JOptionPane.showMessageDialog(null, 
                                    "El campo no existe", 
                                    "Error al ingresar campo", 
                                    JOptionPane.WARNING_MESSAGE);        
                table.close();
                tableAux.close();
            }else
                JOptionPane.showMessageDialog(null, 
                                    "La longitud del valor ingresado no es válida.", 
                                    "Longitud de valor", 
                                    JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(OpRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void join(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void select(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
