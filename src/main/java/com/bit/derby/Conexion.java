/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bit.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

    private static final String DATABASE = "usuario";
    private static final String URL = "jdbc:postgresql://localhost:5432/" + DATABASE;
    private static final String USER = "postgres";
    private static final String PASS = "elite";
    private static Connection connection;

    public Connection getConexion() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASS);
            } catch (ClassNotFoundException ex) {
                System.err.println("Error " + ex);
            }
        }
        return connection;
    }

}
