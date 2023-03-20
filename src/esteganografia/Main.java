package esteganografia;

public class Main {

    public static void main(String[] args) {

        String mensagem = "Aqui você escreve a mensagem que quiser esteganografar.";
        Encriptador enc = new Encriptador();
        enc.informaMensagemParaEncriptar(mensagem);
        enc.encriptaMensagemNaImagem();

        Decriptador dec = new Decriptador();
        dec.removeMensagemDaImagem();
        String mensagemFinal = dec.getMensagemDecriptada();

        System.out.println(mensagemFinal);
    }
}
