/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import modelo.entidades.Cliente;
import Scraping.Scraping;
import vista.rows.ClienteRow;

/**
 *
 * @author oscar
 */
public class GeneradorControlador {
    
    Scraping scrap = new Scraping();
    
    public List<Cliente> getClientesCollection(String cadena){
        List<Cliente> clientes = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(cadena,"\n");
        while (st.hasMoreTokens()) {
            clientes.add(new Cliente(st.nextToken()));   
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
