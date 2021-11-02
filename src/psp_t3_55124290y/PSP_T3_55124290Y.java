package psp_t3_55124290y;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PSP_T3_55124290Y {

    File fichero;
    FileWriter fw;

    public static void main(String[] args) {
        PSP_T3_55124290Y programa = new PSP_T3_55124290Y();
        programa.iniciar();
    }

    public void iniciar() {

        //CREACION DEL FICHERO
        fichero = new File("fichero.txt");
        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error al crear el fichero. " + ex);
            }
        }

        //COMIENZA LA ESCRITURA
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

        //SE LANZAN LOS 10 HILOS
        Tabla tabla = new Tabla();
        for (int i = 1; i <= 10; i++) {
            Hilo hilo = new Hilo(i, tabla);
        }

        
        //SLEEP PARA QUE ME DEJE UN SEGUNDO DE MARGEN, SI NO ME CIERRA EL FICHERO ANTES DE TERMINAR LAS TABLAS
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PSP_T3_55124290Y.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(PSP_T3_55124290Y.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //-----------------------------------------------------------------------------------------
    public class Tabla {

        public synchronized void crearTabla(int numeroTabla) throws IOException {

            fw.write("Tabla del " + numeroTabla + ": \n");

            for (int i = 0; i <= 10; i++) {
                fw.write(numeroTabla + "x" + i + " = " + numeroTabla * i + "\n");
            }

            fw.write("************ \n\n");
        }
    }

    //-------------------------------------------------------------------------------------------
    public class Hilo extends Thread {

        private int numero;
        private Tabla tabla;

        public Hilo(int numero, Tabla tabla) {
            this.numero = numero;
            this.tabla = tabla;
            this.start();
        }

        public void run() {
            try {
                tabla.crearTabla(numero);
            } catch (IOException ex) {
                Logger.getLogger(PSP_T3_55124290Y.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
