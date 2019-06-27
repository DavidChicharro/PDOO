/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author david
 */
public class Calle extends Casilla {
    private int numHoteles;
    private int numCasas;    
    private TituloPropiedad titulo;
    
    public Calle(int numeroCasilla, int coste, TituloPropiedad titulo) {
        super(numeroCasilla, coste, TipoCasilla.CALLE);
        numHoteles = 0;
        numCasas = 0;    
        this.titulo = titulo;
    }
    
    
    int getNumHoteles() {
        return numHoteles;
    }
    
    int getNumCasas() {
        return numCasas;
    }
    
    public TituloPropiedad getTitulo() {
        return titulo;
    }    
    
    void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }
    
    void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }
    
    TituloPropiedad asignarPropietario(Jugador jugador) {
        titulo.setPropietario(jugador);
        return titulo;
    }
    
    int calcularValorHipoteca() {
        return (int)(titulo.getHipotecaBase()*(1+(numCasas*0.5)+numHoteles));
    }
    
    int cancelarHipoteca() {
        titulo.setHipotecada(false);
        return (int)(calcularValorHipoteca()*1.10);       
    }
    
    int cobrarAlquiler() {
        int costeAlquilerBase = titulo.getAlquilerBase();
        int costeAlquiler = costeAlquilerBase + (int)(numCasas*0.5 + numHoteles*2);
        titulo.cobrarAlquiler(costeAlquiler);
        
        return costeAlquiler;
    }    

    int edificarCasa() {
        setNumCasas(numCasas+1);
        int costeEdificarCasa = getPrecioEdificar();
        
        return costeEdificarCasa;
    }
    
    int edificarHotel() {
        setNumHoteles(numHoteles+1);
        setNumCasas(0);
        int costeEdificarHotel = getPrecioEdificar();
        
        return costeEdificarHotel;
    }
    
    boolean estaHipotecada() {
        return titulo.getHipotecada();
    }
    
    int getCosteHipoteca() {
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    int getPrecioEdificar() {
        return titulo.getPrecioEdificar();
    }
    
    int hipotecar() {
        titulo.setHipotecada(true);
        int cantidadRecibida = calcularValorHipoteca();
        
        return cantidadRecibida;
    }
    /*
    int precioTotalComprar() {
        throw new UnsupportedOperationException("Sin implementar");
    }
    */
    boolean propietarioEncarcelado() {
        return titulo.propietarioEncarcelado();
    }
    
    boolean sePuedeEdificarCasa() {
        int factorEspeculador = titulo.getPropietario().getFactorEspeculador();
        return (tipo == TipoCasilla.CALLE) && (numCasas < 4*factorEspeculador);
    }
    
    boolean sePuedeEdificarHotel() {        
        int factorEspeculador = titulo.getPropietario().getFactorEspeculador();
        return (tipo == TipoCasilla.CALLE) && (numHoteles < 4*factorEspeculador) && (numCasas == 4*factorEspeculador);
    }    
        
    boolean tengoPropietario() {
        return titulo.tengoPropietario();
    }
    
    int venderTitulo() {
        int precioCompra = coste + (numCasas+numHoteles)*getPrecioEdificar();
        int precioVenta = (int)(precioCompra*(1 + 0.01*titulo.getFactorRevalorizacion() ));
        titulo.setPropietario(null);
        setNumHoteles(0);
        setNumCasas(0);
        
        return precioVenta;
    }
    
    /*
    private void setTituloPropiedad(TituloPropiedad titulo) {
        if (this.tipo == TipoCasilla.CALLE) {
            this.titulo = titulo;
            this.titulo.setCasilla(this);
        }
    }
    
    private void asignarTituloPropiedad() {
        tiulo.setCasilla(this);
    }*/    
    
    
    @Override
    boolean soyEdificable() {
        return true;            
    }
    
    @Override
    public String toString () {
        return "Número casilla=" + Integer.toString(numeroCasilla) + 
               "\nTipo de casilla: Calle \nCoste: " + Integer.toString(coste) + 
               "\nNúmero de hoteles: " + Integer.toString(numHoteles) + 
               "\nNumero de casas: " + Integer.toString(numCasas) +
               "\n\nTitulo de propiedad" + titulo;
    }
    
}
