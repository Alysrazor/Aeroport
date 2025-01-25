package aeroport;

import aeroport.persona.Persona;

import java.io.Serializable;

import java.util.Objects;

/**
 *
 * Clase que representa un {@link Asiento} en un {@link Avion}
 * 
 * <p>
 *  Cada {@link Asiento} tiene una numeración o código que les diferencia del resto; por lo tanto
 *  no puede repetirse. Además el {@link Asiento} sabe si está ocupado por una {@link Persona} o no.
 * <p>
 * 
 * Cada {@link AvionPublico} tiene en cada fila 6 asientos con un máximo de 9 asientos.<br><br>
 * 
 * En el caso de los {@link AvionPrivado} solo tienen 4 por cada fila con un máximo de 6 filas.<br><br>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 11:52:50
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class Asiento implements Serializable
{
    /**
     * El código del {@link Asiento}
     */
    private String l_CodAsiento;
    
    /**
     * La {@link Persona} que está ocupando el {@link Asiento}
     */
    private Persona l_Persona;
    
    /**
     * Constructor por defecto del {@link Asiento} donde se le genera un código de asiento.
     * Por defecto cada {@link Asiento} está desocupado.
     * @param p_CodAsiento El código de asiento.
     */
    public Asiento(String p_CodAsiento)
    {
        this.l_CodAsiento = p_CodAsiento;
        this.l_Persona = null;
    }
    
    /**
     * Obtiene el código del {@link Asiento}
     * @return Un {@link String} que referencia al código del asiento.
     */
    public String GetCodigoAsiento()
    {
        return this.l_CodAsiento;
    }
    
    /**
     * Obtiene la {@link Persona} que está usando el {@link Asiento}
     * @return <ul>
     *              <li>La {@link Persona} si está ocupado</li>
     *              <li>{@code null} si nadie está ocupando el {@link Asiento}
     *          </ul>
     */
    public Persona GetPersona()
    {
        if (this.l_Persona != null)
            return this.l_Persona;
        
        return null;
    }
    
    /**
     * La {@link Persona} que ha reservado el {@link Asiento}
     * @param p_Persona Una {@link Persona}
     */
    public void SetPersona(Persona p_Persona)
    {
        this.l_Persona = p_Persona;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.l_CodAsiento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        final Asiento other = (Asiento) obj;
        return Objects.equals(this.l_CodAsiento, other.l_CodAsiento);
    }
}
