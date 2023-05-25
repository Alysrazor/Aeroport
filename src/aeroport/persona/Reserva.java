/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import aeroport.Vuelo;

import java.io.Serializable;

import java.util.Objects;

/**
 *
 * El {@link Cliente} puede realizar una {@link Reserva} de un {@link Vuelo}
 * 
 * <p>
 *      Una instancia de reserva solo podrá ser de un {@link Cliente} al que se le adjudicará un {@link Vuelo}
 *      y contendrá toda la información básica y necesaria para que el {@link Cliente} lo entienda.
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 12:08:50
 */
public class Reserva implements Serializable
{
    /**
     * El {@link Vuelo} que hace referencia esta reserva.
     */
    private Vuelo l_Vuelo;
    
    /**
     * El nombre de Origen y Destino del {@link Vuelo}
     */
    private String l_NombreReserva;
    
    /**
     * {@link Cliente} que hace la {@link Reserva}
     */
    private Cliente l_Cliente;
    
    /**
     * El Tipo del billete
     */
    private TipoBillete l_TipoBillete;
    
    /**
     * Constructor por defecto dónde se crea una {@link Reserva}.
     * 
     * <p>
     *      La {@link Reserva} almacena los datos que se le pasan para
     *      su posterior consulta, modificación o suspensión en caso
     *      de ser necesario.
     * </p>
     * @param p_Vuelo El {@link Vuelo} al que está vinculado la {@link Reserva}
     * @param p_Cliente El {@link Cliente} que ha hecho la {@link Reserva}
     * @param p_TipoBillete El {@link TipoBillete} de la {@link Reserva}
     */
    public Reserva(Vuelo p_Vuelo, Cliente p_Cliente, TipoBillete p_TipoBillete)
    {
        // TODO añadir nombre reserva
        this.l_Vuelo = p_Vuelo;
        this.l_Cliente = p_Cliente;
        this.l_TipoBillete = p_TipoBillete;
    }
    
    /**
     * Obtiene la información del {@link Vuelo} al que está vinculado esta {@link Reserva}
     * @return Un {@link String} con la información del {@link Vuelo}
     */
    public String GetInformacionVuelo()
    {
        return this.l_Vuelo.toString();
    }
    
    /**
     * Obtiene el {@link Cliente} que ha realizado esta {@link Reserva}
     * @return Un {@link Cliente}
     */
    public Cliente GetCliente()
    {
        return this.l_Cliente;
    }
    
    /**
     * Obtiene el {@link TipoBillete} de la {@link Reserva}
     * @return Un {@link TipoBillete}
     */
    public TipoBillete GetTipoBillete()
    {
        return this.l_TipoBillete;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.l_Vuelo);
        hash = 97 * hash + Objects.hashCode(this.l_Cliente);
        hash = 97 * hash + Objects.hashCode(this.l_TipoBillete);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;        
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;
        
        final Reserva other = (Reserva) obj;
        
        if (!Objects.equals(this.l_Vuelo, other.l_Vuelo)) return false;        
        if (!Objects.equals(this.l_Cliente, other.l_Cliente)) return false;
        
        return this.l_TipoBillete == other.l_TipoBillete;
    }
    
    @Override
    public String toString()
    {
        return String.format("Información de la Reserva: %n"
                + "Vuelo: %s%n"
                + "Nombre Billete: %s%n"
                + "Origen: %s%n"
                + "Destino: %s%n"
                + "Tipo de Billete: %s%n"
                + "Reservado por: %s%n",
                this.l_Vuelo,
                this.l_NombreReserva,
                this.l_Vuelo.GetOrigen(),
                this.l_Vuelo.GetDestino(),
                this.l_TipoBillete.GetNombre(),
                this.l_Cliente.GetDNI());
    }
}
