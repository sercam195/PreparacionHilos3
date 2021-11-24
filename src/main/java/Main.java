import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Cliente> listaClientes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Cliente c = new Cliente(i);
            listaClientes.add(c);
            c.start();
        }

        for (Cliente c : listaClientes){
            c.join();
        }

        Cajero.decirRecaudacionTotal();
    }
}

class Cliente extends Thread {
    int nombre;
    Random r = new Random();
    ArrayList<Item> l = new ArrayList<>();

    Cliente(int nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        int prob = 10;
        int deboseguir=0;
        do {
            comprarItem();
            prob--;
            deboseguir = (r.nextInt(10) + 1);
        } while (deboseguir <= prob);
        Cajero.cobrar(this);
        System.out.println(getListaCompraEnString());
    }

    private void comprarItem(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.add(new Item());
    }

    private String getListaCompraEnString(){
        String s = "He comprado ";
        for (Item item : l) {
            s += item.toString();
        }
        return s;
    }

}

class Cajero {
    private static int recadacionTotal = 0;

    static synchronized void cobrar(Cliente cliente) {
        System.out.println("El cajero está atendiendo al cliente " + cliente.nombre);
        int precioTotalItems = 0;
        for (Item item : cliente.l) {
            precioTotalItems += item.precio;
        }
        System.out.println("Voy a cobrarle al cliente " + cliente.nombre + " " + precioTotalItems + "€.");
        recadacionTotal+= precioTotalItems;
    }
    public static void decirRecaudacionTotal() {
        System.out.println("La recaudación total ha sido de " + recadacionTotal);
    }
}

class Item {
    String nombre;
    int precio;

    Item() {
        Random r = new Random();
        nombre = "Item"+r.nextInt();
        precio = r.nextInt(100) + 1;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }

}
