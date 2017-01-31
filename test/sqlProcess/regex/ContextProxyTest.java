/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.regex;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author The Worst One
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class ContextProxyTest {
    
    public ContextProxyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of request method, of class ContextProxy.
     */
    
    @Test    
    public void AcreateTable() {
        System.out.println("Create Table Test");
        String command = "CREAR TABLA Testing CAMPOS"
                + " id, nombre, apellido CLAVE id LONGITUD 20";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void BcreateRecord() {
        System.out.println("Create Record Test");
        String command = "CREAR REGISTRO Testing VALOR"
                + " 0706422250, Miguel, Macias";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void CupdateRecord() {
        System.out.println("Update Record Test");
        String command = "MODIFICAR REGISTRO Testing CLAVE"
                + " 0706422250 CAMPO apellido POR Narvaez";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void Dselect() {
        System.out.println("Select Test");
        String command = "SELECCIONAR DE Testing DONDE "
                + "id = 0706422250";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void EremoveRecord() {
        System.out.println("Remove Record Test");
        String command = "ELIMINAR REGISTRO Testing CLAVE"
                + " 076422250";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void FupdateTable() {
        System.out.println("Update Table Test");
        String command = "MODIFICAR TABLA Testing CAMPO"
                + " apellido POR LastName";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    
    
    @Test
    public void Gjoin() {
        System.out.println("Join Test");
        String command = "UNIR Testing, noExiste POR id"
                + " = 0706422250";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    @Test
    public void HremoveTable() {
        System.out.println("Remove Table Test");
        String command = "ELIMINAR TABLA Testing";
        ContextProxy instance = new ContextProxy();
        instance.request(command);
        // TODO review the generated test code and remove the default call to fail.
        
    }  
    
}
