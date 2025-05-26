package pokemon;

import pokemon.ataque.Ataque;
import pokemon.element.ElementPokemon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;


public class Pokemon {
    String namePokemon;
    short hp;
    TipoPokemon typePokemon;
    Ataque[] ataque = new Ataque[4];

    // Nuevas estadísticas
    private int ataqueFisico; // Ataque físico (At)
    private int defensa; // Defensa (Df)
    private int ataqueEspecial; // Ataque Especial (AtS)
    private int defensaEspecial; // Defensa Especial (DeS)
    private int velocidad; // Velocidad

    private Pokemon[] pokemons = new Pokemon[3]; // Equipo de Pokémon creado
    ElementPokemon element = new ElementPokemon();
    private Pokemon[] pokemon;

    public Pokemon() {

    }

    public enum TipoAtaque {
        FISICO, ESPACIAL ;
    };

    public enum TipoPokemon{
        FUEGO(new String[]{"PLANTA"}),
        AGUA(new String[]{"FUEGO"}),
        TIERRA(new String[]{"ELECTRICO"}),
        PLANTA(new String[]{"AGUA", "TIERRA"}),
        ELECTRICO(new String[]{"AGUA"});

        private final String[] strongAgainst;

        TipoPokemon(String[] strongAgainst){
            this.strongAgainst = strongAgainst;
        }

        public boolean isStrongAgainst(TipoPokemon other) {
            return Arrays.asList(strongAgainst).contains(other.name());
        }
    }

    public Pokemon(String namePokemon, short hp, TipoPokemon typePokemon, Ataque[] ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        this.namePokemon = namePokemon;
        this.hp = hp;
        this.typePokemon = typePokemon;
        this.ataque = ataques;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
    }

    public String getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(String namePokemon) {
        this.namePokemon = namePokemon;
    }

    public short getHP() {
        return hp;
    }

    public void setHP(short HP) {
        this.hp = HP;
    }

    public TipoPokemon getTypePokemon() {
        return typePokemon;
    }

    public void setTypePokemon(TipoPokemon typePokemon) {
        this.typePokemon = typePokemon;
    }

    public Pokemon[] getPokemon() {
        return pokemon;
    }

    public Ataque[] getAtaques() {
        return ataque;
    }

    public Pokemon[] getPokemons() {
        return pokemons;
    }

    // Getters para las nuevas estadísticas
    public Ataque[] getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void menuPokemon(Scanner sc, String[] Trainers){
        System.out.println("como quieres jugar?: \n1. pokemones aleatorio\n2. crear tus propios pokemones " );
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1 -> {randomPokemon();}
            
        }
    }
 
    // Implementación del método randomPokemon 
    private void randomPokemon() {
        try {
            System.out.println("Generando un equipo aleatorio...");
            Pokemon[] randomTeam = randomPokemonTeam(3); 

            System.out.println("Equipo generado:");
            for (Pokemon p : randomTeam) {
                System.out.println(p.getNamePokemon() + " - Tipo: " + p.getTypePokemon());
            }
        } catch (Exception e) {
            System.out.println("Error al generar el equipo aleatorio: " + e.getMessage());
        }
    }

    // Método para generar un equipo aleatorio de Pokémon
    public Pokemon[] randomPokemonTeam(int teamSize) {
        ElementPokemon.initializeData();

        
        Pokemon[] allPokemons = (Pokemon[]) ElementPokemon.getPokemon();
        if (teamSize > allPokemons.length) {
            throw new IllegalArgumentException("El tamaño del equipo no puede ser mayor que el número de Pokémon disponibles.");
        }

        Pokemon[] team = new Pokemon[teamSize];
        List<Pokemon> availablePokemons = new ArrayList<>(Arrays.asList(allPokemons));
        Random random = new Random();

        for (int i = 0; i < teamSize; i++) {
            int randomIndex = random.nextInt(availablePokemons.size());
            team[i] = availablePokemons.remove(randomIndex);
        }

        return team;
    }

    public String randomTipoPokemon(){
        Random rand = new Random();
            return (TipoAtaque.values()[rand.nextInt(1,2)]).name();
    }

