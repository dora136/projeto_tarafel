# Projeto LIDE - Goleiro no Escuro

## Visao geral

O projeto LIDE sera um jogo 2D simples, completo e com cara de projeto de aluno, desenvolvido em LibGDX para a aula de jogos digitais.

A ideia atual do jogo e simular uma situacao de penalti pela visao do jogador de linha, mas com o jogador controlando o goleiro. O diferencial e que o jogo nao depende muito da parte visual durante a defesa, porque a proposta e representar um goleiro cego ou com baixa visao. Por isso, o jogador precisa reagir principalmente aos sinais do jogo, aos sons e ao momento certo da bola.

O jogador deve escolher rapidamente para qual lado o goleiro vai se mover e tentar defender a cobranca. Conforme os niveis avancam, o tempo de reacao fica menor, os sons e temas podem mudar, e a dificuldade aumenta.

O jogo continua tendo relacao com fisioterapia e recuperacao motora porque trabalha reflexo, resposta rapida, coordenacao, tomada de decisao e repeticao de movimento. Ele nao substitui tratamento medico ou fisioterapia profissional; e uma proposta ludica e academica para mostrar como jogos podem ser usados em atividades de estimulo motor e cognitivo.

## Nome temporario

LIDE: Goleiro no Escuro

## Plataforma

- Engine: LibGDX
- Linguagem: Java
- Estilo: 2D
- Genero: arcade, reflexo, audio game e penalti
- Publico: estudantes, professores e pessoas interessadas em jogos aplicados a saude

## Conceito da jogabilidade

O jogo acontece em cobrancas de penalti.

A camera representa a visao do jogador de linha, olhando para o gol. Mesmo assim, o personagem controlado pelo jogador e o goleiro. Antes ou durante o chute, o jogador recebe sinais e precisa escolher uma direcao para a defesa.

Como a proposta simula um goleiro cego, a parte visual nao precisa entregar todas as informacoes da jogada. O foco deve estar em tempo de reacao, sons e leitura do momento certo.

## Objetivo do jogador

O jogador precisa defender o maior numero possivel de penaltis.

Cada defesa gera pontuacao. Se a bola entrar no gol, o jogador sofre um gol. Ao tomar uma quantidade X de gols dentro de um nivel Y, acontece Game Over.

## Objetivo pedagogico e terapeutico

O jogo foi pensado para representar, de forma simples, como um jogo pode ajudar em atividades de reabilitacao e estimulo:

- Estimular reflexo e tempo de resposta.
- Trabalhar coordenacao entre percepcao, decisao e movimento.
- Incentivar movimentos repetidos de forma divertida.
- Usar audio como elemento principal de orientacao.
- Mostrar como jogos podem ter aplicacao alem do entretenimento.

## Mecanica principal

O jogador controla o goleiro com escolhas rapidas de direcao.

A bola pode ir para 3 direcoes principais:

- Esquerda.
- Centro.
- Direita.

O goleiro precisa responder com uma das direcoes correspondentes. Para a primeira versao, a programacao pode usar apenas 2 sprites principais do goleiro:

- Goleiro parado ou preparado.
- Goleiro defendendo.

Se der tempo, o sprite de defesa pode ser espelhado para esquerda e direita, ou adaptado para centro.

## Movimento da bola

A bola tera dois comportamentos principais:

- Bola defendida: a bola bate no goleiro ou e desviada.
- Bola nao defendida: a bola entra no gol.

Esses dois resultados ja sao suficientes para criar uma experiencia clara e simples.

## Slow motion e freeze

O jogo tera um momento de slow motion ou freeze durante a cobranca.

Esse recurso serve para dar ao jogador uma pequena janela de reacao. Em vez de ser um slow motion longo e complexo, a primeira versao pode usar um freeze simples:

1. O jogador ouve o som da bola sendo chutada.
2. A tela congela por um instante curto.
3. O jogador escolhe a direcao da defesa.
4. A jogada continua e mostra se a bola foi defendida ou entrou.

Conforme os niveis avancam, o intervalo de freeze diminui, deixando o jogo mais dificil.

## Progressao de dificuldade

A dificuldade aumenta por nivel.

Ideia inicial:

- Nivel 1: maior tempo de reacao, sons mais claros, bola mais lenta.
- Nivel 2: tempo de reacao medio, bola um pouco mais rapida.
- Nivel 3: pouco tempo de reacao, bola rapida e sinais mais curtos.

Tambem e possivel mudar os temas e sons conforme os niveis:

- Nivel 1: torcida mais calma.
- Nivel 2: torcida mais intensa.
- Nivel 3: ambiente mais barulhento e pressao maior.

## Game Over

O jogador perde quando toma uma quantidade definida de gols.

Exemplo simples:

- Nivel 1: Game Over com 5 gols sofridos.
- Nivel 2: Game Over com 4 gols sofridos.
- Nivel 3: Game Over com 3 gols sofridos.

Essa regra pode ser ajustada depois nos testes para deixar o jogo mais justo.

## Pontuacao

