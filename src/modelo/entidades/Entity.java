/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import vista.rows.Row;

/**
 *
 * @author oscar
 */
public abstract class Entity implements Serializable{    
    Long id;

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public abstract Row getRow();
}
