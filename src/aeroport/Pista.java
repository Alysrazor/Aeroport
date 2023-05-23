package aeroport;

/**
 * @author Luis Tomas Sahuquilo
 * @dev.main Luis Tomas Sahuquilo
 * @dev.codevs
 * @my.fecha 23 may 2023 11:10:52
 * @my.company Ciclo Superior de Informática
 */
public abstract class Pista {

    protected int ID;
    protected String NombrePista;
    protected Avion Avion;
    
    public Pista(int ID, String NombrePista, Avion Avion) {
        this.ID = ID;
        this.NombrePista = NombrePista;
        this.Avion = null;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.ID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pista other = (Pista) obj;
        return this.ID == other.ID;
    }

}
