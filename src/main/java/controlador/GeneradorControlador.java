/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javafx.scene.control.TextArea;
import modelo.entidades.Cliente;
import Scraping.Scraping;
import vista.rows.ClienteRow;

/**
 *
 * @author oscar
 */
public class GeneradorControlador {
    
    Scraping scrap = new Scraping();
    
    public List<Cliente> getClientesCollection(String cadena, boolean generar, TextArea textArea,boolean numSearch){
        List<Cliente> clientes = new ArrayList<>();
        if(!generar){
            Long inicio = Long.parseLong(cadena);
            for(int i = 0;i<2000;i++){
                if(numSearch){
                    clientes.add(new Cliente("0000000000",null));
                    clientes.add(new Cliente(inicio.toString(),null));
                    inicio++;
                }else{
                    clientes.add(new Cliente("0000000000"));
                    clientes.add(new Cliente(inicio.toString()));
                    inicio++;
                }
            }
            textArea.setText(textArea.getText()+"\n"+"El últmio número generado fue:\n\n\n\n\n\n\n"+inicio);
        }else{
            StringTokenizer st = new StringTokenizer(cadena,"\n");
            while (st.hasMoreTokens()) {
                if (numSearch) {
                    String numero = st.nextToken();
                    clientes.add(new Cliente("0000000000",null));
                    clientes.add(new Cliente(numero,null));
                }else{
                    String numero = st.nextToken();
                    clientes.add(new Cliente("0000000000"));
                    clientes.add(new Cliente(numero));
                }
            }
        }
        return clientes;
    }

    public List<ClienteRow> getListClienteFromRow(List<Cliente> clientes) throws IOException, Exception{
        List<ClienteRow> result = new ArrayList<>();
        for(Cliente entity:clientes){
            result.add(entity.getRow());
        }
        return result;
    }
    
}
