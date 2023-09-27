
package GUI;

import java.util.ArrayList;
import civitas.CivitasJuego;
import civitas.Dado;
import controladorCivitas.Controlador;

public class TestP5 {
    public static void main(String[] args) {
        boolean debug = true;
        CivitasView vista;
        CapturaNombres capturaNombres;
        ArrayList<String> nombres;
        CivitasJuego juego;
        Controlador controlador;
        
        
        vista = new CivitasView();
        
        // Crear el dado
        Dado.createInstance(vista);
        
        capturaNombres = new CapturaNombres(vista, true);
        nombres = capturaNombres.getNombres();
        juego = new CivitasJuego(nombres, debug);
        controlador = new Controlador(juego, vista);
        
        vista.setCivitasJuego(juego);
        
        controlador.juega();
    }
}