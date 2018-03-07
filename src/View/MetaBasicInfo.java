/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Analise em Curso
 */
public class MetaBasicInfo {

    Connection myConn = null;

    public static void main(String[] args) throws SQLException {
        try {

            // 1. Get a connection to database
            //myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/code0", "root", "");
            
        } catch (Exception e) {
        }
    }

}
