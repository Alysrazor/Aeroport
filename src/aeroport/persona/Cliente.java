/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport.persona;

import aeroport.Aeroport;

import aeroport.crypto.CryptSHA1;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Objects;

/**
 *
 * Clase que representa a un {@link Cliente} en el {@link Aeroport}
 * 
 * 
 * El {@link Cliente} puede realizar las operaciones básicas y comunes de un {@link Aeroport}
 *     <ul>
 *         <li>Realizar una reserva (lo simulamos ya que no podemos acceder aún a una página web)</li>
 *         <li>Revisar, modificar y actualizar sus datos</li>
 *         <li>Modificar las reservas</li>
 *         <li>Ver las reservas realizadas</li>
 *          <li>Registrar su equipaje</li>
 *          <li>Embarcar en su avión</li>
 *     </ul>
 * 
 * 
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 11:51:20
 */
public class Cliente extends Persona
{
    /**
     * El nombre de usuario del {@link Cliente}
     */
    private String l_Usuario;
    
    /**
     * La contraseña del {@link Cliente}
     */
    private String l_Password;
    
    /**
     * El E-Mail del {@link Cliente}
     */
    private String l_Email;
    
    /**
     * El {@link Equipaje} de {@link Cliente}
     */
    private HashSet<Equipaje> l_Equipaje = new HashSet<>();
    
    /**
     * Las {@link Reserva} que tiene el {@link Cliente}
     */
    private HashSet<Reserva> l_Reservas = new HashSet<>();
    
    /**
     * Crea un nuevo {@link Cliente} con su nombre de usuario, contraseña y correo electrónico.
     * 
     * <p>
     *  Por lógica, dos clientes no podrán tener el mismo nombre de usuario, por lo cuál un {@link Cliente} no podrá darse de alta si ya existe
     *  ese nombre de usuario.<br><br>Adicionalmente, no se podrán crear usuarios si el correo electrónico ya está siendo usado por
     *  otro {@link Cliente}.
     * </p>
     * 
     * @param p_DNI El DNI del {@link Cliente}
     * @param p_Nombre El Nombre del {@link Cliente}
     * @param p_Apellidos Los Apellidos del {@link Cliente}
     * @param p_FechaNac La Fecha de Nacimiento del {@link Cliente}
     * @param p_Usuario El nombre de usuario del {@link Cliente}
     * @param p_Password La contraseña del {@link Cliente}
     * @param p_Email El Email del {@link Cliente}
     */
    public Cliente(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_Usuario, String p_Password, String p_Email)
    {
        super(p_DNI, p_Nombre, p_Apellidos, p_FechaNac);
        this.l_Usuario = p_Usuario;
        this.l_Password = CryptSHA1.EncryptPassword(p_Password);
        this.l_Email = p_Email;
    }
    
    
    /**
     * Crea un nuevo {@link Cliente} con su nombre de usuario, contraseña, correo electrónico y equipaje.
     * 
     * <p>
     *  Por lógica, dos clientes no podrán tener el mismo nombre de usuario, por lo cuál un {@link Cliente} no podrá darse de alta si ya existe
     *  ese nombre de usuario.<br><br>Adicionalmente, no se podrán crear usuarios si el correo electrónico ya está siendo usado por
     *  otro {@link Cliente}.
     * </p>
     * 
     * @param p_DNI El DNI del {@link Cliente}
     * @param p_Nombre El Nombre del {@link Cliente}
     * @param p_Apellidos Los Apellidos del {@link Cliente}
     * @param p_FechaNac La Fecha de Nacimiento del {@link Cliente}
     * @param p_Usuario El nombre de usuario del {@link Cliente}
     * @param p_Password La contraseña del {@link Cliente}
     * @param p_Email El Email del {@link Cliente}
     * @param p_Equipaje El equipaje que lleva {@link Cliente} si no está vacío.
     */
    public Cliente(String p_DNI, String p_Nombre, String p_Apellidos, LocalDate p_FechaNac, String p_Usuario, String p_Password, String p_Email, HashSet<Equipaje> p_Equipaje)
    {
        super(p_DNI, p_Nombre, p_Apellidos, p_FechaNac);
        this.l_Usuario = p_Usuario;
        this.l_Password = CryptSHA1.EncryptPassword(p_Password);
        this.l_Email = p_Email;
        if (!p_Equipaje.isEmpty())
            this.l_Equipaje = new HashSet<>(p_Equipaje);
    }
    
