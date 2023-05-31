/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import java.io.Serializable;

import java.util.Objects;

/**
 *
 * Clase que representa a un equipaje que puede llevar un {@link Cliente}
 * 
 * @author Luis Tomás Sahuquillo
 * @dev.main Luis Tomás Sahuquillo
 * @dev.codevs Sergio Capilla Cabades
 * @my.fecha 22 may 2023 10:24:40
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class Equipaje implements Serializable
{
    private String l_CodEquipaje;
    private TipoEquipaje l_TipoEquipaje;
    private double l_Peso;

    /**
     * Constructor por defecto del {@link Equipaje}
     * 
     * <p>
     *      Cada equipaje será identificado únicamente por una combinación única que no podrá
     *      repetirse.
     * </p>
     * 
     * @param p_CodEquipaje El código del {@link Equipaje}
     * @param p_TipoEquipaje El tipo de {@link Equipaje}
     * @param p_Peso El peso.
     */
    public Equipaje(String p_CodEquipaje, TipoEquipaje p_TipoEquipaje, double p_Peso) 
    {
        this.l_CodEquipaje = p_CodEquipaje;
        this.l_TipoEquipaje = p_TipoEquipaje;
        this.l_Peso = p_Peso;
    }

    /**
     * Obtiene el código del {@link Equipaje}
     * @return Un {@link String}
     */
    public String GetCodEquipaje() 
    {
        return l_CodEquipaje;
    }

    /**
     * Obtiene el {@link TipoEquipaje} del {@link Equipaje}
     * @return Un {@link TipoEquipaje}
     * @see TipoEquipaje
     */
    public TipoEquipaje GetTipo() 
    {
        return l_TipoEquipaje;
    }
    
    /**
     * Obtiene el peso del {@link Equipaje}
     * @return Un {@code double}
     */
    public double GetPeso() 
    {
        return l_Peso;
    }
    
    /**
     * Establece el {@link TipoEquipaje} a esta instancia de {@link Equipaje}
     * @param p_Tipo El nuevo {@link TipoEquipaje}
     */
    public void SetTipo(TipoEquipaje p_Tipo) 
    {
        this.l_TipoEquipaje = p_Tipo;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.l_CodEquipaje);
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
        
        final Equipaje other = (Equipaje) obj;
        return Objects.equals(this.l_CodEquipaje, other.l_CodEquipaje);
    }
}
