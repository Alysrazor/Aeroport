package aeroport;

import aeroport.persona.Empleado;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * Clase que representa a una {@link Company} en el {@link Aeropuerto}
 * 
 * Cada instancia de esta clase tendrá bajo su mandato a los {@link Empleado} 
 * que trabajen para cada {@link Company} y a los {@link Avion}.
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @fecha 22 may 2023 18:08:30
 * @company Ciclo Superior de Informática
 */
public class Company implements Comparable<Company>, Serializable
{
    /**
     * El nombre de la {@link Company}
     */
    private final String l_Nombre;
    /**
     * Abreviación de la {@link Company}
     */
    private final String l_ShortName;
    /**
     * Un {@link HashSet} de {@link Avion}
     */
    private HashSet<Avion> l_Aviones;
    /**
     * Un {@link TreeSet} de {@link Empleado}
     */
    private TreeSet<Empleado> l_Empleados;
    
    /**
     * Constructor por defecto de la {@link Company}
     * @param p_Nombre El nombre.
     * @param p_ShortName La abreviación
     * @param p_Aviones Los {@link Avion} que tiene.
     * @param p_Empleados Los {@link Empleado} que tiene bajo su mando.
     */
    public Company(String p_Nombre, String p_ShortName, HashSet<Avion> p_Aviones, TreeSet<Empleado> p_Empleados)
    {
        this.l_Nombre = p_Nombre;
        this.l_ShortName = p_ShortName;
        this.l_Aviones = new HashSet<>(p_Aviones);
        this.l_Empleados = new TreeSet<>(p_Empleados);
    }
    
    /**
     * Obtiene el nombre de la {@link Company}
     * @return Un {@link String} que contiene el nombre de la compañía.
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    }
    
    /**
     * Obtiene la abreviatura de la {@link Company}
     * @return Un {@link String} que contiene la abreviatura.
     */
    public String GetShortName()
    {
        return this.l_ShortName;
    }
    
    /**
     * Obtiene los {@link Avion} que tiene la {@link Company}
     * @return Un {@link HashSet}
     */
    public HashSet<Avion> GetAviones()
    {
        return this.l_Aviones;
    }
    
    /**
     * Obtiene los {@link Empleado} que trabajan en la {@link Company}
     * @return Un {@link TreeSet}
     */
    public TreeSet<Empleado> GetEmpleados()
    {
        return this.l_Empleados;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.l_Nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Company other = (Company) obj;
        return Objects.equals(this.l_Nombre, other.l_Nombre);
    }
    
    @Override
    public int compareTo(Company p_Obj)
    {
        if (this.l_Nombre.compareTo(p_Obj.l_Nombre) < 0) return -1;
        else if (this.l_Nombre.compareTo(p_Obj.l_Nombre) > 0) return 1;
        
        return 0;
    }
}
