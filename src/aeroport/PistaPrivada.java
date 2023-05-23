
package aeroport;

/**
import aeroport.Avion;
/**
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs
 * @my.fecha 23 may 2023 11:30:52
 * @my.company Ciclo Superior de Informática
 */
public class PistaPrivada extends Pista{

    public PistaPrivada(int ID, String NombrePista, Avion Avion) {
        super(ID, NombrePista, Avion);
    }

    public int getID() {
        return ID;
    }

    public String getNombrePista() {
        return NombrePista;
    }

    public aeroport.Avion getAvion() {
        return Avion;
    }

    public void setAvion(aeroport.Avion Avion) {
        this.Avion = Avion;
    }

}
