/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private Integer codigo;
    private Date dataCriacao;
    private Double valorTotal;
    private Integer codigoEmpresa;
    private Integer codigoCliente;

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
    
    public int getEmpresa() {
        return codigoEmpresa;
    }

    public void setEmpresa(int empresa) {
        this.codigoEmpresa = empresa;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getCliente() {
        return codigoCliente;
    }

    public void setCliente(int cliente) {
        this.codigoCliente = cliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    
}
