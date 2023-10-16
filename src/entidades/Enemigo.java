/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author jaime
 */
public class Enemigo extends Nave  {
    
    private int tipoDeAtaque;

    public Enemigo(int tipoDeAtaque) {
        this.tipoDeAtaque = tipoDeAtaque;
    }

    public int getTipoDeAtaque() {
        return tipoDeAtaque;
    }

    public void setTipoDeAtaque(int tipoDeAtaque) {
        this.tipoDeAtaque = tipoDeAtaque;
    }
    
}
