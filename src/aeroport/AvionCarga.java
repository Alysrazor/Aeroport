package aeroport;

import aeroport.persona.Piloto;

import java.util.Objects;

/**
 *
 * Clase que representa a un {@link AvionCarga}
 * 
 * <p>
 *      Cada {@link AvionCarga} solo puede transportar paquetes que se añadirán en forma
 *      de peso en el método {@link #AddCarga}.
 *      Dado que es un {@link AvionCarga} solo tienen 2 {@link Asiento} y solo podrán ser
 *      ocupados por {@link Piloto}
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Julián Alcázar Escobezo
 * @dev.codevs Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 17:02:10
 * @my.company Ciclo Superior de Informática
 */
public class AvionCarga extends Avion 
{
    /**
     * Atributo que identifica el identificador de {@link AvionCarga}
     */
    private String l_Identificador;
    
    /**
     * Atributo que identifica la cantidad de asientos de {@link AvionCarga}
     */
    private Asiento[] l_Asientos = new Asiento[2];
    
    /**
     *  La carga actual que lleva el {@link AvionCarga}
     */
    private double l_Carga;
    
    /**
     *  La cantidad máxima que puede cargar el {@link AvionCarga}
     */
    private final double CARGA_MAX = 500000.00;
    
    /**
     * Constructor por defecto de {@link AvionCarga} con todos sus parámetros.
     * @param p_NumSerie EL número de serie.
     * @param p_Nombre El nombre.
     * @param p_Company La compañia a la que pertenece.
     * @param p_Pilotos Los pilotos del Avion.
     * @param p_Identificador El identificador.
     * @param p_Asientos La cantidad de asientos.
     */
    public AvionCarga(int p_NumSerie, String p_Nombre, Company p_Company, Piloto[] p_Pilotos, String p_Identificador, Asiento[] p_Asientos) 
    {
        super(p_NumSerie, p_Nombre, p_Company, p_Pilotos);
        this.l_Identificador = p_Identificador;
        this.l_Asientos = p_Asientos.clone();
        this.l_Carga = 0;
    }
    
    /**
     * Obtiene el identificador de {@link AvionCarga}
     * @return Un {@link String} que contiene el identificador de {@link AvionCarga}
     */
    public String GetIdentificador() 
    {
        return l_Identificador;
    }
    
    /**
     * Obtiene la cantidad de asientos de {@link AvionCarga}
     * @return Un {@link Asiento} que contiene la cantidad de asientos de {@link AvionCarga}
     */
    public Asiento[] GetAsientos() 
    {
        return l_Asientos;
    }
    
    /**
     *  Obtiene la carga actual de esta instancia de {@link AvionCarga}
     *  @return Un {@code double} con la cantidad de la carga actual.
     */
    public double GetCarga()
    {
        return this.l_Carga;
    }
    
    /**
     *  Añade una determinada cantidad de carga a esta instancia de {@link AvionCarga}
     *  @throws IllegalArgumentException Si se intenta superar el límite máximo {@link #CARGA_MAX}
     */
    public void AddCarga(double p_Carga) throws IllegalArgumentException
    {
        if (this.l_Carga + p_Carga > CARGA_MAX)
            throw new IllegalArgumentException("No se puede añadir la carga porque supera el tope máximo.");
        
        this.l_Carga += p_Carga;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.l_Identificador);
        
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
        
        
        final AvionCarga other = (AvionCarga) obj;
        return Objects.equals(this.l_Identificador, other.l_Identificador);
    }
}
