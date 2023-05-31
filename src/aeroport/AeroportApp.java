package aeroport;

import aeroport.MySQL.MySQL;
import aeroport.crypto.CryptSHA1;

import aeroport.persona.Cliente;
import aeroport.persona.Equipaje;
import aeroport.persona.TipoEquipaje;
import aeroport.persona.TipoBillete;

import static java.lang.System.out;
import static java.lang.System.in;

import java.time.LocalDate;
import java.time.Month;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

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
            l_Menu += String.format("%d. %s%n", l_Count, p_String);

        return l_Menu;
    }
}

/**
 * Programa principal del proyecto del aeropuerto
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 31 may 2023 15:30:10
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class AeroportApp 
{
    private Scanner l_Teclado = new Scanner(in), l_TecladoNum = new Scanner(in);
    private Aeroport l_Aeroport;
    private MySQL m_MySQL = new MySQL();
    private Cliente l_Login = null;
    private String[] l_MenuInicial = {"Crear Usuario", "Iniciar Sesión","Reiniciar Contraseña","Reservar Vuelo"};
    
    // Datos Cliente
    private String l_DNI, l_Nombre, l_Apellidos, l_Usuario, l_Password, l_Email, l_CodAvion, l_CodAsiento;
    private LocalDate l_FechaNac;
    private int l_Opc;

    /**
     * Constructor por defecto del programa principal.
     */
    public AeroportApp()
    {
        this.l_Aeroport = new Aeroport("Valencia Manises", "VLC", m_MySQL.GetCompaniesFromDB(), m_MySQL.GetPistasFromDB(), m_MySQL.GetTerminalFromDB(1), m_MySQL.GetClientesFromDB());
    }

    /**
     * Método que inicia y llama a los demás métodos.
     */
    public void Initialize()
    {
        
    }
    
    /**
     * Menú inicial con el cual el {@link Cliente} puede realizar operaciones
     * @param p_Opcion 
     */
    public void MenuInicial(int p_Opcion)
    {
        switch(p_Opcion)
        {
            case 1:
                    l_DNI = Datos.ReturnDNI("Introduce tu DNI: ");
                    l_Nombre = Datos.ReturnString("Introduce tu nombre: ");
                    l_Apellidos = Datos.ReturnString("Introduce tus apellidos: ");
                    l_FechaNac = Datos.ReturnLocalDate("Introduce tu fecha de nacimiento (ej: 1999-01-20): ");
                    l_Usuario = Datos.ReturnString("Introduce tu nombre de usuario.");
                    l_Password = CryptSHA1.EncryptPassword(Datos.ReturnString("Introduce tu contraseña: "));
                    l_Email = Datos.ReturnString("Introduce tu Email: ");
                    
                    m_MySQL.AddNewClient(l_DNI, l_Nombre, l_Apellidos, l_FechaNac, l_Usuario, l_Password, l_Email);
                    break;
            case 2:
                throw new UnsupportedOperationException("Aún no se ha implementado.");
            case 3:
                throw new UnsupportedOperationException("Aún no se ha implementado.");
        }
    }

    public static void main(String[] p_Args)
    {
        
//        try
//        {
//            for (Avion p_Avion: m_MySQL.GetAvionesFromDB())
//            {
//                out.println(p_Avion);
//                if (p_Avion instanceof AvionPublico)
//                {
//                    AvionPublico p_AvionPb = (AvionPublico)p_Avion;
//                   out.println(p_AvionPb.PrintAsientos());
//                }                
//                else if (p_Avion instanceof AvionPrivado)
//                {
//                    AvionPrivado p_AvionPv = (AvionPrivado)p_Avion;
//                    out.println(p_AvionPv.PrintAsientos());
//                }
//            }
    }
}
