/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport.crypto;

import aeroport.persona.Cliente;

import java.math.BigInteger;

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
            MessageDigest l_MD = MessageDigest.getInstance("SHA-1");
            
            byte[] l_MessageDiggest = l_MD.digest(p_Password.getBytes());
            
            BigInteger l_BI = new BigInteger(1, l_MessageDiggest);
            
            String hashText = l_BI.toString(16);
            
            while (hashText.length() < 32)
                hashText = "0" + hashText;
            
            return hashText;
        }catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
