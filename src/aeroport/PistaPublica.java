package aeroport;

import aeroport.Avion;
/**
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs
 * @my.fecha 23 may 2023 11:20:52
 * @my.company Ciclo Superior de Informática
 */
public class PistaPublica extends Pista{

    public PistaPublica(int ID, String NombrePista, Avion Avion) {
        super(ID, NombrePista, Avion);
    }

    public int getID() {
        return ID;
    }

    public String getNombrePista() {
        return NombrePista;
    }

    public Avion getAvion() {
        return Avion;
    }

    public void setAvion(Avion Avion) {
        this.Avion = Avion;
    }

}
