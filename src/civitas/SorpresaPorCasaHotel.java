
package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa {
    SorpresaPorCasaHotel(String texto, int valor) {
        super(texto, valor);
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        todos.get(actual).modificaSaldo(valor);
    }
}
