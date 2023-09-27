import vistaTextualCivitas.VistaTextual;
import controladorCivitas.Controlador;
import civitas.CivitasJuego;
import java.util.ArrayList;

public class JuegoTexto {
    public static void main (String[] args) {
        boolean debug = true;
        CivitasJuego juego;
        VistaTextual vista;
        Controlador controlador;
        ArrayList<String> jugadores = new ArrayList<String>();
        
        for (int i = 1; i <= 4; i++) {
            jugadores.add(Integer.toString(i));
        }

        juego = new CivitasJuego(jugadores, debug);
        vista = new VistaTextual(juego);
        controlador = new Controlador(juego, vista);

        controlador.juega();
    }
}