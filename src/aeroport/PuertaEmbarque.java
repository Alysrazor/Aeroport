/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import aeroport.persona.Cliente;

import java.io.Serializable;


/**
 *
 * Clase que define las Puertas de Embarque de un {@link Aeroport}
 * instaladas en una {@link Terminal}
 * 
 * <p>
 *      En ella los {@link Cliente} realizarán sus operaciones de embarque y desembarque
 *      así como el personal del {@link Aeroport}
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 18:17:20
 * @my.company Ciclo Superior de Informática
 */

public class PuertaEmbarque implements Comparable<PuertaEmbarque>, Serializable
{
    /**
     * Contador para la {@link PuertaEmbarque}
     */
    private static int l_Count = 1;
    
    /**
     * Terminal donde está instalada esta {@link PuertaEmbarque}
     */
    //private Terminal l_Terminal;
    
    /**
     * Identificador de la Puerta.
     */
    private final int l_Puerta;
    
    /**
     * Avión que efectuará el {@link Vuelo}
     */
    private Avion l_Avion;
    
    public PuertaEmbarque(Avion p_Avion)
    {
        this.l_Puerta = l_Count++;
        this.l_Avion = null;
    }
    
    /**
     * Obtiene el número de la {@link PuertaEmbarque}
     * @return Un {@code int}
     */
    public int GetPuerta()
    {
        return this.l_Puerta;
    }
    
    /**
     * Obtiene la {@link Terminal} donde se encuentra alojada la {@link PuertaEmbarque}
     * @return Una {@link Terminal}
     */
    public Terminal GetTerminal()
    {
        return null; //TODO Devolver la Terminal correctamente
    }
    
    /**
     * Obtiene el {@link Avion} estacionado en la {@link PuertaEmbarque}
     * @return Un {@link Avion} que está estacionado.
     */
    public Avion GetAvion()
    {
        return this.l_Avion;
    }
    
    /**
     * Establece el nuevo {@link Avion} que está en la {@link PuertaEmbarque}.
     * 
     * Acepta valores {@code null} e indicará que no hay ningún {@link Avion} en la {@link PuertaEmbarque}
     * @param p_Avion El {@link Avion}
     */
    public void SetAvion(Avion p_Avion)
    {
        this.l_Avion = p_Avion;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 53 * hash + this.l_Puerta;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;        
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;
        
        final PuertaEmbarque other = (PuertaEmbarque) obj;
        
        return this.l_Puerta == other.l_Puerta;
    }
    
    @Override
    public int compareTo(PuertaEmbarque p_Obj)
    {
        return this.l_Puerta < p_Obj.l_Puerta ? this.l_Puerta : p_Obj.l_Puerta;
    }
}
