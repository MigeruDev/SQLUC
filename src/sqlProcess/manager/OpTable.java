/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.manager;

import sqlDB.MetaDB;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author The Worst One
 */
public class OpTable implements Operation {
    private static final String ruta="DATABASE\\";
    MetaDB metaDB;   // Agregar un patron de comportamiento para esta variable.

    public OpTable() throws IOException {
        this.metaDB = MetaDB.getMetaDB();
    }
           
    @Override
    public void create(String command) //Validado (Pasa la Prueba)
    {
        String [] info = command.split(" ");
        int length = Integer.parseInt(info[info.length-1]); // Longitud de los campos
        if (length<=50)
        {
            List<String> campos = new ArrayList<>(); //Contiene los campos
            for (int i=4;i<info.length-4;i++){
                if (i!=info.length-5)
                    campos.add(info[i].substring(0, info[i].length()-1));
                else
                    campos.add(info[i].substring(0, info[i].length()));
            }
            String name = info[2]; // Nombre de la tabla
            String clave = info[info.length-3]; // Campo clave
            
            try {
                if(metaDB.existTableName(name)){
                     JOptionPane.showMessageDialog(null, 
                                "La tabla ya existe", 
                                "Tabla existente", 
                                JOptionPane.WARNING_MESSAGE);
                }else{
                    if (campos.contains(clave))
                    {
                        metaDB.createTable(name, campos, clave, length);
                        File table = new File(ruta.concat(name).concat(".csv"));
                        CsvWriter newTable = new CsvWriter(new FileWriter(table),',');
                        for(String campo: campos)
                            newTable.write(String.format("%1$-"+(length-1)+"s", campo).concat("*"));
                        newTable.endRecord();
                        newTable.close();
                    }else
                        JOptionPane.showMessageDialog(null, 
                                "El campo clave no existe", 
                                "Campo clave inválido", 
                                JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else
            JOptionPane.showMessageDialog(null, 
                            "La longitud de los campos debe ser menor o igual a 50", 
                            "Longitud de los campos inválida.", 
                            JOptionPane.WARNING_MESSAGE);

    }

    @Override
    public void delete(String command) //Validado (Pasa la Prueba)
    {
        String name = command.split(" ")[2]; // Nombre de la tabla
        try {
            if (metaDB.existTableName(name)){
                File table = new File(ruta.concat(name).concat(".csv"));
                table.delete();
                metaDB.deleteTable(name);
            }else
                JOptionPane.showMessageDialog(null, 
                                "La tabla no existe", 
                                "Tabla inexistente", 
                                JOptionPane.WARNING_MESSAGE);
                
        } catch (IOException ex) {
            Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void update(String command) //Validado (Pasa la Prueba)
    {
        String [] info = command.split(" ");
        String name = info[2];
        int length;
        String campoActual = info[4];
        String campoNuevo = info[6];
        
        try {
            if (metaDB.existTableName(name))
            {
                if (metaDB.isEditable(name))
                {
                    length = metaDB.getTableLength(name);
                    campoActual = String.format("%1$-"+(length-1)+"s", 
                                        info[4]).concat("*");
                    campoNuevo = String.format("%1$-"+(length-1)+"s", 
                                        info[6]).concat("*");
                    
                    
                    CsvReader tableAux = new CsvReader(new FileReader(ruta.concat(name).concat(".csv")),','); 
                    tableAux.readHeaders();
                    List<String> Campos = Arrays.asList(tableAux.getHeaders());
                    
                    if (Campos.contains(campoActual))
                    {
                        if (!info[4].equals(metaDB.getRecordKey(name)))
                        {
                            if (campoNuevo.length()<=length)
                            {
                                CsvReader metaAux = new CsvReader(new FileReader(ruta.concat("MetaDB.csv")),',');        
                                RandomAccessFile table = new RandomAccessFile(ruta.concat(name).concat(".csv"),"rw");
                                RandomAccessFile meta = new RandomAccessFile(ruta.concat("MetaDB.csv"),"rw");
                                metaAux.readHeaders();
                                meta.readLine();
                                long pos = meta.getFilePointer();

                                while(metaAux.readRecord()){
                                    if (metaAux.get("tableName").equals(name)){
                                        List<String> field = Arrays.asList( metaAux.get("tableField").split("-") );
                                        meta.seek(pos+8+name.length()+(length*field.indexOf(campoActual))+
                                                field.indexOf(campoActual));
                                        meta.writeBytes(campoNuevo);
                                        break;
                                    }                                
                                    meta.readLine();
                                    pos = meta.getFilePointer();
                                }                            
                                metaAux.close();
                                meta.close();

                                //table.readLine();
                                pos = 0;                           
                                table.seek(pos+(length*Campos.indexOf(campoActual))+Campos.indexOf(campoActual) );
                                table.writeBytes(campoNuevo);
                                tableAux.close();
                                table.close();

                            }else
                                JOptionPane.showMessageDialog(null, 
                                            "La longitud del campo nuevo es inválida.", 
                                            "Error en el campo", 
                                            JOptionPane.WARNING_MESSAGE);
                        }else
                            JOptionPane.showMessageDialog(null, 
                                            "No se puede modificar un campo clave", 
                                            "Campo clave inmutable", 
                                            JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, 
                                "El campo no existe", 
                                "Error en el campo", 
                                JOptionPane.WARNING_MESSAGE);
                        tableAux.close();
                    }
                }else
                    JOptionPane.showMessageDialog(null, 
                                    "La tabla no se puede modificar", 
                                    "Tabla contiene registros", 
                                    JOptionPane.WARNING_MESSAGE);
            }else
                JOptionPane.showMessageDialog(null, 
                                "La tabla no existe", 
                                "Tabla inexistente", 
                                JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void select(String command) //Validado (Pasa la Prueba)
    {
        List<String[]> records=new ArrayList<>();
        String [] info= command.split(" ");
        String name = info[2];
        String field = info[4];
        String value = info[6];
        List<String> headers = new ArrayList<>();
        int length;
        try 
        {
            if (metaDB.existTableName(name))
            {
                length = metaDB.getTableLength(name);
                field = String.format("%1$-"+(length-1)+"s", 
                                        info[4]).concat("*");
                value = String.format("%1$-"+(length-1)+"s", 
                                        info[6]).concat("*");
                
                File table = new File(ruta.concat(name).concat(".csv"));
                CsvReader tableAux = new CsvReader(new FileReader(table));
                tableAux.readHeaders();
                headers = Arrays.asList(tableAux.getHeaders());
                
                if(headers.contains(field))
                {
                    while(tableAux.readRecord()){
                        if (tableAux.get(field).equals(value))
                            records.add(tableAux.getValues());
                    }
                    String [] cabeceras = headers.toArray(new String[0]);
                    for (int i=0;i<cabeceras.length;i++){
                        cabeceras[i]=headers.get(i).substring(0,headers.get(i).length()-1).trim();
                    }

                    DefaultTableModel model = new DefaultTableModel(null,cabeceras){
                        @Override
                        public boolean isCellEditable(int row, int col){   //impedimos que las celdas sean modificables
                            return false;
                        }
                    };
                    records.stream().forEach((record) -> {
                        String []registro = record;
                        for(int i=0;i<registro.length;i++){
                            registro[i] = record[i].substring(0,record[i].length()-1).trim();
                        }
                        model.addRow(registro);
                    });
                    JTable tabla = new JTable();
                    tabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    tabla.setFont(new java.awt.Font("Verdana", 0, 11));
                    tabla.setModel(model);
                    tabla.setEnabled(false);
                    JScrollPane jScrollPane = new JScrollPane();
                    jScrollPane.setViewportView(tabla);                    
                    jScrollPane.setPreferredSize(new Dimension(500, 100 ));
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(500 , 140));
                    JLabel jLabel1 = new JLabel();
                    jLabel1.setFont(new java.awt.Font("Verdana", 0, 12));
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText(command);
                    panel.add(jLabel1);
                    panel.add(jScrollPane);
                    
                    JOptionPane.showMessageDialog(null, panel, "Consulta de tabla: "+name,
                            1);                   
                    
                }else
                    JOptionPane.showMessageDialog(null, 
                                "El campo no existe", 
                                "Error en el campo", 
                                JOptionPane.WARNING_MESSAGE);
                tableAux.close();
                
                
            }else
                JOptionPane.showMessageDialog(null, 
                                "La tabla no existe", 
                                "Tabla inexistente", 
                                JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    
    @Override
    public void join(String command) //Validado (Pasa la Prueba)
    {
        List<String[]> recordsT1=new ArrayList<>();
        List<String[]> recordsT2=new ArrayList<>();
        List<String[]> join = new ArrayList<>();
        
        String[]info=command.split(" ");
        
        String nameT1=info[1].substring(0, info[1].length()-1);
        String nameT2=info[2];
        
        String field=info[4];
        String value=info[6];
        
        int lengthT1, lengthT2;
        
        try
        {
            if (metaDB.existTableName(nameT1)) 
            {
                if (metaDB.existTableName(nameT2))
                {
                    
                    lengthT1=metaDB.getTableLength(nameT1);
                    lengthT2=metaDB.getTableLength(nameT2);
                    
                    String fieldT1 = String.format("%1$-"+(lengthT1-1)+"s",
                            field).concat("*");
                    String fieldT2 = String.format("%1$-"+(lengthT2-1)+"s",
                            field).concat("*");
                    String valueT1 = String.format("%1$-"+(lengthT1-1)+"s",
                            value).concat("*");
                    String valueT2 = String.format("%1$-"+(lengthT2-1)+"s",
                            value).concat("*");
                    
                    File table1=new File(ruta.concat(nameT1).concat(".csv"));
                    File table2=new File(ruta.concat(nameT2).concat(".csv"));
                    
                    CsvReader table1Aux=new CsvReader(new FileReader(table1));
                    CsvReader table2Aux=new CsvReader(new FileReader(table2));
                    
                    table1Aux.readHeaders();
                    table2Aux.readHeaders();
                    
                    List<String> headersT1=Arrays.asList(table1Aux.getHeaders());
                    List<String> headersT2=Arrays.asList(table2Aux.getHeaders());
                    
                    int indexFieldT2 = headersT2.indexOf(fieldT2);
                    int indice=0;
                    
                    if(headersT1.contains(fieldT1)&& headersT2.contains(fieldT2))
                    {
                        
                        while (table1Aux.readRecord()) 
                        {
                            if(table1Aux.get(fieldT1).equals(valueT1)){
                                while (table2Aux.readRecord()){
                                    if (table2Aux.get(fieldT2).equals(valueT2)){
                                        String []record = new String[headersT1.size()+headersT2.size()-1];
                                        indice=0;
                                        for (String vTable1: table1Aux.getValues()){
                                            record[indice]=vTable1;
                                            indice++;
                                        }
                                        int indiceAux=0;
                                        for(String vTable2 :table2Aux.getValues()){
                                            if (indiceAux!=indexFieldT2){
                                                record[indice]=vTable2;
                                                indice++;
                                            }
                                            indiceAux++;
                                        }
                                        System.out.println("Esto es record: "+Arrays.toString(record));
                                        join.add(record);
                                        break;
                                    }
                                }
                            }
                        }
                        String [] cabeceras = new String[headersT1.size()+headersT2.size()-1];
                        int i=0;
                        for(String header: headersT1){
                            cabeceras[i]=header;
                            i++;
                        }
                        for(String header: headersT2){
                            if (!header.equals(fieldT2)){
                                cabeceras[i]=header;
                                i++;
                            }
                        }
                        System.out.println("Esto es cabeceras: \n"+Arrays.toString(cabeceras));
                        for(String[]recordJ:join){
                            System.out.println(Arrays.toString(recordJ));
                        }
                        
                        DefaultTableModel model = new DefaultTableModel(null,cabeceras){
                            @Override
                            public boolean isCellEditable(int row, int col){   //impedimos que las celdas sean modificables
                                return false;
                            }
                        };
                        
                        for (String[]recordJ: join){
                            String[] registro = recordJ;
                            for(int j=0;j<registro.length;j++){
                                registro[j]=registro[j].substring(0, registro[j].length()-1).trim();
                                //System.out.println(registro[j]);
                            }
                            model.addRow(registro);
                        }
                        
                        JTable tabla = new JTable();
                        tabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                        tabla.setFont(new java.awt.Font("Verdana", 0, 11));
                        tabla.setModel(model);
                        tabla.setEnabled(false);
                        JScrollPane jScrollPane = new JScrollPane();
                        jScrollPane.setViewportView(tabla);                    
                        jScrollPane.setPreferredSize(new Dimension(500, 100 ));
                        JPanel panel = new JPanel();
                        panel.setPreferredSize(new Dimension(500 , 140));
                        JLabel jLabel1 = new JLabel();
                        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12));
                        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel1.setText(command);
                        panel.add(jLabel1);
                        panel.add(jScrollPane);

                        JOptionPane.showMessageDialog(null, panel, "Join de "+
                                nameT1+" y "+nameT2,1);
                        
                    }else
                        JOptionPane.showMessageDialog(null, "Las tablas no contienen al campo",
                            "Campo inválido", JOptionPane.WARNING_MESSAGE);
                    
                    table1Aux.close();
                    table2Aux.close();
                                      
                }else 
                    JOptionPane.showMessageDialog(null, "La tabla "+nameT1+" no existe",
                                "Tabla inexistente", JOptionPane.WARNING_MESSAGE);
            }else 
                JOptionPane.showMessageDialog(null, "La tabla "+nameT2+" no existe",
                            "Tabla inexistente", JOptionPane.WARNING_MESSAGE);
        }catch (IOException ex) {
            Logger.getLogger(OpTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
