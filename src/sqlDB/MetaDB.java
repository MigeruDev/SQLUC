/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlDB;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author EQUIPO
 */
public class MetaDB 
{
    private static String ruta="DATABASE\\MetaDB.csv";
    private static MetaDB metaDB =null;
    
    private MetaDB() throws IOException // Validado (pasa la prueba)
    {  
        File meta = new File(ruta);
        if (!meta.exists())
        {
            CsvWriter metaFile = new CsvWriter(new FileWriter(meta),',');
            metaFile.writeRecord(new String[]{"Exist",
            "tableName", "tableRecord","tableField","tableKey",
            "tableLength"}); 
        
            metaFile.close();
            System.out.println("Se creó la base de datos.");
        }else
            System.out.println("Ya existe la base de datos.");
    }
    
    public static MetaDB getMetaDB() throws IOException // Validado (Pasa la prueba)  
    {  
        if (metaDB==null)
            metaDB = new MetaDB();
        return metaDB;
    }
    
    public boolean existTableName(String tableName) throws FileNotFoundException, IOException // Validado(Pasa la prueba)
    {
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        boolean check = false;
        
        while (metaFile.readRecord())
        {
            if (metaFile.get("tableName").equals(tableName) && metaFile.get("Exist").equals("1"))
            {
                check = true;
            }
        }
        metaFile.close();
        return check;
    }
    
    public void createTable (String tableName, List<String> headers, 
            String campoClave, int longitud) throws IOException        // Validado(Pasa la prueba)
    {
        File meta = new File(ruta);
        CsvWriter metaFile = new CsvWriter(new FileWriter(meta,true ), ',');
        
        metaFile.write("1");
        metaFile.write(tableName);
        metaFile.write("0  *");
        String campos = "";
        for (int i=0; i<headers.size();i++){
            if (i<headers.size()-1)
                campos = campos.concat(String.format("%1$-"+(longitud-1)+"s", headers.get(i))).concat("*-");
            else
                campos = campos.concat(String.format("%1$-"+(longitud-1)+"s", headers.get(i)).concat("*"));
        }
        metaFile.write(campos); 
        metaFile.write(campoClave); 
        metaFile.write(String.valueOf(longitud)); 
        metaFile.endRecord();
        metaFile.close();
    }
    
    public void deleteTable(String tableName) throws FileNotFoundException, IOException // Validado (Pasa la Prueba)
    {
        File metaFile = new File(ruta);
        CsvReader metaAux = new CsvReader(new FileReader(ruta),',');
        RandomAccessFile meta = new RandomAccessFile(ruta,"rw");
        String linea = meta.readLine();        
        long pos = meta.getFilePointer();        
        metaAux.readHeaders();
        while(metaAux.readRecord()){
            if (metaAux.get("tableName").equals(tableName)
                    &&metaAux.get("Exist").equals("1")){
                meta.seek(pos);                
                meta.writeBytes("0");
            }
            linea = meta.readLine();
            pos = meta.getFilePointer();                        
        }
        meta.close();
        metaAux.close();
    }
    
    public int getTableLength(String tableName) throws FileNotFoundException, IOException //Validado (Pasa la prueba)
    {
        int length =0;
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        while(metaFile.readRecord()){
            if (metaFile.get("tableName").equals(tableName)&&
                    metaFile.get("Exist").equals("1"))
                length = Integer.parseInt(metaFile.get("tableLength"));
        }
        metaFile.close();
        return length;
    }
    
    public boolean checkRecordField(String tableName, List<String> Values) throws IOException //Validado (Pasa la Prueba)
    {
        boolean check = true;
        int length = getTableLength(tableName);
        int cantCampos = 0;
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        while(metaFile.readRecord()){
            if (metaFile.get("tableName").equals(tableName)&&
                    metaFile.get("Exist").equals("1"))
                cantCampos = metaFile.get("tableField").split("-").length;
        }  
        if (Values.size()==cantCampos)
            for (String value: Values){
                if (value.length()>length)
                    check = false;
            }
        else
            check=false;
        metaFile.close();
        return check;
    }
    
    
    public boolean checkRecordKey(String tableName, List<String> Values) //Validado (Pasa la Prueba)
            throws FileNotFoundException, IOException
    {
        boolean check = true;
        String fieldKey = "";
        String valueKey = "";
        List<String> Campos = new ArrayList<>();
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        
        while(metaFile.readRecord()){
            if (metaFile.get("tableName").equals(tableName) &&
                    metaFile.get("Exist").equals("1")){
                String []field = metaFile.get("tableField").split("-");
                for(String campo: field){
                    Campos.add(campo.substring(0, campo.length()-1).trim());
                }
                fieldKey = metaFile.get("tableKey");
                break;
            }
        }
        metaFile.close();
        
        valueKey = Values.get(Campos.indexOf(fieldKey));
               
        File table = new File("DATABASE\\".concat(tableName).concat(".csv"));
        CsvReader tableRecord = new CsvReader(new FileReader(table), ',');
        tableRecord.readHeaders();
        String aux = "";
        
        while(tableRecord.readRecord()){
            aux = tableRecord.get(Campos.indexOf(fieldKey));
            if (aux.substring(0, aux.length()-1).trim().equals(valueKey))
            {
                check=false;
                break;
            }
        }
        tableRecord.close();
        
        return check;
    }
    
