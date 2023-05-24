/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 *
 * Clase para no escribir la cabecera cuando se sobreescriba el archivo.
 * 
 * <p>
 *      Esta clase al heredar de {@link ObjectOutputStream} hereda todo, por lo que para más
 *      información debemos ir a la documentación de dicha clase.
 * </p>
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 23 may 2023 18:28:44
 * @my.company Ciclo Superior de Informática
 * 
 * @see ObjectOutputStream
 */

public class AeroportOutputStream extends ObjectOutputStream
{
    public AeroportOutputStream() throws IOException {}
    
    public AeroportOutputStream(OutputStream p_OutputStream) throws IOException
    {
        super(p_OutputStream);
    }
    
    @Override
    public void writeStreamHeader(){}
}