A pontuacao deve recompensar defesas e niveis mais dificeis.

Ideia inicial:

- Defesa no nivel 1: 100 pontos.
- Defesa no nivel 2: 200 pontos.
- Defesa no nivel 3: 300 pontos.
- Sequencia de defesas: bonus opcional.

Pergunta em aberto:

- A pontuacao deve considerar apenas defesas ou tambem tempo de resposta?

## Historia

A historia ainda precisa ser definida pelo grupo.

Possivel ideia simples:

O personagem e um goleiro que perdeu a visao ou joga vendado em um desafio de treinamento. Ele precisa confiar na audicao, no reflexo e na memoria corporal para defender os penaltis.

Perguntas em aberto:

- O goleiro e realmente cego ou esta treinando vendado?
- A historia sera mais seria, sobre superacao, ou mais simples, como um desafio esportivo?
- O jogo precisa explicar a relacao com fisioterapia dentro do menu ou apenas na apresentacao?

## Controles

Controles sugeridos para a primeira versao:

- A ou seta esquerda: defender esquerda.
- S ou seta baixo: defender centro.
- D ou seta direita: defender direita.
- Espaco: iniciar ou confirmar.
- Esc: pausar.

Esses controles sao simples e combinam com a ideia de resposta rapida.

## Loop de jogo

1. O jogador inicia a partida.
2. O jogo mostra o cenario do penalti.
3. O apito toca.
4. A bola e chutada.
5. O jogo entra em freeze ou slow motion por um tempo curto.
6. O jogador escolhe esquerda, centro ou direita.
7. A bola segue para a direcao sorteada.
8. Se a escolha estiver correta, o goleiro defende e ganha pontos.
9. Se a escolha estiver errada, a bola entra no gol e conta como gol sofrido.
10. O jogo avanca para a proxima cobranca.
11. Ao tomar gols demais, o jogador recebe Game Over.

## Telas planejadas

- Menu inicial.
- Tutorial.
- Tela principal do jogo.
- Tela de pausa.
- Tela de Game Over.
- Tela de resultado final.

## Menu

O menu deve ter opcoes simples:

- Iniciar.
- Tutorial.
- Pausar.
- Reiniciar.
- Sair, se necessario.

## Tutorial

O tutorial deve explicar de forma simples:

- O jogador controla o goleiro.
- A bola pode ir para esquerda, centro ou direita.
- O jogador deve reagir durante o freeze.
- Os sons ajudam a perceber o momento do chute.
- A dificuldade aumenta nos niveis seguintes.

Como o jogo simula um goleiro cego, o tutorial deve reforcar que o som e o tempo de reacao sao parte importante da experiencia.

## Elementos visuais

A parte visual sera simples e nao precisa carregar toda a informacao da jogada.

Elementos planejados:

- Cenario com torcida, gramado e trave.
- Goleiro como personagem principal.
- Bola.
- Interface com pontuacao, nivel e gols sofridos.
- Indicacao visual simples do freeze, se necessario.

Observacao:

Como a proposta simula um goleiro cego, o jogo nao precisa depender de muitos detalhes visuais para funcionar. O visual deve ajudar na apresentacao, mas a mecanica principal deve ser guiada por tempo, sons e escolhas de direcao.

## Sprites planejados

Obrigatorios:

- [x] Fundo do penalti com torcida, gramado e trave: `assets/fundo_gol.png`.
- [x] Bola: `assets/bola_copa.png`.
- [x] Goleiro parado/preparado: `assets/goleiro.png`.
- [x] Goleiro defendendo: `assets/goleiro_pulando.png`.

Opcionais:

- [x] Goleiro defendendo caido: `assets/goleiro_caido.png`.
- [ ] Goleiro defendendo para esquerda.
- [ ] Goleiro defendendo para direita.
- [ ] Goleiro defendendo no centro.
- [ ] Efeito simples de bola defendida.
- [ ] Efeito simples de bola no gol.

## Sons planejados

Sons obrigatorios:

- Bola chutada.
- Bola defendida.
- Apito.
- Torcida.
- Bola no gol.

Sons opcionais:

- Botao do menu.
- Som de pausa.
- Som de troca de nivel.
- Torcida mais intensa nos niveis avancados.

## Escopo da primeira versao

Recursos obrigatorios:

- Menu inicial.
- Tutorial simples.
- Goleiro controlado por 3 direcoes.
- Bola com 3 direcoes possiveis.
- Sistema de freeze ou slow motion simples.
- Verificacao de defesa ou gol.
- Pontuacao por defesa.
- Progressao de niveis.
- Game Over por gols sofridos.
- Sons principais.

Recursos opcionais:

- Temas diferentes por nivel.
- Sons de torcida variando por nivel.
- Bonus por sequencia de defesas.
- Ranking local.
- Tela de resultado mais detalhada.
- Animacoes extras do goleiro.

## Estrutura atual no projeto Tarafel

