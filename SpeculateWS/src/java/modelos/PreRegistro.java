/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Hashtable;
import java.util.Map;

/**
 * Gerencia toda a logica de pre registro.
 * @author Bruno
 */
public class PreRegistro {
    
    private static Map<String, Tupla> preRegistro;
    
    public class Tupla {
        public Jogador jogador1;
        public Jogador jogador2;
        
        public Tupla(Jogador j1, Jogador j2){
            jogador1 = j1;
            jogador2 = j2;
        }
    }
    public PreRegistro(int nroMaxDeJogadores){
        preRegistro = new Hashtable<String, Tupla>(nroMaxDeJogadores);
    }
    
    public void fazPreRegistro(Jogador j1, Jogador j2){
        Tupla t = new Tupla(j1, j2);
        preRegistro.put(j1.getNome(), t);
        
        Tupla t2 = new Tupla(j2, j1);
        preRegistro.put(j2.getNome(), t2);
    }
    
    public Tupla getPreRegistro(String chave)
    {
        Tupla t = preRegistro.get(chave);
        return t;
    }
    
    public void removePreRegistro(String chave)
    {
        preRegistro.remove(chave);
    }   
}
