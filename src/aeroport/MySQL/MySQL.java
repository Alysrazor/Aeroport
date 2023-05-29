/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.MySQL;

import aeroport.Asiento;
import aeroport.Avion;
import aeroport.AvionCarga;
import aeroport.AvionPrivado;
import aeroport.AvionPublico;
import aeroport.Company;
import aeroport.Vuelo;

import aeroport.persona.Cliente;
import aeroport.persona.Piloto;
import aeroport.persona.Reserva;

import aeroport.crypto.CryptSHA1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;

import static java.lang.System.out;

import java.sql.SQLException;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.TreeSet;

import javax.lang.model.util.ElementScanner6;

/**
 *
 * @author Sergio Capilla Cabadés
 * @my.fecha 27 may 2023 13:02:55
 * @my.company Ciclo Superior de Informática
 */
public class MySQL 
{

    private static final String l_UserString = "Aeroport";
    private static final String l_PassString = "aeroport";
    private static final String l_PortString = "3312";
    private static final String l_ConnectionString = "jdbc:mysql://localhost:".concat(l_PortString).concat("/").concat(l_PassString);

    /**
     * Compruba y notifica al usuario si se ha podido establecer correctamente
     * conexión con la base de datos.
     */
    public void TestConnection() 
    {
        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString)) {
            out.println("Se ha conectado satisfactoriamente con la base de datos.");
        } catch (SQLException e) {
            out.println(e.getSQLState());
        }
    }

    // INSERTAR REGISTROS EN BASE DE DATOS
    /**
     * Añade un nuevo {@link Cliente} a la base de datos.
     *
     * <p>
     * No se podrá añadir un nuevo {@link Cliente} si el nombre de usuario y / o
     * E-Mail ya existen en la base de datos.
     * </p>
     * <p>
     * Esta consulta solo crea al {@link Cliente} sin {@link Reserva} ni nada.
     * Para hacer {@link Reserva}, el {@link Cliente} deberá conectarse en el
     * programa principal, y hacer las operaciones necesarias desde allí.
     * </p>
     *
     * @param p_DNI El DNI del {@link Cliente}
     * @param p_Nombre El nombre del {@link Cliente}
     * @param p_Apellidos Los apellidos del {@link Cliente}
     * @param p_FechaNac La Fecha de Nacimiento del {@link Cliente}
     * @param p_Usuario El nombre de usuario.
     * @param p_Password La contraseña.
     * @param p_Email El E-Mail.
     * @return <ul>
     * <li>{@code true} si se ha podido añadir correctamente el {@link Cliente}
     * a la base de datos.</li>
     * <li>{@code false} si no se ha podido añadir el {@link Cliente} a la base
     * de datos.</li>
     * </ul>
     */
    public boolean AddNewClient(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_Usuario, String p_Password, String p_Email) 
    {
        String l_Query = "INSERT INTO `cliente` VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString)) 
        {
            try (PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query)) {
                p_Conn.setAutoCommit(false);

                p_Stmt.setString(1, p_DNI);
                p_Stmt.setString(2, p_Nombre);
                p_Stmt.setString(3, p_Apellidos);
                p_Stmt.setDate(4, Date.valueOf(p_FechaNac));
                p_Stmt.setString(5, p_Usuario);
                p_Stmt.setString(6, CryptSHA1.EncryptPassword(p_Password));
                p_Stmt.setString(7, p_Email);
                p_Stmt.setDate(8, Date.valueOf(LocalDate.now()));

                p_Stmt.execute();

                p_Conn.commit();
            } catch (SQLException e) 
            {
                p_Conn.rollback();
                out.println(String.format("%d%n"
                                    + "%s%n"
                                    + "%s%n",
                                    e.getErrorCode(),
                                    e.getMessage(),
                                    e.getSQLState()));
                return false;
            }
        } catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));

            return false;
        }
        return true;
    }

    /**
     * El {@link Cliente} realiza una nueva {@link Reserva} para un determinado
     * {@link Vuelo}
     *
     * <p>
     * Para un determinado {@link Vuelo} los {@link Cliente} pueden hacer la
     * {@link Reserva} con el número de {@link Asiento} que deseen.<br>
     * Si por algún casual no hay suficientes {@link Asiento} la {@link Reserva}
     * no se podrá hacer, por lo que el {@link Cliente} tendrá que especificar
     * menos {@link Asiento}
     * </p>
     *
     * @param p_Cliente El {@link Cliente} que hace la {@link Reserva}
     * @param p_Vuelo El {@link Vuelo}
     * @param p_TipoReserva Puede ser:
     * <ul>
     * <li>Primera Clase</li>
     * <li>Negocios</li>
     * <li>Turista</li>
     * </ul>
     * @param p_Asientos Los {@link Asiento} que ha reservado.
     * @return <ul>
     * <li>{@code true} si la {@link Reserva} se ha hecho correctamente.</li>
     * <li>{@code false} si no se ha podido llevar a cabo la {@link Reserva}
     * </ul>
     * @throws IllegalArgumentException en caso de que un {@link Asiento} esté
     * ocupado.
     */
    public boolean NuevaReservaCliente(Cliente p_Cliente, Vuelo p_Vuelo, String p_TipoReserva, Asiento... p_Asientos) throws IllegalArgumentException 
    {
        String l_ReservaQuery = "INSERT INTO `reserva` VALUES(?, ?, ?, ?)";
        String l_AsientoQuery = "UPDATE `avion_asiento` SET `Cliente` = ? WHERE `Avion` = ? AND `CodAsiento` = ? AND `Cliente` IS NULL";

        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString)) 
        {
            Savepoint l_ReservaSave = p_Conn.setSavepoint("beforeReserva");

            try (PreparedStatement p_StmtReserva = p_Conn.prepareStatement(l_ReservaQuery); PreparedStatement p_StmtAsiento = p_Conn.prepareStatement(l_AsientoQuery)) 
            {
                p_Conn.setAutoCommit(false);

                p_StmtReserva.setString(1, p_Vuelo.GetIdentificador().concat(p_Cliente.GetDNI().subSequence(5, p_Cliente.GetDNI().length() - 1).toString()));
                p_StmtReserva.setString(2, p_Cliente.GetDNI());
                p_StmtReserva.setString(3, p_Vuelo.GetIdentificador());
                p_StmtReserva.setString(4, p_TipoReserva);

                p_StmtReserva.execute();

                for (Asiento p_Asiento : p_Asientos) 
                {
                    p_StmtAsiento.setString(1, p_Cliente.GetDNI());
                    p_StmtAsiento.setInt(2, p_Vuelo.GetAvion().GetNumSerie());
                    p_StmtAsiento.setString(3, p_Asiento.GetCodigoAsiento());

                    if (p_StmtAsiento.executeUpdate() == 0) 
                    {
                        p_Conn.rollback(l_ReservaSave);
                        throw new IllegalArgumentException(String.format("El asiento %s ya está ocupado, por favor vuelve ha realizar la reserva con los asientos libres.", p_Asiento.GetCodigoAsiento()));
                    }
                }

                p_Conn.commit();
            } catch (SQLException e) 
            {
                p_Conn.rollback(l_ReservaSave);
                out.println(String.format("%d%n"
                                    + "%s%n"
                                    + "%s%n",
                                    e.getErrorCode(),
                                    e.getMessage(),
                                    e.getSQLState()));
                return false;
            }
        } catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
            return false;
        }

        return true;
    }

    // CONSULTAS EN BASE DE DATOS
    /**
     * Busca en la base de datos una {@link Company}
     * 
     * <p>
     *      Pasado un nombre por parámetro, intentará buscar una {@link Company}<br>
     *      Si la encuentra, devolverá esa {@link Company} en caso contrario devolverá
     *      {@code null}
     * </p>
     * @param p_Nombre El nombre ha buscar
     * @return <ul>
     *              <li>Una {@link Company} si encuentra esa compañía.</li>
     *              <li>{@code null} ocurre algún error en la consulta..
     *          </ul>
     * @throws IllegalArgumentException si no encuentra la {@link Company}
     */
    public Company GetCompanyFromDB(String p_Nombre) throws IllegalArgumentException
    {
        String l_Query = "SELECT `Nombre`, `CodCompany` FROM `company` WHERE `Nombre` = ? LIMIT 1";

        try(Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);
            ResultSet p_Result = p_Stmt.executeQuery())
        {
            if (p_Result.next())
                return new Company(p_Result.getString(1), p_Result.getString(2));
            else
                throw new IllegalArgumentException("No se ha encontrado esa Compañía. Introduce otra.");
        }
        catch(SQLException e)
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
            return null;
        }
    }

    /**
     * Obtiene y devuelve un {@link TreeSet} de {@link Company} desde la base de datos.
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link TreeSet} vacío.
     * </p>
     * @return Un {@link TreeSet} de {@link Company}
     */
    public TreeSet<Company> GetCompaniesFromDB()
    {
        TreeSet<Company> l_Companies = new TreeSet<>();
        String l_Query = "SELECT `Nombre`, `CodCompany` FROM `company`";

        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString);
                PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);
                ResultSet p_Result = p_Stmt.executeQuery()) 
        {
            while(p_Result.next())
                l_Companies.add(new Company(p_Result.getString(1), p_Result.getString(2)));
        }
        catch(SQLException e)
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Companies;
    }

    /**
     * Obtiene y devuelve un {@link HashSet} de {@link Avion} desde la base de datos.
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link HashSet} vacío.
     * </p>
     * @return Un {@link HashSet} de {@link Avion}
     */
    public HashSet<Avion> GetAvionesFromDB() 
    {
        HashSet<Avion> l_Aviones = new HashSet<>();
        Asiento[][] l_Asientos = null;
        Piloto[] l_Pilotos = new Piloto[2];

        String l_QueryAvion = "SELECT `NumSerie`, `Nombre`, `Company`, `Tipo` FROM `avion`";
        String l_Tipo;

        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString);
                PreparedStatement p_StmtAvion = p_Conn.prepareStatement(l_QueryAvion);
                ResultSet p_ResultAvion = p_StmtAvion.executeQuery()) 
        {
            while (p_ResultAvion.next())
            {
                // Obtienemos los Asientos
                l_Tipo = p_ResultAvion.getString(4);
                String l_QueryAvionAsiento = "SELECT `CodAsiento` FROM `avion_asiento` WHERE `Avion` = ?";
                
                try(PreparedStatement p_StmtAvionAsiento = p_Conn.prepareStatement(l_QueryAvionAsiento))
                {
                    p_StmtAvionAsiento.setInt(1, p_ResultAvion.getInt(1));

                    try(ResultSet p_Result = p_StmtAvionAsiento.executeQuery())
                    {

                        if (p_ResultAvion.getString(4).equals("Público"))                        
                            l_Asientos = new Asiento[10][6];
                        else if (p_ResultAvion.getString(4).equals("Privado"))
                            l_Asientos = new Asiento[6][4];
                                
                        for (Asiento[] p_AsientoF : l_Asientos)
                        {
                            for (int l_AsientoC = 0; l_AsientoC < p_AsientoF.length && p_Result.next(); l_AsientoC++)
                                p_AsientoF[l_AsientoC] = new Asiento(p_Result.getString(1));
                        } 
                    }
                }
                // Obtenemos los Pilotos
                String l_QueryAvionPiloto = "SELECT `e`.`DNI`, `e`.`Nombre`, `e`.`Apellidos`, `e`.`Fecha_Nacimiento`, `e`.`CodEmpleado`, `a`.`Company` FROM `empleado` AS `e` INNER JOIN `avion` AS `a` INNER JOIN `avion_piloto` AS `ap`"
                                            + "ON `a`.`NumSerie` = ? AND `a`.`NumSerie` = `ap`.`Avion` AND `e`.`Tipo` = ? AND `e`.`CodEmpleado` = `ap`.`Piloto`";

                try(PreparedStatement p_StmtAvionPiloto = p_Conn.prepareStatement(l_QueryAvionPiloto))
                {
                    p_StmtAvionPiloto.setInt(1, p_ResultAvion.getInt(1));
                    p_StmtAvionPiloto.setString(2, "Piloto");

                    try(ResultSet p_Result = p_StmtAvionPiloto.executeQuery())
                    {
                        for (Piloto p_Piloto : l_Pilotos)
                        {
                            if (p_Result.next())
                                p_Piloto = new Piloto(p_Result.getString(1), p_Result.getString(2), p_Result.getString(3), p_Result.getDate(4).toLocalDate(), p_Result.getString(5), GetCompanyFromDB(p_Result.getString(6)));
                        }
                    }
                }

                if (p_ResultAvion.getString(4).equals("Público"))
                    l_Aviones.add(new AvionPublico(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone()));
                else if (p_ResultAvion.getString(4).equals("Privado"))
                    l_Aviones.add(new AvionPrivado(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone()));
                else
                    l_Aviones.add(new AvionCarga(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone()));
            }
        }
        catch(SQLException e)
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Aviones;
    }

    /**
     * Obtiene el número de {@link Asiento} libres de un {@link Avion}
     *
     * <p>
     * En la consulta de la base de datos, un {@link Asiento} está libre cuando
     * ningún {@link Cliente} lo ha reservado, es decir, tiene como valor
     * {@code NULL}.
     * </p>
     *
     * @param p_Avion El {@link Avion} que se desea comprobar.
     * @return <ul>
     * <li>{@code -1} si ha ocurrido un error en la consulta de la base de
     * datos.
     * <li>Devolverá un {@code int} con el número de {@link Asiento} libres.
     * </ul>
     */
    public int GetNumeroAsientosDisponibles(Avion p_Avion) 
    {
        String l_Query = "SELECT `Cliente` FROM `avion_asiento` WHERE `Avion` = ? AND `Cliente` IS NULL";
        int l_NumAsientos = 0;

        try (Connection p_Conn = DriverManager.getConnection(l_ConnectionString, l_UserString, l_PassString)) 
        {
            try (PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);) 
            {
                p_Stmt.setInt(1, p_Avion.GetNumSerie());

                try (ResultSet l_Result = p_Stmt.executeQuery()) 
                {
                    while (l_Result.next()) {
                        l_NumAsientos++;
                    }
                }
            }

            return l_NumAsientos;
        } catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return -1;
    }
}
