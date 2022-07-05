package com.bit.derby;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEjemplo {

    private static final String INSERT_USERS_SQL = "INSERT INTO EMPLEADO" +
            "  (CEDULA, NOMBRE, SUELDO) VALUES " +
            " (?, ?, ?);";

    public static void main(String[] args) {

        JdbcEjemplo ejemplo = new JdbcEjemplo();
        ejemplo.insertar();
        ejemplo.consultar();


    }

    void   consultar  (){
        List<Empleado> result = new ArrayList<>();

        String SQL_SELECT = "Select * from EMPLEADO";

        // auto close connection and preparedStatement
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/usuario", "postgres", "elite");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {


            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                long cedula = resultSet.getLong("CEDULA");
                String nombre = resultSet.getString("NOMBRE");
                BigDecimal sueldo = resultSet.getBigDecimal("SUELDO");


                Empleado obj = new Empleado();
                obj.setCedula(cedula);
                obj.setNombre(nombre);
                obj.setSueldo(sueldo);


                result.add(obj);

            }
            result.forEach(x -> System.out.println(x));


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void insertar(){


        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/usuario", "postgres", "elite");
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Tony");
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(5));

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Matias");
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(10));

            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "Eduardo");
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(15));

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
