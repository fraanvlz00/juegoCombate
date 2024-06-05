package org.example;

import java.util.Random;
import java.util.Scanner;

public class Juego {
    private Personaje jugador;
    private Personaje enemigo;
    private boolean enemigoUsadoPocion = false;

    public Juego(Personaje jugador, Personaje enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;
    }

    public void iniciarCombate() {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        // Probabilidad de encontrarse con un santuario
        if (rand.nextBoolean()) {
            Santuario.bendecir(jugador);
        } else {
            System.out.println(jugador.getNombre() + " no encontró un santuario.");
        }

        if (rand.nextBoolean()) {
            Santuario.bendecir(enemigo);
        } else {
            System.out.println(enemigo.getNombre() + " no encontró un santuario.");
        }

        int turno = 0;
        while (jugador.getVida() > 0 && enemigo.getVida() > 0) {
            if (turno % 2 == 0) {
                System.out.println("Turno de " + jugador.getNombre());
                boolean accionValida = false;
                while (!accionValida) {
                    System.out.println("Elige una acción: 1. Atacar 2. Defenderse 3. Tomar poción");
                    int accion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer del scanner

                    switch (accion) {
                        case 1:
                            jugador.atacar(enemigo);
                            accionValida = true;
                            break;
                        case 2:
                            jugador.defender();
                            accionValida = true;
                            break;
                        case 3:
                            if (jugador.usarPocion()) {
                                System.out.println(jugador.getNombre() + " usó una poción y recuperó vida.");
                                accionValida = true;
                            } else {
                                System.out.println(jugador.getNombre() + " no tiene pociones disponibles.");
                            }
                            break;
                        default:
                            System.out.println("Acción no válida. Inténtalo de nuevo.");
                    }
                }
            } else {
                System.out.println("Turno de " + enemigo.getNombre());
                if (!enemigoUsadoPocion && enemigo.getVida() < 20 && enemigo.usarPocion()) {
                    enemigoUsadoPocion = true;
                    System.out.println(enemigo.getNombre() + " usó una poción y recuperó vida.");
                } else {
                    enemigo.atacar(jugador);
                }
            }
            System.out.println(jugador.getNombre() + ": " + jugador.getVida() + " puntos de vida.");
            System.out.println(enemigo.getNombre() + ": " + enemigo.getVida() + " puntos de vida.");

            turno++;
        }

        declararGanador();
        scanner.close();
    }

    private void declararGanador() {
        if (jugador.getVida() > 0) {
            System.out.println(jugador.getNombre() + " ha ganado el combate");
        } else {
            System.out.println(enemigo.getNombre() + " ha ganado el combate");
        }
    }

    public static Juego configurarJuego() {
        Scanner scanner = new Scanner(System.in);

        // Crear enemigo predefinido
        Personaje enemigo = new Guerrero("Enemigo");
        Arma armaEnemigo = new Arma("Espada", "fisico", 5);
        Pocion pocionEnemigo = new Pocion("Poción de Salud", 20);
        enemigo.equiparArma(armaEnemigo);
        enemigo.equiparPocion(pocionEnemigo);

        System.out.println("Bienvenido al juego de combate!");
        System.out.print("Elige el nombre de tu personaje: ");
        String nombre = scanner.nextLine();

        System.out.print("Elige tu clase (1. Guerrero, 2. Mago): ");
        int clase = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        Personaje jugador;
        if (clase == 1) {
            jugador = new Guerrero(nombre);
        } else {
            jugador = new Mago(nombre);
        }

        System.out.println("Elige tu arma:");
        System.out.println("1. Espada (Tipo: fisico, Poder: 5)");
        System.out.println("2. Bastón (Tipo: mágico, Poder: 7)");
        System.out.print("Ingresa el número de tu elección: ");
        int armaEleccion = scanner.nextInt();
        Arma armaJugador;
        if (armaEleccion == 1) {
            armaJugador = new Arma("Espada", "fisico", 5);
        } else {
            armaJugador = new Arma("Bastón", "mágico", 7);
        }
        jugador.equiparArma(armaJugador);

        Pocion pocionJugador = new Pocion("Poción de Salud", 20);
        jugador.equiparPocion(pocionJugador);

        System.out.println("Tu personaje está equipado y listo para el combate!");

        return new Juego(jugador, enemigo);
    }
}
