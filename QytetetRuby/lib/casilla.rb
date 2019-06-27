# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#require_relative "titulo_propiedad"
#require_relative "tipo_casilla"

module ModeloQytetet
  class Casilla  
    attr_reader :coste, :tipo, :numero_casilla
    
    def initialize(num_casilla, coste, tipo)
      @numero_casilla = num_casilla
      @coste = coste
      @tipo = tipo
    end
    
    def soy_edificable
      return false;
    end
    
    def to_s
      return "\nCasilla \nNumero casilla: #{@numero_casilla}, Tipo de casilla: #{@tipo}"
    end
    
    private
#    def set_titulo_propiedad(nombre, factor_revalorizacion, alquiler_base,
#      hipoteca_base, precio_edificar)
#       if @tipo == TipoCasilla::CALLE
#        @titulo = TituloPropiedad.new(nombre, factor_revalorizacion, 
#        alquiler_base, hipoteca_base, precio_edificar)
#      end
#    end
    
#    def set_titulo_propiedad(titulo)
#      if @tipo == TipoCasilla::CALLE
#        @titulo = titulo
#        @titulo.casilla = self;
#      end
#    end
#    
  end
end