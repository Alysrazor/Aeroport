/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aeroport;

import aeroport.persona.Cliente;

import java.io.Serializable;
import java.util.Objects;

import java.util.TreeSet;

/**
 *
 * Clase que representa a un {@link Aeroport}
 * 
 * <p>
 *      Esta clase es el nucleo de todo este programa sobre las demas.
 *      Todas las demas clases dependen de estas para poder realizar sus
 *      operaciones.
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19/05/2023 11:52:50
 */
public class Aeroport implements Serializable
{
    // Sets
    private TreeSet<Company> l_Companies;
    private TreeSet<Pista> l_Pistas;
    private TreeSet<Terminal> l_Terminales;
    private TreeSet<Cliente> l_Clientes;
    
    // Tipos
    public Asiento[][] l_Asientos;
    
    /**
     * El nombre del {@link Aeroport}
     */
    private final String l_Nombre;
    
    /**
     * El codigo del {@link Aeroport}
     */
    private final String l_CodAeroport;
    
    /**
     * Constructor por defecto del {@link Aeroport}
     * @param p_Nombre El nombre del {@link Aeroport}
     * @param p_CodAeroport El codigo del {@link Aeroport}
     * @param p_Companies Las {@link Company} que tiene el {@link Aeroport}
     * @param p_Pistas Las {@link Pista} del {@link Aeroport}
     * @param p_Terminales Las {@link Terminal} del {@link Aeroport}
     * @param p_Clientes Los {@link Cliente} registrados en el {@link Aeroport}
     */
    public Aeroport(String p_Nombre, String p_CodAeroport, TreeSet<Company> p_Companies, TreeSet<Pista> p_Pistas, TreeSet<Terminal> p_Terminales, TreeSet<Cliente> p_Clientes)
    {
        this.l_Nombre = p_Nombre;
        this.l_CodAeroport = p_CodAeroport;
        this.l_Companies = new TreeSet<>(p_Companies);
        this.l_Pistas = new TreeSet<>(p_Pistas);
        this.l_Terminales = new TreeSet<>(p_Terminales);
        this.l_Clientes = new TreeSet<>(p_Clientes);
    }
    
    /**
     * Obtiene el nombre del {@link Aeroport}
     * @return Un {@link String}
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    }
    
    /**
     * Obtiene el codigo del link {@link Aeroport}
     * @return 
     */
    public String GetCodigoAeroport()
    {
        return this.l_CodAeroport;
    }
    
    /**
     * Obtiene las {@link Company} que tiene el {@link Aeroport}
     * @return Un {@link TreeSet} de tipo {@link Company}
     */
    public TreeSet<Company> GetCompanies()
    {
        return this.l_Companies;
    }
    
    /**
     * Busca una {@link Company} en el {@link Aeroport}
     * 
     * <p>
     *      Dado un  {@link String} que se le pasa como parámetro el {@link Aeroport}
     *      intentará buscar una {@link Company} que tenga el mismo nombre, ya que
     *      como el {@link Aeroport} tiene un {@link TreeSet} de {@link Company} no podrán
     *      haber repetidas.<br><br>
     *      Como pueden ser buscadas por minúscula o mayúscula, la búsqueda utilizará el
     *      método {@code String.equalsIgnoreCase(anotherString)}
     * </p>
     * @param p_CompanyName El nombre que se tiene que buscar.
     * @return <ul>
     *                  <li>La {@link Company} cuyo nombre sea igual.</li>
     *                  <li>{@code null} si no encuentra ninguna {@link Company} con ese nombre</li>
     *              </ul>
     */
    public Company BuscarCompany(String p_CompanyName)
    {
        for (Company p_Company : l_Companies)
        {
            if (p_Company.GetNombre().equalsIgnoreCase(p_CompanyName))
                return p_Company;
        }
        
        return null;
    }
    
    /**
     * Obtiene las {@link Pista} del {@link Aeroport}
     * @return Un {@link TreeSet} de tipo {@link Pista}
     */
    public TreeSet<Pista> GetPistas()
    {
        return this.l_Pistas;
    }
    
    /**
     * Obtiene las {@link Terminal} del {@link Aeroport}
     * @return Un {@link TreeSet} de tipo {@link Terminal}
     */
    public TreeSet<Terminal> GetTerminales()
    {
        return this.l_Terminales;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.l_Nombre);
        hash = 53 * hash + Objects.hashCode(this.l_CodAeroport);
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
        final Aeroport other = (Aeroport) obj;
        if (!Objects.equals(this.l_Nombre, other.l_Nombre)) {
            return false;
        }
        return Objects.equals(this.l_CodAeroport, other.l_CodAeroport);
    }
    
    @Override
    public String toString()
    {
        return String.format("Informacion sobre el Aeroport:%n"
                + "Nombre: %s%n"
                + "Codigo del Aeroport: %s%n",
                this.l_Nombre,
                this.l_CodAeroport);
    }
}
