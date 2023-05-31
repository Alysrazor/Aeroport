package aeroport;

import aeroport.MySQL.MySQL;

import aeroport.persona.Cliente;
import aeroport.persona.Equipaje;
import aeroport.persona.TipoEquipaje;
import aeroport.persona.TipoBillete;

import static java.lang.System.out;

import java.time.LocalDate;
import java.time.Month;

import java.util.Arrays;
import java.util.HashSet;

public class AeroportApp 
{
    // Aeroport l_Aeroport = new Aeroport("Valencia Manises", "VLC", )
    public static void main(String[] p_Args)
    {
        MySQL m_MySQL = new MySQL();
        //m_MySQL.TestConnection();
        System.out.println(Arrays.toString(m_MySQL.GetCompaniesFromDB().toArray()));
        try
        {
            for (Avion p_Avion: m_MySQL.GetAvionesFromDB())
            {
                out.println(p_Avion);
                if (p_Avion instanceof AvionPublico)
                {
                    AvionPublico p_AvionPb = (AvionPublico)p_Avion;
                   out.println(p_AvionPb.PrintAsientos());
                }                
                else if (p_Avion instanceof AvionPrivado)
                {
                    AvionPrivado p_AvionPv = (AvionPrivado)p_Avion;
                    out.println(p_AvionPv.PrintAsientos());
                }
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
//        System.out.println(Arrays.toString(m_MySQL.GetTerminalFromDB(1).GetPuertasEmbarque().toArray()));
//          System.out.println(m_MySQL.GetVueloFromDBByAvion(12000));
//        System.out.println(Arrays.toString(m_MySQL.GetTerminalFromDB(1).GetPuertasEmbarque().toArray()));

//            if (m_MySQL.AddNewClient("35795145N", "Nombre A", "Apellidos A", LocalDate.of(1998, Month.MARCH, 12), "User A", "pass", "miemail@gmail.com"))
//                out.println("Cliente añadido correctamente.");
//            else
//                out.println("Ha ocurrido un error.");
//            HashSet<Equipaje> l_Equipaje = new HashSet<>();
//            l_Equipaje.add(new Equipaje("Equipaje A", TipoEquipaje.EQUIPAJE_MALETA, 20.0));
//            
//            HashSet<Asiento> l_Asiento = new HashSet<>();
//            l_Asiento.add(new Asiento("1A"));
//            
//            out.println(TipoBillete.TOURIST.GetNombre());
//            
//            if (m_MySQL.NuevaReservaCliente(new Cliente("35795145N", "Nombre A", "Apellidos A", LocalDate.of(1998, Month.MARCH, 1), "UserA", "pass", "miemail@hotmail.es"), m_MySQL.GetVueloFromDBByAvion(10000),TipoBillete.TOURIST.GetNombre(), l_Equipaje, l_Asiento))
//                out.println("Reserva realizada correctamente.");
//            else
//                out.println("Ha ocurrido un error.");
            
    }
}
