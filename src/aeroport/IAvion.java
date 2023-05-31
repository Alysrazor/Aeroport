package aeroport;

/**
 * Interfaz para {@link Avion} en la que se definen colores y un método abstracto para imprimir los asientos.
 * 
 * @author Sergio Capilla Cabadés
 * @dev.main Sergio Capilla Cabadés
 * @dev.codevs
 * @my.fecha 29 may 2023 16:12:35
 * @my.company Ciclo Superior de Informática
 * @since JDK 1.18
 */
public interface IAvion 
{
    // COLORES
    /**
     * Color Azul en la consola.
     */
    String COLOR_AZUL = "\u001B[34m";
    /**
     * Color Rojo en la consola.
     */
    String COLOR_ROJO = "\u001B[31m";
    /**
     * Color Verde en la consola.
     */
    String COLOR_VERDE = "\u001B[32m";
    /**
     * Reinicia el color en la consola.
     */
    String COLOR_RESET = "\u001B[0m";
    
    /**
     * Imprime y visualiza de forma ordenada los {@link Asiento} disponibles del {@link Avion}
     * @return Un {@link String} con una imagen visual de los {@link Asiento} disponibles y no disponibles.
     */
    String PrintAsientos();
}
