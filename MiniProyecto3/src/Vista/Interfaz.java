package Interfaz;

import entrenador.Entrenador;
import pokemon.Pokemon;
import pokemon.element.ElementPokemon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import pokemon.ataque.Ataque;

public class Interfaz extends JFrame {
    private JTextField entrenador1Field;
    private JTextField entrenador2Field;
    private JTextArea textArea;
    private Entrenador entrenador1;
    private Entrenador entrenador2;

    private ButtonGroup grupoHabilidades1;
    private ButtonGroup grupoHabilidades2;
    private JButton continuarButton; 

    public Interfaz() {
        setTitle("Batalla Pokémon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Configuración inicial de la interfaz
        mostrarPantallaInicial();
    }

    private void mostrarPantallaInicial() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
    
        // Título superior
        JLabel titulo = new JLabel("Bienvenido a las batallas Pokémon", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);
    
        // Panel central con BoxLayout (vertical)
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // margen
    
        // Campo Entrenador 1
        JLabel label1 = new JLabel("Ingrese nombre del entrenador 1:");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(label1);
    
        entrenador1Field = new JTextField();
        entrenador1Field.setMaximumSize(new Dimension(300, 25));
        panelCentral.add(entrenador1Field);
    
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20))); // espacio vertical
    
        // Campo Entrenador 2
        JLabel label2 = new JLabel("Ingrese nombre del entrenador 2:");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(label2);
    
        entrenador2Field = new JTextField();
        entrenador2Field.setMaximumSize(new Dimension(300, 25));
        panelCentral.add(entrenador2Field);
    
        add(panelCentral, BorderLayout.CENTER);
    
        // Botón Aceptar en panel separado centrado
        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.setPreferredSize(new Dimension(100, 30));
    
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(aceptarButton);
    
        aceptarButton.addActionListener(e -> generarEquipos());
    
        add(panelBoton, BorderLayout.SOUTH);
    
        revalidate();
        repaint();
    }
    

    private void generarEquipos() {
        // Obtener los nombres de los entrenadores
        String nombreEntrenador1 = entrenador1Field.getText().trim();
        String nombreEntrenador2 = entrenador2Field.getText().trim();

        if (nombreEntrenador1.isEmpty() || nombreEntrenador2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese los nombres de ambos entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Inicializar datos y generar equipos
        ElementPokemon.initializeData();
        List<Pokemon> availablePokemons = new ArrayList<>(Arrays.asList(ElementPokemon.getPokemon()));

        entrenador1 = new Entrenador();
        entrenador2 = new Entrenador();

        entrenador1.setNameTrainer(new Scanner(nombreEntrenador1), availablePokemons);
        entrenador2.setNameTrainer(new Scanner(nombreEntrenador2), availablePokemons);

        // Mostrar la pantalla con los equipos generados
        mostrarEquipos();
    }

    private void mostrarEquipos() {
        // Limpiar el contenido de la ventana
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Texto superior
        JLabel titulo = new JLabel("Equipos Generados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Área de texto para mostrar los equipos
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Mostrar los equipos
        textArea.append(entrenador1.getNombre() + ":\n");
        for (Pokemon p : entrenador1.getEquipo()) {
            textArea.append("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")\n");
        }
        textArea.append("\n" + entrenador2.getNombre() + ":\n");
        for (Pokemon p : entrenador2.getEquipo()) {
            textArea.append("- " + p.getNamePokemon() + " (HP: " + p.getHP() + ", Tipo: " + p.getTypePokemon() + ")\n");
        }

        // Botón para continuar
        JButton continuarButton = new JButton("Continuar");
        continuarButton.addActionListener(e -> iniciarBatalla());
        add(continuarButton, BorderLayout.SOUTH);

        // Actualizar la ventana
        revalidate();
        repaint();
    }

    private void iniciarBatalla() {
        // Limpiar el contenido de la ventana
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Texto superior
        JLabel titulo = new JLabel("Batalla Pokémon", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Panel central para mostrar los Pokémon y habilidades
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(2, 1, 10, 10));

        // Usar los ButtonGroup globales
        grupoHabilidades1 = new ButtonGroup();
        grupoHabilidades2 = new ButtonGroup();

        // Primer Pokémon del entrenador 1
        Pokemon pokemon1 = entrenador1.getEquipo()[0];
        JPanel panelPokemon1 = crearPanelPokemon(entrenador1.getNombre(), pokemon1, grupoHabilidades1);
        panelCentral.add(panelPokemon1);

        // Primer Pokémon del entrenador 2
        Pokemon pokemon2 = entrenador2.getEquipo()[0];
        JPanel panelPokemon2 = crearPanelPokemon(entrenador2.getNombre(), pokemon2, grupoHabilidades2);
        panelCentral.add(panelPokemon2);

        add(panelCentral, BorderLayout.CENTER);

        continuarButton = new JButton("Continuar");
        continuarButton.setEnabled(false); // Deshabilitado hasta que ambos seleccionen habilidades
        continuarButton.addActionListener(e -> {
            ejecutarTurno(pokemon1, pokemon2);
            continuarButton.setEnabled(false); // Deshabilitar nuevamente hasta la próxima selección
        });
        add(continuarButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JPanel crearPanelPokemon(String nombreEntrenador, Pokemon pokemon, ButtonGroup grupoHabilidades) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Información del Pokémon
        JLabel infoPokemon = new JLabel(
            "<html><center>" + nombreEntrenador + " envía a " + pokemon.getNamePokemon() +
            "<br>HP: " + pokemon.getHP() + " | Velocidad: " + pokemon.getVelocidad() +
            "<br>Tipo: " + pokemon.getTypePokemon() + "</center></html>",
            SwingConstants.CENTER
        );
        panel.add(infoPokemon, BorderLayout.NORTH);

        // Botones para seleccionar habilidades
        JPanel panelHabilidades = new JPanel();
        panelHabilidades.setLayout(new GridLayout(2, 2, 5, 5));

        for (Ataque ataque : pokemon.getAtaque()) {
            JRadioButton botonAtaque = new JRadioButton(ataque.getNameAtaque() + " (Daño: " + ataque.getPower() + ")");
            botonAtaque.setActionCommand(ataque.getNameAtaque()); // Guardar el nombre del ataque
            grupoHabilidades.add(botonAtaque);
            panelHabilidades.add(botonAtaque);

            // Habilitar el botón "Continuar" cuando se selecciona una habilidad
            botonAtaque.addActionListener(e -> verificarSeleccionHabilidades());
        }

        panel.add(panelHabilidades, BorderLayout.CENTER);

        return panel;
    }

    private void verificarSeleccionHabilidades() {
        // Verificar si ambos grupos de habilidades tienen una selección
        boolean entrenador1Selecciono = grupoHabilidades1.getSelection() != null;
        boolean entrenador2Selecciono = grupoHabilidades2.getSelection() != null;

        // Habilitar el botón "Continuar" si ambos seleccionaron
        continuarButton.setEnabled(entrenador1Selecciono && entrenador2Selecciono);
    }

    private void ejecutarTurno(Pokemon pokemon1, Pokemon pokemon2) {
        // Obtener las habilidades seleccionadas
        String habilidad1 = grupoHabilidades1.getSelection().getActionCommand();
        String habilidad2 = grupoHabilidades2.getSelection().getActionCommand();

        Ataque ataque1 = obtenerAtaquePorNombre(pokemon1, habilidad1);
        Ataque ataque2 = obtenerAtaquePorNombre(pokemon2, habilidad2);

        // Determinar el orden de ataque según la velocidad de las habilidades
        int velocidad1 = ataque1.getPower(); // Usamos la potencia como velocidad de la habilidad
        int velocidad2 = ataque2.getPower();

        Pokemon primero = velocidad1 >= velocidad2 ? pokemon1 : pokemon2;
        Pokemon segundo = primero == pokemon1 ? pokemon2 : pokemon1;
        Ataque ataquePrimero = primero == pokemon1 ? ataque1 : ataque2;
        Ataque ataqueSegundo = primero == pokemon1 ? ataque2 : ataque1;

        // Calcular el daño del primer ataque
        int dañoPrimero = ataquePrimero.getPower();
        if (primero.getTypePokemon().isStrongAgainst(segundo.getTypePokemon())) {
            dañoPrimero += dañoPrimero * 0.3; // Aumentar el daño en un 30% por ventaja de tipo
            JOptionPane.showMessageDialog(this, "¡Ventaja de tipo! El ataque de " + primero.getNamePokemon() + " es más efectivo.");
        }

        // Mostrar el ataque del primer Pokémon
        JOptionPane.showMessageDialog(this, primero.getNamePokemon() + " usa " + ataquePrimero.getNameAtaque() + " y ataca primero.");
        segundo.setHP((short) (segundo.getHP() - dañoPrimero)); // Reducir HP del segundo Pokémon

        // Verificar si el segundo Pokémon ha sido derrotado
        if (segundo.getHP() <= 0) {
            JOptionPane.showMessageDialog(this, segundo.getNamePokemon() + " ha sido derrotado.");
            avanzarAlSiguientePokemon(segundo);
            return; // Terminar el turno si el segundo Pokémon es derrotado
        }

        // Calcular el daño del segundo ataque
        int dañoSegundo = ataqueSegundo.getPower();
        if (segundo.getTypePokemon().isStrongAgainst(primero.getTypePokemon())) {
            dañoSegundo += dañoSegundo * 0.3; // Aumentar el daño en un 30% por ventaja de tipo
            JOptionPane.showMessageDialog(this, "¡Ventaja de tipo! El ataque de " + segundo.getNamePokemon() + " es más efectivo.");
        }

        // Mostrar el ataque del segundo Pokémon
        JOptionPane.showMessageDialog(this, segundo.getNamePokemon() + " usa " + ataqueSegundo.getNameAtaque() + ".");
        primero.setHP((short) (primero.getHP() - dañoSegundo)); // Reducir HP del primer Pokémon

        // Verificar si el primer Pokémon ha sido derrotado
        if (primero.getHP() <= 0) {
            JOptionPane.showMessageDialog(this, primero.getNamePokemon() + " ha sido derrotado.");
            avanzarAlSiguientePokemon(primero);
        } else {
            // Mostrar el estado de ambos Pokémon
            JOptionPane.showMessageDialog(this,
                primero.getNamePokemon() + " tiene " + primero.getHP() + " HP restantes.\n" +
                segundo.getNamePokemon() + " tiene " + segundo.getHP() + " HP restantes."
            );
        }

        // Actualizar la ventana
        revalidate();
        repaint();
    }

    private Ataque obtenerAtaquePorNombre(Pokemon pokemon, String nombreAtaque) {
        for (Ataque ataque : pokemon.getAtaque()) {
            if (ataque.getNameAtaque().equals(nombreAtaque)) {
                return ataque;
            }
        }
        throw new IllegalArgumentException("Ataque no encontrado: " + nombreAtaque);
    }

    private void avanzarAlSiguientePokemon(Pokemon pokemonDerrotado) {
        if (pokemonDerrotado == entrenador1.getEquipo()[0]) {
            entrenador1.getEquipo()[0] = obtenerSiguientePokemon(entrenador1);
        } else {
            entrenador2.getEquipo()[0] = obtenerSiguientePokemon(entrenador2);
        }

        // Verificar si el juego ha terminado
        if (entrenador1.getEquipo()[0] == null || entrenador2.getEquipo()[0] == null) {
            String ganador = (entrenador1.getEquipo()[0] == null) ? entrenador2.getNombre() : entrenador1.getNombre();
            JOptionPane.showMessageDialog(this, "¡" + ganador + " ha ganado la batalla!");
            System.exit(0); // Cerrar el programa
        } else {
            // Reiniciar la ventana con los Pokémon actualizados
            reiniciarVentana();
        }
    }

    private Pokemon obtenerSiguientePokemon(Entrenador entrenador) {
        for (Pokemon pokemon : entrenador.getEquipo()) {
            if (pokemon.getHP() > 0) {
                return pokemon;
            }
        }
        JOptionPane.showMessageDialog(this, entrenador.getNombre() + " se ha quedado sin Pokémon. ¡El juego ha terminado!");
        return null; // Devuelve null si no quedan Pokémon
    }

    private void actualizarPanelPokemon(String nombreEntrenador, Pokemon pokemon, ButtonGroup grupoHabilidades) {
        // Limpiar el grupo de botones
        grupoHabilidades.clearSelection();

        // Crear un nuevo panel para el Pokémon
        JPanel panel = crearPanelPokemon(nombreEntrenador, pokemon, grupoHabilidades);

        // Reemplazar el panel existente en la interfaz
        if (grupoHabilidades == grupoHabilidades1) {
            getContentPane().remove(1); // Eliminar el panel del primer Pokémon
            add(panel, BorderLayout.WEST);
        } else {
            getContentPane().remove(2); // Eliminar el panel del segundo Pokémon
            add(panel, BorderLayout.EAST);
        }

        // Actualizar la ventana
        revalidate();
        repaint();
    }

    private void eliminarPanelPokemon(ButtonGroup grupoHabilidades) {
        if (grupoHabilidades == grupoHabilidades1) {
            getContentPane().remove(1); // Eliminar el panel del primer Pokémon
        } else {
            getContentPane().remove(2); // Eliminar el panel del segundo Pokémon
        }

        // Actualizar la ventana
        revalidate();
        repaint();
    }

    private void reiniciarVentana() {
        // Cerrar la ventana actual
        dispose();

        // Crear una nueva instancia de la ventana
        Interfaz nuevaVentana = new Interfaz();
        nuevaVentana.setEntrenadores(entrenador1, entrenador2); // Pasar los entrenadores actuales
        nuevaVentana.setVisible(true);
    }

    public void setEntrenadores(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;

        // Reiniciar la batalla con los Pokémon actualizados
        iniciarBatalla();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interfaz interfaz = new Interfaz();
            interfaz.setVisible(true);
        });
    }
}


