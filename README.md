- Visão geral do projeto

Este sistema foi resultado de um ano de pesquisa desenvolvida durante minha graduação, na Universidade Federal de Goiás. 
Neste trabalho nos propomos a usar as tecnologias NFC e RFID para implementar um serviço de localização indoor para uma 
aplicação de um ambiente inteligente. A aplicação proposta tem como objetivo assegurar a segurança de um estacionamento 
através do controle autônomo de entrada e saída de veículos e da identificação de seus respectivos proprietários. Além disso,
também fizemos o uso de um middleware publish-subscribe, baseado no modelo de dados DDS, para fazer a comunicação entre os 
dispositivos que compõem o ambiente inteligente. Também foi utilizado um sistema de CEP (Complex Event Processing), 
para auxiliar no processamento dos eventos gerados pelo sistema. 


- Arquitetura da aplicação

O sistema proposto, tem como objetivo geral, assegurar a segurança de veículos nos estacionamentos do campus da 
Universidade Federal de Goiás. A arquitetura geral do sistema é ilustrada pela Figura 1.  O sistema realiza controle 
da entrada e saída de veículos, das vagas de idosos e portadores de deficiência e, utiliza um serviço de localização 
baseado em NFC e RFID para inferir se o indivíduo que está tentando sair do estacionamento é mesmo o proprietário do veículo 
em questão. Além disso, há uma parte do sistema que utiliza drones para auxiliar o monitoramento do estacionamento. O sistema 
é composto por quatro componentes principais:

Controle de entrada/saída de veículos: esse componente do sistema é responsável por controlar a entrada e saída de veículos 
através de sua identificação. Um leitor de RFID acoplado à cancela, na entrada do estacionamento, faz a leitura da etiqueta 
RFID que está fixada no veículo. Cada etiqueta armazena a placa do veículo em questão. Quando um veículo se aproxima da cancela, 
pelo lado externo do estacionamento, o leitor de RFID faz a leitura da etiqueta do veículo e sua placa é armazenada no banco de 
dados de um computador embarcado, que está acoplado ao leitor de RFID via interface serial.

Esse banco de dados possui dois campos para cada tupla: placa e permissão. Ou seja, para cada placa o banco de dados armazena um tipo de permissão diferente. 
O campo “permissão” funciona como uma flag, tendo dois valores possíveis: 1 e 0. Quando o veículo entra no estacionamento esse campo é preenchido com um 0, ou seja, sua saída do estacionamento não está autorizada. Apenas quando esse valor é modificado para 1 é que o sistema permite a saída do veículo do estacionamento. O serviço de localização veículo/usuário,
que será explicado mais adiante é responsável por modificar esse valor do bando de dados. 

Quando um veículo está no interior do estacionamento e se aproxima da cancela, o leitor de RFID faz a leitura da etiqueta RFID 
que está fixada no veículo e obtém sua placa. Depois de obter a placa, o computador embarcado faz uma consulta em seu banco de 
dados interno para saber se esse veículo tem ou não permissão para sair do estacionamento. Ou seja, se o campo “permissão” tiver 
valor igual a 1 o veículo é liberado, caso contrário um evento é enviado para outro componente do sistema que utiliza drones para 
fazer o monitoramento aéreo do estacionamento. O funcionamento desse componente será explicado posteriormente. 

Controle de vagas reservadas: esse componente do sistema é responsável por fazer o controle de veículos que estacionam em vagas reservadas. Cada vaga possui um leitor de RFID e um computador embarcado que está acoplado ao leitor de RFID via interface serial. Ao ler a etiqueta de um veículo, o leitor obtém a placa do veículo e o computador embarcado verifica junto a um web service se o veículo cuja placa foi lida, possui ou não permissão para estacionar na vaga. Esse web service possui um banco de dados de usuários previamente cadastrados. Caso o sistema verifique que a placa em questão não está vinculada a um usuário portador de necessidades especiais/idoso, um sinal sonoro é emitido pelo computador embarcado, com o intuito de advertir o proprietário do veículo. 

