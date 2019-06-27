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
public abstract class Casilla {
    protected int coste;
    protected int numeroCasilla;    
    protected TipoCasilla tipo;
    
    public Casilla(int numeroCasilla, int coste, TipoCasilla tipo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.tipo = tipo;
    }
    
    int getCoste() {
        return coste;
    }
    
    public int getNumeroCasilla() {
        return numeroCasilla;
    }
    
    public TipoCasilla getTipo() {
        return tipo;
    }
     
    
    abstract boolean soyEdificable();

    /*
    @Override
    public String toString () {
        return "Casilla{" + "\nNumero casilla=" + Integer.toString(numeroCasilla) + 
               ", Tipo de casilla=" + tipo +", Coste=" + Integer.toString(coste) + 
               ", Numero de hoteles=" + Integer.toString(numHoteles) + 
               ", Numero de casas=" + Integer.toString(numCasas) +
               "\nTitulo de propiedad=" + titulo + "}";
    }*/
}