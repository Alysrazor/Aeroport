/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport.MySQL;

import aeroport.AvionPublico;
import aeroport.Company;
import aeroport.Vuelo;

import aeroport.crypto.CryptSHA1;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import static java.lang.System.out;

import java.sql.SQLException;

import java.time.LocalDate;


/**
 *
 * @author Sergio Capilla Cabadés
 * @fecha 27 may 2023 13:02:55
 * @company Ciclo Superior de Informática
 */

public class MySQL 
{
    private static final String l_UserString = "Aeroport";
    private static final String l_PassString = "aeroport";
    private static final String l_PortString = "3312";
    private static final String l_ConnectionString = "jdbc:mysql://localhost:".concat(l_PortString).concat("/").concat(l_PassString);
    
    /**
     * Compruba y notifica al usuario si se ha podido establecer correctamente conexión con la base de datos.
     */
    public void TestConnection()
    {
        try(Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString))
        {
            out.println("Se ha conectado satisfactoriamente con la base de datos.");
        }
        catch(SQLException e)
        {
            out.println(e.getSQLState());
        }
    }
    
    /*public boolean AddNewClient(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_Usuario, String p_Password, String p_Email)
    {
        String p_Query = "INSERT INTO `cliente` VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        
        try(Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString);
                PreparedStatement p_Stmt = p_Conn.prepareStatement(p_Query))
        {
            p_Stmt.setString(1, p_DNI);
            p_Stmt.setString(2, p_Nombre);
            p_Stmt.setString(3, p_Apellidos);
            p_Stmt.setDate(4, Date.valueOf(p_FechaNac));
            p_Stmt.setString(5, p_Usuario);
            p_Stmt.setString(6, CryptSHA1.EncryptPassword(p_Password));
            p_Stmt.setString(7, p_Email);
            p_Stmt.setDate(8, Date.valueOf(LocalDate.now()));
            
            p_Stmt.execute();
        }
        catch(SQLException e)
        {
            out.println(e.getErrorCode());
            return false;
        }
        
        return true;
    }*/
}
