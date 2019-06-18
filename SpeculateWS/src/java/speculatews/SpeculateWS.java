/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speculatews;

import modelos.Jogador;
import modelos.Partida;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Semaphore;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "SpeculateWS")
public class SpeculateWS {
    
    @WebMethod(operationName = "preRegistro")
    public int preRegistro(@WebParam(name = "nomeJ1") String nomeJ1, @WebParam(name = "idJ1") int idJ1,
            @WebParam(name = "nomeJ2") String nomeJ2, @WebParam(name = "idJ2") int idJ2) {
        return 0;
    }
    
    @WebMethod(operationName = "registraJogador")
    public int registraJogador(@WebParam(name = "nome") String nome) {
        return 0;
    }
    
    @WebMethod(operationName = "encerraPartida")
    public int encerraPartida(@WebParam(name = "id") int id) {
        return 0;
    }
    
    @WebMethod(operationName = "temPartida")
    public int temPartida(@WebParam(name = "id") int id) {
        return 0;
    }
    
    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") int id) {
        return "";
    }
    
    @WebMethod(operationName = "ehMinhaVez")
    public int ehMinhaVez(@WebParam(name = "id") int id) {
        return 0;
    }
      
    @WebMethod(operationName = "obtemNumBolas")
    public int obtemNumBolas(@WebParam(name = "id") int id) {
        return 0;
    }
    
    @WebMethod(operationName = "obtemNumBolasOponente")
    public int obtemNumBolasOponente(@WebParam(name = "id") int id) {
        return 0;
    }
    
    @WebMethod(operationName = "obtemTabuleiro")
    public String obtemTabuleiro(@WebParam(name = "id") int id) {
        return "";
    }
    
    @WebMethod(operationName = "defineJogadas")
    public int defineJogadas(@WebParam(name = "id") int id, @WebParam(name = "jogadas") int jogadas) {
        return 0;
    }
    
    @WebMethod(operationName = "jogaDado")
    public int jogaDado(@WebParam(name = "id") int id) {
        return 0;
    }
}
