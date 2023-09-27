
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CivitasJuego {
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private GestorEstados gestorEstados;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private EstadoJuego estado;

    public CivitasJuego (ArrayList<String> nombres, boolean debug) {
        jugadores = new ArrayList<Jugador>();
        // Crear tantos jugadores como nombres hay en el array
        for (String nombreJugador: nombres)
            jugadores.add(new Jugador(nombreJugador));

        // Crear el gestor de estados y asignarle el estado inicial
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();

        // Poner el dado en debug si el parámetro es true
        Dado.getInstance().setDebug(debug);

        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        // Crear el mazo de sorpresas en modo debug si el parámetro es true
        mazo = new MazoSorpresas(debug);

        // Crear el tablero e inicializarlo
        tablero = new Tablero();
        inicializaTablero(mazo);

        // Inicializar el mazo de sorpresas
        inicializaMazoSorpresas();
    }

    private void inicializaTablero (MazoSorpresas mazo) {
        // Crear y añadir las 20 casillas excepto la de salida
        // que se crea en el constructor de tablero

        // La primera casilla (nº 0) es la salida
        // Añadir las casillas desde la 1 hasta la 8

        for (int i=1; i <= 8; i++) {
            if (i == 3 || i == 6)
                tablero.añadeCasilla(new CasillaSorpresa(
//                    mazo.siguiente().toString(),  // Título alternativo
                    "Casilla sorpresa " + i, // Título
                    mazo)           // Mazo sorpresas
                );
            else
                tablero.añadeCasilla(new CasillaCalle(
                    "Casilla " + i, // Título
                    i * 200,        // Precio de compra
                    i * 250,        // Precio de edificar
                    i * 150)        // Precio de alquilers
                );
        }

        // Casilla 9 es PARKING (Décima casilla)

        tablero.añadeCasilla(new Casilla("PARKING"));

        // Añadir resto de casillas desde la 10 hasta la 19

        for (int i=11; i <= 19; i++) {
            if (i == 13 || i == 17)
                tablero.añadeCasilla(new CasillaSorpresa(
//                    mazo.siguiente().toString(),  // Título alternativo
                    "Casilla sorpresa " + i, // Título
                    mazo)           // Mazo sorpresas
                );
            else
                tablero.añadeCasilla(new CasillaCalle(
                    "Casilla " + i, // Título
                    i * 200,        // Precio de compra
                    i * 250,        // Precio de edificar
                    i * 150)        // Precio de alquilers
                );
        }
    }

    private void inicializaMazoSorpresas() {
        // Crear las 10 cartas sorpresa y almacenarlas en el mazo
        int minCobrar = 100,
            maxCobrar = 400,
            minPagar = -400,
            maxPagar = -100;
        Random rand = new Random();

        // 6 de pagar y cobrar (3 positivas, 3 negativas)
        for (int i=0; i <3; i++)
            mazo.alMazo(new SorpresaPagarCobrar("COBRAR", rand.nextInt(minCobrar, maxCobrar + 1)));
        for (int i=0; i < 3; i++)
            mazo.alMazo(new SorpresaPagarCobrar("PAGAR", rand.nextInt(minPagar, maxPagar + 1)));

        // 4 de por casa y hotel (2 positivas, 2 negativas)
        for (int i=0; i < 2; i++)
            mazo.alMazo(new SorpresaPorCasaHotel("COBRAR", rand.nextInt(minCobrar, maxCobrar + 1)));
        for (int i=0; i < 2; i++)
            mazo.alMazo(new SorpresaPorCasaHotel("PAGAR", rand.nextInt(minPagar, maxPagar + 1)));

        // Se baraja el mazo llamando a siguiente
        mazo.siguiente();
    }

    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero(){
        return tablero;
    }

    private void pasarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }

    public void siguientePasoCompletado(OperacionJuego operacion) {
        estado = gestorEstados.siguienteEstado(
                jugadores.get(indiceJugadorActual), // Jugador actual
                estado,                             // Estado actual del juego
                operacion                           // Operación completada
        );
    }

    public boolean construirCasa (int ip) {
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }

    public boolean construirHotel (int ip) {
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }

    public boolean finalDelJuego() {
        boolean fin = false;
        for (Jugador jugador : jugadores )
            if (jugador.enBancarrota())
                fin = true;

        return fin;
    }

    public ArrayList<Jugador> ranking() {
        // Ordena la lista de jugadores en base a la función compareTo de Jugador
        Collections.sort(jugadores, Jugador::compareTo);
        return jugadores;
    }

    private void contabilizarPasosPorSalida() {
        if (tablero.computarPasoPorSalida())
            jugadores.get(indiceJugadorActual).pasaPorSalida();
    }

    private void avanzaJugador() {
        Jugador jugadorActual = getJugadorActual();

        int posicionActual = jugadorActual.getCasillaActual();

        int tirada = Dado.getInstance().tirar();

        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);

        Casilla casilla = tablero.getCasilla(posicionNueva);

        contabilizarPasosPorSalida();

        jugadorActual.moverACasilla(posicionNueva);

        casilla.recibeJugador(indiceJugadorActual, jugadores);
    }

    public boolean comprar() {
        Jugador jugadorActual = getJugadorActual();
        int numCasillaActual = jugadorActual.getCasillaActual();
        CasillaCalle casilla = ((CasillaCalle)tablero.getCasilla(numCasillaActual));
        boolean res = jugadorActual.comprar(casilla);

        return res;
    }

    public OperacionJuego siguientePaso() {
        Jugador jugadorActual = getJugadorActual();
        OperacionJuego operacion = gestorEstados.siguienteOperacion(jugadorActual, estado);

        if (operacion == OperacionJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        } else if (operacion == OperacionJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }

         return operacion;
    }
}
