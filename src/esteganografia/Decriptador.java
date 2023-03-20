package esteganografia;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Decriptador {

    private String mensagemBinaria;

    public void removeMensagemDaImagem() {

        StringBuilder result = new StringBuilder();
        try {
            File imagem = new File("imagem esteganografada.png");
            BufferedImage leitorDeImagem = ImageIO.read(imagem);

            for (int i = 0; i < leitorDeImagem.getWidth(); i++) {
                for (int j = 0; j < leitorDeImagem.getHeight(); j++) {
                    Color color = new Color(leitorDeImagem.getRGB(i, j));
                    byte red = (byte) color.getRed();
                    byte green = (byte) color.getGreen();
                    byte blue = (byte) color.getBlue();
                    byte[] rgb = {red, green, blue};

                    for (int k = 0; k < 3; k++) {
                        if ((rgb[k] & 1) == 1) {
                            result.append("1");
                        } else {
                            result.append("0");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao retirar a mensagem da imagem: " + e.getMessage());
        }
        mensagemBinaria = result.toString();
    }

    public String getMensagemDecriptada() {

        int indiceDeMensagem = mensagemBinaria.length() - 1;
        int nroDeChars = mensagemBinaria.length() / 8;

        byte[] byteArray = new byte[nroDeChars];
        int indiceDeByteArray = nroDeChars - 1;

        while (indiceDeByteArray > 0) {
            StringBuilder bits = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                bits.insert(0, mensagemBinaria.charAt(indiceDeMensagem - i));
            }
            byte byteMontado = (byte) Integer.parseInt(bits.toString(), 2);
            int valorUnicode = Byte.toUnsignedInt(byteMontado);
            byteArray[indiceDeByteArray] = (byte) valorUnicode;

            indiceDeMensagem = indiceDeMensagem - 8;
            indiceDeByteArray--;

        }
        String mensagemDecriptada = new String(byteArray);

        return mensagemDecriptada;
    }
}
