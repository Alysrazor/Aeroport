
package aeroport;

/**
 * Clase que representa una {@link PistaPrivada} en el {@link Aeroport}
 * 
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 23 may 2023 11:30:52
 * @my.company Ciclo Superior de Informática
 */
public class PistaPrivada extends Pista{

    /**
     * El identificador
     */
    private int l_ID;
    /**
     * El nombre
     */
    private String l_NombrePista;
    /**
     * El {@link Avion}
     */
    private Avion l_Avion;
    
    /**
     * Constructor por defecto de la {@link PistaPrivada}
     * @param ID El identificador
     * @param NombrePista El nombre.
     * @param Avion El {@link Avion}
     */
    public PistaPrivada(int ID, String NombrePista, Avion Avion) 
    {
        super(ID, NombrePista, Avion);
    }
}
