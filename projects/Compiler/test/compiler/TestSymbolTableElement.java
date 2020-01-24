package compiler;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author kurumkan
 */
public class TestSymbolTableElement {
    static String kind = "test kind";
    static String type = "test type";
    static int index = 12345;
    static SymbolTableElement instance;
    
    public TestSymbolTableElement() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new SymbolTableElement(type, kind, index);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void testGetKind() {
        Assert.assertEquals(kind, instance.getKind());
    }
    
    @Test
    public void testGetType() {
        Assert.assertEquals(type, instance.getType());
    }
    
    @Test
    public void testGetIndex() {
        Assert.assertEquals(index, instance.getIndex());
    }
    
    @Test
    public void testToString() {
        String expected = kind + ", " +  type + ", " + index;
        Assert.assertEquals(expected, instance.toString());
    }
}
