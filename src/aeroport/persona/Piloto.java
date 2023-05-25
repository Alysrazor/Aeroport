package aeroport.persona;

import aeroport.Avion;
import aeroport.Company;

import java.time.LocalDate;

import java.util.Objects;

/**
 * 
 * Clase que representa a un {@link Piloto} en el {@link Aeroport}
 * 
 * Cada instancia de esta clase solo puede ser asignada a un {@link Avion}
 * y no podrá realizar otras tareas dentro del {@link Aeroport}
 * 
 * @author Luis Tomás Sahuquillo
 * @dev.main Luis Tomás Sahuquillo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 22 may 2023 10:08:10
 * @my.company Ciclo Superior de Informática
 */
public class Piloto extends Empleado 
{
    /**
     * El {@link Avion} asignado al {@link Piloto}
     */
    private Avion Avion;

    /**
     * Constructor por defecto del {@link Piloto}
     * @param p_DNI El DNI del {@link Piloto}
     * @param p_Nombre El nombre del {@link Piloto}
     * @param p_Apellidos Los apellidos del {@link Piloto}
     * @param p_FechaNac La fecha de nacimiento del {@link Piloto}
     * @param p_CodEmpleado El código de empleado del {@link Piloto}
     * @param p_Avion El {@link Avion} asignado al {@link Piloto}
     */
    public Piloto(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_CodEmpleado, Avion p_Avion) 
    {
        super(p_DNI, p_Nombre, p_Apellidos, p_FechaNac, p_CodEmpleado);
        this.Avion = p_Avion;

    }
    
    /**
     * Obtiene el {@link Avion} al que está asignado esta instancia de {@link Piloto}
     * @return Un {@link Avion}
     */
    public Avion GetAvion() 
    {
        return Avion;
    }

    /**
     * Asigna un nuevo {@link Avion} al {@link Piloto}
     * @param Avion El nuevo {@link Avion}
     */
    public void SetAvion(Avion Avion) 
    {
        this.Avion = Avion;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.Avion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Piloto other = (Piloto) obj;
        return Objects.equals(this.Avion, other.Avion);
    }
    
    @Override
    public String toString()
    {
        return String.format("Informacion del Piloto:%n"
                + "Codigo de Empleado: %s%n"
                + "Nombre: %s%n"
                + "Apellidos: %s%n"
                + "Compañia: %s%n"
                + "Avion asignado: %s%n",
                super.GetCodEmpleado(),
                super.l_Nombre,
                super.l_Apellidos,
                this.Avion.GetCompany(),
                this.Avion.GetNumSerie());
    }
}

