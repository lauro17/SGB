package View;
//
//import Conexao.ModuloConexao; 

import Util.UtilSessao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Analise em Curso
 */
public class ViewPrincipal extends javax.swing.JFrame {

    private Connection connect = null;
    private Statement statement = null;
    //clase com as seções
    UtilSessao utilSessao = new UtilSessao();

    /**
     * Creates new form ViewPrincipal
     */
    public ViewPrincipal() {
        initComponents();
        //chama o metodo de conexao
//        conexao = ModuloConexao.conector();
        //titulo da pagina
        this.setTitle("SGB - Analise em Curso");
        //centraliza o formulario
        this.setLocationRelativeTo(null);
        //faz a janela maxminizada
        this.setExtendedState(MAXIMIZED_BOTH);
        //propiedades de splipanel
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(10);
        //jtree                 
        jTree1.setModel(createTreeModel());
        //listener
        jTree1.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //cuando se realice un clic sobre algun item, se carga el archivo HTML correspondiente
                TreePath path = jTree1.getSelectionPath();
                if (path != null) {
                    DefaultMutableTreeNode NodoSeleccionado = (DefaultMutableTreeNode) path.getLastPathComponent();

                }
            }
        });
        //iconos del jtree
        DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) jTree1.getCellRenderer();

        render.setLeafIcon(new ImageIcon(getClass().getResource("/icons/database.png")));
        render.setOpenIcon(new ImageIcon(getClass().getResource("/icons/database_highlight.png")));
        render.setClosedIcon(new ImageIcon(getClass().getResource("/icons/database.png")));
    }

    /**
     * Método que lê o arquivo HELP para criar a árvore de ajuda
     *
     * @return DefaultTreeModel
     */
    private DefaultTreeModel createTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode banco = new DefaultMutableTreeNode();
        DefaultMutableTreeNode tabela = new DefaultMutableTreeNode();
        DefaultMutableTreeNode colunas = new DefaultMutableTreeNode();
        DefaultMutableTreeNode infocolunas = new DefaultMutableTreeNode();
        try {
            try {
                //Class.forName("com.mysql.jdbc.Driver");
                Class.forName(utilSessao.Driver);
                //connect = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
                connect = DriverManager.getConnection(utilSessao.Sevidor, utilSessao.Usuario, utilSessao.Senha);
                //pega os meta dados
                DatabaseMetaData databaseMetaData = connect.getMetaData();
                //seta o nome do produto
                jlNomeProduto.setText("Nome Produto: " + databaseMetaData.getDatabaseProductName());
                jlVersão.setText("Versão Produto: " + databaseMetaData.getDatabaseProductVersion());
                //Display informações sobre JDBC Driver
                jlDriverNome.setText("JDBC Driver name: " + databaseMetaData.getDriverName());
                jlVersaoDriver.setText("JDBC Driver version: " + databaseMetaData.getDriverVersion());

                try {
                    ResultSet rs = connect.getMetaData().getCatalogs();
                    root = new DefaultMutableTreeNode("Bando de Dados");
                    while (rs.next()) {
                        //System.out.println("Banco: " + rs.getString("TABLE_CAT"));
                        banco = new DefaultMutableTreeNode(rs.getString("TABLE_CAT"));
                        root.add(banco);
                        // Declaração inicial
                        statement = connect.createStatement();
                        // Definir DB atual
                        // !!! AVISO PARA CITAÇÕES - é backticks (`), não" e não '!!!
                        // !!! BACKTICKS SÃO REQUERIDOS SE O NOME DA TABELA CONTÉM ESPAÇOS !!!
                        statement.executeQuery("USE `" + rs.getString("TABLE_CAT") + "`;");
                        //Obter todas as tabelas do DB e colocá-las na combobox ...
                        statement = connect.createStatement();
                        // !!! e agora CITA, NÃO BACKTICKS !!!
                        // !!! porque "jComboBox1.getSelectedItem (). toString ()" é o valor da coluna,
                        // não coluna / tabela / nome db !!!
                        /* ResultSet rs = statement.executeQuery ("SELECT TABLE_NAME FROM information_schema.TABLES" +
                         "WHERE TABLE_SCHEMA = '" + jComboBox1.getSelectedItem (). ToString () + "'"); */
                        ResultSet r = statement.executeQuery("SHOW TABLES;");
                        // Adicionar tabela de tabelas para combobox
                        while (r.next()) {
                            //jComboBox2.addItem(rs.getString(1));
                            //System.out.println("Tabelas: " + r.getString(1));
                            tabela = new DefaultMutableTreeNode(r.getString(1));
                            banco.add(tabela);
                            System.out.println("tabela: " + r.getString(1));

                            if (r.getString(1) != null || r.getString(1) != "") {
                                statement = connect.createStatement();
                                ResultSet c = statement.executeQuery("SELECT * FROM " + r.getString(1));
                                ResultSetMetaData meta = c.getMetaData();
                                for (int x = 1; x <= meta.getColumnCount(); x++) {
                                    //pega os nomes das colunas
                                    colunas = new DefaultMutableTreeNode(meta.getColumnName(x).toLowerCase());
                                    tabela.add(colunas);
                                    //pega os dados da coluna
                                    ///tipo
                                    infocolunas = new DefaultMutableTreeNode(meta.getColumnTypeName(x).toLowerCase() + "(" + meta.getColumnDisplaySize(x) + ")");
                                    colunas.add(infocolunas);
                                    //nome coluna
                                    //infocolunas = new DefaultMutableTreeNode( meta.getColumnLabel(x));
                                    //colunas.add(infocolunas);
                                    //nome coluna em maiusculo
//                                    infocolunas = new DefaultMutableTreeNode(meta.getColumnTypeName(x));
//                                    colunas.add(infocolunas);
                                    //se o campo e auto incremente 
                                    infocolunas = new DefaultMutableTreeNode(meta.isAutoIncrement(x));
                                    colunas.add(infocolunas);
                                    //pega o nome do banco ao qual o campo pertence
                                    infocolunas = new DefaultMutableTreeNode(meta.getCatalogName(x));
                                    colunas.add(infocolunas);
                                }
                            }
                        }
                        // Declaração fechada
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("ERRO BANCO: " + e);
                }
            } catch (Exception ex) {
                System.out.println("ERRO TABELA: " + ex);
            }

            //Árvore é adicionada ao modelo
            DefaultTreeModel modelo = new DefaultTreeModel(root);
            return modelo;
        } catch (Exception e) {
            System.out.println("Error : " + e);

        }

        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        shapeTabbedPane1 = new swing.xp.ShapeTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jlNomeProduto = new javax.swing.JLabel();
        jlVersão = new javax.swing.JLabel();
        jlDriverNome = new javax.swing.JLabel();
        jlVersaoDriver = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jeResultado = new javax.swing.JEditorPane();
        jPanel6 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane3.setDividerLocation(300);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setDividerLocation(155);

        jPanel5.setBackground(new java.awt.Color(51, 51, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel5);

        shapeTabbedPane1.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(153, 255, 102));

        jlNomeProduto.setText("Nome Produto");

        jlVersão.setText("Versão");

        jlDriverNome.setText("Nome Driver");

        jlVersaoDriver.setText("Versão do Driver");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlVersaoDriver)
                    .addComponent(jlDriverNome)
                    .addComponent(jlVersão)
                    .addComponent(jlNomeProduto))
                .addContainerGap(466, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNomeProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlVersão)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDriverNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlVersaoDriver)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        shapeTabbedPane1.addTab("Servidor 127.0.0.1", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );

        shapeTabbedPane1.addTab("Banco : ", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        shapeTabbedPane1.addTab("Consulta", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(shapeTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(shapeTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel1);

        jSplitPane3.setLeftComponent(jSplitPane1);

        jPanel7.setBackground(new java.awt.Color(255, 51, 51));

        jeResultado.setForeground(new java.awt.Color(66, 246, 36));
        jScrollPane3.setViewportView(jeResultado);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );

        jSplitPane3.setRightComponent(jPanel7);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        jToolBar1.setRollover(true);

        jMenu1.setText("Arquivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        if (connect != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
            DefaultMutableTreeNode nodoError = new DefaultMutableTreeNode("Desconectado");
            try {
                statement = connect.createStatement();
                ResultSet c = statement.executeQuery("SELECT * FROM " + node);
                ResultSetMetaData meta = c.getMetaData();

                // for changing column and row model
//                DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.setNumRows(0);
                // clear existing columns 
//                tm.setColumnCount(0);
                for (int x = 1; x <= meta.getColumnCount(); x++) {
                    //pega os nomes das colunas
                    modelo.addRow(new Object[]{meta.getColumnName(x).toLowerCase(), meta.getColumnTypeName(x).toLowerCase() + "(" + meta.getColumnDisplaySize(x) + ")"});

                }
                // Declaração fechada
                statement.close();

            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, "Error: " + ex);
                System.out.println("Error clik: " + ex);
            }
        }


    }//GEN-LAST:event_jTree1MouseClicked

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
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JEditorPane jeResultado;
    private javax.swing.JLabel jlDriverNome;
    private javax.swing.JLabel jlNomeProduto;
    private javax.swing.JLabel jlVersaoDriver;
    private javax.swing.JLabel jlVersão;
    private swing.xp.ShapeTabbedPane shapeTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