Serviço de localização veículo/usuário: esse componente do sistema é responsável por fazer a localização do usuário e também do veículo, dentro do estacionamento. E através dessas duas informações o sistema pode, ou não, liberar sua saída. A localização do usuário é feita da seguinte forma: cada usuário possui uma aplicação que roda em seu smart phone. Ao adentrar em seu veículo, no estacionamento, o usuário aproxima seu smartphone de uma etiqueta NFC, fixada no veículo e a aplicação faz a leitura da etiqueta, que possui a placa do automóvel armazenada e envia para um sistema de CEP a latitude e longitude do smartphone (obtidos através do GPS do aparelho) e, também, o identificador do aparelho (nesse caso nós obtemos o número de série do processador).

A localização do veículo é feita da seguinte forma: cada um dos computadores embarcados vinculados a um leitor de RFID enviam, de 2 em 2 segundos, uma string únca que identifica o leitor de RFID e a placa do veículo que está estacionado (informação que está armazenada na etiqueta RFID que foi lida). Por exemplo, se há um veículo na vaga em que o leitor está, então a placa desse veículo e o identificador do leitor RFID, são enviados de dois em dois segundos para o sistema de CEP. Caso não haja veículo na vaga, a informação enviada para o sistema de CEP, referente à placa, possui valor igual a “nulo”.  

Ao receber um evento que contém a placa de um veículo e o identificador do leitor RFID responsável pela leitura, o sistema de CEP faz uma consulta em um banco de dados, previamente preenchido com as localizações dos leitores RFIDs e suas referentes localizações, para saber qual a localização (latitude e longitude) do dispositivo que fez a leitura da placa. Logo, através dessa consulta o sistema sabe que o veículo com a placa X está posicionado na latitude y e longitude z, que são as mesmas do leitor de RFID que identificou a placa do veículo.

Se o sistema de CEP receber dois eventos, sendo um enviado pelo smartphone do proprietário do veículo e outro evento enviado pelo computador embarcado ligado ao leitor de RFID, em um espaço de 1 segundo, o sistema então faz a comparação entre a latitude e a longitude do smartphone e do leitor de RFID. Se as duas localizações forem idênticas, ou se o smartphone estiver a um raio de 2 metros do veículo, o sistema de CEP envia um evento, contendo a placa do veículo, para o computador embarcado que está na cancela do estacionamento. Esse evento é enviado utilizando um sistema de middleware publish-subscribe que implementa o modelo de dados DDS. Após receber esse evento, o componente do sistema que roda no computador embarcado da cancela do estacionamento, altera em seu banco de dados o campo “permissão” do veículo cuja placa foi recebida do sistema de CEP. Agora o veículo que tinha sua permissão igual a 0, terá sua permissão igual a 1. Portanto, quando esse veículo se dirigir à cancela, pelo lato interno do estacionamento, o leitor de RFID fará a leitura da etiqueta fixada no veículo, encontrará sua placa e verificará junto ao banco de dados interno que o veículo possui permissão para sair do estacionamento. 

Monitoramento via drone: caso um veículo se aproxime da cancela, pelo lado interno do estacionamento e o sistema detecte, através do leitor de RFID e posteriormente através de uma consulta ao banco de dados interno, que esse veículo não tem permissão para sair do estacionamento, um evento é enviado, do sistema que roda no computador embarcado que está na cancela, para o GPS do veículo. A partir daí, o GPS enviará eventos, que contêm a latitude e a longitude do veículo, para um drone. Através dessa informação o drone, de forma autônoma, seguirá as informações da localização do veículo e enviará para uma central de monitoramento, imagens aéreas do veículo, para auxiliar em sua localização caso o mesmo consiga sair do estacionamento. 

<p align="center">
  <img src="https://github.com/marcelorodriguesfortes/sistema-localizacao-indoor-estacionamento/blob/master/Fig/Figura%201.png" width="450"/>
</p>


- Implementação

Para realizar a implementação do sistema proposto, fizemos o uso de smartphones, com sistema operacional Android, para simular tanto os leitores de RFID quanto os computadores embarcados vinculados a esses leitores. O sistema de CEP roda em um desktop, assim como o web service da aplicação. Para realizar a comunicação entre o computador desktop e os smartphones, fizemos o uso do middleware CoreDX DDS.

