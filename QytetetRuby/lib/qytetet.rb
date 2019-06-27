# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require "singleton"

module ModeloQytetet
  class Qytetet
    include Singleton    
    attr_reader :carta_actual, :mazo, :jugadores, :jugador_actual, :tablero, :instance
    
    @@max_jugadores = 4
    @@max_cartas = 10
    @@max_casillas = 20
    @@precio_libertad = 200
    @@saldo_salida = 1000
    
    def initialize      
      @carta_actual = nil
      @mazo = []
      @jugadores = []
      @jugador_actual = nil
      @tablero = nil
      @dado = Dado.instance
    end
    
    def self.saldo_salida
      return @@saldo_salida
    end
    
    
    def aplicar_sorpresa
      tiene_propietario = false
      if (@carta_actual.tipo != nil)        
        case @carta_actual.tipo
          when TipoSorpresa::PAGARCOBRAR
            @jugador_actual.modificar_saldo(@carta_actual.valor)
          when TipoSorpresa::IRACASILLA
            es_carcel = @tablero.es_casilla_carcel(@carta_actual.valor)
            if es_carcel
              encarcelar_jugador
            else
              nueva_casilla = @tablero.obtener_casilla_numero(@carta_actual.valor)
              tiene_propietario = @jugador_actual.actualizar_posicion(nueva_casilla)
            end
          when TipoSorpresa::PORCASAHOTEL
            @jugador_actual.pagar_cobrar_por_casa_y_hotel(@carta_actual.valor)
          when TipoSorpresa::PORJUGADOR
            for jugador in @jugadores
              if jugador != @jugador_actual
                jugador.modificar_saldo(-@carta_actual.valor)
                @jugador_actual.modificar_saldo(@carta_actual.valor)
              end
            end
          when TipoSorpresa::CONVERTIRME
            jugador_especulador = @jugador_actual.convertirme(@carta_actual.valor);
            @jugador_actual = jugador_especulador
            
            i = -1
            for j in @jugadores
              if j.nombre == @jugador_actual.nombre
                i = @jugadores.index(j)
                @jugadores.delete(j)
              end
            end

            @jugadores.insert(i, @jugador_actual);            
        end
                
      end
      if @carta_actual.tipo == TipoSorpresa::SALIRCARCEL
        @jugador_actual.carta_libertad = @carta_actual
      else
        @mazo<< @carta_actual
      end
      return tiene_propietario
    end
    
    def cancelar_hipoteca(casilla)
      esta_hipotecada = casilla.esta_hipotecada
      if esta_hipotecada
        if @jugador_actual.puedo_pagar_hipoteca(casilla)
          coste_cancelacion = casilla.cancelar_hipoteca
          @jugador_actual.modificar_saldo(-coste_cancelacion)
        end
      end
      return esta_hipotecada
    end
    
    def comprar_titulo_propiedad
      return @jugador_actual.comprar_titulo
    end
    
    def edificar_casa(casilla)
      puedo_edificar = false
      if casilla.soy_edificable
        se_puede_edificar = casilla.se_puede_edificar_casa
        if se_puede_edificar
          puedo_edificar = @jugador_actual.puedo_edificar_casa(casilla)
          if puedo_edificar
            coste_edificar_casa = casilla.edificar_casa
            @jugador_actual.modificar_saldo(-coste_edificar_casa)
          end
        end
      end
      return puedo_edificar
    end
    
    def edificar_hotel(casilla)
      puedo_edificar = false
      if casilla.soy_edificable
        se_puede_edificar = casilla.se_puede_edificar_hotel
        if se_puede_edificar
          puedo_edificar = @jugador_actual.puedo_edificar_hotel(casilla)
          if puedo_edificar
            coste_edificar_hotel = casilla.edificar_hotel
            @jugador_actual.modificar_saldo(-coste_edificar_hotel)
          end            
        end
      end
      return puedo_edificar
    end
    
    def hipotecar_propiedad(casilla)
      puedo_hipotecar = false
      if casilla.soy_edificable
        se_puede_hipotecar = !casilla.esta_hipotecada
        if se_puede_hipotecar
          puedo_hipotecar = @jugador_actual.puedo_hipotecar(casilla)
          if puedo_hipotecar
            cantidad_recibida = casilla.hipotecar
            @jugador_actual.modificar_saldo(-cantidad_recibida)
          end
        end
      end
      return puedo_hipotecar
    end
    
    def inicializar_juego(nombres)      
      iniciar_tablero
      iniciar_cartas_sorpresa
      iniciar_jugadores(nombres)      
      salida_jugadores
    end
    
    def intentar_salir_carcel(metodo)      
      if metodo == MetodoSalirCarcel::TIRANDODADO
        valor_dado = @dado.tirar
        puts "Valor dado: #{valor_dado}"
        libre = valor_dado > 5
      else
        tengo_saldo = @jugador_actual.pagar_libertad(@@precio_libertad)
        libre = tengo_saldo
      end
      
      if libre
        @jugador_actual.encarcelado = false
      end
      
      return libre
    end
    
    def jugar
      valor_dado = @dado.tirar
      puts "Valor dado: #{valor_dado}"
      casilla_posicion = @jugador_actual.casilla_actual
      nueva_casilla = @tablero.obtener_nueva_casilla(casilla_posicion, valor_dado)
      tiene_propietario = @jugador_actual.actualizar_posicion(nueva_casilla)
      
      if !nueva_casilla.soy_edificable
        if nueva_casilla.tipo == TipoCasilla::JUEZ
          encarcelar_jugador
        elsif nueva_casilla.tipo == TipoCasilla::SORPRESA
          @carta_actual = @mazo.delete_at(0)
        end
      end
      
      return tiene_propietario
    end
    
    def obtener_ranking
      ranking = []      
      for jugador in @jugadores
        ranking<< [jugador.obtener_capital, jugador.nombre]
      end
      sorted_ranking = ranking.sort.reverse
      salida = "\nRANKING"
      i=1
      sorted_ranking.each do |(a,b)|
        salida += "\nPuesto: #{i} - Jugador: #{b}  Capital: #{a}"
        i=i+1
      end
      return salida
    end
    
    def vender_propiedad(casilla)
      puedo_vender = false
      if casilla.soy_edificable
        puedo_vender = @jugador_actual.puedo_vender_propiedad(casilla)
        if puedo_vender
          @jugador_actual.vender_propiedad(casilla)
        end
      end
      return puedo_vender
    end
    
    def encarcelar_jugador
      if !@jugador_actual.tengo_carta_libertad
        casilla_carcel = @tablero.carcel
        @jugador_actual.ir_a_carcel(casilla_carcel)
      else
        carta = @jugador_actual.devolver_carta_libertad
        @mazo<< carta
      end
    end
    
    def siguiente_jugador
      indice_jugador_actual = @jugadores.index(@jugador_actual)
      
      if indice_jugador_actual != (@jugadores.size - 1)
        indice_siguiente_jugador = indice_jugador_actual + 1
      else
        indice_siguiente_jugador = 0
      end
      
      @jugador_actual = @jugadores[indice_siguiente_jugador]
    end
    
    def salida_jugadores
      for mi_jugador in @jugadores
        mi_jugador.casilla_actual = @tablero.obtener_casilla_numero(0)
      end
      @jugador_actual = @jugadores[rand(@jugadores.size)]
    end
    
    def propiedades_hipotecadas_jugador(hipotecadas)
      casillas_hipotecadas = []
      titulos = @jugador_actual.obtener_propiedades_hipotecadas(hipotecadas)
      
      for titulo in titulos
        casillas_hipotecadas<< titulo.casilla
      end
      
      return casillas_hipotecadas
    end
    
    def to_s
      st = "\nJugadores: \n"
      
      for jugador in @jugadores
        st += jugador.to_s + "\n"
      end
      
      st += "\nJugador actual: " + @jugador_actual.to_s
      
      st += "\nCartas sorpresa:\n"
      
      for sorpresa in @mazo
        st += sorpresa.to_s
      end
        
      st += "\nTablero:" + @tablero.to_s
        
      return st;
    end
    
    
    private
    def iniciar_cartas_sorpresa
      @mazo = Array.new
      @mazo<< Sorpresa.new("He aquí la llave para ser la próxima portada de la revista Forbes", 3000, TipoSorpresa::CONVERTIRME)
      @mazo<< Sorpresa.new("Esto no es una tarjeta Black, pero tendrás los mismos privilegios", 5000, TipoSorpresa::CONVERTIRME)
      @mazo<< Sorpresa.new("Regaligo para tu pecho, por lo bien que lo has hecho.", 1700, TipoSorpresa::PAGARCOBRAR)
      @mazo<< Sorpresa.new("¡IMPUESTO! Hay que pagar para financiar nuestros servicios públicos", -1300, TipoSorpresa::PAGARCOBRAR)
      @mazo<< Sorpresa.new("Aquí no existe la presunción de inocencia, así que te vas derecho a la trena.", 5, TipoSorpresa::IRACASILLA)
      @mazo<< Sorpresa.new("Sabemos que te gusta viajar, así que te has ganado un vuelo directo al comienzo del juego.", 2, TipoSorpresa::IRACASILLA)
      @mazo<< Sorpresa.new("Vuelo directo con destino a... ¿París? ¿Londres? No. Me temo que a un callejón de la ciudad.", 17, TipoSorpresa::IRACASILLA)
      @mazo<< Sorpresa.new("¡Hora de cobrar el alquiler de los inquilinos!", 300, TipoSorpresa::PORCASAHOTEL)
      @mazo<< Sorpresa.new("Es una pena, pero tienes que pagar por cada una de tus propiedades.", -300, TipoSorpresa::PORCASAHOTEL)
      @mazo<< Sorpresa.new("Aquí reina el socialismo, por eso tus queridos vecinos estarán encantados de darte algo de limosna.", 400, TipoSorpresa::PORJUGADOR)
      @mazo<< Sorpresa.new("¿Recuerdas aquella vez que twiteaste que estarías encantado de compartir todo lo que tienes con los demás? ¡Tus desesos son órdenes!", -200, TipoSorpresa::PORJUGADOR)
      @mazo<< Sorpresa.new("Aquella noche de marcha con el fiscal general ha servido de algo, así que ha decidido que no entres en prisión", 0, TipoSorpresa::SALIRCARCEL)

      @mazo.shuffle!
    end
    
    def iniciar_jugadores(nombres)
      for nom_jugadores in nombres
        @jugadores<< Jugador.new(nom_jugadores)
      end
    end
    
    def iniciar_tablero
      @tablero = Tablero.new
    end
  end
end