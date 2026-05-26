# TARAFEL

## Um jogo de pênaltis com integração IoT como ferramenta de apoio à reabilitação

**Daniel Pauleti** - RA: 10723162  
**Eduardo Cardoso** - RA: 10722575  
**Isadora Castelo** - RA: 10729584  
**Lucas Yan** - RA: 10437822  
**Wellington Muniz** - RA: 10389854  

**Jogos Digitais - 4º Semestre**

---

# Resumo

Este short paper apresenta o Tarafel, um jogo digital de pênaltis desenvolvido com a framework LibGDX (Java) e projetado para operar em conjunto com um dispositivo IoT de captura de movimento. O jogador assume o papel de goleiro e deve defender cobranças vindas de três direções, em um ambiente progressivo com cenários, sons e sistema de pontuação dedicado.

O projeto une entretenimento e propósito terapêutico, utilizando a gamificação como mecanismo de engajamento ao longo do processo de reabilitação. São descritas a arquitetura do jogo, suas mecânicas, o sistema PPD (Pontuação por Defesa) e o caminho para integração com sensores IoT que substituem os controles convencionais pelo gesto físico do paciente.

---

# 1. Introdução

O Tarafel é um jogo digital de pênaltis desenvolvido com o framework LibGDX (Java), projetado para operar em conjunto com um dispositivo IoT de captura de movimento. O jogador assume o papel de goleiro e deve defender cobranças de pênalti vindas de três direções distintas, em um ambiente progressivo de dificuldade com cenários, sons e sistema de pontuação dedicado.

O projeto visa unir entretenimento e propósito terapêutico, utilizando a gamificação como mecanismo de engajamento ao longo do processo de reabilitação.

---

# 2. Descrição do Problema e Contexto da Reabilitação

Processos de reabilitação motora frequentemente envolvem exercícios repetitivos focados em reflexo, tempo de resposta, coordenação entre percepção e movimento, e tomada de decisão rápida.

Pacientes em recuperação de eventos como AVC, lesões neurológicas ou cirurgias ortopédicas costumam realizar séries de movimentos curtos e direcionados como parte da rotina terapêutica, e a baixa adesão ao tratamento é um desafio recorrente nessas terapias devido à monotonia dos exercícios.

O Tarafel foi concebido para atuar como ferramenta complementar nesse contexto, propondo um cenário lúdico no qual o gesto terapêutico — uma inclinação ou movimento direcional do corpo do paciente — corresponde diretamente à mecânica central do jogo: defender pênaltis escolhendo entre três direções.

A proposta dialoga com a área de gamificação aplicada à saúde, na qual elementos de jogo são usados para sustentar a motivação durante terapias de longa duração. O jogo não substitui acompanhamento clínico ou fisioterapia profissional; trata-se de uma proposta acadêmica para demonstrar como mecânicas simples podem servir de apoio ao estímulo motor e cognitivo, transformando uma sessão de exercícios em uma experiência mensurável e engajante.

---

# 3. Justificativa via Gamificação

A gamificação consiste na aplicação de elementos típicos de jogos — pontuação, progressão por níveis, desafios e recompensas — em contextos não lúdicos, como a reabilitação física.

Estudos na área indicam que pacientes submetidos a terapias gamificadas apresentam maior adesão ao tratamento, motivados pelo engajamento contínuo proporcionado pelo feedback imediato de suas ações.

No Tarafel, esse princípio se manifesta em múltiplas camadas:

- O sistema PPD recompensa cada defesa bem-sucedida e bônus sequenciais incentivam a consistência do esforço;
- A progressão de cenários (treino → estádio) cria uma narrativa de evolução que simula uma carreira esportiva real;
- A representação de vidas por luvas de goleiro torna o conceito de tentativa e erro visualmente intuitivo;
- O slow motion das cobranças funciona tanto como mecânica de acessibilidade quanto como janela de tempo para a execução do movimento terapêutico pelo paciente.

---

# 4. Descrição do Jogo

## 4.1 Arquitetura e Telas

O Tarafel é desenvolvido utilizando a framework LibGDX com LWJGL3, executado na plataforma desktop (Java). A aplicação é organizada em telas independentes que gerenciam seus próprios ciclos de render e entrada do usuário.

O fluxo de telas é o seguinte:

- **Menu Principal** — botões Jogar, Como Jogar e Pontuação;
- **Tutorial** — apresenta os comandos, a narrativa do cenário e o personagem Tarafel; ao fim exibe o botão "Jogar";
- **Gameplay** — tela principal com a perspectiva do chutador;
- **Pausa** — acessível durante o jogo, com opções Retomar e Reiniciar;
- **Game Over** — exibida ao esgotar as vidas do nível atual.

