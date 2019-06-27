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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private Calle calle;
    private Jugador propietario;


    public TituloPropiedad (String nombre, int alquilerBase, float factorRevalorizacion,
            int hipotecaBase, int precioEdificar){
        this.nombre = nombre;
        hipotecada = false;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        calle = null;
        propietario = null;
    }
    
    void cobrarAlquiler(int coste) {
        propietario.modificarSaldo(coste);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    boolean getHipotecada() {
        return hipotecada;
    }
    
    int getAlquilerBase() {
        return alquilerBase;
    }
    
    float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }
    
    int getHipotecaBase() {
        return hipotecaBase;
    }
    
    int getPrecioEdificar() {
        return precioEdificar;
    }
    
    Jugador getPropietario() {
        return propietario;
    }
    
    boolean propietarioEncarcelado() {
        return propietario.getEncarcelado();
    }
    
    void setCasilla(Calle calle) {
        this.calle = calle;
    }
    
    void setHipotecada(boolean valor) {
        hipotecada = valor;
    }
    
    void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
    
    Calle getCasilla() {
        return calle;
    }
    
    boolean tengoPropietario() {
        return propietario != null;
    }
    
    @Override
    public String toString() {
        String salida = "\nCalle: " + nombre + "\nPrecio edificación: " + 
            Integer.toString(precioEdificar) + "\nFactor revalorización: " + 
            Float.toString(factorRevalorizacion) + "% \nPrecio base alquiler: " 
            + Integer.toString(alquilerBase) + "\nPrecio base hipoteca: " + 
            Integer.toString(hipotecaBase);
        if(propietario != null)
            salida += "\nPropietario: " + propietario.getNombre();
        
        return salida;
    }
}
