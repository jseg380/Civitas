package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {

    private MazoSorpresas mazo;
    private Sorpresa sorpresa;

    CasillaSorpresa (String nombre, MazoSorpresas unMazo) {
        super (nombre);
        mazo = unMazo;
    }

    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        sorpresa = mazo.siguiente();
        informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);
    }
    
    public String infoCasilla() {
        return toString();
    }

    public String toString() {
        String cad = "";
        cad = nombre + "\n";  
        return cad;
    }
    
    public String getTipo() {
        return "Sorpresa";
    }
}