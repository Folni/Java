/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr.algebra;

import hr.algebra.model.User;
import hr.algebra.view.EditArticlesPanel;
import hr.algebra.view.LogInPanel;
import hr.algebra.view.RegisterPanel;
import hr.algebra.view.SaveArticlesPanel;
import hr.algebra.view.UploadArticlesPanel;
import java.awt.Component;

/**
 *
 * @author filip
 */
public class ArticleManager extends javax.swing.JFrame {

    /**
     * Creates new form ArticleManager
     */
    public ArticleManager() {
        initComponents();
        initAuthenticationPanels();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpContent = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ArticleManager");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpContent, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArticleManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArticleManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArticleManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArticleManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArticleManager().setVisible(true);
            }
        });
    }

    // Inside ArticleManager
    public void transferContent(String destination, String content) {
        for (int i = 0; i < tpContent.getTabCount(); i++) {
            if (tpContent.getTitleAt(i).equals(destination)) {
                Component comp = tpContent.getComponentAt(i);

                if (comp instanceof EditArticlesPanel) {
                    ((EditArticlesPanel) comp).setContent(content);
                    return;
                }
            }
        }
    }

    public void showTabByTitle(String title) {
        for (int i = 0; i < tpContent.getTabCount(); i++) {
            if (tpContent.getTitleAt(i).equals(title)) {
                tpContent.setSelectedIndex(i);
                return;
            }
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tpContent;
    // End of variables declaration//GEN-END:variables

    private void initPanels() {
        tpContent.add(UPLOAD__ARTICLES, new UploadArticlesPanel(this));
        tpContent.add(EDIT__ARTICLES, new EditArticlesPanel(this));
        tpContent.add(SAVE__ARTICLES, new SaveArticlesPanel(this));
    }

    private void initUserPanels() {
        tpContent.add(EDIT__ARTICLES, new EditArticlesPanel(this));
        tpContent.add(SAVE__ARTICLES, new SaveArticlesPanel(this));
    }

    /*Panel const*/
    private static final String SAVE__ARTICLES = "Save Articles";
    private static final String EDIT__ARTICLES = "Edit Articles";
    private static final String UPLOAD__ARTICLES = "Upload Articles";
    private static final String LOG_IN = "Log-in";
    private static final String REGISTER = "Register";

    private void initAuthenticationPanels() {
        tpContent.add(LOG_IN, new LogInPanel(this));
        tpContent.add(REGISTER, new RegisterPanel(this));
    }

    private User currentUser;

    public void onAuthenticationSuccess(User user) {
        this.currentUser = user;

        // Clear existing tabs
        tpContent.removeAll();

        if (user.getIsAdmin()) {
            initPanels(); // Admin has access to all panels
        } else {
            initUserPanels(); // Regular user has limited access
        }
    }

}
