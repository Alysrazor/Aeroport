package aeroport;

import aeroport.persona.Piloto;
import java.util.Objects;

public class AvionPublico extends Avion 
{
    
    /**
     * Atributo que identifica el identificador de {@link AvionPublico}
     */
    private String l_Identificador;
    
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
     * @param p_Identificador El identificador.
     * @param p_Asientos La cantidad de asientos.
     */
    public AvionPublico(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, String p_Identificador, Asiento[][] p_Asientos) 
    {
        super(p_NumSerie, p_Nombre, p_Company, p_Pilotos);
        this.l_Identificador = p_Identificador;
        this.l_Asientos = p_Asientos.clone();
    }
    
    /**
     * Obtiene el identificador de {@link AvionPublico}
     * @return Un {@link String} que contiene el identificador de {@link AvionPublico}
     */
    public String GetIdentificador() 
    {
        return l_Identificador;
    }
    
    /**
     * Obtiene la cantidad de asientos de {@link AvionPublico}
     * @return Un {@link Asientos[][]} que contiene la cantidad de asientos de {@link AvionPublico}
     */
    public Asiento[][] GetAsientos() 
    {
        return l_Asientos;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.l_Identificador);
        
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
        return Objects.equals(this.l_Identificador, other.l_Identificador);
    }
}