    /**
     * Obtiene el nombre de usuario del {@link Cliente}
     * @return Un {@link String} que contiene el nombre de usuario.
     */
    public String GetUsuario()
    {
        return this.l_Usuario;
    }
    
    /**
     * Cambia el nombre del usuario del {@link Cliente}
     * @param p_Usuario El nuevo nombre de usuario.
     * @throws IllegalArgumentException Si ya existe un nombre de usuario con el nombre que se le pasa por parámetro. 
     */
    public void SetUsuario(String p_Usuario) throws IllegalArgumentException
    {
        //TODO Comprobar que no existe ya un nombre así.
        this.l_Usuario = p_Usuario;
    }
    
    /**
     * Obtiene la contraseña del {@link Cliente}
     * @return Un {@link String} que contiene la contraseña.
     */
    public String GetPassword()
    {
        return this.l_Password;
    }
    
    /**
     * Cambia la contraseña del {@link Cliente}
     * @param p_Password La nueva contraseña
     */
    public void SetPassword(String p_Password)
    {
        this.l_Password = CryptSHA1.EncryptPassword(p_Password);
    }
    
    /**
     * Obtiene el correo electrónico del {@link Cliente}
     * @return Un {@link String} que contiene el correo electrónico.
     */
    public String GetEmail()
    {
        return this.l_Email;
    }
    
    /**
     * Cambia el E-Mail del {@link Cliente}
     * @param p_Email El nuevo E-Mail
     * @throws IllegalArgumentException Si ya hay un usuario con ese E-Mail.
     */
    public void SetEmail(String p_Email) throws IllegalArgumentException
    {
        // TODO Comprobar que no existe ya un E-Mail así
        this.l_Email = p_Email;
    }
    
    /**
     * Obtiene la información completa del equipaje del {@link Cliente}
     * @return Un {@link HashSet} de {@link Equipaje}
     */
    public String GetEquipaje()
    {
        throw new UnsupportedOperationException("Método no implementado.");
    }
    
    /**
     * Añade una {@link Reserva} a las {@link Reserva} del {@link Cliente}
     * @param p_Reserva La {@link Reserva} que el {@link Cliente} ha realizado.
     * @return <ul>
     *              <li>{@code true} si se ha podido añadir.</li>
     *              <li>{@code false} si no se ha podido añadir</li>
     *          </ul>
     */
    public boolean AddReserva(Reserva p_Reserva)
    {
        return l_Reservas.add(p_Reserva);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.l_DNI);
        hash = 17 * hash + Objects.hashCode(this.l_Usuario);
        hash = 17 * hash + Objects.hashCode(this.l_Email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final Cliente other = (Cliente) obj;
        
        if (!Objects.equals(this.l_DNI, other.l_DNI)) return false;
        if (!Objects.equals(this.l_Usuario, other.l_Usuario)) return false;
        
        return Objects.equals(this.l_Email, other.l_Email);
    }
    
    
    @Override
    public String toString()
    {
        return String.format("Información del Cliente:%n"
                + "DNI: %s%n"
                + "Nombre: %s%n"
                + "Apellidos:%s%n"
                + "Fecha de Nacimiento: %s%n"
                + "Nombre de Usuario: %s%n"
                + "E-Mail: %s%n",
                super.l_DNI,
                super.l_Nombre,
                super.l_Apellidos,
                super.l_FechaNac,
                this.l_Usuario,
                this.l_Email);
    }
}
