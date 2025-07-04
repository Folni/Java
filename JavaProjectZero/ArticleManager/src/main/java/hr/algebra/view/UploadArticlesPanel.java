/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.ArticleManager;
import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Article;
import hr.algebra.model.Person;
import hr.algebra.parsers.rss.ArticleParser;
import hr.algebra.utilities.MessageUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;

/**
 *
 * @author filip
 */
public class UploadArticlesPanel extends javax.swing.JPanel {

    /**
     * Creates new form UploadArticlesPanel
     */
    private ArticleManager manager;
    public UploadArticlesPanel(ArticleManager manager) {
        this.manager = manager;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lsArticles = new javax.swing.JList<>();
        btnUpload = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jScrollPane1.setViewportView(lsArticles);

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnDeleteAll.setText("Delete all");
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(btnDeleteAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private Repository repository;
    private DefaultListModel<Article> model;

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        try {
            List<Article> articles = ArticleParser.parse();
            Map<Integer, List<Integer>> dictionary = repository.createArticles(articles);

            for (Map.Entry<Integer, List<Integer>> entry : dictionary.entrySet()) {
                Integer articleId = entry.getKey();
                List<Integer> contributorIds = entry.getValue();
                repository.insertArticleContributor(articleId, contributorIds);
            }

            loadModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.showErrorMessage("Unrecoverable!", "Exiting...");
            System.exit(1);
        }
    }//GEN-LAST:event_formComponentShown

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        try {
            List<Article> articles = repository.selectArticles();


            for (Article article : articles) {
                try {
                    List<Person> people = repository.getArticleContributors(article.getId());
                    List<Integer> contributorIds = people.stream()
                            .map(Person::getId)
                            .collect(Collectors.toList());

                    repository.deleteArticleContributor(article.getId(), contributorIds);
                    repository.deleteArticle(article.getId());
                } catch (Exception ex) {
                    Logger.getLogger(UploadArticlesPanel.class.getName()).log(Level.SEVERE,
                            "Failed to delete contributors for article ID: " + article.getId(), ex);
                }
            }


            /*for (Article article : articles) {
                try {
                    repository.deleteArticle(article.getId());
                } catch (Exception ex) {
                    Logger.getLogger(UploadArticlesPanel.class.getName()).log(Level.SEVERE,
                            "Failed to delete article ID: " + article.getId(), ex);
                }
            }*/

            loadModel();
        } catch (Exception e) {
            Logger.getLogger(UploadArticlesPanel.class.getName()).log(Level.SEVERE, "Failed to delete all articles", e);
        }
    }//GEN-LAST:event_btnDeleteAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnUpload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Article> lsArticles;
    // End of variables declaration//GEN-END:variables

    private void init() throws Exception {
        repository = RepositoryFactory.getRepository();
        model = new DefaultListModel<>();
        loadModel();
    }

    private void loadModel() throws Exception {
        List<Article> articles = repository.selectArticles();
        articles.forEach(model::addElement);
        lsArticles.setModel(model);
    }
}
