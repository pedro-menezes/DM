package com.example.fernando.menudeslisante;

import android.os.Environment;

import com.example.fernando.menudeslisante.beans.Prova;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class PDF {
    //Document document;
    //public String nome;
    //public String diretorio;
    //fontes usadas para criação do pdf
    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static Font catFont3 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static Font smallFont1 = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
    private static Font dados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    private static BaseColor color = new BaseColor(153, 255, 153);

    /**
     * Função responsável por criar um diretório caso ele não exista
     * @param diretorio
     */
    private void criandoDiretorio(String diretorio) {
        File file1 = new File(Environment.getExternalStorageDirectory(), "/" + diretorio + "/");
        if (!file1.exists()) {
            file1.mkdir();
            file1 = new File(Environment.getExternalStorageDirectory(), "/" + diretorio + "/");
        }
    }

    //gerando o pdf
    public PDF(String diretorio, String nomePDF, Prova prova) {
        //Criando um diretório caso ele não exista
        criandoDiretorio(diretorio);

        //alocando espaço do arquivo
        File file = new File(Environment.getExternalStorageDirectory(), "/" + diretorio + "/" + nomePDF + ".pdf");
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(file);
            if (file.exists()) {
                new FileOutputStream(file);
            } else {
                file.createNewFile();
                new FileOutputStream(file);
            }
            //document = new Document(PageSize.A4);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, fileOut);
            document.open();

            //insere dados formatados do pdf
            tabFormatada(document);
            document.newPage();//gera a pagina
            document.close(); //fecha o documento
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void tabFormatada(Document document) throws DocumentException {
        PdfPTable table;
        PdfPCell c1;
        table = new PdfPTable(20);//cria um pdfTable com 20 colunas
        table.setWidthPercentage(110);// porcentagem de largura do documento em relação a folha
        /**
         * Cria-se linhas e celulas para tabela formatada.
         * primeiramente cria-se as celulas e adiciona a tabela. as colunas são controladas
         * pela colspan de acordo com o tamanho de colunas criadas anteriormente
         */

        //____________________________________________-
        //Criando célula por celula da tabela
        c1 = new PdfPCell(new Phrase("Prova", catFont3));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setColspan(20);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Professor: ", catFont3));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setColspan(20);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Temas: ", catFont3));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setColspan(20);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Data: / / ", catFont3));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setColspan(20);
        table.addCell(c1);

        document.add(table);
    }

    private static void addLinhaBranco(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}

