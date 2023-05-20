/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import java.util.Objects;
import java.util.TreeSet;


/**
 *
 * Clase que define la {@link Terminal} del {@link Aeroport} donde 
 * estarán las {@link PuertaEmbarque}
 * 
 * @author Sergio Capilla Cabadés
 * @fecha 19 may 2023 18:10:52
 * @company Ciclo Superior de Informática
 */

public class Terminal 
{
    private String l_Nombre;
    private TreeSet<PuertaEmbarque> l_Puertas;
    
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
