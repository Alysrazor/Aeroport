package aeroport;

import aeroport.persona.Piloto;

import java.io.Serializable;

/**
 *
 * Clase que representa a un {@link Avion}
 *
 * <p>
 * Esta clase representa un concepto abstracto de un {@link Avion} que puede
 * realizar {@link Vuelo} y aterrizar en una {@link Pista}.
 * </p>
 *
 * @author Sergio Capilla Cabadés
 * @dev.main Julián Alcázar Escobedo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 17:02:10
 * @my.company Ciclo Superior de Informática
 */
public abstract class Avion implements Serializable
{
    /**
     * Atributo que identifica el número de serie de {@link Avion}
     */
    protected int l_NumSerie;
    
    /**
     * Atributo que identifica el nombre de {@link Avion}
     */
    protected String l_Nombre;
    
    /**
     * Atributo que identifica la {@link Company} de {@link Avion}
     */
    protected Company l_Company;
    
    /**
     * Atributo que identifica los {@link Piloto} de {@link Avion}
     */
    protected Piloto[] l_Pilotos = new Piloto[2];
    
    protected Vuelo l_Vuelo;
    
    /**
     * Constructor principal de Avión con todos sus parámetros excepto el {@link Vuelo}.
     * @param p_NumSerie El número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañía a la que pertenece.
     * @param p_Pilotos Los pilotos que pilotan el avión
     */
    public Avion(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos) 
    {
        this.l_NumSerie = p_NumSerie;
        this.l_Nombre = p_Nombre;
        this.l_Company = p_Company;
        this.l_Pilotos = p_Pilotos.clone();
    }
    
    /**
     * Constructor secundario de Avión con todos sus parámetros.
     * @param p_NumSerie El número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañía a la que pertenece.
     * @param p_Pilotos Los pilotos que pilotan el avión
     * @param p_Vuelo El {@link Vuelo} que realizara el {@link Avion}
     */
    public Avion(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, Vuelo p_Vuelo) 
    {
        this.l_NumSerie = p_NumSerie;
        this.l_Nombre = p_Nombre;
        this.l_Company = p_Company;
        this.l_Pilotos = p_Pilotos.clone();
        this.l_Vuelo = p_Vuelo;
    }
    
    /**
     * Obtiene el número de serie de {@link Avion}
     * @return Un {@code int} que contiene el número de serie de {@link Avion}
     */
    public int GetNumSerie() 
    {
        return this.l_NumSerie;
    }
    
    /**
     * Obtiene el nombre del {@link Avion}
     * @return Un {@link String} que contiene el nombre de {@link Avion}
     */
    public String GetNombre() 
    {
        return this.l_Nombre;
    }
    
    /**
     * Obtiene la {@link Company} del {@link Avion}
     * @return Una {@link Company} que es a la que pertenece {@link Avion}
     */
    public Company GetCompany() 
    {
        return this.l_Company;
    }
    
    /**
     * Obtiene los {@link Piloto} de {@link Avion}
     * @return Un {@link Piloto} que contiene los pilotos de {@link Avion}
     */
    public Piloto[] GetPilotos() 
    {
        return this.l_Pilotos;
    }
    
    /**
     * Obtiene el {@link Vuelo} que realizara el {@link Avion}
     * @return Un {@link Vuelo}
     */
    public Vuelo GetVuelo()
    {
        return this.l_Vuelo;
    }
    
    /**
     * Establece el nuevo {@link Vuelo} del {@link Avion}
     * 
     * Para hacer que un {@link Avion} no tenga un {@link Vuelo} se le puede pasar {@code null} como parametro.
     * @param p_Vuelo El nuevo {@link Vuelo} del {@link Avion}
     */
    public void SetVuelo(Vuelo p_Vuelo)
    {
        this.l_Vuelo = p_Vuelo;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 17 * hash + this.l_NumSerie;
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Avion other = (Avion) obj;
        return this.l_NumSerie == other.l_NumSerie;
    }
    
    @Override
    public String toString()
    {
        return String.format("Informacion del Avion:%n"
                + "Numero de Serie: %d%n"
                + "Nombre: %s%n"
                + "Compañia: %s%n"
                + "Vuelo:%s%n",
                this.l_NumSerie,
                this.l_Nombre,
                this.l_Company,
                (this.l_Vuelo == null ? "No tiene un vuelo asignado" : this.l_Vuelo.toString()));
    }
}
