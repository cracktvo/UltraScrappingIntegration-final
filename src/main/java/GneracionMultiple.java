import Scraping.Scraping;
import modelo.entidades.Cliente;

import java.io.IOException;

/**
 * Created by oscar on 15/10/2016.
 */
public class GneracionMultiple {
    public static void main(String args[]) throws IOException {
        Scraping scraping = new Scraping();
        scraping.inicializar();
        Cliente cliente = new Cliente("92096786");
        System.out.println(scraping.obtenerCliente(cliente,false));
        scraping.finalizar();
    }
}
