/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docbuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author guilherme
 */
public class DocBuilderUI extends javax.swing.JFrame {

  /**
   * Creates new form DocBuilderUI
   */
  public DocBuilderUI() {
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

    mainPanel = new javax.swing.JPanel();
    templateComboBox = new javax.swing.JComboBox();
    outputPathTextField = new javax.swing.JTextField();
    generateButton = new javax.swing.JButton();
    templateLabel = new javax.swing.JLabel();
    outputPathLabel = new javax.swing.JLabel();
    tableScrollPane = new javax.swing.JScrollPane();
    templateValuesTable = new javax.swing.JTable();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("DocBuilder - Guilherme Taschetto e Fernando Delazeri");
    setResizable(false);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowOpened(java.awt.event.WindowEvent evt) {
        OnWindowOpened(evt);
      }
    });

    templateComboBox.setModel(new DefaultComboBoxModel(Template.values()));
    templateComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        OnTemplateChanged(evt);
      }
    });

    outputPathTextField.setText("output/novoDocumento.rtf");
    outputPathTextField.setToolTipText("");

    generateButton.setText("Gerar Documento");
    generateButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        generateButtonActionPerformed(evt);
      }
    });

    templateLabel.setLabelFor(templateComboBox);
    templateLabel.setText("Template:");

    outputPathLabel.setLabelFor(outputPathTextField);
    outputPathLabel.setText("Saída:");

    templateValuesTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {

      }
    ));
    tableScrollPane.setViewportView(templateValuesTable);

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
            .addComponent(outputPathLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(outputPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(mainPanelLayout.createSequentialGroup()
            .addComponent(templateLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(templateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(0, 17, Short.MAX_VALUE))
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(templateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(templateLabel))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(outputPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(outputPathLabel)
          .addComponent(generateButton))
        .addGap(1, 1, 1))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
    TableModel m = this.templateValuesTable.getModel();
    HashMap<String, String> templateValues = new HashMap<String, String>();
    
    for (int i = 0; i < m.getRowCount(); i++)
    {
      templateValues.put(m.getValueAt(i, 0).toString(), m.getValueAt(i, 1).toString());
    }
    
    DocBuilder db = new DocBuilder();
    db.CreateDocument((Template)templateComboBox.getSelectedItem(), templateValues, outputPathTextField.getText());
    
    JOptionPane.showMessageDialog(this, "Documento criado com sucesso em: \n\n" + outputPathTextField.getText());
  }//GEN-LAST:event_generateButtonActionPerformed

  private void OnTemplateChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnTemplateChanged
    this.createDefaultValues();
  }//GEN-LAST:event_OnTemplateChanged

  private void OnWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_OnWindowOpened
    this.createDefaultValues();
  }//GEN-LAST:event_OnWindowOpened

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
      java.util.logging.Logger.getLogger(DocBuilderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(DocBuilderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(DocBuilderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(DocBuilderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new DocBuilderUI().setVisible(true);
      }
    });
  }

  private void createDefaultValues()
  {
    Map<String, String> values = new LinkedHashMap<String, String>();
    
    switch((Template)templateComboBox.getSelectedItem())
    {
      case Contract:
        values.put("#local#", "Porto Alegre");
        values.put("#nome#", "Guilherme Taschetto");
        values.put("#valor#", "12345,99");
        values.put("#numero#", "10");
        values.put("#juros#", "0.8");
        break;
        
      case Receipt:
        values.put("#nome#", "Guilherme Taschetto");
        values.put("#valor#", "12345,99");
        values.put("#pagador#", "Fernando Delazeri");
        values.put("#item#", "desenvolvimento de software");
        values.put("#local#", "Porto Alegre");
        break;
        
      case Certificate:
        values.put("#nome#", "Guilherme Taschetto");
        values.put("#curso#", "Compiladores");
        values.put("#professor#", "Alexandre Agustini");
        values.put("#cargahoraria#", "60");
        values.put("#local#", "Porto Alegre");
        values.put("#nomeassinatura1#", "Alexandre Agustini");
        values.put("#cargoassinatura1#", "Professor");
        values.put("#nomeassinatura2#", "Alfio Martini");
        values.put("#cargoassinatura2#", "Diretor");
        break;
    }
    
    this.templateValuesTable.setModel(toTableModel(values));    
  }
    
  private static TableModel toTableModel(Map<?,?> map) {
    DefaultTableModel model = new DefaultTableModel(
        new Object[] { "Campo", "Valor" }, 0
    ) {
      @Override
      public boolean isCellEditable(int row, int column)
      {
        return column == 1;
      }
    };
    for (Map.Entry<?,?> entry : map.entrySet()) {
        model.addRow(new Object[] { entry.getKey(), entry.getValue() });
    }
    return model;
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton generateButton;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JLabel outputPathLabel;
  private javax.swing.JTextField outputPathTextField;
  private javax.swing.JScrollPane tableScrollPane;
  private javax.swing.JComboBox templateComboBox;
  private javax.swing.JLabel templateLabel;
  private javax.swing.JTable templateValuesTable;
  // End of variables declaration//GEN-END:variables
}
