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
import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelos.PreRegistro;
import modelos.Tabuleiro;

@WebService(serviceName = "SpeculateWS")
public class SpeculateWS {
    
    // Parametros de configuracao do servidor e jogo.
    private static final long serialVersionUID = 515651651;
    private static final int maxPartidas = 500;
    private static final int maxJogadores = 2  * maxPartidas;
    
    // Propriedades que armazenam os dados do jogo.
    private static Map<Integer, Partida> partidas = new Hashtable<Integer, Partida>(maxPartidas);
    private static Map<String, Jogador> jogadores = new Hashtable<String, Jogador>(maxJogadores);
    private static int IDPartidas = 0;
    
    // Classes auxiliares para a logica.
    private final PreRegistro preRegistro = new PreRegistro(maxJogadores);
    private static Semaphore semaforo = new Semaphore(1);
    
    @WebMethod(operationName = "preRegistro")
    public int preRegistro(@WebParam(name = "nomeJ1") String nomeJ1, @WebParam(name = "idJ1") int idJ1,
            @WebParam(name = "nomeJ2") String nomeJ2, @WebParam(name = "idJ2") int idJ2) {
        
        try
        {
            semaforo.acquire();
            
            Jogador j1 = new Jogador(idJ1, nomeJ1);
            Jogador j2 = new Jogador(idJ2, nomeJ2);

            preRegistro.fazPreRegistro(j1, j2);

            System.out.println("Jogadores pré-registrados: " + nomeJ1 + " e " + nomeJ2);
        }
        catch (Exception e)
        {
            System.out.println("Erro em registraJogador.");
            e.printStackTrace();
        }
        finally 
        {
            semaforo.release();
        }
        
        return 0;
    }
    
