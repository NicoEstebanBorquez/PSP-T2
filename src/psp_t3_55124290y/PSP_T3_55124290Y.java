package psp_t3_55124290y;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas Esteban 55124290Y
 */
public class PSP_T3_55124290Y {

    /**
     * fichero - Objeto de tipo File que crea el dichero en el directorio del proyecto. fw - Objeto de tipo FileWriter que escribe en el fichero.
     */
    File fichero;
    FileWriter fw;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PSP_T3_55124290Y programa = new PSP_T3_55124290Y();
        programa.iniciar();
    }

    /**
     * Este método es utilizado para lanzar la aplicacion fuera del entorno estático del método main.
     */
    public void iniciar() {
        /**
         * Creación del fichero donde se escribirán la tablas.
         */
        fichero = new File("fichero.txt");
        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error al crear el fichero. " + ex);
            }
        }

        /**
         * Apertura del flujo de escritura y escritura del encabezado con los datos del alumno.
         */
        try {
            fw = new FileWriter(fichero, true);
            fw.write("******************************************\n"
                    + "* PSP - Tarea Individual 2            *\n"
                    + "******************************************\n"
                    + "* Nicolás Esteban Borquez *\n"
                    + "******************************************\n"
                    + "* 55124290Y                          *\n"
                    + "******************************************\n\n"
                    + "Resultado de las operaciones: \n\n");

        } catch (IOException ex) {
            System.out.println("Error al escribir en el fichero. " + ex);
        }

        /**
         * Bucle que lanza los 10 hilos y les asigna una prioridad siguiendo el criterio dado en la tarea.
         */
        Tabla tabla = new Tabla();
        for (int i = 1; i <= 10; i++) {
            Hilo hilo = new Hilo(i, tabla);
            System.out.println("INICIADO    Hilo "  + i + " - Estado: " + hilo.getState());

            /**
             * A continuación se asignan las prioridades a los hilos.
             * Hilos 9, 8 y 7 con prioridad máxima.
             * Hilos 6, 5, 4, y 3 con prioridad normal.
             * Hilos 10, 1 y 2 con prioridad mímina.
             */
            if (i == 9 || i == 8 || i == 7) {
                hilo.setPriority(Thread.MAX_PRIORITY);
            } else if (i == 6 || i == 5 || i == 4 || i == 3) {
                hilo.setPriority(Thread.NORM_PRIORITY);
            } else if (i == 10 || i == 2 || i == 1) {
                hilo.setPriority(Thread.MIN_PRIORITY);
            }
        }

        /**
         * Este "sleep" de 1 segundo es utilizado para evitar que el flujo de escritura se cierre antes de que los 10 hilos terminen de ejecutarse.
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PSP_T3_55124290Y.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Cierre del flujo de escritura tras ejecutarse los 10 hilos.
         */
        try {
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error al cerrar el fichero. " + ex);
        }
        
    }

    /**
     * Clase que contiene un método encargado de crear las tablas de multiplicar.
     */
    public class Tabla {

        /**
         * Método sincronizado que crea las tablas de multiplicar.
         *
         * @param numeroTabla Parámetro que indica al método qué tabla debe calcular.
         */
        public synchronized void crearTabla(int numeroTabla) {
            
            try {
                fw.write("Tabla del " + numeroTabla + ": \n");
                for (int i = 0; i <= 10; i++) {
                    fw.write(numeroTabla + "x" + i + " = " + numeroTabla * i + "\n");

                }
                fw.write("************ \n\n");
            } catch (IOException ex) {
                System.out.println("Error al escribir en el fichero. " + ex);
            }
        }
    }

    /**
     * Clase encargada de crear los hilos.
     */
    public class Hilo extends Thread {

        /**
         * Miembros de la clase "Hilo".
         */
        private int numero;
        private Tabla tabla;

        /**
         * Constructor de la clase "Hilo"
         *
         * @param numero Tabla que creará el hilo.
         * @param tabla Objeto de tipo "Tabla" utilizado para crear la tabla.
         */
        public Hilo(int numero, Tabla tabla) {
            System.out.println("CREADO      Hilo " + numero + " - Estado: " + this.getState());
            
            this.numero = numero;
            this.tabla = tabla;
            start(); 
        }

        
        public void run() {
            tabla.crearTabla(numero);
            System.out.println("ESCRIBIENDO Hilo " + numero + " - Estado: " + this.getState());
        }
    }
}
