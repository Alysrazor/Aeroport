package aeroport;

/**
 * Clase que representa una {@link PistaPublica} en el {@link Aeroport}
 * 
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 23 may 2023 11:20:52
 * @my.company Ciclo Superior de Informática
 */
public class PistaPublica extends Pista{

    /**
     * Constructor por defecto de {@link PistaPublica}
     * @param p_ID El identificador
     * @param p_NombrePista El nombre de la {@link PistaPublica}
     * @param p_Avion El {@link Avion}
     */
    public PistaPublica(int p_ID, String p_NombrePista, Avion p_Avion) 
    {
        super(p_ID, p_NombrePista, p_Avion);
    }
}