    public String getRecordKey(String tableName) throws FileNotFoundException, IOException //Validado (Pasa la Prueba) 
    {
        String fieldKey = "";
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        
        while(metaFile.readRecord()){
            if (metaFile.get("tableName").equals(tableName)&&
                    metaFile.get("Exist").equals("1"))
                fieldKey = metaFile.get("tableKey");
        }       
        metaFile.close();
        return fieldKey;
    }
    
    public void addRecordTable(String tableName) throws FileNotFoundException, IOException //Validado (Pasa la Prueba)
    {
        RandomAccessFile meta = new RandomAccessFile(ruta,"rw");
        String []aux;
        String linea = meta.readLine();
        long pos = meta.getFilePointer();
        linea = meta.readLine();
        while(linea!=null){
            aux=linea.split(",");
            //System.out.println("Esto es aux: "+Arrays.toString(aux));
            //System.out.println("aux[1] = "+aux[1]+"\naux[0] = "+aux[0]);
            if (tableName.equals(aux[1])&&"1".equals(aux[0]))
            {
                meta.seek(pos+3+aux[1].length());
                int numRecord = Integer.parseInt(aux[2].substring(0, aux[2].length()-1).trim())+1;
                meta.writeBytes(String.format("%1$-3s", String.valueOf(numRecord)).concat("*"));
            }
            pos = meta.getFilePointer();
            linea = meta.readLine();
        }
        meta.close();
    }
    
    public void removeRecordTable(String tableName) throws FileNotFoundException, IOException //Validado (Pasa la Prueba)
    {
        RandomAccessFile meta = new RandomAccessFile(ruta,"rw");
        String []aux;
        String linea = meta.readLine();
        long pos = meta.getFilePointer();
        linea = meta.readLine();
        while(linea!=null){
            aux=linea.split(",");
            //System.out.println("aux[1] = "+aux[1]+"\naux[0] = "+aux[0]);
            if (tableName.equals(aux[1])&&"1".equals(aux[0]))
            {
                meta.seek(pos+3+aux[1].length());
                int numRecord = Integer.parseInt(aux[2].substring(0, aux[2].length()-1).trim())-1;
                meta.writeBytes(String.format("%1$-3s", String.valueOf(numRecord)).concat("*"));
            }
            pos = meta.getFilePointer();
            linea = meta.readLine();
        }
        meta.close();
    }
    
    public boolean isEditable(String tableName) throws FileNotFoundException, IOException //Validado (Pasa la Prueba)
    {
        boolean check = true;
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        
        while(metaFile.readRecord()){
            if (metaFile.get("tableName").equals(tableName)&&
                    metaFile.get("Exist").equals("1")){                
                if(!metaFile.get("tableRecord").equals("0  *"))
                    check=false;
                break;
            }
        }        
        metaFile.close();
        return check;
    }
    
        
    public List<String[]> getMetaRecord() throws FileNotFoundException, IOException
    {
        List<String[]> records=new ArrayList<>();
        File meta = new File(ruta);
        CsvReader metaFile = new CsvReader(new FileReader(meta), ',');
        metaFile.readHeaders();
        while(metaFile.readRecord()){
            if (metaFile.get("Exist").equals("1")){
                String [] values = metaFile.getValues();
                values[2] = values[2].substring(0, values[2].length()-1).trim();
                String fields = "";
                String []aux = values[3].split("-");
                for (String campo: aux){
                    fields += campo.substring(0, campo.length()-1).trim()+", ";
                }
                fields = fields.substring(0, fields.length()-2);
                values[3] = fields;
                records.add(values);
            }
                
        }        
        metaFile.close();
        
        
        return records;
    }
}
