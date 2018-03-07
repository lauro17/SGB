//package Conexao;
//
//import static View.ViewPrincipal.jcDriver;
//import static View.ViewPrincipal.jtBanco;
//import static View.ViewPrincipal.jtHost;
//import static View.ViewPrincipal.jtSenha;
//import static View.ViewPrincipal.jtUsauraio;
//import java.sql.Connection;
//import java.sql.DriverManager;
//
///** 
// *
// * @author Analise em Curso
// */
//public class ModuloConexao {
//    //metodo de conexão
//
//    public static Connection conector() {
//        //variavel de conexao que recebe a string de conexao    
//        java.sql.Connection conexao = null;
//        //carega o driver de conexao 
//        String driver = jcDriver.getSelectedItem().toString();
//        //banco
//        String banco = jtBanco.getText();
//        //caminho e nome do banco
//        String host = jtHost.getText();
//        //usuario
//        String user = jtUsauraio.getText();
//        // senha do banco
//        String password = jtSenha.getText();
//        //estabelecendo a conexao com o banco de dados
//        try {
//            Class.forName(driver);
//            //obtem a conexao e amarzena na variavel
//            conexao = DriverManager.getConnection(host + banco, user, password);
//            //modifica a imagem se a conexao for true
//            //lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/conectado.png")));
//
//            ///retorna a conexao
//            return conexao;
//
//        } catch (Exception e) {
//            //System.out.println(e);          
//            //modifica a imagem se a conexao for false
//            //lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/desconectado.png")));
//            return null;
//        }
//
//    }
//
//}
