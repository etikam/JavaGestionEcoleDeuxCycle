import javax.swing.*;
import java.awt.*;

public class test extends JFrame {
    public test() {
        // Création des panels
        JPanel panelHaut = new JPanel();
        JPanel panelBas = new JPanel();

        // Définition du layout pour la JFrame
        getContentPane().setLayout(new BorderLayout());

        // Ajout des panels à la JFrame
        getContentPane().add(panelHaut, BorderLayout.NORTH);
        getContentPane().add(panelBas, BorderLayout.CENTER);

        // Définition de la couleur de fond pour les panels (pour mieux voir la distinction)
        panelHaut.setBackground(Color.BLUE);
        panelBas.setBackground(Color.RED);

        // Calcul de la taille du panel haut
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight = (int) screenSize.getHeight();
        int panelHautHeight = (int) (frameHeight * 0.8);

        // Définition de la taille pour le panel haut
        panelHaut.setPreferredSize(new Dimension(0, panelHautHeight));

        // Configuration de la JFrame
        setTitle("Exemple de JFrame avec 2 JPanel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Taille par défaut
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Création de la fenêtre
        new test();
    }
}