```text
Tarafel/
  assets/
    bola_copa.png
    fundo_gol.png
    goleiro.png
    goleiro_caido.png
    goleiro_pulando.png
  planning/
    projeto_lide.md
  core/
  lwjgl3/
```

## Programacao de desenvolvimento

### Etapa 1 - Planejamento e regras

Status: concluida.

Prazo sugerido: 1 aula

Tarefas:

- [x] Definir conceito principal do jogo.
- [x] Definir mecanica de penalti com 3 direcoes.
- [x] Definir valores iniciais de pontuacao.
- [x] Definir regra inicial de Game Over por nivel.
- [x] Definir controles sugeridos.
- [ ] Definir a historia final do goleiro.
- [ ] Validar a ideia do goleiro cego com o professor.

Entrega esperada:

- [x] Documento de planejamento atualizado.
- [x] Lista de regras principais.
- [x] Fluxo basico da partida.

### Etapa 2 - Preparacao do projeto LibGDX

Status: concluida.

Prazo sugerido: 1 aula

Tarefas:

- [x] Criar o projeto LibGDX.
- [x] Configurar desktop como plataforma principal.
- [x] Manter estrutura `core` e `lwjgl3`.
- [x] Organizar assets visuais principais na pasta `assets`.
- [ ] Organizar sons quando forem adicionados.

Entrega esperada:

- [x] Projeto base criado no repositorio original.
- [ ] Confirmar execucao local depois da implementacao da tela inicial.

### Etapa 3 - Prototipo do penalti

Status: pendente.

Prazo sugerido: 1 a 2 aulas

Tarefas:

- [ ] Criar a tela principal do penalti.
- [ ] Adicionar fundo, bola e goleiro.
- [ ] Sortear uma das 3 direcoes da bola.
- [ ] Ler a direcao escolhida pelo jogador.
- [ ] Comparar direcao da bola com direcao do goleiro.

Entrega esperada:

- Prototipo jogavel com defesa ou gol.

### Etapa 4 - Freeze, pontuacao e Game Over

Status: pendente.

Prazo sugerido: 1 aula

Tarefas:

- [ ] Implementar freeze ou slow motion.
- [ ] Adicionar pontuacao por defesa.
- [ ] Contar gols sofridos.
- [ ] Criar regra de Game Over.
- [ ] Reiniciar cobranca apos defesa ou gol.

Entrega esperada:

- Jogo com loop completo de cobrancas.

### Etapa 5 - Niveis e audio

Status: pendente.

Prazo sugerido: 1 aula

Tarefas:

- [ ] Criar pelo menos 3 niveis.
- [ ] Diminuir o tempo de reacao a cada nivel.
- [ ] Adicionar sons de apito, chute, defesa, torcida e bola no gol.
- [ ] Ajustar temas ou intensidade dos sons por nivel, se der tempo.

Entrega esperada:

- Jogo com progressao de dificuldade e sons principais.

### Etapa 6 - Menu, tutorial e polimento

Status: pendente.

Prazo sugerido: 1 aula

Tarefas:

- [ ] Criar menu inicial.
- [ ] Criar tela de tutorial.
- [ ] Adicionar pausa e reinicio.
- [ ] Ajustar dificuldade.
- [ ] Corrigir bugs.
- [ ] Preparar apresentacao para a aula.

Entrega esperada:

- Versao final jogavel.
- Apresentacao curta explicando mecanica, acessibilidade, fisioterapia e objetivo do projeto.

## Divisao sugerida de responsabilidades

- Programacao: penalti, direcoes, freeze, pontuacao, niveis, menu e Game Over.
- Arte: fundo, goleiro, bola, interface e telas.
- Audio: apito, chute, defesa, torcida e gol.
- Documentacao: planejamento, tutorial, historia e explicacao do objetivo.
- Testes: jogar, anotar bugs e ajustar dificuldade.

## Criterios de sucesso

O projeto sera considerado bem sucedido se:

- O jogo abrir corretamente.
- O jogador conseguir iniciar, pausar e reiniciar.
- A mecanica de penalti tiver 3 direcoes.
- O freeze ou slow motion funcionar.
- A defesa e o gol forem identificados corretamente.
- Existir pontuacao por defesa.
- Existir Game Over por gols sofridos.
- Os sons principais estiverem funcionando.
- A ideia do goleiro cego estiver clara no tutorial ou na apresentacao.

## Perguntas pendentes

- Qual sera a historia final do goleiro?
- A pontuacao sera apenas por defesa ou tambem pelo tempo de resposta?
- Quantos gols o jogador pode tomar em cada nivel?
- Os temas dos niveis serao visuais, sonoros ou os dois?
- O jogo vai explicar a fisioterapia dentro dele ou apenas na apresentacao da aula?

## Observacoes finais

A prioridade e fazer um jogo pequeno, funcional e divertido. A mecanica principal deve ser simples: ouvir o momento do chute, reagir durante o freeze e escolher uma das tres direcoes.

Melhor entregar uma versao redonda com menu, tutorial, sons, pontuacao e Game Over do que tentar colocar muitas animacoes e deixar o jogo incompleto.