    @WebMethod(operationName = "registraJogador")
    public int registraJogador(@WebParam(name = "nome") String nome) {
        int codigo = 0;
        
        try
        {
            semaforo.acquire();
            
            // Retorna -3 se o maximo de pertidas ja foram cadastradas.
            if (partidas.size() >= maxPartidas)
            {
                codigo = -3;
            }
            // Retorna -2 se o maximo de jogadores ja foram cadastrados.
            else if (jogadores.size() >= maxJogadores)
            {
                codigo = -2;
            }
            // Retorna -1 se o jogador ja estiver cadastrado.
            else if (jogadores.containsKey(nome))
            {
                codigo = -1;
            }
            else
            {
                // Pega o pre registro do jogador.
                PreRegistro.Tupla tupla = preRegistro.getPreRegistro(nome);
                codigo = tupla.jogador1.getId();
                
                // Verifica se o seu oponente também já foi registrado.
                if (preRegistro.preRegistroExiste(tupla.jogador2.getNome()))
                {
                    Partida novaPartida = new Partida(IDPartidas++);
                    novaPartida.adicionaJogador(tupla.jogador1);
                    novaPartida.adicionaJogador(tupla.jogador2);
                    partidas.put(tupla.jogador1.getId(), novaPartida);
                    partidas.put(tupla.jogador2.getId(), novaPartida);
                    
                    jogadores.put(tupla.jogador1.getNome(), tupla.jogador1);
                    jogadores.put(tupla.jogador2.getNome(), tupla.jogador2);
                    
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Erro em registraJogador.");
            e.printStackTrace();
        }
        finally
        {
            semaforo.release();
        }
        return codigo;
    }
    
    @WebMethod(operationName = "encerraPartida")
    public int encerraPartida(@WebParam(name = "id") int id) {
        int codigo = 0;
        
        try
        {
            semaforo.acquire();

            Partida partida = getPartida(id);

            // Se a partida nao acabou, acaba ela por WO.
            if (!partida.getPartidaAcabou())
            {
                partida.encerraPartidaPorWO(id, true);
            }
            // Se nao, apenas a encerra por parte deste jogador.
            else
            {
                partida.encerraPartida(id);
            }
            
            // Remove a partida vinculada a este jogador da lista de partidas
            partidas.remove(id);
            
            // Tambem remove o jogador da lista de jogadores.
            String nomeDoJogador = (partida.getJogador1().getId() == id)
                    ? partida.getJogador1().getNome()
                    : partida.getJogador2().getNome();
            jogadores.remove(nomeDoJogador);

            codigo = 0;
        }
        catch(Exception e) {
                System.out.println("Erro em encerraPartida");
                e.printStackTrace();
                codigo = -1;
        } finally {
                semaforo.release();
        }
        
        return codigo;
    }
    
    @WebMethod(operationName = "temPartida")
    public int temPartida(@WebParam(name = "id") int id) {
        try {
            semaforo.acquire();

            Partida partida = getPartida(id);
            if (partida == null || partida.getJogador2() == null) {
                    return 0;
            }
            else if (partida.getJogador1().getId() == id) {
                    return 1;
            }
            else if (partida.getJogador2().getId() == id){
                    return 2;
            }
        } catch(Exception ex) {
                System.out.println("Erro em encerraPartida");
                ex.printStackTrace();
                return -1;
        } finally {
                semaforo.release();
        }

        return -2;
    }
    
    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") int id) {
        try
        {
            semaforo.acquire();

            Partida partida = getPartida(id);

            if (partida.getJogador1() == null || partida.getJogador2() == null)
                    return "";

            if (partida.getJogador1().getId() == id)
                    return partida.getJogador2().getNome();
            else
                    return partida.getJogador1().getNome();
        }
        catch(Exception e)
        {
                System.out.println("Erro em obtemOponente");
                e.printStackTrace();
        }
        finally
        {
            semaforo.release();
        }

        return null;
    }
    
    @WebMethod(operationName = "ehMinhaVez")
    public int ehMinhaVez(@WebParam(name = "id") int id) {
         
        Partida partida = getPartida(id);
		
        // Nao foi encontrada partida ou ha partida mas ainda nao iniciou (nao ha dois jogadores).
        if (partida == null || (partida != null && partida.getJogador2() == null)) {
            return -2;
        }
        else
        {
            Jogador ganhador = partida.getGanhador();
        
            // Caso a partida ainda nao tenha finalizado..
            if (ganhador == null) {
                Jogador jogadorEmAcao = partida.getJogadorEmAcao();
                if (jogadorEmAcao.getId() == id)
                    return 1;
                else
                    return 0;
            }
            else
            {
                // Se a partida acabou por WO..
                if(partida.getPartidaAcabouPorWO()) {
                    
                    // Se quem ganhou eh o jogador que fez a chamada, retorna 5.
                    if (ganhador.getId() == id)
                        return 5;
                    // Se nao, retorna 6.
                    else
                        return 6;
                }
                else
                {
                    // Se quem ganhou eh o jogador que fez a chamada, retorna 2.
                    if (ganhador.getId() == id)
                        return 2;
                    // Se nao, retorna 3.
                    else
                        return 3;
                }
            }
        }
    }
      
    @WebMethod(operationName = "obtemNumBolas")
    public int obtemNumBolas(@WebParam(name = "id") int id) {
        Partida partida = getPartida(id);

        Jogador jogador = (partida.getJogador1().getId() == id)
                ? partida.getJogador1()
                : partida.getJogador2();

        return jogador.getBolasEmMao();
    }
    
    @WebMethod(operationName = "obtemNumBolasOponente")
    public int obtemNumBolasOponente(@WebParam(name = "id") int id) {
        Partida partida = getPartida(id);

        Jogador jogador = (partida.getJogador1().getId() == id)
                ? partida.getJogador2()
                : partida.getJogador1();

        return jogador.getBolasEmMao();			
    }
    
    @WebMethod(operationName = "obtemTabuleiro")
    public String obtemTabuleiro(@WebParam(name = "id") int id) {
        Partida partida = getPartida(id);
        if(partida == null)
                return "";

        return partida.getTabuleiro().toString();
    }
    
    @WebMethod(operationName = "defineJogadas")
    public int defineJogadas(@WebParam(name = "id") int id, @WebParam(name = "jogadas") int jogadas) {		
        Partida partida = getPartida(id);
        
        if (partida == null || partida.getJogador2() == null)
                return -2;
        else {
            Jogador jogador = partida.getJogadorEmAcao();
            if (jogador.getId() != id) {
                return -3;
            }
            else if (jogadas < 0 || jogador.getBolasEmMao() < jogadas) {
                return -5;
            }
            else {
                jogador.setNumeroDeJogadas(jogadas);
                return 1;
            }
        }
    }
    
    @WebMethod(operationName = "jogaDado")
    public int jogaDado(@WebParam(name = "id") int id) {
        boolean podeJogar = ehMinhaVez(id) == 1;
        
        if (podeJogar)
        {
            Partida partida = getPartida(id);
            if (partida == null || partida.getJogador2() == null)
                return -2; // erro: ainda nao ha partida
            
            Jogador jogador = (partida.getJogador1().getId() == id)
                ? partida.getJogador1()
                : partida.getJogador2();

            int nroDeJogadas = jogador.getNumeroDeJogadas();
            if (nroDeJogadas == 0)
            {
                return -4; // eh a vez do jogador, mas nao para jogar dados
            }
            else
            {
                // Joga o dado ate o numero de jogadas definidas pelo usuario
                Tabuleiro tabuleiro = partida.getTabuleiro();

                Random r = new Random();
                int resultado = r.nextInt(6) + 1;
                if (resultado == 6)
                {
                    tabuleiro.colocaBolaNaCaneleta();
                    jogador.decrementaBolasEmMaos(1);
                }
                else {
                    if (tabuleiro.temBolaNaCasa(resultado)) {
                        tabuleiro.tiraBolaDaCasa(resultado);
                        jogador.incrementaBolasEmMaos(1);
                    }
                    else {
                        tabuleiro.colocaBolaNaCasa(resultado);
                        jogador.decrementaBolasEmMaos(1);
                    }
                }

                if (jogador.getBolasEmMao() == 0) {
                    partida.encerraPartida(id);
                }
                else {
                    nroDeJogadas -= 1;
                    jogador.setNumeroDeJogadas(nroDeJogadas);
                    if (nroDeJogadas == 0) {
                            partida.trocaTurno();
                    }							
                }
                return resultado;
            }
        }
        else
        {
            return -3; // nao eh a vez do jogador
        }
    }
    
    // Pega a partida de forma sincronizada para nao haver conflitos entre threads.
    private synchronized Partida getPartida(int id) {
	return partidas.get(id);
    }
}
