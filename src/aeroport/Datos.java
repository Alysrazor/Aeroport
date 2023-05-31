/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport;

import aeroport.persona.Cliente;

import static java.lang.System.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para pedir los datos al usuario.
 *
 * <p>
 * Esta clase contiene todos los métodos estáticos para pedir los datos a los
 * {@link Cliente}. No se puede crear instancias de esta clase.
 * </p>
 *
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 31 may 2023 9:14:50
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public class Datos 
{

    /**
     * Excepción personalizada para los DNI de los {@link Cliente}
     */
    public static class NotADNIException extends Exception
    {
        public NotADNIException() {}

        public NotADNIException(String p_Msg) 
        {
            super(p_Msg);
        }
    }

    private static Scanner l_Teclado = new Scanner(in), l_TecladoNum = new Scanner(in);

    /**
     * Esta clase no está diseñada para ser invocada. Se utilizarán sus métodos
     * automáticamente.
     */
    private Datos() {}

    /**
     * Pide un {@link String} al {@link Cliente}
     *
     * <p>
     * Este método muestra un mensaje por pantalla para que el {@link Cliente}
     * sepa lo que tiene que introducir.<br><br>
     * Si deja el campo vacío o no introduce nada lanzará una
     * {@link IllegalArgumentException}<br>
     * Para que el {@link Cliente} pueda introducir un valor correcto, este
     * método atrapará la excepción lanzada mediante un bucle hasta que se
     * introduzca un valor válido para ahorrar código en el programa principal.
     *
     * </p>
     *
     * @param p_Msg El mensaje que verá el {@link Cliente}
     * @return Un {@link String}
     */
    public static String ReturnString(String p_Msg) 
    {
        String l_Valor = "";
        boolean l_OK = false;

        do 
        {
            try 
            {
                out.println(p_Msg);

                l_Valor = l_Teclado.nextLine();
                
                if (l_Valor.isEmpty()) 
                    throw new IllegalArgumentException("No puedes dejar el campo vacío.");
                

                l_OK = true;
            } catch (IllegalArgumentException e) 
            {
                out.println(e);
            }
        } while (!l_OK);

        return l_Valor;
    }

    /**
     * Pide al {@link Cliente} un DNI.
     * 
     * <p>
     *      El funcionamiento es el mismo que {@link #ReturnString} pero además
     *      comprueba si el {@link String} que se le pasa por parámetro es un formato DNI adecuado.<br><br>
     *      En caso de no serlo, lanzará una excepción de tipo {@link NotADNIException}
     * </p>
     * @param p_Msg El mensaje que ve el {@link Cliente}
     * @return Un {@link String}
     * @see ReturnString
     */
    public static String ReturnDNI(String p_Msg) 
    {
        String l_Valor = "";
        boolean l_OK = false;

        do 
        {
            try 
            {
                out.println(p_Msg);

                l_Valor = l_Teclado.nextLine();
                
                Pattern l_Pattern = Pattern.compile("[0-9]{8}[A-Z]");
                Matcher l_Matcher = l_Pattern.matcher(l_Valor);

                if (l_Valor.isEmpty()) 
                    throw new IllegalArgumentException("No puedes dejar el campo vacío.");
                
                if (l_Matcher.matches())
                    throw new NotADNIException("No has introducido un DNI válido.");

                l_OK = true;
            } catch (IllegalArgumentException|NotADNIException e) 
            {
                out.println(e);
            }
        } while (!l_OK);

        return l_Valor;
    }
    
    /**
     * Pide al {@link Cliente} una fecha.
     * 
     * <p>
     *      Para que la fecha sea correcta debe tener el siguiente formato
     *      YYYY-MM-DD, donde Y es año, M mes y D el día e intentará convertir
     *      el {@link String} en {@link LocalDate}
     * </p>
     * 
     * @param p_Msg El mensaje que ve el {@link Cliente}
     * @return Una {@link LocalDate}
     */
    public static LocalDate ReturnLocalDate(String p_Msg)
    {
        LocalDate l_Value = null;
        boolean l_OK = false;

        do
        {
            try
            {
                out.println(p_Msg);
                l_Value = LocalDate.parse(l_Teclado.nextLine());

                l_OK = true;
            }
            catch(DateTimeParseException e)
            {
                out.println(e.getMessage());
            }
        }while(!l_OK);
        
        return l_Value;
    }

    /**
     * Pide al {@link Cliente} un número.
     * 
     * @param p_Msg El mensaje que ve el {@link Cliente}
     * @return Un {@code int}
     */
    public static int ReturnInteger(String p_Msg)
    {
        int l_Value = 0;
        boolean l_OK = false;

        do
        {
            try
            {
                out.println(p_Msg);
                l_Value = l_TecladoNum.nextInt();

                l_OK = true;
            }
            catch(InputMismatchException|IllegalArgumentException e)
            {
                out.println(e.getMessage());
            }
        }while(!l_OK);
        
        return l_Value;
    }
}
