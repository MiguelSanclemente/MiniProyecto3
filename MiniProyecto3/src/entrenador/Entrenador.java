package entrenador;

import pokemon.Pokemon;
import pokemon.element.ElementPokemon; 

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Entrenador {
    private String nombre; // Nombre del entrenador
    private Pokemon[] equipo; // Equipo de Pokémon del entrenador

    public void setNameTrainer(Scanner sc, List<Pokemon> availablePokemons) {
        // Solicitar el nombre del entrenador
        System.out.println("Ingrese el nombre del entrenador:");
        nombre = sc.nextLine();

        // Asignar equipo aleatorio
        equipo = new Pokemon[3];
        Collections.shuffle(availablePokemons);

        for (int i = 0; i < equipo.length; i++) {
            equipo[i] = availablePokemons.remove(0); // Remover el Pokémon asignado
        }

        System.out.println("Equipo asignado a " + nombre + ":");
        for (Pokemon p : equipo) {
            System.out.println("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Pokemon[] getEquipo() {
        return equipo;
    }
}
