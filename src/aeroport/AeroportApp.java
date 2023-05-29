package aeroport;

import aeroport.MySQL.MySQL;
import java.util.Arrays;

public class AeroportApp 
{
    // Aeroport l_Aeroport = new Aeroport("Valencia Manises", "VLC", )
    public static void main(String[] p_Args)
    {
        MySQL m_MySQL = new MySQL();
        m_MySQL.TestConnection();
        //System.out.println(Arrays.toString(m_MySQL.GetCompaniesFromDB().toArray()));
        try
        {
            System.out.println(Arrays.toString(m_MySQL.GetAvionesFromDB().toArray()));
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
}
