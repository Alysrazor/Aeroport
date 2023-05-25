/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import aeroport.persona.Cliente;
import aeroport.persona.Reserva;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.Objects;

/**
 *
 * La clase {@link Vuelo} contiene la información principal de los vuelos para el {@link Aeroport}
 * 
 * <p>
 *      Cada uno de los {@link Vuelo} tiene un identificador único que lo diferencia del resto, por
 *      lo tanto cada {@link Vuelo} será único.
 * </p>
 * 
 * <p>
 *      Los {@link Cliente} pueden hacer {@link Reserva} de estos vuelos y pueden seleccionar el tipo
 *      de billetes.
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 16:20:03
 * @my.company Ciclo Superior de Informática
 * 
 */
public class Vuelo implements Serializable
{
    /**
     * Un identificador único para cada {@link Vuelo}
     */
    private String l_Identificador;
    
    /**
     * La {@link PuertaEmbarque} que usarán los pasajeros para subirse al {@link Avión}
     */
    private PuertaEmbarque l_Puerta;
    
    /**
     * El Origen del {@link Vuelo}
     */
    private String l_Origen;
    
    /**
     * El Destino del {@link Vuelo}
     */
    private String l_Destino;
    
    /**
     * Día y hora que el {@link Vuelo} sale del {@link Aeroport}
     */
    private LocalDateTime l_HoraVuelo;
    
    /**
     * Escalas que tiene el {@link Vuelo}
     */
    private int l_Escalas;
    
    /**
     * Constructor principal de {@link Vuelo}
     * 
     * <p>
     *      Crea una instancia de {@link Vuelo} que puede usarse para hacer la {@link Reserva} de vuelos usables
     *      por los {@link Cliente}.
     * </p>
     * @param p_Identificador El identificador único de cada {@link Vuelo}
     * @param p_Puerta La {@link PuertaEmbarque} que estará alojada en su {@link Terminal}
     * @param p_Origen El aeropuerto de origen del {@link Vuelo}.
     * @param p_Destino El destino del {@link Vuelo}.
     * @param p_HoraVuelo La hora de despegue.
     */
    public Vuelo(String p_Identificador, PuertaEmbarque p_Puerta, String p_Origen, String p_Destino, LocalDateTime p_HoraVuelo)
    {
        this.l_Identificador = "a"; // TODO Generar un string aleatorio.
        this.l_Puerta = p_Puerta;
        this.l_Origen = p_Origen;
        this.l_Destino = p_Destino;
        this.l_HoraVuelo = p_HoraVuelo;
        this.l_Escalas = 0;
    }
    
    /**
     * Constructor secundario de {@link Vuelo} que incluye escalas.
     * 
     * <p>
     *      Crea una instancia de {@link Vuelo} que puede usarse para hacer la {@link Reserva} de vuelos usables
     *      por los {@link Cliente}.
     * </p>
     * @param p_Identificador El identificador único de cada {@link Vuelo}
     * @param p_Puerta La {@link PuertaEmbarque} que estará alojada en su {@link Terminal}
     * @param p_Origen El aeropuerto de origen del {@link Vuelo}.
     * @param p_Destino El destino del {@link Vuelo}.
     * @param p_HoraVuelo La hora de despegue.
     * @param p_Escalas Las escalas del {@link Vuelo}
     */
    public Vuelo(String p_Identificador, PuertaEmbarque p_Puerta, String p_Origen, String p_Destino, LocalDateTime p_HoraVuelo, int p_Escalas)
    {
        this.l_Identificador = "a"; // TODO Generar un string aleatorio.
        this.l_Puerta = p_Puerta;
        this.l_Origen = p_Origen;
        this.l_Destino = p_Destino;
        this.l_HoraVuelo = p_HoraVuelo;
        this.l_Escalas = p_Escalas;
    }
    
    /**
     * Obtiene el identificador del {@link Vuelo}
     * @return Un {@link String} con el identificador del {@link Vuelo}
     */
    public String GetIdentificador()
    {
        return this.l_Identificador;
    }
    
    /**
     * Obtiene la {@link PuertaEmbarque} del {@link Vuelo}
     * @return Una {@link PuertaEmbarque}
     */
    public PuertaEmbarque GetPuertaEmbarque()
    {
        return this.l_Puerta;
    }
    
    /**
     * Obtiene el origen del {@link Vuelo}
     * @return Un {@link String} que contiene el origen del {@link Vuelo}
     */
    public String GetOrigen()
    {
        return this.l_Origen;
    }
    
    /**
     * Obtiene el destino del {@link Vuelo}
     * @return Un {@link String} que contiene el destino del {@link Vuelo}
     */
    public String GetDestino()
    {
        return this.l_Destino;
    }
    
    /**
     * Obtiene la Fecha y la Hora del {@link Vuelo}
     * @return Un {@link LocalDateTime} del {@link Vuelo}
     */
    public LocalDateTime GetHoraVuelo()
    {
        return this.l_HoraVuelo;
    }
    
    /**
     * Establece una nueva fecha y hora para el {@link Vuelo}
     * @param p_Time La nueva fecha y hora del {@link Vuelo}
     * @throws IllegalArgumentException Si se intenta poner un {@link LocalDateTime} anterior a la fecha y hora del {@link Vuelo}
     */
    public void SetHoraVuelo(LocalDateTime p_Time) throws IllegalArgumentException
    {
        if (this.l_HoraVuelo.isAfter(p_Time))
            throw new IllegalArgumentException("Debes especificar una fecha y hora posteriores.");
        
        this.l_HoraVuelo = p_Time;
    }
    
    /**
     * Obtiene las escalas del {@link Vuelo}
     * @return Un {@link Integer} con el número de escalas del {@link Vuelo}
     */
    public int GetEscalas()
    {
        return this.l_Escalas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.l_Identificador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vuelo other = (Vuelo) obj;
        return Objects.equals(this.l_Identificador, other.l_Identificador);
    }
    
    //TODO toString
}
