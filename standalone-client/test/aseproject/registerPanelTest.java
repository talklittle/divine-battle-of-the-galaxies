/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author _yy
 */
public class registerPanelTest {

    public static final String superString = "12345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "123456789012345678901234567890123456789012345678901234567890"
            + "123456789012345678901234567890123456789012345678901234567890"
            + "123456789012345";

    public registerPanelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
//        PlayerEntityFacadeRemote playerFacade = Lookup.lookupPlayerEntityFacadeRemote();
//        accountInfoFacadeRemote accountInfoFacade = Lookup.lookupaccountInfoFacadeRemote();
//        List accounts = accountInfoFacade.findAll();
//        Iterator it = accounts.iterator();
//        while (it.hasNext()) {
//            accountInfoFacade.remove((accountInfo) it.next());
//        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
//        PlayerEntityFacadeRemote playerFacade = Lookup.lookupPlayerEntityFacadeRemote();
//        accountInfoFacadeRemote accountInfoFacade = Lookup.lookupaccountInfoFacadeRemote();
    }

    /**
     * Test of createNewAccount method, of class registerPanel.
     */
    @Test
    public void testCreateNewAccount_True_1() {
        System.out.println("JUnit - test of createNewAccount: 1");
        registerPanel instance = new registerPanel(null);
        String newAccount = "xxxyy";
        String input = "xxxxx";
        assertTrue(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_True_2() {
        System.out.println("JUnit - test of createNewAccount: 2");
        registerPanel instance = new registerPanel(null);
        String newAccount = "admin";
        String input = "xxxxx";
        assertTrue(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_True_3() {
        System.out.println("JUnit - test of createNewAccount: 3");
        registerPanel instance = new registerPanel(null);
        String newAccount = "Janessa1234";
        String input = "xxxxx";
        assertTrue(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_1() {
        System.out.println("JUnit - test of createNewAccount: 1");
        registerPanel instance = new registerPanel(null);
        String newAccount = "xxxyy";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_2() {
        System.out.println("JUnit - test of createNewAccount: 2");
        registerPanel instance = new registerPanel(null);
        String newAccount = "Calderón";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_3() {
        System.out.println("JUnit - test of createNewAccount: 3");
        registerPanel instance = new registerPanel(null);
        String newAccount = "播放机";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_4() {
        System.out.println("JUnit - test of createNewAccount: 4");
        registerPanel instance = new registerPanel(null);
        String newAccount = "ผู้เล่น";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_5() {
        System.out.println("JUnit - test of createNewAccount: 5");
        registerPanel instance = new registerPanel(null);
        String newAccount = "y.y.y.y";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_6() {
        System.out.println("JUnit - test of createNewAccount: 6");
        registerPanel instance = new registerPanel(null);
        String newAccount = "The Best One";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_7() {
        System.out.println("JUnit - test of createNewAccount: 7");
        registerPanel instance = new registerPanel(null);
        String newAccount = "1111";
        String input = "播放机";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_8() {
        System.out.println("JUnit - test of createNewAccount: 8");
        registerPanel instance = new registerPanel(null);
        String newAccount = superString;
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_9() {
        System.out.println("JUnit - test of createNewAccount: 9");
        registerPanel instance = new registerPanel(null);
        String newAccount = "dasfasfasf";
        String input = superString;
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_10() {
        // null username
        System.out.println("JUnit - test of createNewAccount: 10");
        registerPanel instance = new registerPanel(null);
        String newAccount = null;
        String input = "daf";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_11() {
        System.out.println("JUnit - test of createNewAccount: 11");
        registerPanel instance = new registerPanel(null);
        String newAccount = "SUperman";
        String input = null;
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_12() {
        System.out.println("JUnit - test of createNewAccount: 12");
        registerPanel instance = new registerPanel(null);
        String newAccount = superString + "0";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_13() {
        System.out.println("JUnit - test of createNewAccount: 9");
        registerPanel instance = new registerPanel(null);
        String newAccount = "abcde";
        String input = superString + "0";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    /**
     * Test of verifyOldAccount method, of class registerPanel.
     */
    @Test
    public void testVerifyOldAccount_1() {
        System.out.println("JUnit - test of verifyOldAccount: 1");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "yyyy";
        char[] input = null;
        assertFalse(instance.verifyOldAccount(oldAccount, input));

    }

    @Test
    public void testVerifyOldAccount_2() {
        System.out.println("JUnit - test of verifyOldAccount: 2");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "xxxyy";
        char[] input = "xxxxx".toCharArray();
        assertTrue(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_3() {
        System.out.println("JUnit - test of verifyOldAccount: 3");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "xxxyy";
        char[] input = "eraw".toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_4() {
        
        System.out.println("JUnit - test of verifyOldAccount: 4");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "admin";
        char[] input = "admin".toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_5() {
       
        System.out.println("JUnit - test of verifyOldAccount: 5");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "播放机";
        char[] input = "xxxxx".toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_6() {
        System.out.println("JUnit - test of verifyOldAccount: 6");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "ผู้เล่น";
        char[] input = "xxxxx".toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_7() {
        System.out.println("JUnit - test of verifyOldAccount: 7");
        registerPanel instance = new registerPanel(null);

        String oldAccount = superString;
        char[] input = null;
        assertTrue(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_8() {
        System.out.println("JUnit - test of verifyOldAccount: 8");
        registerPanel instance = new registerPanel(null);

        String oldAccount = superString + "1";
        char[] input = "xxxxx".toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));
    }

    @Test
    public void testVerifyOldAccount_9() {
   
        System.out.println("JUnit - test of verifyOldAccount: 9");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "abcde";
        char[] input = (superString + "0").toCharArray();
        assertFalse(instance.verifyOldAccount(oldAccount, input));

    }

    @Test
    public void testVerifyOldAccount_10() {
        System.out.println("JUnit - test of verifyOldAccount: 10");
        registerPanel instance = new registerPanel(null);

        String oldAccount = "dasfasfasf";
        char[] input = superString.toCharArray();
        assertTrue(instance.verifyOldAccount(oldAccount, input));
    }
}
