package Vista;

import javax.swing.*;

import Controlador.Controlador;
import Modelo.Ataque;
import Modelo.ElementPokemon;
import Modelo.Entrenador;
import Modelo.Pokemon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Interfaz extends JFrame {
    private Controlador controlador;
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

        // Notifica al controlador
        if (controlador != null) {
            controlador.setEntrenadores(entrenador1, entrenador2);
        }

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
        continuarButton.addActionListener(e -> {
            if (controlador != null) {
                controlador.iniciarBatalla();
            }
        });
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
        continuarButton.addActionListener(e -> notificarTurnoSeleccionado());
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

    // Llama al controlador cuando ambos ataques están seleccionados
    private void notificarTurnoSeleccionado() {
        if (controlador != null && grupoHabilidades1.getSelection() != null && grupoHabilidades2.getSelection() != null) {
            String ataque1 = grupoHabilidades1.getSelection().getActionCommand();
            String ataque2 = grupoHabilidades2.getSelection().getActionCommand();
            controlador.ejecutarTurno(ataque1, ataque2);
            continuarButton.setEnabled(false); // Deshabilitar hasta la próxima selección
        }
    }

    // Métodos para que el controlador actualice la interfaz
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarPaneles(Pokemon pokemon1, Pokemon pokemon2) {
        // Aquí puedes actualizar los paneles de los Pokémon en la interfaz
        // ...
    }

    public void finalizarBatalla(String ganador) {
        JOptionPane.showMessageDialog(this, "¡" + ganador + " ha ganado la batalla!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interfaz interfaz = new Interfaz();
            interfaz.setVisible(true);
        });
    }
}


