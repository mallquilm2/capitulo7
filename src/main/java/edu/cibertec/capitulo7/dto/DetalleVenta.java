package edu.cibertec.capitulo7.dto;

public class DetalleVenta {

    private Integer codigoventa;
    private Integer codigoproducto;
    private Double cantidad;
    private Double descuento;

    public Integer getCodigoventa() {
        return codigoventa;
    }

    public void setCodigoventa(Integer codigoventa) {
        this.codigoventa = codigoventa;
    }

    public Integer getCodigoproducto() {
        return codigoproducto;
    }

    public void setCodigoproducto(Integer codigoproducto) {
        this.codigoproducto = codigoproducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
