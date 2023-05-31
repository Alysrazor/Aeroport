/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

/**
 *
 * Enumerador para los tipos de billete.<br><br>
 * 
 * Hay varios tipos de billete:
 * 
 * <ul>
 *      <li>Primera clase: Ofrecen la mayor de las comodidades.</li>
 *      <li>Clase de Negocios: Para aquellos que realizan viajes de negocios.</li>
 *      <li>Clase Turista: Clase más común y la más econónica.</li>
 * </ul>
 * 
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 12:28:20
 * @my.company Ciclo Superior de Informática
 */
public enum TipoBillete
{
    /**
     * El billete para Primera Clase que ofrece los mejores servicios.
     */
    FIRST_CLASS("Primera Clase"),
    /**
     * El billete para la clase de negocios, solamente para aquellos que realicen viajes de negocios.
     */
    BUSINESS("Business"),
    /**
     * El billete más común para la Clase Turista.
     */
    TOURIST("Clase Turista");
    
    /**
     * Variable para el nombre
     */
    private String l_Nombre;
    
    private TipoBillete(String p_String)
    {
        this.l_Nombre = p_String;
    }
    
    /**
     * Obtiene el nombre del {@link TipoBillete}
     * @return Un {@link String} con una breve descripción del {@link TipoBillete}
     */
    public String GetNombre()
    {
        return this.l_Nombre;
    } 
}
