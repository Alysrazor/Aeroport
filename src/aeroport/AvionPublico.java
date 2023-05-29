package aeroport;

import aeroport.persona.Piloto;

import java.util.Objects;

/**
 *
 * Clase que representa a un {@link AvionPublico}
 * 
 * <p>
 *      Es el tipo de {@link Avion} más común ya que lleva a una mayor cantidad
 *      de {@link Cliente} que los {@link AvionPrivado}<br><br>
 *      Solo pueden aterrizar en las {@link PistaPublica}
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Julián Alcázar Escobezo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 17:02:10
 * @my.company Ciclo Superior de Informática
 */
public class AvionPublico extends Avion implements IAvion
{    
    /**
     * Atributo que identifica la cantidad de asientos de {@link AvionPublico}
     */
    private Asiento[][] l_Asientos;
    
    /**
     * Constructor por defecto de AvionPublico con todos sus parámetros.
     * @param p_NumSerie EL número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañia a la que pertenece.
     * @param p_Pilotos Los pilotos del Avion.
     * @param p_Asientos La cantidad de asientos.
     */
    public AvionPublico(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, Asiento[][] p_Asientos) 
    {
        super(p_NumSerie, p_Nombre, p_Company, p_Pilotos.clone());
        this.l_Asientos = p_Asientos.clone();
    }

    
    /**
     * Obtiene la cantidad de asientos de {@link AvionPublico}
     * @return Un {@link Asiento} que contiene la cantidad de asientos de {@link AvionPublico}
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
                if (p_AsientoC.GetPersona() != null) 
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
                    sb.append(String.format("%-5s", COLOR_VERDE + p_AsientoC.GetCodigoAsiento() + COLOR_RESET));
                else
                    sb.append(String.format("%-5s", COLOR_ROJO + p_AsientoC.GetCodigoAsiento() + COLOR_RESET));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(super.l_NumSerie);
        
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
        
        final AvionPublico other = (AvionPublico) obj;
        return Objects.equals(super.l_NumSerie, other.l_NumSerie);
    }
    
    @Override
    public String toString()
    {
        return String.format("Información del Avión Privado:%n"
                + "%s%n"
                + "Asientos Disponibles: %d%n",
                super.toString(),
                this.GetAsientosLibres());
    }
}
