/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Jugador {
    private boolean encarcelado;
    private String nombre;
    private int saldo;
    private ArrayList<TituloPropiedad> propiedades;
    private Casilla casillaActual;
    private Sorpresa cartaLibertad;
    static int factorEspeculador = 1;
    
    
    public Jugador(String nombre) {
        encarcelado = false;
        this.nombre = nombre;
        saldo = 7500;
        cartaLibertad = null;
        casillaActual = null;
        propiedades = new ArrayList();
    }
    
    protected Jugador(Jugador jugador){
        encarcelado = jugador.getEncarcelado();
        nombre = jugador.getNombre();
        saldo = jugador.getSaldo();
        propiedades = jugador.propiedades;
        casillaActual = jugador.getCasillaActual();
        cartaLibertad = jugador.cartaLibertad;
        
        for(TituloPropiedad miTitulo : propiedades)
            miTitulo.setPropietario(this);
    }
    
    public int getFactorEspeculador() {
        return factorEspeculador;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Casilla getCasillaActual() {
        return casillaActual;
    }
    
    public boolean getEncarcelado() {
        return encarcelado;
    }
    
    public int getSaldo() {
        return saldo;
    }
    
    public boolean tengoPropiedades() {
        return !(propiedades.isEmpty());
    }   
    
    boolean actualizarPosicion(Casilla casilla) { 
        if(casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla())
            modificarSaldo(Qytetet.PRECIO_SALIDA);
        
        boolean tengoPropietario = false;
        setCasillaActual(casilla);        
        
        if(casilla.soyEdificable()) {
            tengoPropietario = ((Calle)casilla).tengoPropietario();
            if(tengoPropietario) {
                encarcelado = ((Calle)casilla).propietarioEncarcelado();
                if(!encarcelado) {
                    int costeAlquiler = ((Calle)casilla).cobrarAlquiler();
                    modificarSaldo(-costeAlquiler);
                }
            }
        }
        else {
            if(casilla.getTipo() == TipoCasilla.IMPUESTO){
                int coste = casilla.getCoste();
                pagarImpuestos(coste);
                System.out.println("\n¡IMPUESTO! Pierdes "+(-coste));
            }
        }
        return tengoPropietario;
    }

    
    boolean comprarTitulo() {
        boolean puedoComprar = false;
        
        if(casillaActual.soyEdificable()) {
            boolean tengoPropietario = ((Calle)casillaActual).tengoPropietario();
            if(!tengoPropietario) {
                int costeCompra = ((Calle)casillaActual).getCoste();
                if(costeCompra <= saldo) {
                    TituloPropiedad titulo = ((Calle)casillaActual).asignarPropietario(this);
                    titulo.setCasilla(((Calle)casillaActual));
                    propiedades.add(titulo);
                    modificarSaldo(-costeCompra);
                    puedoComprar = true;
                }
            }
        }
        
        return puedoComprar;
    }
    
    Sorpresa devolverCartaLibertad() {
        Sorpresa cartaDevuelta = cartaLibertad;
        cartaLibertad = null;
        
        return cartaDevuelta;
    }
    
    void irACarcel(Casilla casilla) {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    void modificarSaldo(int cantidad) {
        saldo += cantidad;
    }
    
    int obtenerCapital() {
        int capital = saldo;
        
        for (TituloPropiedad misTitulos : propiedades) {
            capital += misTitulos.getCasilla().getCoste() + (misTitulos.getCasilla().getNumCasas() + 
            misTitulos.getCasilla().getNumHoteles()) * misTitulos.getPrecioEdificar();
            
            if (misTitulos.getHipotecada())
                capital -= misTitulos.getHipotecaBase();
        }
        
        return capital;
    }
    
    ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas(boolean hipotecada) {
        ArrayList<TituloPropiedad> propiedadesHipotecasdas = new ArrayList();
        
        for (TituloPropiedad miTitulo : propiedades)
            if(miTitulo.getHipotecada() == hipotecada)
                propiedadesHipotecasdas.add(miTitulo);
        
        return propiedadesHipotecasdas;
    }    
    
    void pagarCobrarPorCasaYHotel(int cantidad) {
        int numeroTotal = cuantasCasasHotelesTengo();
        modificarSaldo(numeroTotal*cantidad);
    }
    
    boolean pagarLibertad(int cantidad) {
        if(tengoSaldo(cantidad))
            modificarSaldo(-cantidad);
        
        return tengoSaldo(0);
    }
    
    boolean puedoEdificarCasa(Calle calle) {
        boolean puedoEdificar = false;
        boolean esMia = esDeMiPropiedad(calle);
        
        if (esMia) {
            int costeEdificarCasa = calle.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
            puedoEdificar = esMia && tengoSaldo;
        }
        
        return puedoEdificar;
    }

    boolean puedoEdificarHotel(Calle calle) {
        boolean puedoEdificar = false;
        boolean esMia = esDeMiPropiedad(calle);
        
        if (esMia) {
            int costeEdificarHotel = calle.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarHotel);
            puedoEdificar = esMia && tengoSaldo;
        }
        
        return puedoEdificar;
    }
    
    boolean puedoHipotecar(Calle calle) { 
        return esDeMiPropiedad(calle);
    }

    boolean puedoPagarHipoteca(Calle calle) {
        return saldo >= calle.cancelarHipoteca();
    }

    boolean puedoVenderPropiedad(Calle calle) {
        return esDeMiPropiedad(calle) && !calle.estaHipotecada();
    }
    
    void setCartaLibertad(Sorpresa carta) {
        cartaLibertad = carta;
    }

    void setCasillaActual(Casilla casilla) {
        casillaActual = casilla;
    }

    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    
    boolean tengoCartaLibertad() {
        return cartaLibertad != null;
    }

    void venderPropiedad(Calle calle) {
        int precioVenta = calle.venderTitulo();
        modificarSaldo(precioVenta);
        eliminarDeMisPropiedades(calle);
    }
    
    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad);
    }
    
    
    protected Especulador convertirme(int fianza) {
        Especulador especulador = new Especulador (this, fianza);
        
        return especulador;
    }
    
    private int cuantasCasasHotelesTengo() {
        int numCasasHoteles = 0;
        for (TituloPropiedad miTitulo : propiedades)
            numCasasHoteles += miTitulo.getCasilla().getNumHoteles() +
                    miTitulo.getCasilla().getNumCasas();
            
        return numCasasHoteles;
    }
    
    private void eliminarDeMisPropiedades(Calle calle) {
        TituloPropiedad miTitulo = null;
        for (TituloPropiedad titulo : propiedades)
            if (calle == titulo.getCasilla())
                miTitulo = titulo;
        if (miTitulo != null)
            propiedades.remove(miTitulo);
    }

    private boolean esDeMiPropiedad(Calle calle) {
        boolean mePertenece = false;
        for (TituloPropiedad misTitulos : propiedades)
            if (calle == misTitulos.getCasilla())
                mePertenece = true;
        
        return mePertenece;    
    }

    private boolean tengoSaldo(int cantidad) {
        return saldo >= cantidad;
    }
    
    @Override
    public String toString() {
        return getNombre() + "\nSaldo: " + getSaldo();
    }

}
