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
     * Atributo que identifica la compañía de {@link Empleado}
     */
    private Company l_Company;
    
     /**
     * Constructor por defecto de Persona con todos sus parámetros
     * @param p_DNI El DNI.
     * @param p_Nombre El nombre.
     * @param p_Apellidos Los apellidos.
     * @param p_FechaNac La fecha de nacimiento.
     * @param p_CodEmpleado El código del empleado.
     * @param p_Company La compañía a la que pertenece el empleado.
     */
    public Empleado (String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_CodEmpleado, Company p_Company)
    {
        super(p_DNI, p_Nombre, p_Apellidos, p_FechaNac);
        this.l_CodEmpleado = p_CodEmpleado;
        this.l_Company = p_Company;
    }

    /**
     * Obtiene el código de {@link Empleado}
     * @return Un {@link String} que contiene la código de {@link Empleado}
     */
    public String GetCodEmpleado() 
    {
        return l_CodEmpleado;
    }
    
    /**
     * Obtiene la {@link Company} de la {@link Persona}
     * @return Una {@link Company} que es a la que pertenece el {@link Empleado}
     */
    public Company GetCompany() 
    {
        return l_Company;
    }
    
    /**
     * Cambia el valor de la {@link Company} de {@link Empleado}
     * @param p_Company La nueva {@link Company} del {@link Empleado}
     */
    public void SetCompany(Company p_Company) 
    {
        this.l_Company = p_Company;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.l_CodEmpleado);
        hash = 67 * hash + Objects.hashCode(this.l_Company);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        
        final Empleado other = (Empleado) obj;
        if (!Objects.equals(this.l_CodEmpleado, other.l_CodEmpleado)) return false;
        
        return Objects.equals(this.l_Company, other.l_Company);
    } 
}
