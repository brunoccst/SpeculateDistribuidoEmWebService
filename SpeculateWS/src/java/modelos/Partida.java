/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Random;
import java.util.Timer;

public class Partida {
	private int id;
	private Jogador[] jogadores = new Jogador[2];
	private Jogador jogadorEmAcao;
	
	private Jogador ganhador;
	private Jogador perdedor;
	private boolean partidaAcabou;
	private boolean partidaAcabouPorWO;
	
	private Tabuleiro tabuleiro;
        
        private Random gerador;

	/**
	 * Cria uma nova instancia de partida sendo o jogador 1 o primeiro em ação.
	 * @param id ID da partida.
	 * @param j1 Jogador 1 e primeiro a jogar.
	 * @param j2 Jogador 2 e segundo a jogar.
	 * @param tabuleiro Tabuleiro da partida.
	 */
	public Partida(int id) {
		this.setId(id);
		this.tabuleiro = new Tabuleiro();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jogador[] getJogadores() {
		return jogadores;
	}
	
	public boolean adicionaJogador(Jogador jogador) {
		if (this.jogadores[0] == null) {
			this.jogadores[0] = jogador;
			this.setJogadorEmAcao(jogador);
			return true;
		}
		else if (this.jogadores[1] == null) {
			this.jogadores[1] = jogador;
                        gerador = new Random(jogadores[0].getId() + jogadores[1].getId());
			return true;
		}
		else {
			return false; 
		}
	}
	
	public Jogador getJogador1() {
		return this.jogadores[0];
	}
	
	public Jogador getJogador2() {
		return this.jogadores[1];
	}

	public Jogador getJogadorEmAcao() {
		return jogadorEmAcao;
	}

	public void setJogadorEmAcao(Jogador jogadorEmAcao) {
		this.jogadorEmAcao = jogadorEmAcao;
	}
	
	public void trocaTurno() {
		if (this.jogadorEmAcao == getJogador1()) {
			setJogadorEmAcao(getJogador2());
		}
		else {
			setJogadorEmAcao(getJogador1());
		}
	}

	public Jogador getGanhador() {
		return ganhador;
	}
	
	public Jogador getPerdedor() {
		return perdedor;
	}
	
	public boolean getPartidaAcabou() {
		return partidaAcabou;
	}
	
	public boolean getPartidaAcabouPorWO() {
		return partidaAcabouPorWO;
	}
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public void encerraPartida(int idDoGanhador) {
		if (getJogador1().getId() == idDoGanhador)
		{
			perdedor = getJogador2();
			ganhador = getJogador1();
		}
		else {
			perdedor = getJogador1();
			ganhador = getJogador2();
		}
				
		this.partidaAcabou = true;
	}
	public void encerraPartidaPorWO(int idDoPerdedor, boolean porWo) {
		if (getJogador1().getId() == idDoPerdedor)
		{
			perdedor = getJogador1();
			ganhador = getJogador2();
		}
		else {
			perdedor = getJogador2();
			ganhador = getJogador1();
		}
		this.partidaAcabou = true;
		this.partidaAcabouPorWO = porWo;
	}
        
        public int valorDado()
        {
            return gerador.nextInt(6) + 1;
        }

}