/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package aeroport.persona;

import aeroport.Avion;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Luis Tomás Sahuquillo
 * @dev.main Luis Tomás Sahuquillo
 * @dev.codevs
 * @my.fecha 22 may 2023 10:08:10
 * @my.company Ciclo Superior de Informática
 */
public class Piloto extends Empleado {

    private Avion Avion;

    public Piloto(String DNI, String Nombre, String apellido, LocalDate FechaNac, String CodEmpleado, Company Company, Avion Avion) {

        this.Avion = Avion;

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.Avion);
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
        final Piloto other = (Piloto) obj;
        return Objects.equals(this.Avion, other.Avion);
    }

    public Avion getAvion() {
        return Avion;
    }

    public void setAvion(Avion Avion) {
        this.Avion = Avion;
    }
}

