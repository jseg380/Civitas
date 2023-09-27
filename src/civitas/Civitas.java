
package civitas;

public class Civitas {

    
    public static void main(String[] args) {
        
        Dado dado = Dado.getInstance();
        
        
        // Punto 1
        
        final int numJugadores = 4;
        int numero, n1 = 0, n2 = 0, n3 = 0, n4 = 0;
        
        for (int i = 0; i < 100; i++) {
            numero = dado.quienEmpieza(numJugadores);
            switch (numero) {
                case 1:
                    n1++;
                    break;
                case 2:
                    n2++;
                    break;
                case 3:
                    n3++;
                    break;
                case 4:
                    n4++;
                    break;
            }
        }
        
        System.out.println("Cantidad de 1: " + n1);
        System.out.println("Cantidad de 2: " + n2);
        System.out.println("Cantidad de 3: " + n3);
        System.out.println("Cantidad de 4: " + n4);
        
        System.out.println("\n\n\n");
        
        // Punto 2
        
        boolean debug = true;
        int tiradaDebug = 0, vecesATirar = 20;
        
        dado.setDebug(debug);
        
        for (int i = 0; i < vecesATirar; i++) {
            if (dado.tirar() == 1)
                tiradaDebug++;
        }
        
        if (tiradaDebug == vecesATirar)
            System.out.println("Veces tiradas: " + vecesATirar + 
                               "\nUnos sacados: " + tiradaDebug + 
                               "\nFuncionamiento de debug correcto");
        
        debug = false;
        dado.setDebug(debug);
        tiradaDebug = 0;
        
        for (int i = 0; i < vecesATirar; i++) {
            if (dado.tirar() == 1)
                tiradaDebug++;
        }
        
        if (tiradaDebug != vecesATirar)
            System.out.println("Veces tiradas: " + vecesATirar + 
                               "\nUnos sacados: " + tiradaDebug + 
                               "\nFuncionamiento de debug correcto");
        
        System.out.println("\n\n\n");
        
        // Punto 3
        
        for (int i = 0; i < 4; i++) {
            System.out.println("\nTirada: " + dado.tirar() + 
                    "\nÚltimo resultado: " + dado.getUltimoResultado());
        }
        
        System.out.println("\n\n\n");
        
        // Punto 4
        
        System.out.println(EstadosJuego.DESPUES_AVANZAR);
        System.out.println(EstadosJuego.DESPUES_COMPRAR);
        System.out.println(EstadosJuego.DESPUES_GESTIONAR);
        System.out.println(EstadosJuego.INICIO_TURNO);
        
        System.out.println("\n\n\n");
        
        // Punto 5
        
        Tablero tablero = new Tablero();
        int numCasillas = 4;
        Casilla casilla;
        
        casilla = new CasillaCalle("Sócrates", 1, 2, 3);
        tablero.añadeCasilla(casilla);
        
        casilla = new CasillaCalle("Platón", 4, 5, 6);
        tablero.añadeCasilla(casilla);
        
        casilla = new CasillaCalle("Aristóteles", 7, 8, 9);
        tablero.añadeCasilla(casilla);
        
        for (int i = 0; i < numCasillas; i++) {
            System.out.println(tablero.getCasilla(i));
        }
        
        System.out.println("\n\n\n");
        
        // Punto 6
        
        float precioMax = 0,
              precioMin = 0,
              media = 0;
        
        for (int i = 1; i < numCasillas; i++) {
            float precio = ((CasillaCalle)tablero.getCasilla(i)).getPrecioCompra();
            
            if (precio > precioMax)
                precioMax = precio;
            else if (precio < precioMin)
                precioMin = precio;
            
            media += precio;
        }
        
        media = media / (numCasillas - 1);  // La salida no cuenta (cuesta 0)
        
        System.out.println("Precio de la calle más cara: " + precioMax);
        System.out.println("Precio de la calle más barata: " + precioMin);
        System.out.println("Precio medio de las calles: " + media);
        
        System.out.println("\n\n\n");
        
        // Punto 7
        
        Diario elDiario = Diario.getInstance();
        
        elDiario.ocurreEvento("Esto es una prueba de evento");
        System.out.println(elDiario.getEventos());

        System.out.println("\n\n\n");
        
        // Punto 8
        
        int posicion = 1, tirada;
        System.out.println("Cantidad de casillas que tiene el tablero: "
               + tablero.getCasillas().size());
        
        for (int i = 0; i < 5; i++) {
            tirada = dado.tirar();
            
            System.out.println("Posición actual: " + posicion + 
                    "\nTirada: " + tirada);
            posicion = tablero.nuevaPosicion(posicion, tirada);
            System.out.println("Posición tras tirar: " + posicion);
        }
    }
    
}
