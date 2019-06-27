# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require "singleton"

module ModeloQytetet
  class Dado
    attr_reader :instance
    include Singleton
    
    def tirar
      return rand(1..6)
    end
    
  end
end
