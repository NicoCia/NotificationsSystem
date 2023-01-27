package NotificationsSystem;

import java.util.concurrent.ThreadFactory;

/**
 * Fabrica de hilos disponibles para ejecucion
 */
public class MyThreadFactory implements ThreadFactory {
    private int contador;
    private String nombre;

    /**
     * contructor
     * @param nombre nombre del hilo
     */
    public MyThreadFactory(String nombre){
        contador=0;
        this.nombre=nombre;
    }

    /**
     * Crea un nuevo hilo listo para ejecucion asignandole una clase Runnable
     * @param r clase a ser asignada al nuevo hilo
     * @return hilo listo para ejecucion
     */
    @Override
    public Thread newThread(Runnable r){
        Thread t=new Thread(r, nombre+"-Thread_"+contador);
        contador++;

        return t;
    }

}