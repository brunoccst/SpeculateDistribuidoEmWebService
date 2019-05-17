# SpeculateDistribuidoEmWebService
Trabalho 2 da cadeira 4645F-04 - Programação Distribuída - Turma 128 - 2019/1 - Prof. Roland Teodorowitsch - PUCRS

# Definição (retirada do arquivo "Definição.pdf")
Neste trabalho deverá ser implementada uma aplicação distribuída em Java Web Services (usando
SOAP – Simple Object Access Protocol) que permita o gerenciamento de várias partidas simultâneas
entre 2 jogadores do jogo Speculate (conforme regras definidas na seção Introdução).

A aplicação deverá ser formada por dois programas: um servidor (executado por um processo) e um
cliente (eventualmente executado por vários processos).

Quando o servidor for iniciado, ele deverá ser criado de forma que se possa disputar até 500 partidas
simultâneas. Este servidor deverá funcionar de forma muito parecida com o servidor implementado
como Trabalho 1 desta disciplina. No entanto, antes do registro normal dos jogadores para iniciar uma
partida, o servidor deverá aceitar o “pré-registro” dos jogadores, onde informa-se o nome do primeiro
jogador, o identificador que este primeiro jogador receberá quando ele fizer o registro, o nome do
segundo jogador e o identificador que este segundo jogador receberá quando ele fizer o registro. Esta
especificação de nomes e seus respectivos identificadores servirá apenas como ferramenta de teste
automatizado do servidor. Se for feito o pré-registro envolvendo 2 jogadores, eles deverão ser
associados na mesma partida, mesmo que entre o registro deles ocorra o registro de outros jogadores.

O programa cliente, por sua vez, terá uma estrutura diferente da estrutura do cliente tradicional para
execução de uma aplicação interativa (portanto, diferente da estrutura do cliente sugerida para o
Trabalho 1 desta disciplina). A nova estrutura do cliente lerá de um arquivo (com a extensão “.in”) a
especificação de um conjunto de chamadas remotas que devem ser executadas no servidor. E deverá
salvar em outro arquivo (com a extensão “.out”) o resultado da execução de cada uma destas chamadas.

Para iniciar uma partida de Speculate no servidor remoto, é preciso fazer o registro de dois jogadores,
cada um recebendo como resposta um identificador único (que será usado nas demais chamadas). O uso
de determinado nome de jogador deve ser feito de forma única, sem permitir dois jogadores com o
mesmo nome e reservando-se o direito de uso de determinado nome a quem registrar-se antes no
servidor. Com o pré-registro, será possível prever qual o identificador que determinado jogador
receberá no seu registro, e assim predefinir uma série de operações.
