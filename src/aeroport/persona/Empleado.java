package aeroport.persona;

import aeroport.Company;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * Clase que representa a un {@link Empleado} de un {@link Aeroport}
 * 
 * Un {@link Empleado} puede ser a su vez {@link Piloto} y {@link AuxiliarVuelo}
 * que serán los trabajadores del {@link Aeroport}
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Julián Alcázar Escobedo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 22 may 2023 6:27:58
 * @my.company Ciclo Superior de Informática
 */
public class Empleado extends Persona
{
    /**
     * Atributo que identifica el código de {@link Empleado}
     */
    private String l_CodEmpleado;
    
     /**
     * Constructor por defecto de Persona con todos sus parámetros
     * @param p_DNI El DNI.
     * @param p_Nombre El nombre.
     * @param p_Apellidos Los apellidos.
     * @param p_FechaNac La fecha de nacimiento.
     * @param p_CodEmpleado El código del empleado.
     */
    public Empleado (String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_CodEmpleado)
    {
        super(p_DNI, p_Nombre, p_Apellidos, p_FechaNac);
        this.l_CodEmpleado = p_CodEmpleado;
    }

    /**
     * Obtiene el código de {@link Empleado}
     * @return Un {@link String} que contiene la código de {@link Empleado}
     */
    public String GetCodEmpleado() 
    {
        return l_CodEmpleado;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.l_CodEmpleado);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        
        final Empleado other = (Empleado) obj;
        
        return Objects.equals(this.l_CodEmpleado, other.l_CodEmpleado);
    } 
    
    @Override
    public String toString()
    {
        return String.format("Informacion del Empleado:%n"
                + "Codigo de Empleado: %s%n"
                + "Nombre: %s%n"
                + "Apellidos: %s%n",
                this.l_CodEmpleado,
                super.l_Nombre,
                super.l_Apellidos);
    }
}
