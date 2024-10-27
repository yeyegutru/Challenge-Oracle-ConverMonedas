package com.alura.api.modelos;

public class Conversor {
    private String monedaOrigen;
    private String monedaDestino;
    private String valorConvertido;
    private double valorCambio;

    public Conversor(String monedaOrigen, String monedaDestino, String valorConvertido, double valorCambio) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.valorConvertido = valorConvertido;
        this.valorCambio = valorCambio;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }

    public String getValorConvertido() {
        return valorConvertido;
    }

    public void setValorConvertido(String valorConvertido) {
        this.valorConvertido = valorConvertido;
    }

    public double getValorCambio() {
        return valorCambio;
    }

    public void setValorCambio(double valorCambio) {
        this.valorCambio = valorCambio;
    }


    @Override
    public String toString() {
        return """
                ***********************************
                *Conversion Generada Correctamente*
                Divisa de Origen : """+monedaOrigen+
                "\nDivisa Destino : "+ monedaDestino+
                "\nValor : "+ valorConvertido+
                "\nValorCambio : "+ valorCambio+
                "\n**********************************";

    }
}
