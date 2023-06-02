package aeroport;

import java.io.Serializable;

/**
 * Clase que representa una {@link Pista} en el {@link Aeroport}
 *
 * @author Luis Tomas Sahuquillo
 * @dev.main Luis Tomas Sahuquillo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 23 may 2023 11:10:52
 * @my.company Ciclo Superior de Informática´
 * @since JDK 1.18
 */
public abstract class Pista implements Comparable<Pista>, Serializable
{
    /**
    * Identificador de la pista
    */
    protected int l_ID;
    
    /**
     * Nombre de la {@link Pista}
     */
    protected String l_NombrePista;
    
    /**
     * El {@link Avion} que aterriza en la {@link Pista}
     */
    protected Avion l_Avion;
    
    /**
     * Constructor por defecto de la {@link Pista}
     * @param p_ID El identificador de la {@link Pista}
     * @param p_NombrePista El nombre de la {@link Pista}
     */
    public Pista(int p_ID, String p_NombrePista) {
        this.l_ID = p_ID;
        this.l_NombrePista = p_NombrePista;
        this.l_Avion = null;
    }

    /**
     *  Obtiene la ID de la {@link Pista}
     *  @return Un {@code int} que es la ID de la {@link Pista}
     */
    public int GetID() 
    {
        return this.l_ID;
    }

    /**
     *  Obtiene el nombre de la {@link Pista}
     *  @return Un {@link String} que es el nombre de la {@link Pista}
     */
    public String GetNombrePista() 
    {
        return this.l_NombrePista;
    }

    /**
     *  Obtiene el {@link Avion} que está aterrizando en la {@link Pista}
     *  @return <ul>
                    <li>Un {@link Avion} que está aterrizando</li>
                    <li>{@code null} si no hay ningún {@link Avion} aterrizando en la pista</li>
               </ul>
     */
    public Avion GetAvion() 
    {
        return this.l_Avion;
    }
    
    /**
     * Establece el {@link Avion} de la {@link Pista}
     * @param p_Avion El {@link Avion} que está aterrizando.
     * @throws Exception Si ya hay un {@link Avion} en la {@link Pista}
     */
    public abstract void SetAvion(Avion p_Avion) throws Exception;

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 97 * hash + this.l_ID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
            return true;
        
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        final Pista other = (Pista) obj;
        return this.l_ID == other.l_ID;
    }
    
    @Override
    public int compareTo(Pista p_Obj)
    {
        return this.l_ID - p_Obj.l_ID;
    }
    
    @Override
    public String toString()
    {
        return String.format("Informacion de la Pista:%n"
                + "Numero de la Pista: %d%n"
                + "Nombre de la Pista: %s%n",
                this.l_ID,
                this.l_NombrePista);
    }
}
