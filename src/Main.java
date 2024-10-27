import com.alura.api.conversor.ConsultaApiConversor;
import com.alura.api.modelos.Conversor;
import com.alura.api.modelos.MonedaPare;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // para la lectura de los datos
        Scanner lectorOpciones = new Scanner(System.in);
        // Segundo lector Para evitar Conflictos String y Numbers
        Scanner lectorStrings = new Scanner(System.in);
        //variables
        int divisaOrigen ;
        int divisaDestino;
        double valorOrigen=0;
        String valorDestino="";
        String continuarConversion;
        String divisaOrigenPeticion="", divisaDestionPeticion="";
        ConsultaApiConversor consulta = new ConsultaApiConversor();
//        creacion del elemento GSON de la Libreria GOOGLE
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Conversor conversor;


        outerLoopPrincipal:
        while(true){
            //Generacion del menu de entrada :
        System.out.println("""
                **************************************************************
                ********Bienvenido Al Conversor de Moneda actual :]***********
                **************************************************************
                Seleccione la Divisa Actual que desea Convertir : 
                1) (USD) Dolar Estadounidense 
                2) (EUR) Euro
                3) (COP) Peso Colombiano
                4) (CHF) Franco Suizo 
                5) (GBP) Libra Esterlina
                6) (JOD) Dinar Jordano 
                7) (MXN) Peso Mexicano
                9) SALIR 
                Elija una opcion Valida: 
                **************************************************************
                """);
//        lectura de la divisa de Origen:
            divisaOrigen = lectorOpciones.nextInt();
            //opcion de Salir
            if (divisaOrigen== 9){
                break;
            }
            divisaOrigenPeticion = consulta.TraerAbreviaturasDivisas(divisaOrigen);
           if(divisaOrigenPeticion.equalsIgnoreCase("")){
                continue outerLoopPrincipal;
            }
            System.out.println("Ingrese el valor Positivo a convertir en " + divisaOrigenPeticion+" :");
           try {
               valorOrigen = lectorOpciones.nextDouble();

               if (valorOrigen <= 0) {
                   System.out.println("Debes Ingresar valores Positivos");
                   continue outerLoopPrincipal;
               }
           } catch (NumberFormatException e){
               System.out.println("Se ha Presentado Un Error en el Formato del Monto");
               continue outerLoopPrincipal;
           }

//            bucle secundario para obtener la divisa secundaria
           outerLoopSecundario:
            while(true){
                System.out.println("""
                **************************************************************
                Seleccione la Divisa Destino : 
                1) (USD) Dolar Estadounidense 
                2) (EUR) Euro
                3) (COP) Peso Colombiano
                4) (CHF) Franco Suizo 
                5) (GBP) Libra Esterlina
                6) (JOD) Dinar Jordano 
                7) (MXN) Peso Mexicano
                Elija una opcion Valida: 
                **************************************************************
                    """);
//           lectura divisa Destino
                divisaDestino = lectorOpciones.nextInt();
//                obtener abreviatura de la divisa
                divisaDestionPeticion = consulta.TraerAbreviaturasDivisas(divisaDestino);
                if(divisaDestionPeticion.equalsIgnoreCase("")){
                    continue outerLoopSecundario;
                }
//                Aqui Se Genera el Funcionamiento de la API PARES
                try{
                    String respuesta = consulta.ConversionDivisasPar(divisaOrigenPeticion,divisaDestionPeticion,valorOrigen);
//                    Convertir el JSON de la respuesta a nuestro MonedaPar DTO
                    MonedaPare moneda = gson.fromJson(respuesta, MonedaPare.class);
                    DecimalFormat formato = new DecimalFormat("#,##0.00");
                    valorDestino = formato.format(moneda.conversion_result());
                    conversor = new Conversor(moneda.base_code(),moneda.target_code(),valorDestino, moneda.conversion_rate());
                    System.out.println(conversor.toString());
                    try {
                        File file = new File("Historial-Conversiones.txt");
                        Date fecha = new Date();
                        SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
                        String fechaFormateada = formateadorFecha.format(fecha);
                        //Escritura del archivo txt
                        FileWriter escritorTXT = new FileWriter(file, true);
                        escritorTXT.write("\n Fecha de Conversion: " + fechaFormateada + "\n" + conversor.toString() + "\n");
                        escritorTXT.close();
                    }catch (Exception e){
                        System.out.println("Error al Guardar el Historial.");
                    }
                        System.out.println("¿Desea realizar otra Conversion? S/N");
                        continuarConversion = lectorStrings.next().toUpperCase();
                    if (continuarConversion.equals("N")){
                        break outerLoopPrincipal;
                    }else{
                        break outerLoopSecundario;
                    }
                }catch(Exception e){
                    System.out.println("Se ha Generado un Error en la conversion:  "+e.getMessage());
                }



            }




        }




    }
}