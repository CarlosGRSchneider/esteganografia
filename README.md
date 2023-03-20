# esteganografia

Projeto para demonstrar a ocultação de mensagens dentro de imagens utilizando esteganografia.

Nesse projeto, temos um encriptador, que pega a mensagem, transforma em binario, e utiliza a tecnica do bit menos significativo para encriptar a mensagem enviada dentro de uma imagem selecionada.

Da mesma forma, o decriptador contém o algoritmo capaz de remover a mensagem da imagem esteganografada.

Para realizar uma esteganografia, é preciso fazer o seguinte:

1) coloque uma imagem PNG dentro da pasta raiz do projeto
2) substitua o nome da imagem na linha 28 do Encriptador
3) coloque a mensagem que você quer ocultar dentro do Main

Feito isso, basta apenas rodar o projeto e a sua imagem será gerada com a mensagem oculta, bem como o Decriptador realizando a decodificação.
