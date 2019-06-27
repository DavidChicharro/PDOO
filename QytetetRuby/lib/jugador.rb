# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module ModeloQytetet

  class Jugador    
    attr_accessor :encarcelado, :nombre, :saldo, :propiedades, 
      :casilla_actual, :carta_libertad, :factor_especulador    

    def initialize(nombre)
      @nombre = nombre
      @saldo = 7500
      @encarcelado = false
      @propiedades = []
      @casilla_actual = nil
      @carta_libertad = nil
      @factor_especulador = 1
    
    end
    
    def copia_jugador(jugador)
      @nombre = jugador.nombre
      @saldo = jugador.saldo
      @encarcelado = jugador.encarcelado
      @propiedades = jugador.propiedades
      @casilla_actual = jugador.casilla_actual
      @carta_libertad = jugador.carta_libertad     

      if @propiedades != nil
        for mi_titulo in @propiedades
          mi_titulo.propietario = self
        end
      end
    
    #  @factor_especulador = jugador.factor_especulador
    end
    
    def actualizar_posicion(casilla)
      if(casilla.numero_casilla < @casilla_actual.numero_casilla)
        modificar_saldo(Qytetet.saldo_salida)
      end
      @casilla_actual = casilla
      tengo_propietaro = false
      
      if casilla.soy_edificable
        tengo_propietaro = casilla.tengo_propietario
        if tengo_propietaro
          @encarcelado = casilla.propietario_encarcelado
          if !@encarcelado
            coste_alquiler = casilla.cobrar_alquiler
            modificar_saldo(-coste_alquiler)
          end
        end
      elsif casilla.tipo == TipoCasilla::IMPUESTO
          coste = casilla.coste
          pagar_impuestos(coste)
          puts "\n¡IMPUESTO! Pierdes - #{coste}"
      end
      
      return tengo_propietaro
    end
    
    def comprar_titulo
      puedo_comprar = false
      
      if @casilla_actual.soy_edificable
        tengo_propietario = @casilla_actual.tengo_propietario
        if !tengo_propietario
          coste_compra = @casilla_actual.coste
          if coste_compra <= @saldo
            titulo = @casilla_actual.asignar_propietario(self)
            titulo.casilla = @casilla_actual
            @propiedades<< titulo
            modificar_saldo(-coste_compra)
            puedo_comprar = true
          end
        end
      end
      
      return puedo_comprar
    end
    
    def devolver_carta_libertad
      carta_devuelta = @carta_libertad
      @carta_libertad = nil
      return carta_devuelta
    end
    
    def ir_a_carcel(casilla)
      @casilla_actual = casilla
      @encarcelado = true
    end
    
    def modificar_saldo(cantidad)
      @saldo += cantidad
    end
    
    def obtener_capital
      capital = @saldo
      
      for titulos in @propiedades
        capital += titulos.casilla.coste + 
          (titulos.casilla.num_casas + titulos.casilla.num_hoteles)*(titulos.precio_edificar)
        if titulos.hipotecada
          capital -= titulos.hipoteca_base
        end
      end
      
      return capital
    end
    
    def obtener_propiedades_hipotecadas(hipotecada)
      propiedades_hipotecadas = []
      
      for titulo in @propiedades
        if titulo.hipotecada == hipotecada
          propiedades_hipotecadas<< titulo
        end
      end
      
      return propiedades_hipotecadas
    end
    
    def pagar_cobrar_por_casa_y_hotel(cantidad)
      numero_total = cuantas_casas_hoteles_tengo
      modificar_saldo(numero_total*cantidad)
    end
    
    def pagar_impuestos(cantidad)
      modificar_saldo(-cantidad)
    end
    
    def pagar_libertad(cantidad)
      if tengo_saldo(cantidad)
        modificar_saldo(-cantidad)
      end
      
      return tengo_saldo(cantidad)
    end
    
    def puedo_edificar_casa(casilla)
      puedo_edificar = false
      es_mia = es_de_mi_propiedad(casilla)
      
      if es_mia
        coste_edificar_casa = casilla.precio_edificar
        tengo_saldo = tengo_saldo(coste_edificar_casa)
        puedo_edificar = es_mia && tengo_saldo
      end
      
      return puedo_edificar
    end
    
    def puedo_edificar_hotel(casilla)
      puedo_edificar = false
      es_mia = es_de_mi_propiedad(casilla)
      
      if es_mia
        coste_edificar_hotel = casilla.precio_edificar
        tengo_saldo = tengo_saldo(coste_edificar_hotel)
        puedo_edificar = es_mia && tengo_saldo
      end
      
      return puedo_edificar
    end
    
    def puedo_hipotecar(casilla)
      return es_de_mi_propiedad(casilla)
    end
    
    def puedo_vender_propiedad(casilla)
      return es_de_mi_propiedad(casilla) && !casilla.esta_hipotecada
    end
    
    def puedo_pagar_hipoteca(casilla)
      return @saldo >= casilla.cancelar_hipoteca      
    end
    
    def tengo_carta_libertad
      return @carta_libertad != nil
    end
    
    def vender_propiedad(casilla)
      precio_venta = casilla.vender_titulo
      modificar_saldo(precio_venta)
      eliminar_de_mis_propiedades(casilla)
    end
    
    def tengo_propiedades
      return !(@propiedades.empty?)
    end
    
    def convertirme(fianza)
      especulador = Especulador.new(self,fianza)
      return especulador
    end
    
    def to_s
      "\nNombre: #{@nombre} \nSaldo: #{@saldo}"
    end
    
    private
    def cuantas_casas_hoteles_tengo
      num_casas_hoteles = 0
      for titulo in @propiedades
        num_casas_hoteles += titulo.casilla.num_hoteles + titulo.casilla.num_casas
      end
      
      return num_casas_hoteles      
    end
    
    def eliminar_de_mis_propiedades(casilla)
      @propiedades.delete(casilla.titulo)
    end
    
    def es_de_mi_propiedad(casilla)
      me_pertenece = false;
      for titulo in @propiedades
        if casilla == titulo.casilla
          me_pertenece = true
        end
      end
      
      return me_pertenece
    end
    
    def tengo_saldo(cantidad)
      return @saldo >= cantidad
    end
    
  end
  
end
