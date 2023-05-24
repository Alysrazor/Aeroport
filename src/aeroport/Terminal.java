/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import java.io.Serializable;

import java.util.Objects;
import java.util.TreeSet;


/**
 *
 * Clase que define la {@link Terminal} del {@link Aeroport} donde 
 * estarán las {@link PuertaEmbarque}
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 18:10:52
 * @my.company Ciclo Superior de Informática
 */

public class Terminal implements Serializable
{
    /**
     * El nombre de la {@link Terminal}
     */
    private String l_Nombre;
    
    /**
     * Un {@link TreeSet} de {@link PuertaEmbarque}
     */
    private TreeSet<PuertaEmbarque> l_Puertas;
    
    /**
     * Constructor por defecto de las {@link Terminal} donde cada instancia tendrá sus {@link PuertaEmbarque}
     * @param p_Nombre El nombre de la {@link Terminal}
     * @param p_Puertas Un {@link TreeSet} con las {@link PuertaEmbarque}
     */
    public Terminal(String p_Nombre, TreeSet<PuertaEmbarque> p_Puertas)
    {
        this.l_Nombre = p_Nombre;
        this.l_Puertas = new TreeSet<>(p_Puertas);
    }
    
    /**
     * Obtiene el nombre de la {@link Terminal}
     * @return Un {@link String} con el nombre de la {@link Terminal}
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    }
    
    /**
     * Obtiene información sobre las {@link PuertaEmbarque} instaladas en la {@link Terminal}
     * @return Un {@link TreeSet} que contiene las {@link PuertaEmbarque}
     */
    public TreeSet<PuertaEmbarque> GetPuertasEmbarque()
    {
        return this.l_Puertas;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.l_Nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;        
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;
        
        final Terminal other = (Terminal) obj;
        
        return Objects.equals(this.l_Nombre, other.l_Nombre);
    }
}