// No es necesaria ya esta funcion 
/* 
    public void createPokemon(Scanner sc) {
        for (int i = 0; i < pokemons.length; i++) {
            pokemons[i] = new Pokemon();
            System.out.println("Ingrese el nombre del Pokémon " + (i + 1) + ":");
            pokemons[i].setNamePokemon(sc.nextLine());

            System.out.println("Ingrese el número de vida del Pokémon (1-350):");
            short hp = sc.nextShort();
            sc.nextLine(); // Consumir el salto de línea pendiente
            pokemons[i].setHP(hp);

            System.out.println("Seleccione el tipo del Pokémon:");
            System.out.println("1. FUEGO");
            System.out.println("2. AGUA");
            System.out.println("3. TIERRA");
            System.out.println("4. PLANTA");
            System.out.println("5. ELECTRICO");
            int tipo = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea pendiente
            pokemons[i].setTypePokemon(TipoPokemon.values()[tipo - 1]);

            System.out.println("Forma de ataque: \n1. Crear tus propios ataques \n2. Hacer ataques aleatorios");
            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea pendiente
            switch (opcion) {
                case 1 -> pokemons[i].ataque = crearAtaquesPersonalizados(sc);
                case 2 -> pokemons[i].ataque = obtenerAtaquesAleatorios(pokemons[i].getTypePokemon());
            }
        }
    }

    

    // Método para obtener 4 ataques aleatorios del tipo correspondiente
    private Ataque[] obtenerAtaquesAleatorios(TipoPokemon tipoPokemon) {
        Ataque[] ataquesDisponibles;
        switch (tipoPokemon) {
            case FUEGO -> ataquesDisponibles = ElementPokemon.getFireMoves();
            case AGUA -> ataquesDisponibles = ElementPokemon.getWaterMoves();
            case TIERRA -> ataquesDisponibles = ElementPokemon.getGroundMoves();
            case PLANTA -> ataquesDisponibles = ElementPokemon.getGrassMoves();
            case ELECTRICO -> ataquesDisponibles = ElementPokemon.getElectricMoves();
            default -> throw new IllegalArgumentException("Tipo de Pokémon no válido");
        }

        if (ataquesDisponibles == null || ataquesDisponibles.length < 4) {
            throw new IllegalStateException("No hay suficientes ataques disponibles para este tipo de Pokémon.");
        }

        List<Ataque> listaAtaques = new ArrayList<>(Arrays.asList(ataquesDisponibles));
        Collections.shuffle(listaAtaques); // Mezcla los ataques
        return listaAtaques.subList(0, 4).toArray(new Ataque[4]); // Selecciona los primeros 4
    }

    private Ataque[] crearAtaquesPersonalizados(Scanner sc) {
        Ataque[] ataques = new Ataque[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("Ingrese el nombre del ataque " + (i + 1) + ":");
            String nombreAtaque = sc.nextLine();

            System.out.println("Seleccione el tipo de ataque:");
            System.out.println("1. FISICO");
            System.out.println("2. ESPACIAL");
            int tipoOpcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea
            Pokemon.TipoAtaque tipoAtaque = (tipoOpcion == 1) ? Pokemon.TipoAtaque.FISICO : Pokemon.TipoAtaque.ESPACIAL;

            int poder;
            do {
                if (tipoAtaque == Pokemon.TipoAtaque.FISICO) {
                    System.out.println("Ingrese el poder del ataque físico (máximo 100):");
                } else {
                    System.out.println("Ingrese el poder del ataque especial (máximo 150):");
                }
                poder = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea

                if ((tipoAtaque == Pokemon.TipoAtaque.FISICO && poder > 0 && poder <= 100) ||
                    (tipoAtaque == Pokemon.TipoAtaque.ESPACIAL && poder > 0 && poder <= 150)) {
                    break;
                } else {
                    System.out.println("El poder del ataque no es válido. Intente de nuevo.");
                }
            } while (true);

            ataques[i] = new Ataque(nombreAtaque, tipoAtaque, (byte) poder);
        }
        return ataques;
    }
    */
}
