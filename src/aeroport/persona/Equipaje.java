/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import java.io.Serializable;

import java.util.Objects;

/**
 *
 * Clase que representa a un equipaje que puede llevar un {@link Cliente}
 * 
 * @author Luis Tomás Sahuquillo
 * @dev.main Luis Tomás Sahuquillo
 * @dev.codevs
 * @my.fecha 22 may 2023 10:24:40
 * @my.company Ciclo Superior de Informática
 */
public class Equipaje implements Serializable
{
    private String CodEquipaje;
    private Cliente Cliente ;
    private TipoEquipaje tipo;
    private double peso;

    public Equipaje(String CodEquipaje, Cliente Cliente, TipoEquipaje tipo, double peso) {
        this.CodEquipaje = CodEquipaje;
        this.Cliente = Cliente;
        this.tipo = tipo;
        this.peso = peso;
    }

    public String getCodEquipaje() {
        return CodEquipaje;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public TipoEquipaje getTipo() {
        return tipo;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setTipo(TipoEquipaje tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.CodEquipaje);
        hash = 53 * hash + Objects.hashCode(this.Cliente);
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
        final Equipaje other = (Equipaje) obj;
        if (!Objects.equals(this.CodEquipaje, other.CodEquipaje)) {
            return false;
        }
        return Objects.equals(this.Cliente, other.Cliente);
    }
    
    
}
