package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {

    private static final float FACTORALQUILERCALLE = 1.0f;
    private static final float FACTORALQUILERCASA = 1.0f;
    private static final float FACTORALQUILERHOTEL = 4.0f;

    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;
    private Jugador propietario;

    CasillaCalle (String nombre, float unPrecioCompra,
                float unPrecioEdificar, float unPrecioAlquilerBase) {
        super(nombre);
        init();
        precioCompra = unPrecioCompra;
        precioEdificar = unPrecioEdificar;
        precioBaseAlquiler = unPrecioAlquilerBase;
    }

    private void init() {
        // Inicializar los atributos a un valor "adecuado" por si no se
        // proporciona al constructor un valor para ese atributo
        numHoteles = 0;
        numCasas = 0;
        propietario = null;
    }

    boolean comprar (Jugador jugador) {
        propietario = jugador;
        return propietario.paga(precioCompra);
    }

    boolean construirCasa (Jugador jugador) {
        jugador.paga(precioEdificar);

        numCasas++;

        return true;
    }

    boolean construirHotel (Jugador jugador) {
        jugador.paga(precioEdificar);

        numHoteles++;

        return true;
    }

    boolean derruirCasas (int numero, Jugador jugador) {
        boolean valido = (jugador == propietario) && (numCasas >= numero);

        if (valido)
            numCasas -= numero;

        return valido;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public int getNumHoteles() {
        return numHoteles;
    }

    float getPrecioCompra() {
        return precioCompra;
    }

    float getPrecioEdificar() {
        return precioEdificar;
    }

    float getPrecioAlquilerCompleto() {
        return (precioBaseAlquiler * (FACTORALQUILERCALLE + numCasas * FACTORALQUILERCASA +
                FACTORALQUILERHOTEL * numHoteles));
    }


    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        informe(iactual, todos);
        Jugador jugador = todos.get(iactual);

        if (!tienePropietario())
            jugador.puedeComprarCasilla();
        else
            tramitarAlquiler(jugador);
    }

    public int cantidadCasasHoteles() {
        return numCasas + numHoteles;
    }

    public boolean esEsteElPropietario (Jugador jugador) {
        return (propietario == jugador);
    }

    public boolean tienePropietario() {
        return (propietario != null);
    }

    public void tramitarAlquiler (Jugador jugador) {
        if (tienePropietario() && !esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(getPrecioAlquilerCompleto());
            propietario.recibe(getPrecioAlquilerCompleto());
        }
    }
    
    public String infoCasilla() {
        String cadena = "";
        
        if (propietario != null)
            cadena += "Propiedad de " + propietario.getNombre() + "\n"
                + "Alquiler a pagar: " + getPrecioAlquilerCompleto() + "\n";
        else
            cadena += "Compra " + precioCompra + "\nEdificar: "
                + precioEdificar + "\nAlquiler base: " + precioBaseAlquiler
                + "\nNúmero de casas: " + numCasas + "\nNúmero de hoteles: "
                + numHoteles;
        
        
        return cadena;
    }

    public String toString() {
        String cadena = "Calle ";

        cadena += nombre + "\n";

        cadena += infoCasilla();

        return cadena;
    }

    public CasillaCalle actualizaPropietarioPorConversion (Jugador jugador){
        propietario = jugador;
        return this;
    }
    
    public String getTipo() {
        return "Calle";
    }

}
