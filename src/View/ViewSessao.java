/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Util.UtilCriptografiaDescriptografia;
import Util.UtilSessao;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Analise em Curso
 */
public class ViewSessao extends javax.swing.JFrame {

    private final String configuracao = "sessao/sessao.properties";
    private final String caminhosessao = "sessao/sessao.json";
    //pegara a localização do projeto
    File diretorio = new File(".");
    //clase com as seções
    UtilSessao utilSessao = new UtilSessao();
    UtilCriptografiaDescriptografia criptodescripto = new UtilCriptografiaDescriptografia();

    /**
     * Creates new form ViewSessao
     */
    public ViewSessao() {
        initComponents();

        try {
            verificaArquivos();
        } catch (IOException ex) {
            Logger.getLogger(ViewSessao.class.getName()).log(Level.SEVERE, null, ex);
        }
        //chama o metodo que lista os arquivos de sessão
        this.listarArquivos();
        //chama o metodo que ler os arquivos de sessão
        this.lersessao();
    }

    /**
     * metodo para listar os arquivos de sessão
     */
    public void listarArquivos() {
        File file = new File(diretorio + "\\sessao");
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];
            System.out.println(arquivos.getName());
//            jList1.add(""+arquivos.getName());
        }
    }

    public void verificaArquivos() throws IOException {
        //istancia as variaveis para criptografa
        UtilCriptografiaDescriptografia criptodescripto = null;
        criptodescripto = new UtilCriptografiaDescriptografia();
        //valida a pasta sys
        File PastaSYS = new File("" + diretorio.getCanonicalPath() + "\\sessao"); // verifica se a pasta existe
        if (!PastaSYS.exists()) {
            PastaSYS.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.            
        }
        //verifica se o arquivo de  sessão pradão existe
        File ArquivoSystema = new File("" + diretorio.getCanonicalPath() + "\\" + configuracao); // verifica se a pasta existe
        if (!ArquivoSystema.exists()) {
            //sys/configuracao.properties

            File file = new File(configuracao);
            Properties properties = new Properties();
            if (jCBTipodeRede.getSelectedItem().equals("Mysql (TCP/IP)")) {
                properties.setProperty("Driver", "com.mysql.jdbc.Driver");
            }
            properties.setProperty("Sevidor", jTFServidor.getText());
            properties.setProperty("Usuario", jTFUsuario.getText());
            properties.setProperty("Senha", criptodescripto.Reverter(jPSenha.getText()));
            properties.setProperty("Porta", jTFPorta.getText());
            properties.setProperty("Descricao", jEPDescricao.getText());
            if (jCBTipodeRede.getSelectedItem().equals("Mysql (TCP/IP)")) {
                utilSessao.Driver = "com.mysql.jdbc.Driver";
            }
            utilSessao.Sevidor = "jdbc:mysql://" + properties.getProperty("Sevidor") + ":" + properties.getProperty("Porta") + "/";
            utilSessao.Usuario = jTFUsuario.getText();
            utilSessao.Senha = criptodescripto.Reverter(jPSenha.getText());
            utilSessao.Descricao = jEPDescricao.getText();
            utilSessao.Descricao = jEPDescricao.getText();
            utilSessao.Porta = jTFPorta.getText();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                properties.store(fos, "SGB:");
                fos.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //dados da sessao() 
    public void lersessao() {
        File file = new File(configuracao);
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
            if (jCBTipodeRede.getSelectedItem().equals("com.mysql.jdbc.Driver")) {
                jCBTipodeRede.setSelectedItem("Mysql (TCP/IP)");
            }
            jTFServidor.setText(properties.getProperty("Sevidor"));
            jTFUsuario.setText(properties.getProperty("Usuario"));
            jPSenha.setText(criptodescripto.Converter(properties.getProperty("Senha")));
            jTFPorta.setText(properties.getProperty("Porta"));
            jEPDescricao.setText(properties.getProperty("Descricao"));
            if (jCBTipodeRede.getSelectedItem().equals("Mysql (TCP/IP)")) {
                utilSessao.Driver = "com.mysql.jdbc.Driver";
            }
            utilSessao.Sevidor = "jdbc:mysql://" + properties.getProperty("Sevidor") + ":" + properties.getProperty("Porta") + "/";
            utilSessao.Usuario = properties.getProperty("Usuario");
            utilSessao.Senha = criptodescripto.Reverter(properties.getProperty("Senha"));
            utilSessao.Porta = properties.getProperty("Porta");
            utilSessao.Descricao = properties.getProperty("Descricao");
        } catch (IOException e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        shapeTabbedPane1 = new swing.xp.ShapeTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCBTipodeRede = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTFServidor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPSenha = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEPDescricao = new javax.swing.JEditorPane();
        jTFPorta = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jbNovo = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de sessão");

        jSplitPane1.setDividerLocation(150);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Sem Nome", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Nome da Sessão");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        shapeTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        shapeTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel1.setText("Tipo de Rede: ");

        jCBTipodeRede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mysql (TCP/IP)" }));

        jLabel2.setText("Servidor: ");

        jTFServidor.setText("127.0.0.1");

        jLabel3.setText("Usúario:");

        jTFUsuario.setText("root");

        jLabel4.setText("Senha:");

        jLabel5.setText("Prota:");

        jLabel7.setText("Descrição");

        jScrollPane2.setViewportView(jEPDescricao);

        jTFPorta.setText("3306");

        jButton6.setText("+");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setText("-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTFPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTFServidor)
                    .addComponent(jCBTipodeRede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTFUsuario)
                    .addComponent(jPSenha)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCBTipodeRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jPSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTFPorta, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 143, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );

        shapeTabbedPane1.addTab("Configuração", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(shapeTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(shapeTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jbNovo.setText("Novo");
        jbNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoActionPerformed(evt);
            }
        });

        jButton4.setText("Apagar");

        jButton3.setText("Salvar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Ler Json");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton3, jButton4, jbNovo});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNovo)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ViewPrincipal principal = new ViewPrincipal();
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int sniper = Integer.parseInt(jTFPorta.getText());
        sniper += 1;
        jTFPorta.setText("" + sniper);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int sniper = Integer.parseInt(jTFPorta.getText());
        sniper -= 1;
        jTFPorta.setText("" + sniper);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        File file = new File(configuracao);
        Properties properties = new Properties();
        if (jCBTipodeRede.getSelectedItem().equals("Mysql (TCP/IP)")) {
            properties.setProperty("Driver", "com.mysql.jdbc.Driver");
        }
        properties.setProperty("Sevidor", jTFServidor.getText());
        properties.setProperty("Usuario", jTFUsuario.getText());
        properties.setProperty("Senha", criptodescripto.Reverter(jPSenha.getText()));
        properties.setProperty("Porta", jTFPorta.getText());
        properties.setProperty("Descricao", jEPDescricao.getText());
        if (jCBTipodeRede.getSelectedItem().equals("Mysql (TCP/IP)")) {
            utilSessao.Driver = "com.mysql.jdbc.Driver";
        }
        utilSessao.Sevidor = "jdbc:mysql://" + properties.getProperty("Sevidor") + ":" + properties.getProperty("Porta") + "/";

        utilSessao.Usuario = jTFUsuario.getText();
        utilSessao.Senha = criptodescripto.Reverter(jPSenha.getText());
        utilSessao.Descricao = jEPDescricao.getText();
        utilSessao.Porta = jTFPorta.getText();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "SGB:");
            fos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
        // Cria uma nova sessao
        BufferedWriter  bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(""+caminhosessao, true));

            String nomesessao = JOptionPane.showInputDialog("Digite o nome da sessão");
            String banco = JOptionPane.showInputDialog("Digite o nome do banco");
            //abri uma sessao            
            bw.write("{\n");
            bw.write("\"" + nomesessao + "\": {\n");
            bw.write("\"" + banco  + "\": {\n");

            bw.write("\"Sevidor\": " + "\"" + jTFServidor.getText() + "\",\n");
            bw.write("\"Usuario\": " + "\"" + jTFUsuario.getText() + "\",\n");
            bw.write("\"Senha\": " + "\"" + criptodescripto.Reverter(jPSenha.getText()) + "\",\n");
            bw.write("\"Porta\": " + "\"" + jTFPorta.getText() + "\",\n");
            bw.write("\"Descricao\": " + "\"" + jEPDescricao.getText() + "\",\n");
            //fecha a jTFServidor.getText()
            bw.write("}\n");
            bw.write("}\n");
            bw.write("}\n");

            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally { // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) { 
                    // just ignore it
                }
            }
        }
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewSessao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSessao.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSessao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jCBTipodeRede;
    private javax.swing.JEditorPane jEPDescricao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPasswordField jPSenha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTFPorta;
    private javax.swing.JTextField jTFServidor;
    private javax.swing.JTextField jTFUsuario;
    private javax.swing.JButton jbNovo;
    private swing.xp.ShapeTabbedPane shapeTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
