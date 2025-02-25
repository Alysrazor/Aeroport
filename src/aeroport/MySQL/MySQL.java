/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.MySQL;

import aeroport.Aeroport;
import aeroport.Asiento;
import aeroport.Avion;
import aeroport.AvionCarga;
import aeroport.AvionPrivado;
import aeroport.AvionPublico;
import aeroport.Company;
import aeroport.Pista;
import aeroport.PistaPrivada;
import aeroport.PistaPublica;
import aeroport.PuertaEmbarque;
import aeroport.Terminal;
import aeroport.Vuelo;

import aeroport.persona.Cliente;
import aeroport.persona.Equipaje;
import aeroport.persona.Piloto;
import aeroport.persona.Reserva;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

import static java.lang.System.out;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.TreeSet;


/**
 * Clase MySQL que gestiona el {@link Aeroport}
 * 
 * <p>
 *      Para poder utilizar esta clase se debe contar con MySQL y 
 *      la base de datos de este programa.<br><br>
 *      Si no se cuenta con la base de datos es imposible importar
 *      los datos al programa y hacer las operaciones necesarias.
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 27 may 2023 13:02:55
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class MySQL 
{
    private static final int TERMINAL = 1;
    private static final String USER = "Aeroport";
    private static final String PASSWORD = "aeroport";
    private static final String PORT = "3312";
    private static final String CONNECTION = "jdbc:mysql://localhost:".concat(PORT).concat("/").concat(PASSWORD);
    private static final String COLOR_VERDE = "\u001B[32m";
    private static final String COLOR_ROJO = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";

    /**
     * Compruba y notifica al usuario si se ha podido establecer correctamente
     * conexión con la base de datos.<br>
     * Este método es para fines de prueba solo.
     */
    public void TestConnection() 
    {
        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
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
     *      No se podrá añadir un nuevo {@link Cliente} si el DNI, nombre de usuario y / o
     *      E-Mail ya existen en la base de datos.
     * </p>
     * <p>
     *      Esta consulta solo crea al {@link Cliente} sin {@link Reserva} ni nada.
     *      Para hacer {@link Reserva}, el {@link Cliente} deberá conectarse en el
     *      programa principal, y hacer las operaciones necesarias desde allí.
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
     *          <li>{@code true} si se ha podido añadir correctamente el {@link Cliente} a la base de datos.</li>
     *          <li>{@code false} si no se ha podido añadir el {@link Cliente} a la base de datos.</li>
     *        </ul>
     */
    public boolean AddNewClient(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_Usuario, String p_Password, String p_Email) 
    {
        String l_Query = "INSERT INTO `cliente` VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) 
        {
            try (PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query)) 
            {
                p_Conn.setAutoCommit(false);

                p_Stmt.setString(1, p_DNI);
                p_Stmt.setString(2, p_Nombre);
                p_Stmt.setString(3, p_Apellidos);
                p_Stmt.setDate(4, Date.valueOf(p_FechaNac));
                p_Stmt.setString(5, p_Usuario);
                p_Stmt.setString(6, p_Password);
                p_Stmt.setString(7, p_Email);
                p_Stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));

                p_Stmt.execute();

                p_Conn.commit();
            } 
            catch (SQLException e) 
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
        } 
        catch (SQLException e) 
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
     *      Para un determinado {@link Vuelo} los {@link Cliente} pueden hacer la
     *      {@link Reserva} con el número de {@link Asiento} que deseen.<br>
     *      Si por algún casual no hay suficientes {@link Asiento} la {@link Reserva}
     *      no se podrá hacer, por lo que el {@link Cliente} tendrá que especificar
     *      menos {@link Asiento}
     * </p>
     *
     * @param p_Cliente El {@link Cliente} que hace la {@link Reserva}
     * @param p_Vuelo El {@link Vuelo}
     * @param p_TipoReserva Puede ser:
     * <ul>
     *      <li>Primera Clase</li>
     *      <li>Negocios</li>
     *      <li>Turista</li>
     * </ul>
     * @param p_Equipaje El equipaje del {@link Cliente}
     * @param p_Asientos Los {@link Asiento} que ha reservado.
     * @return <ul>
     *              <li>{@code true} si la {@link Reserva} se ha hecho correctamente.</li>
     *              <li>{@code false} si no se ha podido llevar a cabo la {@link Reserva}
     *          </ul>
     * @throws IllegalArgumentException en caso de que un {@link Asiento} esté ocupado.
     */
    public boolean NuevaReservaCliente(Cliente p_Cliente, Vuelo p_Vuelo, String p_TipoReserva, HashSet<Equipaje> p_Equipaje, HashSet<Asiento> p_Asientos) throws IllegalArgumentException 
    {        
        String l_ReservaQuery = "INSERT INTO `reserva`(`CodReserva`, `Cliente`, `Vuelo`, `Tipo`) VALUES(LEFT(SHA1(?), 10), ?, ?, ?)";
        String l_AsientoQuery = "UPDATE `avion_asiento` SET `Cliente` = ? WHERE `Avion` = ? AND `CodAsiento` = ? AND `Cliente` IS NULL";
        String l_EquipajeQuery = "INSERT INTO `equipaje` VALUES(LEFT(SHA1(?), 10), ?, ?, ?)";

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) 
        {
            try (PreparedStatement p_StmtReserva = p_Conn.prepareStatement(l_ReservaQuery); 
                    PreparedStatement p_StmtEquipaje = p_Conn.prepareStatement(l_EquipajeQuery);
                    PreparedStatement p_StmtAsiento = p_Conn.prepareStatement(l_AsientoQuery)) 
            {
                p_Conn.setAutoCommit(false);

                p_StmtReserva.setString(1, LocalDateTime.now().toString().concat(p_Cliente.GetDNI().substring(0, 9)));
                p_StmtReserva.setString(2, p_Cliente.GetDNI());
                p_StmtReserva.setString(3, p_Vuelo.GetIdentificador());
                p_StmtReserva.setString(4, p_TipoReserva);

                p_StmtReserva.execute();

                for (Equipaje p_Equip : p_Equipaje)
                {
                    p_StmtEquipaje.setString(1, LocalDateTime.now().toString().concat(p_Cliente.GetDNI().substring(0, 9)));
                    p_StmtEquipaje.setDouble(2, p_Equip.GetPeso());
                    p_StmtEquipaje.setString(3, p_Equip.GetTipo().GetNombre());
                    p_StmtEquipaje.setString(4, p_Cliente.GetDNI());

                    p_StmtEquipaje.execute();
                }

                for (Asiento p_Asiento : p_Asientos) 
                {
                    p_StmtAsiento.setString(1, p_Cliente.GetDNI());
                    p_StmtAsiento.setInt(2, p_Vuelo.GetAvion().GetNumSerie());
                    p_StmtAsiento.setString(3, p_Asiento.GetCodigoAsiento());

                    if (p_StmtAsiento.executeUpdate() == 0) 
                    {
                        p_Conn.rollback();
                        throw new IllegalArgumentException(String.format("El asiento %s ya está ocupado, por favor vuelve ha realizar la reserva con los asientos libres.", p_Asiento.GetCodigoAsiento()));
                    }
                }

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

    // ACTUALIZAR REGISTROS EN BASE DE DATOS

    /**
     * Actualiza la contraseña del {@link Cliente}
     * 
     * <p>
     *      Para que el cambio de contraseña tenga efecto, las contraseñas
     *      deben ser iguales.<br>
     *      En caso de que no sean iguales, el cambio no se efectuará.
     * </p>
     * 
     * @param p_Cliente El {@link Cliente} que realiza el cambio.
     * @param p_NewPass La nueva contraseña
     * @param p_ConfirmNewPass Confirmación de la nueva contraseña.
     * @return <ul>
     *              <li>{@code true} si ha sido posible el cambio.</li>
     *              <li>{@code false} si no ha sido posible el cambio.</li>
     *          </ul>
     * @throws IllegalArgumentException si las contraseñas no coinciden.
     * @throws NoSuchElementException si no encuentra al {@link Cliente} en la base de datos.
     */
    public boolean ClientChangePassword(Cliente p_Cliente, String p_NewPass, String p_ConfirmNewPass) throws IllegalArgumentException, NoSuchElementException
    {
        String l_Query = "UPDATE `cliente` SET `password` = ? WHERE `DNI` = ?";

        if (!IsClientInDB(p_Cliente))
            throw new NoSuchElementException("No existe el cliente en la base de datos.");

        if (!p_NewPass.equals(p_ConfirmNewPass))            
            throw new IllegalArgumentException("Las contraseñas no coinciden.");            
        
        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Conn.setAutoCommit(false);

            p_Stmt.setString(1, p_NewPass);
            p_Stmt.setString(2, p_Cliente.GetDNI());

            p_Stmt.executeUpdate();

            p_Conn.commit();
        }
        catch(SQLException e)
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

    // AEROPORT
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
     *              <li>{@code null} ocurre algún error en la consulta.
     *          </ul>
     * @throws IllegalArgumentException si no encuentra la {@link Company}
     */
    public Company GetCompanyFromDB(String p_Nombre) throws IllegalArgumentException
    {
        String l_Query = "SELECT `Nombre`, `CodCompany` FROM `company` WHERE `Nombre` = ?;";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setString(1, p_Nombre);

            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                if (p_Result.next())
                    return new Company(p_Result.getString(1), p_Result.getString(2));
                else
                    throw new IllegalArgumentException("No se ha encontrado esa Compañía. Introduce otra.");
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

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
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
     * Obtiene y devuelve un {@link TreeSet} de {@link Cliente} desde la base de datos.
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link TreeSet} vacío.
     * </p>
     * @return Un {@link TreeSet} de {@link Cliente}
     */
    public TreeSet<Cliente> GetClientesFromDB()
    {
        TreeSet<Cliente> l_Clientes = new TreeSet<>();
        String l_Query = "SELECT `DNI`, `Nombre`, `Apellidos`, `Fecha_Nacimiento`, `Usuario`, `Password`, `EMail` FROM `cliente`";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);
            ResultSet p_Result = p_Stmt.executeQuery())
        {
            while(p_Result.next())
                l_Clientes.add(new Cliente(p_Result.getString(1), p_Result.getString(2), p_Result.getString(3), p_Result.getDate(4).toLocalDate(), p_Result.getString(5), p_Result.getString(6), p_Result.getString(7)));
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

        return l_Clientes;
    }

    /**
     * Busca en la base de datos un {@link Avion}
     * 
     * <p>
     *      Pasado un nombre por parámetro, intentará buscar un {@link Avion}<br>
     *      Si la encuentra, devolverá ese {@link Avion} en caso contrario devolverá
     *      {@code null}
     * </p>
     * @param p_NumSerie El número de serie
     * @return <ul>
     *              <li>Un {@link Avion} si encuentra ese avión.</li>
     *              <li>{@code null} ocurre algún error en la consulta o no ha encontrado el {@link Avion}.
     *          </ul>
     */
    public Avion GetAvionFromDB(int p_NumSerie) throws IllegalArgumentException
    {
        Asiento[][] l_Asientos = null;
        Piloto[] l_Pilotos = new Piloto[2];
        Avion l_Avion = null;
        Vuelo l_Vuelo = null;

        String l_QueryAvion = "SELECT `NumSerie`, `Nombre`, `Company`, `Tipo` FROM `avion` WHERE `NumSerie` = ?";
        String l_QueryVuelo = "SELECT `Company`, `Avion`, `Identificador`, `Origen`, `Destino`, `Hora_Salida` FROM `vuelo` WHERE `Avion` = ?";

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
                PreparedStatement p_StmtAvion = p_Conn.prepareStatement(l_QueryAvion)) 
        {
            p_StmtAvion.setInt(1, p_NumSerie);

            try(ResultSet p_ResultAvion = p_StmtAvion.executeQuery())
            {
                if (p_ResultAvion.next())
                {
                    // Obtienemos los Asientos
                    String l_QueryAvionAsiento = "SELECT `CodAsiento` FROM `avion_asiento` WHERE `Avion` = ? ORDER BY `CodAsiento` ASC";
                    
                    try(PreparedStatement p_StmtAvionAsiento = p_Conn.prepareStatement(l_QueryAvionAsiento))
                    {
                        p_StmtAvionAsiento.setInt(1, p_ResultAvion.getInt(1));
    
                        try(ResultSet p_Result = p_StmtAvionAsiento.executeQuery())
                        {
    
                            switch (p_ResultAvion.getString(4)) 
                            {
                                case "Público":
                                    l_Asientos = new Asiento[9][6];
                                    break;
                                case "Privado":
                                    l_Asientos = new Asiento[6][4];
                                    break;
                                default:
                                    l_Asientos = null;
                                    break;
                            }

                            if (l_Asientos != null)
                            {
                                for (Asiento[] p_AsientoF : l_Asientos)
                                {
                                    for (int l_AsientoC = 0; l_AsientoC < p_AsientoF.length && p_Result.next(); l_AsientoC++)
                                        p_AsientoF[l_AsientoC] = new Asiento(p_Result.getString(1));
                                } 
                            }
                            
                        }
                    }
                    // Obtenemos los Pilotos
                    String l_QueryAvionPiloto = "SELECT `e`.`DNI`, `e`.`Nombre`, `e`.`Apellidos`, `e`.`Fecha_Nacimiento`, `e`.`CodEmpleado`, `a`.`Company` FROM `empleado` AS `e` INNER JOIN `avion` AS `a` INNER JOIN `avion_piloto` AS `ap`"
                                                + "ON `a`.`NumSerie` = ? AND `a`.`NumSerie` = `ap`.`Avion` AND `e`.`Tipo` = ? AND `e`.`CodEmpleado` = `ap`.`Piloto`";
    
                    try(PreparedStatement p_StmtAvionPiloto = p_Conn.prepareStatement(l_QueryAvionPiloto))
                    {
                        p_StmtAvionPiloto.setInt(1, p_NumSerie);
                        p_StmtAvionPiloto.setString(2, "Piloto");
    
                        try(ResultSet p_Result = p_StmtAvionPiloto.executeQuery())
                        {
                            for (int l_Itr = 0; l_Itr < l_Pilotos.length; l_Itr++)
                            {
                                if (p_Result.next())
                                    l_Pilotos[l_Itr] = new Piloto(p_Result.getString(1), p_Result.getString(2), p_Result.getString(3), p_Result.getDate(4).toLocalDate(), p_Result.getString(5), GetCompanyFromDB(p_Result.getString(6)));
                            }
                        }
                    }
    
                    switch (p_ResultAvion.getString(4)) 
                    {
                        case "Público":
                            l_Avion = new AvionPublico(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone());
                            try(PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo))
                            {
                                p_StmtVuelo.setInt(1, p_NumSerie);
                                try(ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
                                {
                                    if (p_ResultVuelo.next())
                                        l_Vuelo = new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), l_Avion, p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime());
                                }
                            }
                            l_Avion.SetVuelo(l_Vuelo);
                            break;
                        case "Privado":
                            l_Avion = new AvionPrivado(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone());
                            
                            try(PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo))
                            {
                                p_StmtVuelo.setInt(1, p_NumSerie);
                                try(ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
                                {
                                    if (p_ResultVuelo.next())
                                        l_Vuelo = new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), l_Avion, p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime());
                                }
                            }
                            
                            l_Avion.SetVuelo(l_Vuelo);
                            break;
                        default:
                            l_Avion = new AvionCarga(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone());
                    }
                }
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

        return l_Avion;
    }

    /**
     * Obtiene y devuelve un {@link TreeSet} de {@link Avion} desde la base de datos.
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link TreeSet} vacío.
     * </p>
     * @return Un {@link TreeSet} de {@link Avion}
     */
    public TreeSet<Avion> GetAvionesFromDB() 
    {
        TreeSet<Avion> l_Aviones = new TreeSet<>();
        Asiento[][] l_Asientos = null;
        Piloto[] l_Pilotos = new Piloto[2];
        Avion l_Avion = null;
        Vuelo l_Vuelo = null;

        String l_QueryAvion = "SELECT `NumSerie`, `Nombre`, `Company`, `Tipo` FROM `avion`";
        String l_QueryVuelo = "SELECT `Company`, `Avion`, `Identificador`, `Origen`, `Destino`, `Hora_Salida` FROM `vuelo` WHERE `Avion` = ?";

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
                PreparedStatement p_StmtAvion = p_Conn.prepareStatement(l_QueryAvion);
                ResultSet p_ResultAvion = p_StmtAvion.executeQuery()) 
        {
            while (p_ResultAvion.next())
            {
                // Obtienemos los Asientos
                String l_QueryAvionAsiento = "SELECT `CodAsiento` FROM `avion_asiento` WHERE `Avion` = ? ORDER BY `CodAsiento` ASC";
                
                try(PreparedStatement p_StmtAvionAsiento = p_Conn.prepareStatement(l_QueryAvionAsiento))
                {
                    p_StmtAvionAsiento.setInt(1, p_ResultAvion.getInt(1));

                    try(ResultSet p_Result = p_StmtAvionAsiento.executeQuery())
                    {

                        switch (p_ResultAvion.getString(4)) 
                        {
                            case "Público":
                                l_Asientos = new Asiento[9][6];
                                break;
                            case "Privado":
                                l_Asientos = new Asiento[6][4];
                                break;
                            default:
                                l_Asientos = null;
                                break;
                        }
                               
                        if (l_Asientos != null)
                        {
                            for (Asiento[] p_AsientoF : l_Asientos)
                            {
                                for (int l_AsientoC = 0; l_AsientoC < p_AsientoF.length && p_Result.next(); l_AsientoC++)
                                    p_AsientoF[l_AsientoC] = new Asiento(p_Result.getString(1));
                            } 
                        }
                        
                    }
                }
                // Obtenemos los Pilotos
                String l_QueryAvionPiloto = "SELECT `e`.`DNI`, `e`.`Nombre`, `e`.`Apellidos`, `e`.`Fecha_Nacimiento`, `e`.`CodEmpleado`, `a`.`Company` FROM `empleado` AS `e` INNER JOIN `avion` AS `a` INNER JOIN `avion_piloto` AS `ap`"
                                            + "ON `a`.`NumSerie` = ? AND `a`.`NumSerie` = `ap`.`Avion` AND `e`.`Tipo` = ? AND `e`.`CodEmpleado` = `ap`.`Piloto` ORDER BY `ap`.`Avion`";

                try(PreparedStatement p_StmtAvionPiloto = p_Conn.prepareStatement(l_QueryAvionPiloto))
                {
                    p_StmtAvionPiloto.setInt(1, p_ResultAvion.getInt(1));
                    p_StmtAvionPiloto.setString(2, "Piloto");

                    try(ResultSet p_Result = p_StmtAvionPiloto.executeQuery())
                    {
                           for (int l_Itr = 0; l_Itr < l_Pilotos.length; l_Itr++)
                           {
                                if (p_Result.next())
                                    l_Pilotos[l_Itr] = new Piloto(p_Result.getString(1), p_Result.getString(2), p_Result.getString(3), p_Result.getDate(4).toLocalDate(), p_Result.getString(5), GetCompanyFromDB(p_Result.getString(6)));
                           }
                    }
                }

                switch (p_ResultAvion.getString(4)) 
                {
                    case "Público":     
                        l_Avion = new AvionPublico(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone());

                        try(PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo))
                        {
                            p_StmtVuelo.setInt(1, p_ResultAvion.getInt(1));
                            try(ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
                            {
                                if (p_ResultVuelo.next())
                                    l_Vuelo = new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), l_Avion, p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime());
                            }
                        }
                            

                        l_Avion.SetVuelo(l_Vuelo);                   
                        l_Aviones.add(l_Avion);
                        break;
                    case "Privado":
                    l_Avion = new AvionPrivado(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone(), l_Asientos.clone());

                    try(PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo))
                    {
                        p_StmtVuelo.setInt(1, p_ResultAvion.getInt(1));
                        try(ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
                        {
                            if (p_ResultVuelo.next())
                                l_Vuelo = new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), l_Avion, p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime());
                        }
                    }
                    l_Avion.SetVuelo(l_Vuelo);                   
                    l_Aviones.add(l_Avion);
                        break;
                    default:
                        l_Aviones.add(new AvionCarga(p_ResultAvion.getInt(1), p_ResultAvion.getString(2), GetCompanyFromDB(p_ResultAvion.getString(3)), l_Pilotos.clone()));
                        break;
                }
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
     * @param p_NumSerie El {@link Avion} que se desea comprobar.
     * @return <ul>
     *              <li>{@code -1} si ha ocurrido un error en la consulta de la base de datos.</li>
     *              <li>Devolverá un {@code int} con el número de {@link Asiento} libres.</li>
     *          </ul>
     */
    public int GetNumeroAsientosDisponibles(int p_NumSerie) 
    {
        String l_Query = "SELECT `Cliente` FROM `avion_asiento` WHERE `Avion` = ? AND `Cliente` IS NULL";
        int l_NumAsientos = 0;

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) 
        {
            try (PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);) 
            {
                p_Stmt.setInt(1, p_NumSerie);

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

    /**
     * Busca en la base de datos las distintas {@link Pista} que hay en el {@link Aeroport}
     * 
     * <p>
     *      Para realizar la búsqueda se deberá contar con acceso a la base de datos, en caso
     *      contrario devolverá una excepción de {@link SQLException} indicando el código de error,
     *      el mensaje, y el estado de SQL.
     * </p>
     * @return Un {@link TreeSet} de tipo {@link Pista}
     */
    public TreeSet<Pista> GetPistasFromDB()
    {
        TreeSet<Pista> l_Pistas = new TreeSet<>();
        String l_Query = "SELECT `NumPista`, `Tipo` FROM `pista`";

        try (Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
                PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query);
                ResultSet p_Result = p_Stmt.executeQuery()) 
        {
            while(p_Result.next())
            {
                switch (p_Result.getString(2))
                {
                    case "Privada":
                        l_Pistas.add(new PistaPrivada(p_Result.getInt(1), "Pista Privada"));
                        break;
                    case "Pública":
                        l_Pistas.add(new PistaPublica(p_Result.getInt(1), "Pista Pública"));
                        break;
                }
            }
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Pistas;
    }

    /**
     * Busca en la base de datos una {@link Terminal}
     * 
     * <p>
     *      Pasado un nombre por parámetro, intentará buscar una {@link Terminal}<br>
     *      Si la encuentra, devolverá esa {@link Terminal} en caso contrario devolverá
     *      {@code null}
     * </p>
     * @param p_NumeroTerminal El número de la {@link Terminal}
     * @return <ul>
     *              <li>Un {@link Terminal} si encuentra esa terminal.</li>
     *              <li>{@code null} ocurre algún error en la consulta.
     *          </ul>
     */
    public Terminal GetTerminalFromDB(int p_NumeroTerminal)
    {
        String l_Query = "SELECT `NumTerminal`, `Nombre` FROM `terminal` WHERE `NumTerminal` = ?";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setInt(1, p_NumeroTerminal);

            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                if (p_Result.next())
                    return new Terminal(p_Result.getInt(1), p_Result.getString(2), GetPuertasEmbarqueFromDB(p_NumeroTerminal));
            }
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return null;
    }

    /**
     * Obtiene las {@link PuertaEmbarque} desde la base de datos
     * @param p_Terminal La {@link Terminal} donde están.
     * @return Un {@link TreeSet}
     */
    public TreeSet<PuertaEmbarque> GetPuertasEmbarqueFromDB(int p_Terminal)
    {
        TreeSet<PuertaEmbarque> l_Puertas = new TreeSet<>();
        String l_Query = "SELECT `NumPuerta`, `Terminal` FROM `puerta_embarque` WHERE `Terminal` = ?";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setInt(1, p_Terminal);

            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                while (p_Result.next())
                    l_Puertas.add(new PuertaEmbarque(p_Result.getInt(1)));
            }
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Puertas;
    }

    /**
     * Obtiene y devuelve un {@link TreeSet} de {@link Vuelo} desde la base de datos.
     * 
     * <p>
     *      Busca en la base de datos los {@link Vuelo} que llegan a nuestro {@link Aeroport}
     *      para ello el campo {@code Hora_Salida} tiene que estar {@code NULL}.<br><br>
     *      En nuestro proyecto, esto no tiene relevancia puesto que solo gestionamos los {@link Vuelo}
     *      que salen de nuestro {@link Aeroport}
     * </p>
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link TreeSet} vacío.
     * </p>
     * @return Un {@link TreeSet} de {@link Vuelo}
     */
    public TreeSet<Vuelo> GetVuelosLlegadaFromDB()
    {
        TreeSet<Vuelo> l_Vuelos = new TreeSet<>();
        String l_QueryVuelo = "SELECT `Company`, `Avion`, `Identificador`, `Origen`, `Destino`, `Hora_Llegada` FROM `vuelo` WHERE `Hora_Salida` IS NULL";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo);
            ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
        {
            while(p_ResultVuelo.next())            
                l_Vuelos.add(new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), GetAvionFromDB(p_ResultVuelo.getInt(2)), p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime()));
            
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Vuelos;
    }

    /**
     * Obtiene y devuelve un {@link TreeSet} de {@link Vuelo} desde la base de datos.
     * 
     * <p>
     *      Busca en la base de datos los {@link Vuelo} que salgan de nuestro {@link Aeroport}
     *      para ello el campo {@code Hora_Llegada} tiene que estar {@code NULL}.
     * </p>
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá un {@link TreeSet} vacío.
     * </p>
     * @return Un {@link TreeSet} de {@link Vuelo}
     */
    public TreeSet<Vuelo> GetVuelosSalidaFromDB()
    {
        TreeSet<Vuelo> l_Vuelos = new TreeSet<>();
        String l_QueryVuelo = "SELECT `Company`, `Avion`, `Identificador`, `Origen`, `Destino`, `Hora_Salida` FROM `vuelo` WHERE `Hora_Llegada` IS NULL";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo);
            ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
        {
            while(p_ResultVuelo.next())            
                l_Vuelos.add(new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), GetAvionFromDB(p_ResultVuelo.getInt(2)), p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime()));
            
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Vuelos;
    }

    /**
     * Obtiene y devuelve un {@link Vuelo} desde la base de datos.
     * 
     * <p>
     *      Busca en la base de datos el {@link Vuelo} buscando su identificador
     *      para ello el campo {@code Hora_Llegada} tiene que estar {@code NULL}.
     * </p>
     * 
     * <p>
     *      Para que la carga sea efectiva, se debe contar con acceso a la base de datos.
     *      En caso contrario no se podrá acceder y devolverá {@code null}
     * </p>
     * @param p_Identificador El identificadpor del {@link Vuelo}
     * @return Un {@link Vuelo}
     */
    public Vuelo GetVueloSalidaFromDB(String p_Identificador)
    {
        Vuelo l_Vuelo = null;
        String l_QueryVuelo = "SELECT `Company`, `Avion`, `Identificador`, `Origen`, `Destino`, `Hora_Salida` FROM `vuelo` WHERE `Identificador` = ? AND `Hora_Llegada` IS NULL";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_StmtVuelo = p_Conn.prepareStatement(l_QueryVuelo))
        {
             p_StmtVuelo.setString(1, p_Identificador);
             
            try(ResultSet p_ResultVuelo = p_StmtVuelo.executeQuery())
            {
                if (p_ResultVuelo.next())            
                    l_Vuelo = new Vuelo(GetCompanyFromDB(p_ResultVuelo.getString(1)), GetAvionFromDB(p_ResultVuelo.getInt(2)), p_ResultVuelo.getString(3), GetTerminalFromDB(TERMINAL), GetTerminalFromDB(TERMINAL).GetRandomPuertaEmbarque(), p_ResultVuelo.getString(4), p_ResultVuelo.getString(5), p_ResultVuelo.getTimestamp(6).toLocalDateTime());
            }
            
            
        }
        catch (SQLException e) 
        {
            out.println(String.format("%d%n"
                                + "%s%n"
                                + "%s%n",
                                e.getErrorCode(),
                                e.getMessage(),
                                e.getSQLState()));
        }

        return l_Vuelo;
    }

    // CLIENTE
    /**
     * Busca un {@link Cliente} en la base de datos y lo devuelve si existe.
     * @param p_DNI El DNI del {@link Cliente}
     * @return <ul>
     *                  <li>Un {@link Cliente} en caso de que lo encuentre.</li>
     *                  <li>{@code null} si no ha encontrado ningún {@link Cliente}</li>
     *              </ul>
     */
    public Cliente GetClienteFromDB(String p_DNI)
    {
        String l_Query = "SELECT `DNI`, `Nombre`, `Apellidos`, `Fecha_Nacimiento`, `Usuario`, `Password`, `EMail` FROM `cliente` WHERE `DNI` = ?";
        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setString(1, p_DNI);

            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                if (p_Result.next())
                    return new Cliente(p_Result.getString(1),
                                        p_Result.getString(2),
                                        p_Result.getString(3),
                                        p_Result.getDate(4).toLocalDate(),
                                        p_Result.getString(5),
                                        p_Result.getString(6),
                                        p_Result.getString(7));
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

        return null;
    }


    /**
     * Busca y comprueba si un {@link Cliente} existe en la base de datos.
     * @param p_Cliente {@link Cliente} ha buscar.
     * @return <ul>
     *                  <li>{@code true}si existe.</li>
     *                  <li>{@code false} si no existe.</li>
     *              </ul>
     */
    public boolean IsClientInDB(Cliente p_Cliente)
    {
        String l_Query = "SELECT `DNI` FROM `cliente` WHERE `DNI` = ?";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setString(1, p_Cliente.GetDNI());
            
            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                if (!p_Result.next())
                    return false;
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
        
        return true;
    }

    /**
     * Obtiene una cadena de hash en encriptación SHA1 desde la base de datos.
     * @param p_String La cadena ha convertir.
     * @return Un {@link String}
     */
    public String GetCodEquipajeCrypt(String p_String)
    {
        String l_Query = "SELECT LEFT(SHA1(?), 10) as CodigoEquipaje";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            PreparedStatement p_Stmt = p_Conn.prepareStatement(l_Query))
        {
            p_Stmt.setString(1, p_String);
            
            try(ResultSet p_Result = p_Stmt.executeQuery())
            {
                if (p_Result.next())
                    return p_Result.getString("CodigoEquipaje");
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

        return Integer.toString(p_String.hashCode());
    }

    // METODOS ESPECIALES NO SE RECOMIENDA SU INVOCACION
    /**
     * Este método es para las fases de prueba. Su uso borra todos
     * los datos de los {@link Cliente} de la base de datos.<br>
     *
     */
    public void DeleteClientDataFromDB()
    {
        String l_DeleteReservaQuery = "DELETE FROM `reserva`";
        String l_DeleteEquipajeQuery = "DELETE FROM `equipaje`";
        String l_DeleteClienteQuery = "DELETE FROM `cliente` WHERE `DNI` <> '12345678A'";
        String l_SetNullAsientoQuery = "UPDATE `avion_asiento` SET `Cliente` = NULL";

        try(Connection p_Conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD))
        {
            try(PreparedStatement p_DelReservaStmt = p_Conn.prepareStatement(l_DeleteReservaQuery);
                PreparedStatement p_DelEquipajeStmt = p_Conn.prepareStatement(l_DeleteEquipajeQuery);
                PreparedStatement p_DelClienteStmt = p_Conn.prepareStatement(l_DeleteClienteQuery);
                PreparedStatement p_SetNullAsientoStmt = p_Conn.prepareStatement(l_SetNullAsientoQuery))
            {
                p_Conn.setAutoCommit(false);
                
                p_DelEquipajeStmt.execute();
                p_DelReservaStmt.execute();
                p_DelClienteStmt.execute();
                p_SetNullAsientoStmt.executeUpdate();
            }

            p_Conn.commit();
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
    }
}
