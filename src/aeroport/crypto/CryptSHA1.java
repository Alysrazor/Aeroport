/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport.crypto;

import aeroport.persona.Cliente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * Clase para encriptar las contraseñas del {@link Cliente}
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 19 may 2023 16:35:47
 * @my.company Ciclo Superior de Informática
 */
public class CryptSHA1 
{
    /**
     * Encripta la contraseña que se le pasa por parámetro a la encriptación SHA-1
     * @param p_Password La contraseña
     * @return Un {@link String} que es la contraseña encriptada.
     */
    public static String EncryptPassword(String p_Password)
    {
        try 
        {
            // Obtener la instancia de MessageDigest para SHA-1
            MessageDigest l_MDigest = MessageDigest.getInstance("SHA-1");
        
            // Convertir el input en bytes y calcular el hash
            byte[] hashBytes = l_MDigest.digest(p_Password.getBytes());
        
            // Convertir el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) 
                hexString.append(String.format("%02x", b));
            
            
            return hexString.toString();
        }catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
