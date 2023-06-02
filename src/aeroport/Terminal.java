/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.TreeSet;


/**
 *
 * Clase que define la {@link Terminal} del {@link Aeroport} donde 
 * estarán las {@link PuertaEmbarque}
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @my.fecha 19 may 2023 18:10:52
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */

public class Terminal implements Serializable
{
    /**
     * Número de la Terminal
     */
    private int l_Numero;
    /**
     * El nombre de la {@link Terminal}
     */
    private String l_Nombre;
    
    /**
     * Un {@link TreeSet} de {@link PuertaEmbarque}
     */
    private TreeSet<PuertaEmbarque> l_Puertas;
    
    /**
     * Constructor por defecto de las {@link Terminal}
     * @param p_Numero El número de la {@link Terminal}
     * @param p_Nombre El nombre de la {@link Terminal}
     */
    public Terminal(int p_Numero, String p_Nombre)
    {
        this.l_Numero = p_Numero;
        this.l_Nombre = p_Nombre;
        this.l_Puertas = new TreeSet<>();
    }
    
    /**
     * Constructor secundario de las {@link Terminal} donde cada instancia tendrá sus {@link PuertaEmbarque}
     * @param p_Numero El número de la {@link Terminal}
     * @param p_Nombre El nombre de la {@link Terminal}
     * @param p_Puertas Un {@link TreeSet} con las {@link PuertaEmbarque}
     */
    public Terminal(int p_Numero, String p_Nombre, TreeSet<PuertaEmbarque> p_Puertas)
    {
        this.l_Numero = p_Numero;
        this.l_Nombre = p_Nombre;
        this.l_Puertas = new TreeSet<>(p_Puertas);
    }
    
    /**
     * Obtiene el número de la {@link Terminal}
     * @return Un {@code int} con el número de la {@link Terminal}
     */
    public int GetNumero()
    {
        return this.l_Numero;
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
    
    /**
     * Añade una {@link PuertaEmbarque} a la {@link Terminal}
     * @param p_Puerta La {@link PuertaEmbarque}
     * @return <ul>
     *              <li>{@code true} si lo ha podido añadir</li>
     *              <li>{@code false} si no ha podido añadir al nuevo {@link PuertaEmbarque}</li>
     *          </ul>
     */
    public boolean AddPuertaEmbarque(PuertaEmbarque p_Puerta)
    {
        return this.l_Puertas.add(p_Puerta);
    }
    
    /**
     * 
     * Elimina una {@link PuertaEmbarque} de la {@link Terminal}
     * 
     * @param p_Puerta La {@link PuertaEmbarque}
     * @return <ul>
     *              <li>{@code true} si lo ha podido añadir</li>
     *              <li>{@code false} si no ha podido añadir al nuevo {@link PuertaEmbarque}</li>
     *          </ul> 
     */
    public boolean RemovePuertaEmbarque(PuertaEmbarque p_Puerta)
    {        
        return this.l_Puertas.remove(p_Puerta);
    }

    /**
     * Devuelve una {@link PuertaEmbarque} aleatoria.
     * @return Una {@link PuertaEmbarque}
     */
    public PuertaEmbarque GetRandomPuertaEmbarque()
    {
        ArrayList<PuertaEmbarque> l_ALPuertas = new ArrayList<>(this.l_Puertas); 
        Random l_Rand = new Random();

        return l_ALPuertas.get(l_Rand.nextInt(l_ALPuertas.size()));
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
    
    @Override
    public String toString()
    {
        return String.format("Informacion de la Terminal: %n"
                + "Nombre: %s%n",
                this.l_Nombre);
    }
}
