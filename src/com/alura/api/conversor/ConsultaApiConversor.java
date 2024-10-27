package com.alura.api.conversor;

import com.alura.api.modelos.Moneda;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaApiConversor {
    public String ConvertirMoneda(String moneda){
        //Creacion de la direccion y asignacion de la Llave de la API
        final String keyAPI  = "6d0855fd23134ccc55e0933b";
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/"+keyAPI+"/latest/"+moneda);
        System.out.println(direccion);
        //Generacion del cliente que me permite enviar y recibir las peticiones .
        HttpClient client = HttpClient.newHttpClient();
        // Creo el cuerpo de la peticion de la peticion con la URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion).build();
        // el encargado de entregarnos la respuesta de nuestra peticion.
        HttpResponse<String> response;
        try {
            //Obtener respuesta
            response=client.send(request,HttpResponse.BodyHandlers.ofString());

        }catch(IOException | InterruptedException e){
            System.out.println("Ocurrio un error : " + e.getMessage());
            return null;
        }
        return response.body();
    }

    public String ConversionDivisasPar(String divisaOrigen, String divisaDestino, Double valor){
        final String keyAPI  = "6d0855fd23134ccc55e0933b";
        String direccion = "https://v6.exchangerate-api.com/v6/"+keyAPI+"/pair/"+divisaOrigen+"/"+divisaDestino+"/"+valor;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return null;
        }
    }

    public String TraerAbreviaturasDivisas(int value) {
    String divisa = "";
        switch (value){
            case 1:
                divisa = "USD";
                break;
            case 2:
                divisa = "EUR";
                break;
            case 3:
                divisa = "COP";
                break;
            case 4:
                divisa = "CHF";
                break;
            case 5:
                divisa = "GBP";
                break;
            case 6:
                divisa = "JOD";
                break;
            case 7:
                divisa = "MXN";
                break;
            default:
                System.out.println("Por Ingrese una Opcion Valida");
                break;
        }
    return divisa;
    }
}
