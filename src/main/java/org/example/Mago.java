package org.example;

public class Mago extends Personaje {
    private int poderMagico;

    public Mago(String nombre) {
        super(nombre, 130, 15, 8);
        this.poderMagico = 8;
    }

    @Override
    public void atacar(Personaje otro) {
        int daño = Math.max(0, ataque + poderMagico + (arma != null ? arma.getPoder() : 0) - otro.defensa);
        otro.recibirDaño(daño);
    }
}