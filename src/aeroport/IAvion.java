package aeroport;

public interface IAvion 
{
    // COLORES
    String COLOR_AZUL = "\u001B[34m";
    String COLOR_ROJO = "\u001B[31m";
    String COLOR_VERDE = "\u001B[32m";
    String COLOR_RESET = "\u001B[0m";
    
    /**
     * Imprime y visualiza de forma ordenada los {@link Asiento} disponibles del {@link Avion}
     * @return Un {@link String} con una imagen visual de los {@link Asiento} disponibles y no disponibles.
     */
    String PrintAsientos();
}
