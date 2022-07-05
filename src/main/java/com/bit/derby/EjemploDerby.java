package com.bit.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

public class EjemploDerby {

    private final String framework = "embedded";
    private final String protocol = "jdbc:derby:";

    public static void main(String[] args) {




        EjemploDerby test = new EjemploDerby();
//        test.crear();
        test.consultar();
        System.out.println("OK finaliza correctamente");


    }

    void consultar() {
        System.out.println("Base de datos simple iniciada en modo " + framework);
        Connection conn = null;
        Statement statement = null;
        ResultSet result = null;
        String query = "SELECT ZAP_CODIGO, ZAP_NOMBRE, ZAP_MODELO, ZAP_GENERO, ZAP_COLOR, ZAP_TALLA, ZAP_STOCK, ZAP_PRECIO FROM ZAPATO";
        try {
            Properties props = new Properties();
            props.put("user", "bit");
            props.put("password", "java2022");
            String dbName = "CURSO_JAVA";
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            result = statement.executeQuery(query);
            System.out.println("________________________________________________________________________________________________________________________");
            while (result.next()) {
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    System.out.print(result.getMetaData().getColumnName(i) + ": " + result.getObject(i) + "  \t| ");
                }
                System.out.println("\n________________________________________________________________________________________________________________________");
            }

            conn.commit();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                    conn.close();
                }
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    void inserts() {
        System.out.println("Base de datos simple iniciada en modo " + framework);
        Connection conn = null;
        Statement statement = null;
        String insert = "INSERT INTO ZAPATO(ZAP_CODIGO, ZAP_NOMBRE, ZAP_MODELO, ZAP_GENERO, ZAP_COLOR, ZAP_TALLA, ZAP_STOCK, ZAP_PRECIO) "
                + "VALUES('ZAP-0%s', '%s', '%s', '%s', '%s', '%s', %s, %s)";
        try {
            Properties props = new Properties();
            props.put("user", "bit");
            props.put("password", "java2022");
            String dbName = "CURSO_JAVA";
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
            for (int i = 0; i < 10; i++) {
                conn.setAutoCommit(false);
                statement = conn.createStatement();
                statement.execute(String.format(insert, i, "ZAPATO CURSO " + i, i % 2 == 0 ? "DEPORTIVO " + i : "CASUAL " + i,
                        i % 2 == 0 ? "M" : "F", i % 2 == 0 ? "BLANCO" : "NEGRO", (i / 2.0 + 6.5), i * 5 + 20, 20.0 + i));

            }
            System.out.println("INSERTS EN ZAPATO");
            conn.commit();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                    conn.close();
                }
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    void crear() {
        System.out.println("Base de datos simple iniciada en modo " + framework);

        Connection conn = null;
        Statement statement = null;
        try {
            Properties props = new Properties();
            props.put("user", "bit");
            props.put("password", "java2022");
            String dbName = "CURSO_JAVA";
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);

            System.out.println("Conectado y creada la base de datos " + dbName);

            conn.setAutoCommit(false);
            statement = conn.createStatement();
            statement.execute("CREATE TABLE ZAPATO (\n"
                    + " ZAP_CODIGO VARCHAR(10) NOT NULL,\n"
                    + " ZAP_NOMBRE VARCHAR(100) NOT NULL,\n"
                    + " ZAP_MODELO VARCHAR(100) NOT NULL,\n"
                    + " ZAP_GENERO VARCHAR(2) NOT NULL,\n"
                    + " ZAP_COLOR VARCHAR(50) NOT NULL,\n"
                    + " ZAP_TALLA VARCHAR(20) NOT NULL,\n"
                    + " ZAP_STOCK INT NOT NULL,\n"
                    + " ZAP_PRECIO DOUBLE PRECISION,\n"
                    + " PRIMARY KEY (ZAP_CODIGO)\n"
                    + ")");
            System.out.println("Creada tabla ZAPATO");

            conn.commit();
            System.out.println("Ejecutada transaccion");
            //Cierra las conexion            
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                    conn.close();
                }
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    public static void printSQLException(SQLException e) {
        System.err.println("\n----- SQLException -----");
        System.err.println("  Estado:  " + e.getSQLState());
        System.err.println("  Codigo de error: " + e.getErrorCode());
        System.err.println("  Mesnsaje:    " + e.getMessage());
    }

}
