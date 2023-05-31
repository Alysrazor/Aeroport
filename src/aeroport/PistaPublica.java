package aeroport;

/**
 * Clase que representa una {@link PistaPublica} en el {@link Aeroport}
 * 
 * @author Luis Tomas Sahuquillo
 * @dev.main Luis Tomas Sahuquillo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 23 may 2023 11:20:52
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class PistaPublica extends Pista{

    /**
     * Constructor por defecto de {@link PistaPublica}
     * @param p_ID El identificador
     * @param p_NombrePista El nombre de la {@link PistaPublica}
     */
    public PistaPublica(int p_ID, String p_NombrePista) 
    {
        super(p_ID, p_NombrePista);
    }
    
    @Override
    public void SetAvion(Avion p_Avion) throws Exception
    {
        if (this.GetAvion() != null)
            throw new IllegalArgumentException("La pista ya está ocupada.");
        
        if (p_Avion instanceof AvionPrivado)
            throw new Exception("No puedes aterrizar en una pista pública. No tienes permiso.");
        
        this.l_Avion = p_Avion;
    }
}