O estado global do jogo (pontuação, vidas, nível e status de pausa) é centralizado na classe `GameState`, permitindo que qualquer tela acesse e modifique o contexto sem acoplamento direto entre elas.

---

## 4.2 Mecânicas de Jogo

A perspectiva do jogo é a do cobrador de pênalti: o jogador visualiza a bola no centro da tela e a trave ao fundo. Uma mensagem sinaliza o início de cada cobrança.

Ao pressionar a tecla definida, a bola se desloca em slow motion em direção a um dos três cantos do gol — esquerda, cima ou direita — escolhido aleatoriamente pelo sistema.

O tempo de slow motion diminui conforme a dificuldade avança:

- `0,5s`
- `0,4s`
- `0,3s`

reduzindo progressivamente a janela de reação do jogador.

O jogador controla o goleiro reagindo à direção da bola. O personagem possui dois estados visuais — parado e pulando — animados por sprites distintos.

A detecção de colisão entre goleiro e bola determina o resultado de cada cobrança:

- **Bola defendida** → acumula PPD; bola retorna à posição inicial;
- **Bola no gol** → desconta uma vida (luva de goleiro); exibe animação de gol.

O jogador deve defender um número mínimo de pênaltis por nível para avançar. Ao esgotar as vidas, a tela de Game Over é exibida.

---

## 4.3 Níveis, Sons e Sistema de Pontuação

### Níveis e Cenários

A progressão narrativa do jogo é apresentada na tabela abaixo:

| Nível | Cenário | Condição de Vitória |
|---|---|---|
| 1 - Treino | Campo de treino | Defender X pênaltis |
| 2 - Estádio (fim de tarde) | Estádio com iluminação crepuscular | Defender X pênaltis |
| 3 - Estádio (meio-dia) | Estádio com luz solar intensa | Defender X pênaltis |
| Endless | Qualquer cenário | Sobreviver o máximo possível |

**Tabela 1. Estrutura de níveis do Tarafel.**

### Sons

Cada evento de jogo possui efeito sonoro correspondente:

- Chute da bola;
- Defesa do goleiro;
- Apito de início;
- Torcida ao fundo;
- Gol sofrido.

O tema sonoro ambiente varia conforme o cenário ativo, reforçando a imersão e a progressão narrativa.

### Sistema PPD

O sistema PPD (Pontuação por Defesa) opera em duas dimensões:

- Pontuação base por cada defesa realizada;
- Bônus sequencial acumulado a cada série de defesas consecutivas sem tomar gol.

A pontuação total é registrada e consultável na tela de Pontuação do menu principal.

---

# 5. Integração IoT

A camada de integração IoT do Tarafel é construída sobre um acelerômetro acoplado ao paciente, responsável por capturar o movimento físico que substitui os controles convencionais de teclado.

O dispositivo mede a aceleração nos eixos espaciais e detecta inclinações do corpo ou do segmento sob terapia (por exemplo, um movimento lateral do tronco ou do braço), traduzindo esses dados em comandos discretos para o jogo.

O mapeamento do gesto físico para as três direções da defesa segue uma lógica direta:

- Inclinação para a esquerda → defesa à esquerda;
- Inclinação para a direita → defesa à direita;
- Ausência de inclinação significativa → defesa central.

Esse limiar é calibrável conforme a amplitude de movimento de cada paciente, permitindo que o jogo se adapte ao estágio de reabilitação em que o usuário se encontra.

A comunicação entre o acelerômetro e a aplicação LibGDX ocorre por um canal de leitura contínua, no qual as amostras do sensor são processadas em tempo real e convertidas em eventos equivalentes aos comandos de teclado já consumidos pela classe de controle do jogo.

Essa equivalência mantém a arquitetura interna do Tarafel desacoplada do meio de entrada — teclado e acelerômetro coexistem como fontes de comando, possibilitando uso clínico com sensor e uso convencional para testes e desenvolvimento.

---

# 6. Conclusão

O Tarafel demonstra como mecânicas de jogo bem estruturadas podem servir de suporte a processos terapêuticos, tornando exercícios repetitivos mais engajantes e mensuráveis.

A progressão por níveis, o sistema PPD e o slow motion configurável criam um ambiente adaptável ao ritmo de recuperação de cada paciente.

Como próximos passos, o projeto prevê a integração com dispositivo IoT para captura do movimento físico do paciente, substituindo os controles convencionais pelo gesto terapêutico real.

Essa camada transformará o Tarafel de um jogo funcional em uma ferramenta clínica completa, onde cada defesa representa tanto um ponto no placar quanto um movimento executado no processo de reabilitação.