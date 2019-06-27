/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import GUIQytetet.Dado;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author david
 */
public class Qytetet {
    private static final Qytetet instance = new Qytetet();
    
    public static int MAX_JUGADORES = 4;
    static int MAX_CARTAS = 10;
    static int MAX_CASILLAS = 20;
    static int PRECIO_LIBERTAD = 200;
    static int PRECIO_SALIDA = 1000;
    //Atributos de referencia
    private Sorpresa cartaActual;
    private ArrayList<Sorpresa> mazo;
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private Dado dado;
    private Jugador jugadorActual;
    
    private Qytetet() {
        cartaActual = null;
        jugadores = new ArrayList();
        mazo = new ArrayList();
        tablero = null;
        //dado = Dado.getInstance();
        dado = GUIQytetet.Dado.getInstance();
        jugadorActual = null;        
    }
    
    public static Qytetet getInstance() {
        return instance;
    }
    
    public boolean aplicarSorpresa() {
        boolean tienePropietario = false;
        
        if(null != cartaActual.getTipo())
            switch (cartaActual.getTipo()) {
            case PAGARCOBRAR:
                jugadorActual.modificarSaldo(cartaActual.getValor());
                break;
            case IRACASILLA:
                boolean esCarcel = tablero.esCasillaCarcel(cartaActual.getValor());
                if(esCarcel)
                    encarcelarJugador();
                else{
                    Casilla nuevaCasilla = tablero.obtenerCasillaNumero(cartaActual.getValor());
                    tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
                }
                break;
            case PORCASAHOTEL:
                jugadorActual.pagarCobrarPorCasaYHotel(cartaActual.getValor());
                break;
            case PORJUGADOR:
                for (Jugador jugador : jugadores)
                    if(jugador != jugadorActual) {
                        jugador.modificarSaldo(-cartaActual.getValor());
                        jugadorActual.modificarSaldo(cartaActual.getValor());
                    }
                break;
            case CONVERTIRME:
                jugadorActual.convertirme(cartaActual.getValor());
                
                int i = -1;
                for(Jugador j : jugadores)
                    if(j.getNombre().equals(jugadorActual.getNombre()))
                        i = jugadores.indexOf(j);
                
                jugadores.set(i, jugadorActual);
                
                break;
            default:
                break;
            }
        
        if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL)
            jugadorActual.setCartaLibertad(cartaActual);
        else
            mazo.add(cartaActual);
        
