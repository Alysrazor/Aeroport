package aeroport;

import aeroport.MySQL.MySQL;

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
    private String[] l_MenuInicial = {"Crear Usuario", "Reservar Vuelo"};
    private int l_Opc;

    
    public AeroportApp()
    {
        this.l_Aeroport = new Aeroport("Valencia Manises", "VLC", m_MySQL.GetCompaniesFromDB(), m_MySQL.GetPistasFromDB(), m_MySQL.GetTerminalFromDB(1), m_MySQL.GetClientesFromDB());
    }

    void Initialize()
    {

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
