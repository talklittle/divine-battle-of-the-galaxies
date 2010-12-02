/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
    public void testCreateNewAccount_True_4() {
        System.out.println("JUnit - test of createNewAccount: 8");
        registerPanel instance = new registerPanel(null);
        String newAccount = superString;
        String input = "xxxxx";
        assertTrue(instance.createNewAccount(newAccount, input));
    }
	
    @Test
    public void testCreateNewAccount_True_5() {
        System.out.println("JUnit - test of createNewAccount: 9");
        registerPanel instance = new registerPanel(null);
        String newAccount = "dasfasfasf";
        String input = superString;
        assertTrue(instance.createNewAccount(newAccount, input));
    }
	
	@Test
    public void testCreateNewAccount_True_6() {
        System.out.println("JUnit - test of createNewAccount: 9");
        registerPanel instance = new registerPanel(null);
        String newAccount = "l";
        String input = "xxxxx";
        assertTrue(instance.createNewAccount(newAccount, input));
    }

	@Test
    public void testCreateNewAccount_True_7() {
        System.out.println("JUnit - test of createNewAccount: 9");
        registerPanel instance = new registerPanel(null);
        String newAccount = "abcd";
        String input = "a";
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
        // null username
        System.out.println("JUnit - test of createNewAccount: 10");
        registerPanel instance = new registerPanel(null);
        String newAccount = null;
        String input = "daf";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_9() {
        System.out.println("JUnit - test of createNewAccount: 11");
        registerPanel instance = new registerPanel(null);
        String newAccount = "SUperman";
        String input = null;
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_10() {
        System.out.println("JUnit - test of createNewAccount: 12");
        registerPanel instance = new registerPanel(null);
        String newAccount = superString + "0";
        String input = "xxxxx";
        assertFalse(instance.createNewAccount(newAccount, input));
    }

    @Test
    public void testCreateNewAccount_False_11() {
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
        char[] input = "xxxxx".toCharArray();
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
	
	@Test
    public void testVerifyOldAccount_11() {
        System.out.println("JUnit - test of verifyOldAccount: 11");
        registerPanel instance = new registerPanel(null);
		
        String oldAccount = "l";
        char[] input = "xxxxx".toCharArray();
        assertTrue(instance.verifyOldAccount(oldAccount, input));
    }
	
	@Test
    public void testVerifyOldAccount_12() {
        System.out.println("JUnit - test of verifyOldAccount: 12");
        registerPanel instance = new registerPanel(null);
        String oldAccount = "abcd";
        String input = "a";
        assertTrue(instance.createNewAccount(oldAccount, input));
    }


    /**
     * Test of actionPerformed method, of class registerPanel.
     */
    @Test
    public void testActionPerformed_1() {
        System.out.println("JUnit - test of actionPerformed: 1");
        registerPanel instance = new registerPanel(null);
        ActionEvent e = new ActionEvent(instance.loginBtn, 0, "");
        instance.actionPerformed(e);
        assertTrue(true);
    }

    /**
     * Test of actionPerformed method, of class registerPanel.
     */
    @Test
    public void testActionPerformed_2() {
        System.out.println("JUnit - test of actionPerformed: 2");
        registerPanel instance = new registerPanel(null);
        ActionEvent e = new ActionEvent(instance.newAccountBtn, 0, "");
        instance.actionPerformed(e);
        assertTrue(true);
    }

    /**
     * Test of actionPerformed method, of class registerPanel.
     */
    @Test
    public void testActionPerformed_3() {
        System.out.println("JUnit - test of actionPerformed: 3");
        registerPanel instance = new registerPanel(null);
        ActionEvent e = new ActionEvent(instance.newAccountOK, 0, "");
        instance.actionPerformed(e);
        assertTrue(true);
    }

    /**
     * Test of actionPerformed method, of class registerPanel.
     */
    @Test
    public void testActionPerformed_4() {
        System.out.println("JUnit - test of actionPerformed: 4");
        registerPanel instance = new registerPanel(null);
        ActionEvent e = new ActionEvent(instance.loginOKBtn, 0, "");
        instance.actionPerformed(e);
        assertTrue(true);
    }

    /**
     * Test of mouseEntered method, of class registerPanel.
     */
    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent e = null;
        registerPanel instance = new registerPanel(null);
        instance.mouseEntered(e);
        assertTrue(true);
    }

//    /**
//     * Test of mouseExited method, of class registerPanel.
//     */
//    @Test
//    public void testMouseExited() {
//        System.out.println("mouseExited");
//        MouseEvent e = null;
//        registerPanel instance = null;
//        instance.mouseExited(e);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mouseClicked method, of class registerPanel.
//     */
//    @Test
//    public void testMouseClicked() {
//        System.out.println("mouseClicked");
//        MouseEvent e = null;
//        registerPanel instance = null;
//        instance.mouseClicked(e);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mousePressed method, of class registerPanel.
//     */
//    @Test
//    public void testMousePressed() {
//        System.out.println("mousePressed");
//        MouseEvent e = null;
//        registerPanel instance = null;
//        instance.mousePressed(e);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mouseReleased method, of class registerPanel.
//     */
//    @Test
//    public void testMouseReleased() {
//        System.out.println("mouseReleased");
//        MouseEvent e = null;
//        registerPanel instance = null;
//        instance.mouseReleased(e);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//

}
