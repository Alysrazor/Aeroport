
package aeroport;

/**
 * Clase que representa una {@link PistaPrivada} en el {@link Aeroport}
 * 
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 23 may 2023 11:30:52
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class PistaPrivada extends Pista
{
    /**
     * Constructor por defecto de la {@link PistaPrivada}
     * @param ID El identificador
     * @param NombrePista El nombre.
     */
    public PistaPrivada(int ID, String NombrePista) 
    {
        super(ID, NombrePista);
    }
    
    @Override
    public void SetAvion(Avion p_Avion) throws Exception
    {
        if (this.GetAvion() != null)
            throw new IllegalArgumentException("La pista ya está ocupada.");
        
        if (p_Avion instanceof AvionPublico || p_Avion instanceof AvionCarga)
            throw new Exception("No puedes aterrizar en una pista privada. No tienes permiso.");
        
        this.l_Avion = p_Avion;
    }
}
