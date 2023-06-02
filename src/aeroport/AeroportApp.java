package aeroport;

import aeroport.MySQL.MySQL;

import aeroport.persona.Cliente;
import aeroport.persona.Equipaje;
import aeroport.persona.TipoEquipaje;
import aeroport.persona.TipoBillete;

import static java.lang.System.out;
import static java.lang.System.in;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

import javax.lang.model.util.ElementScanner6;

/**
 * Clase para mostrar un menú
 */
class Menu
{
    /**
     * Nadie puede invocar la clase.
     */
    private Menu() {}

    /**
     * Muestra un menú dado un vector de {@link String}
     * @param p_Menu El vector de {@link String}
     * @return Un {@link String} con el menú formado.
     */
    public static String MostrarMenu(String[] p_Menu)
    {
        String l_Menu = "";
        int l_Count = 1;

        for (String p_String : p_Menu)
            l_Menu += String.format("%d. %s%n", l_Count++, p_String);

        return l_Menu;
    }
}

/**
 * Programa principal del proyecto del aeropuerto
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @my.fecha 31 may 2023 15:30:10
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class AeroportApp 
{
    private Scanner l_Teclado = new Scanner(in), l_TecladoNum = new Scanner(in);
    private Aeroport l_Aeroport;
    private MySQL m_MySQL = new MySQL();
    private Cliente l_Login;
    private Vuelo l_Vuelo;
    HashSet<Asiento> l_Asientos;
    private String[] l_MenuInicial = {"Crear Usuario", "Iniciar Sesión","Reiniciar Contraseña","Reservar Vuelo", "Cerrar Sesión", "Salir"},
                         l_MenuEspecial = {"Crear Usuario", "Iniciar Sesión","Reiniciar Contraseña","Reservar Vuelo", "Borrar datos Cliente", "Cerrar Sesión", "Salir"};
    private static final int TERMINAL = 1;
    
    // Datos Cliente
    private String l_DNI, l_Nombre, l_Apellidos, l_Usuario, l_Password, l_Email, l_CodVuelo, l_CodAsiento;
    private LocalDate l_FechaNac;
    private int l_Opc, l_CantidadEquipaje, l_CantidadAsientos;
    private double l_Peso;

    /**
     * Constructor por defecto del programa principal.
     */
    public AeroportApp()
    {
        this.l_Aeroport = new Aeroport("Valencia Manises", "VLC", m_MySQL.GetCompaniesFromDB(), m_MySQL.GetPistasFromDB(), m_MySQL.GetTerminalFromDB(TERMINAL), m_MySQL.GetClientesFromDB());
        this.l_Login = null;
        
        m_MySQL.DeleteClientDataFromDB();
    }

    /**
     * Método que inicia y llama a los demás métodos.
     */
    public void Initialize()
    {        
        while (true)
          MenuInicial();
    }
    
    /**
     * Menú inicial con el cual el {@link Cliente} puede realizar operaciones
     */
    public void MenuInicial()
    {
        do
        {
            try
            {
                out.println("Selecciona una opción: ");
                l_Opc = Datos.ReturnInteger(Menu.MostrarMenu(l_MenuInicial));
            }
            catch(InputMismatchException e)
            {
                out.println(e.getMessage());
            }
            switch(l_Opc)
            {
                case 1: DatosCliente(); break;
                case 2: IniciarSesion(); break;
                case 3: CambiarPassword(); break;
                case 4: MenuVuelos(); break;
                case 5: l_Login = null; break;
                case 6: System.exit(0); break;
            }
        }while(!(l_Opc >= 1 && l_Opc <= 6));
    }

    /**
     * Menú donde los {@link Cliente} pueden hacer las operaciones básicas de reserva.
     */
    public void MenuVuelos()
    {
        TreeSet<String> l_NombreVuelos = new TreeSet<>();
        boolean l_OK = false;
        
        if (l_Login == null)
        {
            out.println("Debes estar conectado para porder reservar un vuelo.");
            return;
        }

        for (Vuelo p_Vuelo : m_MySQL.GetVuelosSalidaFromDB())
            l_NombreVuelos.add(p_Vuelo.GetIdentificador().concat(" " + p_Vuelo.GetOrigen().concat(" " + p_Vuelo.GetDestino().concat(" " + p_Vuelo.GetHoraVuelo()))));
        
        for (String p_String : l_NombreVuelos)
            out.println(p_String);

        do
        {
            try
            {
                out.println("Introduce el Identificador de un vuelo: ");
                Menu.MostrarMenu(l_NombreVuelos.toArray(String[]::new));
                l_CodVuelo = l_Teclado.nextLine();

                if (m_MySQL.GetVueloSalidaFromDB(l_CodVuelo) == null)
                    throw new IllegalArgumentException("Ese vuelo no existe.");
                else if (m_MySQL.GetVueloSalidaFromDB(l_CodVuelo).GetHoraVuelo().isBefore(LocalDateTime.now()))
                    throw new IllegalArgumentException("No puedes reservar un vuelo que ya ha pasado.");
                else
                {
                    l_Vuelo = m_MySQL.GetVueloSalidaFromDB(l_CodVuelo);
                    System.out.println(l_Vuelo.toString());
                    try
                    {
                        l_CantidadEquipaje = Datos.ReturnInteger("¿Cuántas maletas vas a facturar?");
                    }
                    catch(InputMismatchException|IllegalArgumentException e)
                    {
                        out.println(e.getMessage());
                    }

                    try
                    {
                        l_CantidadAsientos = Datos.ReturnInteger("Introduce cuantos asientos quieres reservar.");
                    }
                    catch(InputMismatchException|IllegalArgumentException e)
                    {
                        out.println(e.getMessage());
                    }
                    

                    if (l_CantidadEquipaje < 0)
                        throw new IllegalArgumentException("Debes indicar una cantidad positiva");                    
                    else                    
                        if(m_MySQL.NuevaReservaCliente(l_Login, l_Vuelo, TipoBillete.TOURIST.GetNombre(), DatosClienteEquipaje(l_CantidadEquipaje), DatosAsiento(l_CantidadAsientos)))                        
                            out.println("Reserva realizada.");
                        else
                            out.println("Error en realizar la reserva.");
                        
                }

                l_OK = true;
            }
            catch(IllegalArgumentException|InputMismatchException e)
            {
                out.println(e.getMessage());
            }
        }while(!(l_Opc >= 1 && l_Opc <= l_NombreVuelos.size()) && !l_OK);
    }

    /**
     * Pregunta al {@link Cliente} sus datos y los añade tanto a la base de datos como al {@link TreeSet}.
     */
    public void DatosCliente()
    {
        do
        {
            l_DNI = Datos.ReturnDNI("Introduce tu DNI: ");
            l_Nombre = Datos.ReturnString("Introduce tu nombre: ");
            l_Apellidos = Datos.ReturnString("Introduce tus apellidos: ");
            l_FechaNac = Datos.ReturnLocalDate("Introduce tu fecha de nacimiento (ej: 1999-01-20): ");
            l_Usuario = Datos.ReturnString("Introduce tu nombre de usuario.");
            l_Password = Datos.ReturnString("Introduce tu contraseña: ");
            l_Email = Datos.ReturnString("Introduce tu Email: ");
            
            l_Login = new Cliente(l_DNI, l_Nombre, l_Apellidos, l_FechaNac, l_Usuario, l_Password, l_Email);
        }while(!m_MySQL.AddNewClient(l_DNI, l_Nombre, l_Apellidos, l_FechaNac, l_Usuario, l_Password, l_Email));
        
        this.l_Aeroport.GetClientes().add(l_Login);
    }

    /**
     * Pide al {@link Cliente} los datos de su {@link Equipaje}
     * @param p_Maletas La cantidad de {@link Equipaje} que tiene.
     * @return Un {@link HashSet}
     */
    public HashSet<Equipaje> DatosClienteEquipaje(int p_Maletas)
    {
        HashSet<Equipaje> l_Equipaje = new HashSet<>();

        for (int l_Itr = 0; l_Itr < p_Maletas; l_Itr++)        
            l_Equipaje.add(DatosEquipaje());

        return l_Equipaje;
    }

    /**
     * Pide individualmente los datos de cada {@link Equipaje}
     * @return UN {@link Equipaje}
     */
    public Equipaje DatosEquipaje()
    {
        TipoEquipaje l_TipoEquipaje;
        boolean l_OK = false;
        
        l_TipoEquipaje = TipoMaleta();
        
        do
        {
            try
            {
                l_Peso = Datos.ReturnDouble("Indique el peso de la maleta.");
                
                if (l_Peso < 0)
                    throw new IllegalArgumentException("El peso debe ser positivo.");
                else if (l_TipoEquipaje.equals(TipoEquipaje.EQUIPAJE_MANO) && l_Peso > 14)
                    throw new IllegalArgumentException("Solo se puede llevar hasta 14kg en maletas de mano.");                
                else if (l_TipoEquipaje.equals(TipoEquipaje.EQUIPAJE_MALETA) && l_Peso > 23)
                    throw new IllegalArgumentException("Solo se puede llevar hasta 23kg en maletas.");
                
                l_OK = true;
            }
            catch(NumberFormatException e)
            {
                out.println(e.getMessage());
            }
            catch(InputMismatchException|IllegalArgumentException e)
            {
                out.println(e.getMessage());
            }
        }while(!l_OK);

        return new Equipaje(m_MySQL.GetCodEquipajeCrypt(l_Login.GetDNI().concat(LocalDateTime.now().toString())), l_TipoEquipaje, l_Peso);
    }
    
    /**
     * Pregunta el {@link TipoEquipaje} al {@link Cliente}
     * @return Un {@link TipoEquipaje}
     */
    public TipoEquipaje TipoMaleta()
    {
        do
        {
            try
            {
                l_Opc = Datos.ReturnInteger("Elige tipo de maleta:\n"
                                    + "1. Mano\n"
                                    + "2. Maleta\n");
                
                switch (l_Opc)
                {
                    case 1:
                           return TipoEquipaje.EQUIPAJE_MANO;
                    case 2:
                            return TipoEquipaje.EQUIPAJE_MALETA;
                    default: 
                        break;
                }
            }
            catch(Exception e)
            {
                out.println(e.getMessage());
            }
        }while(!(l_Opc >= 1 && l_Opc <= 2));
        
        return null;
    }
    
    /**
     * Pide los datos de un {@link Vuelo} al {@link Cliente}
     * @return Un {@link Vuelo}
     */
    public Vuelo DatosVuelo()
    {
        out.println("Vuelos disponibles\n"
                            + "Elige un vuelo por su IDENTIFICADOR.");
        
        for (Vuelo p_Vuelo : m_MySQL.GetVuelosSalidaFromDB())
            out.println(String.format("%s%n", p_Vuelo.toString()));
        
        do
        {
            l_CodVuelo = Datos.ReturnString("Introduce el identificador del vuelo");
            l_Vuelo = m_MySQL.GetVueloSalidaFromDB(l_CodVuelo);
        }while(m_MySQL.GetVueloSalidaFromDB(l_CodVuelo) == null);
        
        return l_Vuelo;
    }
    
    /**
     * Pide los datos de los {@link Asiento} al {@link Cliente}
     * @param p_Asientos El número de {@link Asiento}
     * @return Un {@link HashSet}
     */
    public HashSet<Asiento> DatosAsiento(int p_Asientos)                        
    {
        l_Asientos = new HashSet<>();
        
        for (int l_Itr = 0; l_Itr < p_Asientos; l_Itr++)
        {
            out.println("Los asientos tienen 9 filas y van del XA a XF donde X es el número de la fila.");
            l_CodAsiento = Datos.ReturnString("Elige un asiento: ");
            l_Asientos.add(new Asiento(l_CodAsiento));
        }
        
        return l_Asientos;
    }

    /**
     * Un {@link Cliente} inicia sesión en nuestro programa.
     * 
     * <p>
     *      El usuario debe existir en la base de datos y en el {@link TreeSet} para que la conexión sea efectiva.
     * </p>
     */
    public void IniciarSesion()
    {
        l_DNI = Datos.ReturnDNI("Introduce tu DNI: ");
                    
        if (m_MySQL.GetClienteFromDB(l_DNI) != null && l_Aeroport.GetClientes().contains(m_MySQL.GetClienteFromDB(l_DNI)))
        {
            l_Password = Datos.ReturnString("Introduce tu contraseña: ");

            if (!m_MySQL.GetClienteFromDB(l_DNI).GetPassword().equals(l_Password))
                out.println("Contraseña incorrecta.");                       
            else
            {
                l_Login = m_MySQL.GetClienteFromDB(l_DNI);
                out.printf("Bienvenido: %s%n", l_Login.GetUsuario());
            }
        }
        else
            out.println("No existe ese usuario, por favor crea una cuenta.");
    }

    /**
     * Cambia la contraseña del {@link Cliente}.
     * 
     * <p>
     *      Para que el cambio sea efectivo el {@link Cliente} debe existir tanto en la base de datos
     *      como en el {@link TreeSet}, además de que tanto la nueva contraseña como la confirmación
     *      de la misma deben coincidir.<br><br>
     *      En el caso de que el {@link Cliente} no exista se notificará al usuario para que
     *      se cree una cuenta.
     * </p>
     */
    public void CambiarPassword()
    {
        l_DNI = Datos.ReturnDNI("Introduce tu DNI: ");
                    
        if (m_MySQL.GetClienteFromDB(l_DNI) != null && l_Aeroport.GetClientes().contains(m_MySQL.GetClienteFromDB(l_DNI)))
        {
            l_Password = Datos.ReturnString("Introduce tu nueva contraseña: ");
            String l_PasswordConfirm = Datos.ReturnString("Vuelve ha introducir tu nueva contraseña: ");

            try
            {
                if (!m_MySQL.ClientChangePassword(l_Login == null ? m_MySQL.GetClienteFromDB(l_DNI) : l_Login , l_Password, l_PasswordConfirm))
                out.println("Las contraseñas no coinciden.");                       
                else                        
                {
                    l_Login = m_MySQL.GetClienteFromDB(l_DNI);
                    l_Login.SetPassword(l_Password);
                    out.println("Contraseña cambiada correctamente.");
                }
            }
            catch(IllegalArgumentException e)
            {
                out.println(e.getMessage());
            } 
        }
        else
            out.println("No existe ese usuario, por favor crea una cuenta.");
    }

    /**
     * Programa principal
     * @param p_Args Comandos de consola.
     */
    public static void main(String[] p_Args)
    {
        out.println("Cargando datos...");
        AeroportApp m_AeroportApp = new AeroportApp();
        out.println("Datos Cargados");
        m_AeroportApp.Initialize();
    }
}
