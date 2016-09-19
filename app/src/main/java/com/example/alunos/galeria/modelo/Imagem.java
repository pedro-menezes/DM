package com.example.alunos.galeria.modelo;

import java.io.Serializable;

/**
 * Created by fernando on 13/05/2016.
 */
public class Imagem implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;
    
    private long codigo;
    private String nome;
    private String url;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Imagem{" + "nome='" + nome + '\'' + ", descrição='" + descricao + '\'' + ", url='" + url + '}';
    }
}
