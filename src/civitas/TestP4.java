package civitas;

public class TestP4 {
    public static void main(String[] args) {
        CasillaCalle casilla = new CasillaCalle("prueba", 10, 0, 0);

        Jugador jugador = new Jugador("Pedro");

        jugador.comprar(casilla);

        System.out.println("Antes de convertir:\n" + jugador);

        jugador = jugador.convertir(); // Jugador -> Especulador
        
        // JugadorEspeculador

        System.out.println("\n\n\nDespués de convertir a especulador:\n" + jugador);
        
        casilla = new CasillaCalle("prueba2", 20, 0, 0);
        jugador.puedeComprarCasilla(); // Para que pueda volver a comprar
        // (como no hay turnos hay que forzar la posibilidad de compra)

        jugador.comprar(casilla);
        
        System.out.println("\n\n\nCompra siendo especulador:\n" + jugador);
        
        jugador = jugador.convertir(); // Especulador -> Jugador
        
        // Jugador
        
        System.out.println("\n\n\nDespués de volver a convertir a jugador:\n" + jugador);
    }
}
