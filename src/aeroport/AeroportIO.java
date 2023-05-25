/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package aeroport;

import aeroport.crypto.CryptSHA1;

import aeroport.persona.Cliente;
import aeroport.persona.Empleado;
import aeroport.persona.Equipaje;
import aeroport.persona.Persona;
import aeroport.persona.Reserva;

import aeroport.persona.TipoEquipaje;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static java.lang.System.out;

import java.time.LocalDate;
import java.time.Month;

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
    // Aeroport Types and Collections and Sets
    private Aeroport l_Aeroport;
    private Asiento[][] l_Asientos;
    private HashSet<Avion> l_Aviones;
    private TreeSet<Company> l_Companies;
    private TreeSet<PuertaEmbarque> l_Puertas;
    private TreeSet<Terminal> l_Terminales;
    private TreeSet<Vuelo> l_Vuelos;
    private HashSet<Equipaje> l_Equipajes;
    private TreeSet<Persona> l_Personas;
    private HashSet<Reserva> l_Reservas;
    
    // Aeroport Files
    private File l_AeroportFileBin = new File("Aeroport.dat");
    
    // Asiento Files
    private File l_AsientoFileBin = new File("Asiento.dat");
    
    // Avion Files
    private File l_AvionFileBin = new File("Avion.dat");
    
    // Compañía Files
    private File l_CompanyFileBin = new File("Company.dat");
    
    // Pista Files
    private File l_PuertaFileBin = new File("Pista.dat");
    
    // PuertaEmbarqueFiles
    private File l_PuertasFileBin = new File("PuertaEmbarque.dat");
    
    // Terminal Files
    private File l_TerminalFileBin = new File("Terminal.dat");
    
    // Vuelo Files
    private File l_VueloFileBin = new File("Vuelo.dat");
    
    // Equipaje Files
    private File l_EquipajeFileBin = new File("Equipaje.dat");
    
    // Persona Files
    private File l_PersonaFileBin = new File("Persona.dat");
    
    // Reserva Files
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
    
    /**
     * Comprueba si un archivo está o no vacío.
     * 
     * <p>
     *      Este método comprueba si no hay ningún objeto escrito
     *      en el archivo para poder usar {@link AeroportOutputStream}
     * </p>
     * 
     * @param p_File El {@link File ha comprobar}
     * @return <ul>
     *              <li>{@code true} si está vacío.</li>
     *              <li>{@code false} si no está vacío.</li>
     *          </ul>
     * @see AeroportOutputStream
     */
    private boolean IsFileEmpty(File p_File)
    {
        try(FileInputStream fis = new FileInputStream(p_File);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            return ois.readObject() == null;
        }
        catch(ClassNotFoundException | EOFException e) {}
        catch(IOException e) {}
        
        return false;
    }
    
    // Aeroport IORelated
    // Output
    
    public void GuardarAeroportBin(Aeroport p_Aeroport) throws IOException, SecurityException
    {
        CreateFileIfNotExists(l_AeroportFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_AeroportFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_AeroportFileBin) && l_AeroportFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            oos.writeObject(p_Aeroport);
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
     * @throws ClassNotFoundException si no encuentra la clase {@link Aeroport}
     * @throws EOFException si llega al final del archivo.
     * @throws IOException si ocurre algún error relacionado en la lectura del archivo.
     */
    public void LeerAeroportBin(HashSet<Aeroport> p_Aeroports) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_AeroportFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {                        
            while (ois.readObject() != null)            
                p_Aeroports.add((Aeroport)ois.readObject());      
        }
    }
    
    // Asientos
    // Output
    
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
     * @throws IOException si ocurre algún error en el guardado de los datos.
     * @see AeroportOutputStream
     * @see FileOutputStream
     * @see ObjectOutputStream
     */
    public void GuardarAsientosBin(Asiento[][] p_Asientos) throws IOException
    {

        CreateFileIfNotExists(l_AsientoFileBin);        

        try (FileOutputStream fos = new FileOutputStream(l_AsientoFileBin);
             AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_AsientoFileBin) && l_AsientoFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos)) {

            for (Asiento[] p_AsientoF : p_Asientos) 
            {
                for (Asiento p_AsientoC : p_AsientoF)                    
                    oos.writeObject(p_AsientoC);                    
            }
        }
    }
    
    // Input
    
    /**
     * Lee los {@link Asiento} de un archivo binario y los añade a la matriz bidimensional que 
     * se le pasa por parámetro.
     * @param p_Asientos La matriz bidimensional de tipo {@link Asiento}
     * @throws ClassNotFoundException en caso de no encontrar la clase {@link Asiento}
     * @throws EOFException si llega al final del archivo.
     * @throws IOException si ocurre algún otro error relacionado con la lectura.
     */
    public void LeerAsientoBin(Asiento[][] p_Asientos) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_AsientoFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            for (Asiento[] p_AsientoF : p_Asientos)
            {
                for (int l_Col = 0; l_Col < p_AsientoF.length; l_Col++)
                {
                    Asiento p_Asiento = (Asiento) ois.readObject();
                    if (p_Asiento != null) p_AsientoF[l_Col] = p_Asiento;
                }
            }
        }
    }
    
    // Avion
    //Output
    
    /**
     * Guarda los {@link Avion} y sus hijos derivados en un {@link File} a partir de
     * un {@link HashSet}
     * 
     * <p>
     *      Dado el {@link HashSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Aviones
     * @throws IOException 
     */
    public void GuardarAvionBin(HashSet<Avion> p_Aviones) throws IOException
    {
        CreateFileIfNotExists(l_AvionFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_AvionFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_AvionFileBin) && l_AvionFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Avion p_Avion : p_Aviones)
                oos.writeObject(p_Avion);
        }
    }
    
    // Input
    /**
     * Lee el archivo binario de los {@link Avion} y los añade a un {@link HashSet}
     * 
     * @param p_Aviones El {@link HashSet} de los {@link Avion}
     * @throws ClassNotFoundException en caso de no encontrar la clase {@link Avion}
     * @throws EOFException si llega al final del archivo.
     * @throws IOException si se produce algún error con el archivo.
     */
    public void LeerAvionBin(HashSet<Avion> p_Aviones) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_AvionFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Avion l_Avion;
            while((l_Avion = (Avion) ois.readObject()) != null)            
                p_Aviones.add(l_Avion);
        }
    }
    
    // Company
    // Output
    
    /**
     * Guarda la(s) {@link Company} que tenga el {@link Aeroport} en un
     * fichero binario.
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * 
     * @param p_Companies Un {@link TreeSet} de {@link Company}
     * @throws IOException si ocurre algún error con el fichero.
     */
    public void GuardarCompanyBin(TreeSet<Company> p_Companies) throws IOException
    {
        CreateFileIfNotExists(l_CompanyFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_CompanyFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_CompanyFileBin) && l_CompanyFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Company p_Company : p_Companies)
                oos.writeObject(p_Company);
        }
    }
    
    //Input
    
    /**
     * Lee el archivo y carga las {@link Company} al {@link TreeSet} que se le pasa como parámetro.
     * @param p_Companies El {@link TreeSet} de las {@link Company}
     * @throws ClassNotFoundException si no encuentra la clase {@link Company}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerCompanyBin(TreeSet<Company> p_Companies) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_CompanyFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Company l_Company;
            
            while ((l_Company = (Company)ois.readObject()) != null)
                p_Companies.add(l_Company);
        }
    }
    
    //Pista
    //Output
    
    /**
     * Guarda las {@link Pista} y sus hijos derivados en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Pistas Las {@link Pista} en un {@link TreeSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarPistaBin(TreeSet<Pista> p_Pistas) throws IOException
    {
        CreateFileIfNotExists(l_PuertaFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_PuertaFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_PuertaFileBin) && l_PuertaFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Pista p_Pista : p_Pistas)
                oos.writeObject(p_Pista);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga las {@link Pista} al {@link TreeSet} que se le pasa como parámetro.
     * 
     * @param p_Pista El {@link TreeSet} de las {@link Pista}
     * @throws ClassNotFoundException si no encuentra la clase {@link Pista}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerPistaBin(TreeSet<Pista> p_Pista) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_PuertaFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Pista l_Pista;
            
            while ((l_Pista = (Pista)ois.readObject()) != null)
                p_Pista.add(l_Pista);
        }
    }
    
    // PuertaEmbarque
    //Output
    
    /**
     * Guarda las {@link PuertaEmbarque} y sus hijos derivados en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Puertas Las {@link PuertaEmbarque} en un {@link TreeSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarPuertaEmbarqueBin(TreeSet<PuertaEmbarque> p_Puertas) throws IOException
    {
        CreateFileIfNotExists(l_PuertaFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_PuertaFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_PuertaFileBin) && l_PuertaFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (PuertaEmbarque p_Puerta : p_Puertas)
                oos.writeObject(p_Puerta);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga las {@link PuertaEmbarque} al {@link TreeSet} que se le pasa como parámetro.
     * 
     * @param p_Puertas El {@link TreeSet} de las {@link PuertaEmbarque}
     * @throws ClassNotFoundException si no encuentra la clase {@link PuertaEmbarque}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerPuertaEmbarqueBin(TreeSet<PuertaEmbarque> p_Puertas) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_PuertaFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            PuertaEmbarque l_Puerta;
            
            while ((l_Puerta = (PuertaEmbarque)ois.readObject()) != null)
                p_Puertas.add(l_Puerta);
        }
    }
    
    // Terminal
    //Output
    
    /**
     * Guarda las {@link Terminal} en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Terminales Las {@link Terminal} en un {@link TreeSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarTerminalBin(TreeSet<Terminal> p_Terminales) throws IOException
    {
        CreateFileIfNotExists(l_TerminalFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_TerminalFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_TerminalFileBin) && l_TerminalFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Terminal p_Terminal : p_Terminales)
                oos.writeObject(p_Terminal);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga las {@link Terminal} al {@link TreeSet} que se le pasa como parámetro.
     * 
     * @param p_Terminales El {@link TreeSet} de las {@link Terminal}
     * @throws ClassNotFoundException si no encuentra la clase {@link Terminal}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerTerminalBin(TreeSet<Terminal> p_Terminales) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_TerminalFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Terminal l_Terminal;
            
            while ((l_Terminal = (Terminal)ois.readObject()) != null)
                p_Terminales.add(l_Terminal);
        }
    }
    
    // Vuelo
    //Output
    
    /**
     * Guarda los {@link Vuelo} en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Vuelos Los {@link Vuelo} en un {@link TreeSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarVueloBin(TreeSet<Vuelo> p_Vuelos) throws IOException
    {
        CreateFileIfNotExists(l_VueloFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_VueloFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_VueloFileBin) && l_VueloFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Vuelo p_Vuelo : p_Vuelos)
                oos.writeObject(p_Vuelo);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga los {@link Vuelo} al {@link TreeSet} que se le pasa como parámetro.
     * 
     * @param p_Vuelos El {@link TreeSet} de los {@link Vuelo}
     * @throws ClassNotFoundException si no encuentra la clase {@link Vuelo}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerVueloBin(TreeSet<Vuelo> p_Vuelos) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_TerminalFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Vuelo l_Vuelo;
            
            while ((l_Vuelo = (Vuelo)ois.readObject()) != null)
                p_Vuelos.add(l_Vuelo);
        }
    }
    
    // Equipaje
    //Output
    
    /**
     * Guarda los {@link HashSet} en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Equipajes Los {@link Equipaje} en un {@link HashSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarEquipajeBin(HashSet<Equipaje> p_Equipajes) throws IOException
    {
        CreateFileIfNotExists(l_EquipajeFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_EquipajeFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_EquipajeFileBin) && l_EquipajeFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Equipaje p_Equipaje : p_Equipajes)
                oos.writeObject(p_Equipaje);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga los {@link Equipaje} al {@link HashSet} que se le pasa como parámetro.
     * 
     * @param p_Equipajes El {@link HashSet} de los {@link Equipaje}
     * @throws ClassNotFoundException si no encuentra la clase {@link Equipaje}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerEquipajeBin(HashSet<Equipaje> p_Equipajes) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_EquipajeFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Equipaje l_Equipaje;
            
            while ((l_Equipaje = (Equipaje)ois.readObject()) != null)
                p_Equipajes.add(l_Equipaje);
        }
    }
    
    // Persona
    //Output
    
    /**
     * Guarda las {@link Persona} en un {@link File} a partir de
     * un {@link TreeSet}
     * 
     * <p>
     *      Dado el {@link TreeSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Personas Las {@link Persona} en un {@link TreeSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarPersonaBin(TreeSet<Persona> p_Personas) throws IOException
    {
        CreateFileIfNotExists(l_PersonaFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_PersonaFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_PersonaFileBin) && l_PersonaFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Persona p_Persona : p_Personas)
                oos.writeObject(p_Persona);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga los {@link Equipaje} al {@link TreeSet} que se le pasa como parámetro.
     * 
     * @param p_Personas El {@link TreeSet} de las {@link Persona}
     * @throws ClassNotFoundException si no encuentra la clase {@link Persona}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerPersonaBin(TreeSet<Persona> p_Personas) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_EquipajeFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Persona l_Persona;
            
            while ((l_Persona = (Persona)ois.readObject()) != null)
                p_Personas.add(l_Persona);
        }
    }
    
    // Reserva
    //Output
    
    /**
     * Guarda las {@link Reserva} en un {@link File} a partir de
     * un {@link HashSet}
     * 
     * <p>
     *      Dado el {@link HashSet} que se le pasa por parámetro intentará en este try-with-resources
     *      escribirlo al archivo
     * </p>
     * @param p_Reservas Las {@link Reserva} en un {@link HashSet}
     * @throws IOException si ocurre un error al guardar los datos.
     */
    public void GuardarReservaBin(HashSet<Reserva> p_Reservas) throws IOException
    {
        CreateFileIfNotExists(l_ReservaFileBin);
        
        try(FileOutputStream fos = new FileOutputStream(l_ReservaFileBin);
                AeroportOutputStream p_AeroportStream = new AeroportOutputStream(fos);
                ObjectOutputStream oos = (IsFileEmpty(l_ReservaFileBin) && l_ReservaFileBin.length() > 0) ? p_AeroportStream : new ObjectOutputStream(fos))
        {
            for (Reserva p_Reserva : p_Reservas)
                oos.writeObject(p_Reserva);
        }
    }
    
    // Input
    
    /**
     * Lee el archivo y carga las {@link Reserva} al {@link HashSet} que se le pasa como parámetro.
     * 
     * @param p_Reservas El {@link HashSet} de las {@link Reserva}
     * @throws ClassNotFoundException si no encuentra la clase {@link Reserva}
     * @throws EOFException si ha terminado de leer el archivo.
     * @throws IOException si ocurre algún problema con la lectura del archivo.
     */
    public void LeerReservaBin(HashSet<Reserva> p_Reservas) throws ClassNotFoundException, EOFException, IOException
    {
        try(FileInputStream fis = new FileInputStream(l_ReservaFileBin);
                ObjectInputStream ois = new ObjectInputStream(fis))
        {
            Reserva l_Reserva;
            
            while ((l_Reserva = (Reserva)ois.readObject()) != null)
                p_Reservas.add(l_Reserva);
        }
    }
    
    public void CargarDatosPrincipales()
    {
        
    }
    
    private void CargarPersona()
    {
        // Empleados
        this.l_Personas.add(new Empleado("32175398A", "Pablo", "Garcia Gomez", LocalDate.of(1990, Month.MARCH, 15), "PCC8A"));
        this.l_Personas.add(new Empleado("75365489G", "Mario", "Blasco Ibañez", LocalDate.of(1987, Month.JUNE, 12), "MBI9G"));
        this.l_Personas.add(new Empleado("32175398A", "Pablo", "Garcia Gomez", LocalDate.of(1990, Month.MARCH, 15), "PCC8A"));
        //
        HashSet<Equipaje> l_Equipajes = new HashSet<Equipaje>();
        l_Equipajes.add(new Equipaje("CAR65N01", TipoEquipaje.EQUIPAJE_MALETA, 20));
        Cliente l_CA = new Cliente("15975365N", "Carlos", "Aparicio Garcia", LocalDate.of(1998, Month.DECEMBER, 10), "Carlosag", CryptSHA1.EncryptPassword("carlosap"), "carlosapariciogarcia@gmail.com", l_Equipajes);
        
    }
}
