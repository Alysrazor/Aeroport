/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import aeroport.Aeroport;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Clase que representa a una persona en el {@link Aeroport}
 * 
 * <p>
 *      No se puede instanciar una {@link Persona} porque cada {@link Persona}
 *      tiene su propia funcionalidad, ya sea {@link Cliente} o {@link Empleado}
 * </p>
 * 
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 23 11:08:10
 * @my.company Ciclo Superior de Informática
 */
public abstract class Persona implements Comparable<Persona>, Serializable
{
    /**
     * Atributo que identifica el DNI de {@link Persona}
     */
    protected String l_DNI;
    
    /**
     * Atributo que identifica el Nombre de {@link Persona}
     */
    protected String l_Nombre;
    
    /**
     * Atributo que identifica los apellidos de {@link Persona}
     */
    protected String l_Apellidos;
    
    protected LocalDate l_FechaNac;
    
    /**
     * Constructor por defecto de Persona con todos sus parámetros
     * @param p_DNI El DNI.
     * @param p_Nombre El nombre.
     * @param p_Apellidos Los apellidos.
     * @param p_FechaNac La my.fecha de nacimiento.
     */
    public Persona(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac)
    {
        this.l_DNI = p_DNI;
        this.l_Nombre = p_Nombre;
        this.l_Apellidos = p_Apellidos;
        this.l_FechaNac = p_FechaNac;
    }
    
    /**
     * Obtiene el DNI de la {@link Persona}
     * @return Un {@link String} que contiene el DNI de {@link Persona}
     */
    public String GetDNI()
    {
        return this.l_DNI;
    }
    
    /**
     * Obtiene el Nombre de la {@link Persona}
     * @return Un {@link String} que contiene el Nombre de {@link Persona}
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    }
    
    /**
     * Obtiene los Apellidos de la {@link Persona}
     * @return Un {@link String} que contiene los Apellidos de la {@link Persona}
     */
    public String GetApellidos()
    {
        return this.l_Apellidos;
    }
    
    /**
     * Obtiene la my.fecha de nacimiento de la {@link Persona}
     * @return Un {@link LocalDate} formateado con {@link DateTimeFormatter}
     */
    public String GetFechaNacimieneto()
    {
        return this.l_FechaNac.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.l_DNI);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;        
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;
        
        final Persona other = (Persona) obj;
        return Objects.equals(this.l_DNI, other.l_DNI);
    }
    
    @Override
    public int compareTo(Persona p_Obj)
    {
        if (this.l_DNI.compareTo(p_Obj.l_DNI) < 0) return -1;
        else if (this.l_DNI.compareTo(p_Obj.l_DNI) > 0) return 1;
        
        return 0;
    }
}
