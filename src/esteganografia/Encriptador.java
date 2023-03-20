package esteganografia;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Encriptador {

    private String mensagemBinaria;

    public void informaMensagemParaEncriptar(String mensagem) {
        StringBuilder result = new StringBuilder();
        byte[] letrasBinarias = mensagem.getBytes();
        for (byte letra : letrasBinarias) {
            System.out.print(Integer.toBinaryString(letra & 255 | 256).substring(1) + " ");
            String letraEmByte = Integer.toBinaryString(letra & 255 | 256).substring(1);
            result.append(letraEmByte);
        }
        System.out.println();
        mensagemBinaria = result.toString();
    }

    public void encriptaMensagemNaImagem() {
        try {

            File imagem = new File("o nome da sua imagem vem aqui.png");
            BufferedImage leitorDeImagem = ImageIO.read(imagem);

            validaCompatibilidade(leitorDeImagem);

            int indiceDeMensagem = mensagemBinaria.length() - 1;

            System.out.println("Altura: " + leitorDeImagem.getHeight() + " Largura: " + leitorDeImagem.getWidth());
            for (int i = leitorDeImagem.getWidth() - 1; i >= 0; i--) {
                for (int j = leitorDeImagem.getHeight() - 1; j >= 0; j--) {

                    int pixel = leitorDeImagem.getRGB(i, j);
                    byte red = (byte) ((pixel >> 16) & 0xff);
                    byte green = (byte) ((pixel >> 8) & 0xff);
                    byte blue = (byte) (pixel & 0xff);

                    byte[] rgb = {red, green, blue};
                    byte[] rgbEncriptado = new byte[3];

                    for (int k = 2; k >= 0; k--) {
                        if (indiceDeMensagem >= 0) {

                            int bitMenosSignificativo;
                            if ((rgb[k] & 1) == 1) {
                                bitMenosSignificativo = 1;
                            } else {
                                bitMenosSignificativo = 0;
                            }

                            if (Character.getNumericValue(mensagemBinaria.charAt(indiceDeMensagem)) != bitMenosSignificativo) {
                                rgbEncriptado[k] = bitMenosSignificativo == 1 ? (byte) (rgb[k] & ~(1)) :
                                        (byte) (rgb[k] | 1);
                            } else {
                                rgbEncriptado[k] = rgb[k];
                            }
                        } else {
                            rgbEncriptado[k] = (byte) (rgb[k] & ~(1));
                        }
                        indiceDeMensagem--;
                    }
                    Color novoPixel = new Color(Byte.toUnsignedInt(rgbEncriptado[0]), Byte.toUnsignedInt(rgbEncriptado[1]), Byte.toUnsignedInt(rgbEncriptado[2]));
                    leitorDeImagem.setRGB(i, j, novoPixel.getRGB());
                }
            }
            File imagemPronta = new File("imagem esteganografada.png");
            ImageIO.write(leitorDeImagem, "png", imagemPronta);

            System.out.println("Mensagem escondida dentro da imagem com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao ler a imagem: " + e.getMessage());
        }
    }

    private void validaCompatibilidade(BufferedImage leitorDeImagem) {

        int totalDeBytes = (leitorDeImagem.getHeight() * leitorDeImagem.getWidth()) * 3;
        if(totalDeBytes < mensagemBinaria.length()) {
            throw new IllegalArgumentException("Não há bytes o suficiente para encriptar a mensagem");
        }
    }
}
