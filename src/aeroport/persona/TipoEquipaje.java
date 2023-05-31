/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import aeroport.Avion;

/**
 *
 * Enumerador para los tipos de {@link Equipaje}
 * 
 * Hay varios tipos de {@link Equipaje}
 * <ul>
 *      <li>Equipaje de Mano: Puede subirse al {@link Avion}</li>
 *      <li>Equipaje Maleta: Debe ser facturado</li>
 * </ul>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 22 may 2023 9:51:35
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public enum TipoEquipaje
{
    /**
     * Equipaje de tipo equipaje de mano.
     */
    EQUIPAJE_MANO("Mano"),
    /**
     * Equipaje de tipo maleta.
     */
    EQUIPAJE_MALETA("Maleta");
    
    /**
     * Variable read-only para el nombre
     */
    private final String l_Nombre;
    
    private TipoEquipaje(String p_String)
    {
        this.l_Nombre = p_String;
    }
    
    /**
     * Obtiene el nombre del {@link TipoEquipaje}
     * @return Un {@link String} con una breve descripción del {@link TipoEquipaje}
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    } 
}
