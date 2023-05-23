package aeroport;

import aeroport.persona.Piloto;

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
 * @dev.Julián Alcázar Escobedo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 17:02:10
 * @my.company Ciclo Superior de Informática
 */
public abstract class Avion 
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
    
    /**
     * Constructor por defecto de Avión con todos sus parámetros.
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
     * Obtiene el número de serie de {@link Avion}
     * @return Un {@code int} que contiene el número de serie de {@link Avion}
     */
    public int GetNumSerie() 
    {
        return l_NumSerie;
    }
    
    /**
     * Obtiene el nombre del {@link Avion}
     * @return Un {@link String} que contiene el nombre de {@link Avion}
     */
    public String GetNombre() 
    {
        return l_Nombre;
    }
    
    /**
     * Obtiene la {@link Company} del {@link Avion}
     * @return Una {@link Company} que es a la que pertenece {@link Avion}
     */
    public Company GetCompany() 
    {
        return l_Company;
    }
    
    /**
     * Obtiene los {@link Piloto} de {@link Avion}
     * @return Un {@link Piloto} que contiene los pilotos de {@link Avion}
     */
    public Piloto[] GetPilotos() 
    {
        return l_Pilotos;
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
}
