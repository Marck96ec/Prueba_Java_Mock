/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bit.derby;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @Captor
    ArgumentCaptor captor;

    @Mock
    List<Empleado> result = new ArrayList<>();

    @InjectMocks
    Empleado empleado;

    @Spy
    List<Object> lista2;
    
    @Mock
    Connection con;

    @Mock
    static Statement stmt;

    {
        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/usuario", "postgres", "elite");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    private static final String INSERT_USERS_SQL = "INSERT INTO EMPLEADO" +
            "  (CEDULA, NOMBRE, SUELDO) VALUES " +
            " (?, ?, ?);";
    
    @Test
    public void pruebaCon() throws SQLException{
        System.out.println("Conexion_jdbc?  " + con.getClass().getName());
        Mockito.when(con.getSchema()).thenReturn("PRUEBA CONEXION EXITOSA");
        Mockito.when(con.createStatement()).thenReturn(null);
        System.out.println("Esquema  " + con.getSchema());
        con.createStatement();
        assertEquals("PRUEBA CONEXION EXITOSA", con.getSchema());



        System.out.println("Connected " + stmt.getClass().getName());
    }

    @Test
    public void testSpyinsert() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/usuario", "postgres", "elite");
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Tony");
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(5));

            preparedStatement.executeUpdate();

            assertFalse("Existe estatement?  ", preparedStatement == null);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }


    @Test
    public void testSpy() {
        JdbcEjemplo ejemplo = new JdbcEjemplo();
        ejemplo.consultar();


        System.out.println("Empleado?   " + ejemplo.getClass().getName());

//        assertEquals("Tony", result.getClass().getName());
    }


    
}
