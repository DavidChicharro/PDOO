# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#require_relative "casilla"
#require_relative "jugador"

module ModeloQytetet
  class TituloPropiedad
    attr_reader :nombre, :alquiler_base, :factor_revalorizacion, :hipoteca_base, :precio_edificar
    attr_accessor :hipotecada, :casilla, :propietario
    
    def initialize(nombre, alquiler_base, factor_revalorizacion, 
        hipoteca_base, precio_edificar)
      @nombre = nombre
      @hipotecada = false
      @alquiler_base = alquiler_base
      @factor_revalorizacion = factor_revalorizacion
      @hipoteca_base = hipoteca_base
      @precio_edificar = precio_edificar
      @propietario = nil
      @casilla = nil
    end
    
    def cobrar_alquiler(coste)
      @propietario.modificar_saldo(coste)
    end
    
    def propietario_encarcelado
      return @propietario.encarcelado
    end
    
    def tengo_propietario
      return @propietario != nil
    end
    
    def to_s
      salida = " Calle: #{@nombre}, Precio edificar=#{@precio_edificar}, 
      Factor revalorizacion=#{@factor_revalorizacion}%, Precio alquiler base=#{@alquiler_base}, 
      Precio hipoteca base=#{@hipoteca_base},  ¿hipotecada?: #{@hipotecada}"
      
      if(@propietario != nil)
        salida += "\nPropietario: #{@propietario.nombre}"
      end
      
      return salida
    end
  end
  
  
  
end