A leitura de uma etiqueta pelo leitor RFID, quando um veículo se aproxima da cancela pelo lado externo ou interno do estacionamento, é simulada pela aproximação de uma etiqueta NFC ao smarphone. Quando a aplicação, responsável pelo controle de entrada e saída de veículos, que roda no smartphone, lê a etiqueta NFC, a placa armazenada nela é então salva no banco de dados do aparelho. O campo “permissão”, que está vinculado à placa que acabara de ser salva, é armazenado com o valor 0. 
Quando a aplicação responsável por simular os leitores de RFID das vagas do estacionamento, detecta uma etiqueta NFC, ela envia um evento, contendo a placa do veículo (que foi lida da etiqueta NFC) e o id do smartphone (representando o identificador do leitor de RFID), para o sistema de CEP.  Assim que o sistema de CEP recebe o evento, uma consulta é feita no banco de dados interno do computador, que tem a localização de todos os leitores de RFID, com o objetivo de recuperar a posição do veículo. Essa informação é inferida através da latitude e longitude do leitor de RFID.

A autenticação do usuário é feita por outra aplicação que roda em um smartphone. Assim que essa aplicação detecta uma etiqueta NFC, ela envia para o sistema de CEP a placa que está armazenada na etiqueta e, a latitude e a longitude que são obtidas através do GPS do smartphone. Quando o sistema de CEP recebe um evento da aplicação que simula os leitores de RFID e outro evento, no espaço de um segundo, da aplicação de autenticação do usuário, ele compara as placas de ambos os eventos.  Caso as placas dos dois eventos forem iguais e a posição do smartphone (que representa a localização do usuário) for igual ou próxima à posição do veículo (representada pela latitude e longitude do leitor de RFID), o sistema de CEP faz uma consulta a outro banco de dados onde estão cadastrados os dados de todos os usuários da universidade. O objetivo dessa consulta é saber se o id do smartphone e a placa do veículo, que foram recebidos pelo sistema de CEP, são da mesma pessoa cadastrada no banco de dados. Se esse requisito for satisfeito, o sistema de CEP envia um evento para a aplicação responsável pelo controle de entrada/saída liberar o veículo em questão. O objetivo dessas comparações é saber se o indivíduo que está solicitando a liberação do veículo é realmente seu proprietário. Para isso, faz-se necessário estar fisicamente próximo ao veículo dentro do estacionamento e, com o smartphone do proprietário do veículo, para poder liberar sua saída.  
A verificação da permissão de um veículo para estacionar em uma vaga reservada é feita através de uma consulta a um web service. A aplicação que simula esse componente do sistema, lê a placa que está armazenada em uma etiqueta NFC e faz uma consulta. Assim que o web service recebe a consulta, uma verificação é feita junto a um banco de dados que contém todos os usuários portadores de deficiência/idosos cadastrados. Posteriormente uma resposta é devolvida para o smartphone onde está rodando a aplicação. Se a resposta for negativa, ou seja, se o usuário não estiver cadastrado no banco de dados, um sinal sonoro é emitido pela aplicação. Caso contrário, nada acontece. 

Para simular os drones responsáveis por enviar imagens aéreas de veículos que saem do estacionamento sem permissão, nós utilizamos a API do google maps para android. Utilizando um smartphone, nós plotamos no mapa a posição do drone de acordo com as coordenadas recebidas de outra aplicação que simula o gps de um veículo. Portanto, nós utilizamos duas aplicações diferentes, em dois smartphones diferentes. Uma aplicação para enviar latitudes e longitudes e outra aplicação para plotar, nessas posições, a representação do drone no mapa. A Figura 2 mostra a interface da aplicação que simula a trajetória do drone. 

<p align="center">
  <img src="https://github.com/marcelorodriguesfortes/sistema-localizacao-indoor-estacionamento/blob/master/Fig/Figura%202.png" width="350"/>
</p>

