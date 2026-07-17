package org.example;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {

        try{
            //1. Registro el driver
            Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            DatabaseManager.registerDatabase(database);
            //2. Intento conectar a mi Existdb
            String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ejercicios";
            Collection col = DatabaseManager.getCollection(uri, "admin", "TU_PASSWORD_AQUI");

            if (col != null) {
                System.out.println("¡CONECTADO!");
                //1. Pedimos los datos necesarios
                int id = pedirID();
                int comensales = pedirComensales();
                // 2. Obtener el servicio para ejecutar consultas
                XQueryService servicio = (XQueryService) col.getService("XQueryService", "1.0");
                // 3. Definir la consulta XQuery
                String xquery =
                        "xquery version \"3.1\";\n" +
                                "\n" +
                                "declare variable $id external;\n" +
                                "declare variable $comensales external;\n" +
                                "\n" +
                                "for $i in doc('/db/ejercicios/menu.xml')//menu[@id = $id]//ingrediente\n" +
                                "let $cantidadIndividual := number($i/cantidad) * xs:integer($comensales)\n" +
                                "group by $nombre := $i/@nombre\n" +
                                "return\n" +
                                "    concat($nombre, ':', sum($cantidadIndividual))";
                // PASO CLAVE: Inyectar las variables de Java al motor de eXist
                // Convertimos los int a String porque los atributos (@id) suelen leerse como texto
                servicio.declareVariable("id", String.valueOf(id));
                servicio.declareVariable("comensales", String.valueOf(comensales));
                // 4. Ejecutar la consulta y guardar el resultado
                ResourceSet resultado = servicio.query(xquery);
                // 5. Recorrer los resultados encontrados
                ResourceIterator i = resultado.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println("No se han encontrado resultados.");
                }

                System.out.println("\n======= LISTA DE LA COMPRA =======");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    // Obtenemos el texto "nombre:cantidad"
                    String linea = (String) r.getContent();

                    // Lo separamos por los dos puntos
                    String[] partes = linea.split(":");
                    String nombre = partes[0];
                    String cantidad = partes[1];

                    // Imprimimos con formato de lista
                    System.out.printf("- %-15s : %s unidades/gr\n", nombre, cantidad);
                }
                System.out.println("==================================\n");
                col.close();
            } else {
                System.out.println("No se pudo conectar. ¿Está arrancado eXist-db?");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static int pedirID(){
        Scanner teclado = new Scanner(System.in);
        int id = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("Indica el ID del menu que quieres consultar: ");
                id = Integer.parseInt(teclado.nextLine());
                entradaValida = true; // Si llega aquí, es un número válido y sale del bucle
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número entero válido.");
            }
        }
        return id;
    }
    public static int pedirComensales() {
        Scanner teclado = new Scanner(System.in);
        int comensales = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("Indica el número de comensales que serán: ");
                comensales = Integer.parseInt(teclado.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número entero válido.");
            }
        }
        return comensales;
    }
}