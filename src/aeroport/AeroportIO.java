/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import aeroport.persona.Equipaje;
import aeroport.persona.Persona;
import aeroport.persona.Reserva;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static java.lang.System.out;

import java.util.HashSet;
import java.util.TreeSet;



/**
 *
 * Clase que se encarga de añadir, actualizar y registrar en el {@link Aeroport}
 * los datos dados hacia ficheros y desde ficheros.
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @fecha 23 may 2023 15:38:49
 * @company Ciclo Superior de Informática
 */

public class AeroportIO 
{
    // Aeroport Files
    private File l_AeroportFile = new File("Aeroport.txt");
    private File l_AeroportFileBin = new File("Aeroport.dat");
    
    // Asiento Files
    private File l_AsientoFile = new File("Asiento.txt");
    private File l_AsientoFileBin = new File("Asiento.dat");
    
    // Avion Files
    private File l_AvionFile = new File("Avion.txt");
    private File l_AvionFileBin = new File("Avion.dat");
    
    // Compañía Files
    private File l_CompanyFile = new File("Company.txt");
    private File l_CompanyFileBin = new File("Company.dat");
    
    // Pista Files
    private File l_PistaFile = new File("Pista.txt");
    private File l_PistaFileBin = new File("Pista.dat");
    
    // PuertaEmbarqueFiles
    private File l_PuertasFile = new File("PuertaEmbarque.txt");
    private File l_PuertasFileBin = new File("PuertaEmbarque.dat");
    
    // Terminal Files
    private File l_TerminalFile = new File("Terminal.txt");
    private File l_TerminalFileBin = new File("Terminal.dat");
    
    // Vuelo Files
    private File l_VueloFile = new File("Vuelo.txt");
    private File l_VueloFileBin = new File("Vuelo.dat");
    
    // Equipaje Files
    private File l_EquipajeFile = new File("Equipaje.txt");
    private File l_EquipajeFileBin = new File("Equipaje.dat");
    
    // Persona Files
    private File l_PersonaFile = new File("Persona.txt");
    private File l_PersonaFileBin = new File("Persona.dat");
    
    // Reserva Files
    private File l_ReservaFile = new File("Reserva.txt");
    private File l_ReservaFileBin = new File("Reserva.dat");
    
    
    public AeroportIO() {}
    
    /**
     * Comprueba un {@link File} y crea sus directorios, si los tiene, y crea el {@link File}
     * @param p_File El {@link File} que se crea.
     * @throws IOException si ocurre algún error durante la creación.
     */
    private void CreateFileIfNotExists(File p_File) throws IOException
    {
        if (!p_File.exists())
        {
            if (!p_File.getParentFile().mkdir())
                p_File.createNewFile();
        }
    }
    
    // Aeroport IORelated
    // Output
    
    public void GuardarAeroport(Aeroport p_Aeroport)
    {
        throw new UnsupportedOperationException("Método no implementado");
        
        /*try(FileWriter p_Writer = new FileWriter(l_AeroportFile))
        {
            p_Writer.write("");
        }
        catch(IOException e)
        {
            out.println(e.getMessage());
        }*/
    }
    
    /**
     * Intenta guardar en un archivo de extensión .dat una instancia de {@link Aeroport}
     * @param p_Aeroport El Aeropuerto que se guardará en el archivo .dat
     */
    public void GuardarAeroportBin(Aeroport p_Aeroport)
    {   
        try
        {
            CreateFileIfNotExists(l_AeroportFile);
            
            try(FileOutputStream fos = new FileOutputStream(l_AeroportFileBin);
                    AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                    ObjectOutputStream oos = (l_AeroportFileBin.exists()) ? p_AeroportStream : new ObjectOutputStream(fos))
            {
                oos.writeObject(p_Aeroport);
            }
        }
        catch(IOException e)
        {
            out.println(e.getMessage());
        }
    }
    
    // Input
    
    public Aeroport LeerAeroport()
    {
        throw new UnsupportedOperationException("No se ha implementado aún el método.");
    }
    
    /**
     * Lee los {@link Aeroport} almacenados en el fichero binario de los {@link Aeroport}
     * y los almacena en un {@link HashSet} que posteriormente se harán para crear los demás {@link Aeroport}
     * @param p_Aeroports El {@link HashSet} donde se guardarán los {@link Aeroport}
     */
    public void LeerAeroportBin(HashSet<Aeroport> p_Aeroports)
    {
        try(FileInputStream fis = new FileInputStream(l_AeroportFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {                        
            while (ois.readObject() != null)            
                p_Aeroports.add((Aeroport)ois.readObject());      
        }
        catch(EOFException e)
        {
            out.println(e.getMessage());
        }
        catch(ClassNotFoundException|IOException e)
        {
            out.println(e.getMessage());
        }
    }
    
    // Asientos
    // Output
    
    /**
     * Escribe el código del {@link Asiento} en un fichero de texto para su posterior carga.
     * 
     * <p>
     *      Para evitar la innecesaria carga de muchos ficheros, se usará ún unico fichero para
     *      cada tipo de {@link Avion}
     * </p>
     * @param p_Asientos El array de {@link Asiento}
     */
    public void GuardarAsientos(Asiento[][] p_Asientos)
    {
        try
        {
            CreateFileIfNotExists(l_AsientoFile); 
        
            for (Asiento p_AsientoF[] : p_Asientos)
            {
                for (Asiento p_AsientoC : p_AsientoF)
                {
                    try(FileWriter p_Writer = new FileWriter(l_AsientoFile))
                    {
                        p_Writer.write(String.format("%s%n", p_AsientoC.GetCodigoAsiento()));
                    }
                }            
            }
        }
        catch(IOException e)
        {
            out.println(e.getMessage());
        }
    }
    
    /**
     * Guarda las instancias de {@link Asiento} en un archivo de extensión .dat
     * 
     * <p>
     *      En el {@code try-with-resources} se usan 3 recursos:<br>
     *      {@link FileOutputStream} que será el que escriba en el {@link File} de los {@link Asiento}<br>
     *      {@link AeroportOutputStream} que si existe el archivo escribirá el tipo de dato de {@link Asiento}
     *      sin la cabecera que genera el {@link ObjectOutputStream}<br>
     *      {@link ObjectOutputStream} si el archivo no existe y se ha creado previamente.
     * </p>
     * 
     * @param p_Asientos La matriz de los {@link Asiento}
     * @see AeroportOutputStream
     * @see FileOutputStream
     * @see ObjectOutputStream
     */
    public void GuardarAsientosBin(Asiento[][] p_Asientos)
    {
        try 
        {
            CreateFileIfNotExists(l_AsientoFileBin);        

            try (FileOutputStream fos = new FileOutputStream(l_AsientoFileBin);
                 AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                    ObjectOutputStream oos = (l_AsientoFileBin.exists()) ? p_AeroportStream : new ObjectOutputStream(fos)) {

                for (Asiento[] p_AsientoF : p_Asientos) 
                {
                    for (Asiento p_AsientoC : p_AsientoF)                    
                        oos.writeObject(p_AsientoC);                    
                }
            }
        } catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