        return tienePropietario;
    }
    
    public boolean cancelarHipoteca(Calle calle) {
        boolean estaHipotecada = calle.estaHipotecada();
        
        if(estaHipotecada)
            if(jugadorActual.puedoPagarHipoteca(calle)){
                int costeCancelacion = calle.cancelarHipoteca();                
                jugadorActual.modificarSaldo(-costeCancelacion);
            }
                
        return estaHipotecada;
    }
    
    public boolean comprarTituloPropiedad() {
        return jugadorActual.comprarTitulo();
    }
    
    public boolean edificarCasa(Calle calle) {
        boolean puedoEdificar = false;
        if (calle.soyEdificable()) {
            boolean sePuedeEdificar = calle.sePuedeEdificarCasa();
            if (sePuedeEdificar) {
                puedoEdificar = jugadorActual.puedoEdificarCasa(calle);
                if (puedoEdificar) {
                    int costeEdificarCasa = calle.edificarCasa();
                    jugadorActual.modificarSaldo(-costeEdificarCasa);
                }
            }
        }
        
        return puedoEdificar;
    }
    
    public boolean edificarHotel(Calle calle) {
        boolean puedoEdificar = false;
        if (calle.soyEdificable()) {
            boolean sePuedeEdificar = calle.sePuedeEdificarHotel();
            if (sePuedeEdificar) {                
                puedoEdificar = jugadorActual.puedoEdificarHotel(calle);
                if (puedoEdificar) {
                    int costeEdificarHotel = calle.edificarHotel();
                    jugadorActual.modificarSaldo(-costeEdificarHotel);
                }
            }
        }
        
        return puedoEdificar;
    }
    
    public Sorpresa getCartaActual() {
        return cartaActual;
    }
    
    public Jugador getJugadorActual() {
        return jugadorActual;
    }
    
    public boolean hipotecarPropiedad(Calle calle) {
        boolean puedoHipotecar = false;
        if(calle.soyEdificable()) {
            boolean sePuedeHipotecar = !calle.estaHipotecada();
            if(sePuedeHipotecar) {
                puedoHipotecar = jugadorActual.puedoHipotecar(calle);
                if(puedoHipotecar) {
                    int cantidadRecibida = calle.hipotecar();
                    jugadorActual.modificarSaldo(cantidadRecibida);
                }
            }
        }
        
        return puedoHipotecar;
    }
            
    public void inicializarJuego(ArrayList<String> nombres) {
        inicializarTablero();
        inicializarJugadores(nombres);
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        boolean libre;
        
        if(metodo == MetodoSalirCarcel.TIRANDODADO){
            int valorDado = dado.nextNumber(); //dado.tirar();
            String stDado = "Valor dado: " + valorDado;
            System.out.println(stDado);
            libre = valorDado > 5;
        }
        else{
            boolean tengoSaldo = jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            libre = tengoSaldo;
        }
        
        if(libre)
            jugadorActual.setEncarcelado(false);
                    
        return libre;
    }
    

    public boolean jugar() {
        int valorDado = dado.nextNumber(); //dado.tirar();
        Casilla casillaPosicion = jugadorActual.getCasillaActual();
        Casilla nuevaCasilla = tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        boolean tienePropietario = jugadorActual.actualizarPosicion(nuevaCasilla);
        
        if(!nuevaCasilla.soyEdificable()) {
            if(nuevaCasilla.getTipo() == TipoCasilla.JUEZ)
                encarcelarJugador();
            else if(nuevaCasilla.getTipo() == TipoCasilla.SORPRESA){
                cartaActual = mazo.remove(0);
            }            
        }
        
        return tienePropietario;
    }
    
    
    public String obtenerRanking() {
        String rankingJugadores = "RANKING";
        
        Map<Integer, String> ranking = new TreeMap(Collections.reverseOrder());
        for(Jugador jugador : jugadores)
            ranking.put(jugador.obtenerCapital(), jugador.getNombre());
        
        int i=1;
        for (Map.Entry<Integer, String> entry : ranking.entrySet()){
            rankingJugadores += "\n"+i+"º : "+entry.getValue() + " \tCapital: "+entry.getKey();
            i++;
        }
        
        return rankingJugadores;
    }
    
    public boolean venderPropiedad(Calle calle) {
        boolean puedoVender = false;
        if(calle.soyEdificable()) {
            puedoVender = jugadorActual.puedoVenderPropiedad(calle);
            if(puedoVender)
                jugadorActual.venderPropiedad(calle);
        }
        return puedoVender;
    }

    private void encarcelarJugador(){
        if(!jugadorActual.tengoCartaLibertad()) {
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
        }
        else {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
        }
    }

    
    private void inicializarCartasSorpresa() {
        mazo.add(new Sorpresa("Regalito para tu pecho, por lo bien que "
                + "lo has hecho.", 1700, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("¡IMPUESTO! Hay que pagar para financiar "
                + "nuestros servicios públicos", -1300, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Aquí no existe la presunción de inocencia, así que"
                + "te vas derecho a la trena.", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));    // Cárcel
        mazo.add(new Sorpresa("Sabemos que te gusta viajar, así que te has ganado "
                + "un vuelo directo al comienzo del juego.", 2, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Vuelo directo con destino a... ¿París? ¿Londres? "
                + "No. Me temo que a un callejón de la ciudad.", 17, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("¡Hora de cobrar el alquiler de los inquilinos!", 300, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Es una pena, pero tienes que pagar por cada una "
                + "de tus propiedades.", -300, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Aquí reina el socialismo, por eso tus queridos vecinos "
                + "estarán encantados de darte algo de limosna.", 400, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("¿Recuerdas aquella vez que twiteaste que estarías encantado de compartir"
                + "todo lo que tienes con los demás? ¡Tus desesos son órdenes!", -200, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Aquella noche de marcha con el fiscal general ha servido de algo, "
                + "así que ha decidido que salgas de prisión", 0, TipoSorpresa.SALIRCARCEL));
        mazo.add(new Sorpresa("He aquí la llave para ser la próxima portada de la revista Forbes",
                3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Esto no es una tarjeta Black, pero tendrás los mismo priviliegios",
                5000, TipoSorpresa.CONVERTIRME));
        
        Collections.shuffle(mazo);
    }
    
    public void siguienteJugador() {
        int indiceSiguienteJugador;
        
        if (jugadores.indexOf(jugadorActual) != jugadores.size()-1)
            indiceSiguienteJugador = jugadores.indexOf(jugadorActual) + 1;
        else
            indiceSiguienteJugador = 0;
            
        jugadorActual = jugadores.get(indiceSiguienteJugador);
    }
    
    public void setJugadorActual(Jugador jugador) {
        jugadorActual = jugador;
    }
    
    private void salidaJugadores() {
        for (Jugador miJugador : jugadores)
            miJugador.setCasillaActual(tablero.obtenerCasillaNumero(0));
             
        Random randomGenerator = new Random();
        int indice = randomGenerator.nextInt(jugadores.size());
        jugadorActual = jugadores.get(indice);
    }
    
    public ArrayList<Calle> propiedadesHipotecadasJugador(boolean hipotecadas) {
        ArrayList<Calle> casillasHipotecasdas = new ArrayList();
        ArrayList<TituloPropiedad> titulos = jugadorActual.obtenerPropiedadesHipotecadas(hipotecadas);
        
        for (TituloPropiedad miTitulo : titulos)
            casillasHipotecasdas.add(miTitulo.getCasilla());
                
        return casillasHipotecasdas;
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    private void inicializarJugadores(ArrayList<String> nombres){
        for (String nom : nombres)
            jugadores.add(new Jugador(nom));
    }
    
    private void inicializarTablero() {
        tablero = new Tablero();
    }
    
    @Override
    public String toString(){
        String st = "\nJugadores: \n";
        
        for (Jugador jugador : jugadores)
            st += jugador.toString() + "\n";
        
        st += "\nJugador actual: " + jugadorActual;
        
        st += "\nCartas sorpresa: \n";
        
        for (Sorpresa sorpresa : mazo)
            st += sorpresa.toString();
        
        st += "\nTablero:" + tablero.toString();
        
        return st;
    }
    
}
