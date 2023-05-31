package aeroport;

import aeroport.persona.Piloto;

import java.util.Objects;

/**
 *
 * Clase que representa a un {@link AvionPrivado}
 * 
 * <p>
 *      Un {@link AvionPrivado} tiene menos {@link Asiento} que un {@link AvionPublico}
 *      y aterriza en las {@link PistaPrivada}
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Julián Alcázar Escobezo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 17:02:10
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class AvionPrivado extends Avion implements IAvion
{    
    /**
     * Atributo que identifica la cantidad de asientos de {@link AvionPrivado}
     */
    private Asiento[][] l_Asientos = new Asiento[6][4];
    
    /**
     * Constructor por defecto de AvionPrivado con todos sus parámetros.
     * @param p_NumSerie EL número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañia a la que pertenece.
     * @param p_Pilotos Los pilotos del Avion.
     * @param p_Asientos La cantidad de asientos.
     */
    public AvionPrivado(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, Asiento[][] p_Asientos) 
    {
        super(p_NumSerie, p_Nombre, p_Company, p_Pilotos.clone());
        this.l_Asientos = p_Asientos.clone();
    }

    /**
     * Constructor por defecto de AvionPrivado con todos sus parámetros.
     * @param p_NumSerie EL número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañia a la que pertenece.
     * @param p_Pilotos Los pilotos del Avion.
     * @param p_Vuelo El {@link Vuelo} del {@link Avion}
     * @param p_Asientos La cantidad de asientos.
     */
    public AvionPrivado(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, Vuelo p_Vuelo, Asiento[][] p_Asientos) 
    {
        super(p_NumSerie, p_Nombre, p_Company, p_Pilotos.clone(), p_Vuelo);
        this.l_Asientos = p_Asientos.clone();
    }
    
    /**
     * Obtiene la cantidad de asientos de {@link AvionPrivado}
     * @return Un {@link Asiento} que contiene la cantidad de asientos de {@link AvionPrivado}
     */
    public Asiento[][] GetAsientos() 
    {
        return l_Asientos;
    }
    
    /**
     * Obtiene cuantos {@link Asiento} hay disponibles.
     * @return La cantidad de {@link Asiento} disponibles.
     */
    public int GetAsientosLibres()
    {
        int l_AsientosLibres = 0;
        
        for (Asiento[] p_AsientoF : this.l_Asientos)        
            for (Asiento p_AsientoC : p_AsientoF)            
                if (p_AsientoC.GetPersona() == null) 
                    l_AsientosLibres++;
        
        return l_AsientosLibres;
    }

    @Override
    public String PrintAsientos() 
    {
        StringBuilder sb = new StringBuilder();

        for (Asiento[] p_AsientoF : this.l_Asientos)
        {
            for (Asiento p_AsientoC : p_AsientoF)
            {
                if (p_AsientoC.GetPersona() == null)                
                    sb.append(COLOR_VERDE).append(String.format("%-4s", p_AsientoC.GetCodigoAsiento())).append(COLOR_RESET);
                else
                    sb.append(COLOR_ROJO).append(String.format("%-4s", p_AsientoC.GetCodigoAsiento())).append(COLOR_RESET);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(super.GetNumSerie());
        
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
        
        
        final AvionPrivado other = (AvionPrivado) obj;
        return Objects.equals(super.GetNumSerie(), other.GetNumSerie());
    } 
    
    @Override
    public String toString()
    {
        return String.format("Información del Avión Privado:%n"
                + "%s"
                + "Asientos Disponibles: %d%n",
                super.toString(),
                this.GetAsientosLibres());
    }
}
