package aeroport;

import aeroport.MySQL.MySQL;

import aeroport.persona.Empleado;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * Clase que representa a una {@link Company} en el {@link Aeroport}
 * 
 * Cada instancia de esta clase tendrá bajo su mandato a los {@link Empleado} 
 * que trabajen para cada {@link Company} y a los {@link Avion}.
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @my.fecha 22 may 2023 18:08:30
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
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
     * Un {@link TreeSet} de {@link Avion}
     */
    private TreeSet<Avion> l_Aviones;
    /**
     * Un {@link TreeSet} de {@link Empleado}
     */
    private TreeSet<Empleado> l_Empleados;
    
    /**
     * Constructor por defecto de la {@link Company}
     * @param p_Nombre El nombre.
     * @param p_ShortName La abreviación
     */
    public Company(String p_Nombre, String p_ShortName)
    {
        this.l_Nombre = p_Nombre;
        this.l_ShortName = p_ShortName;
        this.l_Aviones = new TreeSet<>();
        this.l_Empleados = new TreeSet<>();
    }
    
    /**
     * Constructor secundario de la {@link Company} con todos los atributos.
     * @param p_Nombre El nombre.
     * @param p_ShortName La abreviación
     * @param p_Aviones Los {@link Avion} que tiene.
     * @param p_Empleados Los {@link Empleado} que tiene bajo su mando.
     */
    public Company(String p_Nombre, String p_ShortName, TreeSet<Avion> p_Aviones, TreeSet<Empleado> p_Empleados)
    {
        this.l_Nombre = p_Nombre;
        this.l_ShortName = p_ShortName;
        this.l_Aviones = new TreeSet<>(p_Aviones);
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
     * @return Un {@link TreeSet}
     */
    public TreeSet<Avion> GetAviones()
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
    
    /**
     * Añade un nuevo {@link Empleado} a la {@link Company}
     * @param p_Empleado El {@link Empleado} ha añadir.
     * @return <ul>
     *              <li>{@code true} si lo ha podido añadir</li>
     *              <li>{@code false} si no ha podido añadir al nuevo {@link Empleado}</li>
     *          </ul>
     */
    public boolean AddEmpleado(Empleado p_Empleado)
    {
        return this.l_Empleados.add(p_Empleado);
    }
    
    /**
     * Elimina un {@link Empleado} de la {@link Company}
     * @param p_Empleado El {@link Empleado} ha eliminar.
     * @return <ul>
     *              <li>{@code true} si lo ha podido eliminar</li>
     *              <li>{@code false} si no ha podido eliminar el {@link Empleado}</li>
     *          </ul>
     */
    public boolean RemoveEmpleado(Empleado p_Empleado)
    {
        return this.l_Empleados.remove(p_Empleado);
    }
    
    /**
     * Añade un nuevo {@link Avion} a la {@link Company}
     * @param p_Avion El {@link Avion} ha añadir.
     * @return <ul>
     *              <li>{@code true} si lo ha podido añadir</li>
     *              <li>{@code false} si no ha podido añadir al nuevo {@link Avion}</li>
     *          </ul>
     */
    public boolean AddAvion(Avion p_Avion)
    {
        return this.l_Aviones.add(p_Avion);
    }
    
     /**
     * Elimina un {@link Avion} de la {@link Company}
     * @param p_Avion El {@link Avion} ha eliminar.
     * @return <ul>
     *              <li>{@code true} si lo ha podido eliminar</li>
     *              <li>{@code false} si no ha podido eliminar el {@link Avion}</li>
     *          </ul>
     */
    public boolean RemoveAvion(Avion p_Avion)
    {
        return this.l_Aviones.remove(p_Avion);
    }
    
    /**
     * Establece un nuevo {@link TreeSet} de {@link Avion} para la {@link Company}
     * @param p_MySQL La conexión de {@link MySQL} donde se realizará las operaciones.
     */
    public void SetAviones(MySQL p_MySQL)
    {
        TreeSet<Avion> l_AvionesFromDB = new TreeSet<>();
        
        for (Avion p_Avion : p_MySQL.GetAvionesFromDB())
        {
            if (p_Avion.GetCompany().equals(this))
                l_AvionesFromDB.add(p_Avion);
        }
        
        this.l_Aviones = new TreeSet<>(l_AvionesFromDB);
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
    
    @Override
    public String toString()
    {
        return String.format("Informacion de la Compañia: %n"
                + "Nombre: %s%n"
                + "Codigo de la Compañia: %s%n",
                this.l_Nombre,
                this.l_ShortName);
    }
}
