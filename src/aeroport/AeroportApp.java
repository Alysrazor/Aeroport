package aeroport;

import aeroport.MySQL.MySQL;

import static java.lang.System.out;

import java.util.Arrays;

public class AeroportApp 
{
    // Aeroport l_Aeroport = new Aeroport("Valencia Manises", "VLC", )
    public static void main(String[] p_Args)
    {
        MySQL m_MySQL = new MySQL();
        //m_MySQL.TestConnection();
//        System.out.println(Arrays.toString(m_MySQL.GetCompaniesFromDB().toArray()));
        try
        {
            for (Avion p_Avion: m_MySQL.GetAvionesFromDB())
                out.println(p_Avion);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
//        System.out.println(Arrays.toString(m_MySQL.GetTerminalFromDB(1).GetPuertasEmbarque().toArray()));
//        System.out.println(m_MySQL.GetVueloFromDBByAvion(10000));
//        System.out.println(Arrays.toString(m_MySQL.GetTerminalFromDB(1).GetPuertasEmbarque().toArray()));
    }
}
