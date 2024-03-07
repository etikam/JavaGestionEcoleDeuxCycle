import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ElevePrimaire extends JFrame {

    private DBConnection dbConnection;

    public ElevePrimaire() {
        setTitle("Gestion des élèves primaires");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre
        setResizable(false);

        dbConnection = new DBConnection(); // Initialisation de la connexion à la base de données

        // Création des onglets et des panneaux
        JTabbedPane onglets = new JTabbedPane();
        JPanel inscriptionPanel = createFormulairePanel();
        JPanel reInscriptionPanel = createReInscriptionPanel();
        JPanel listePanel = createListePanel();

        // Ajout des onglets à la JTabbedPane
        onglets.addTab("Inscription", inscriptionPanel);
        onglets.addTab("Réinscription", reInscriptionPanel);
        onglets.addTab("Liste", listePanel);

        // Ajout de la JTabbedPane à la fenêtre
        add(onglets);
        setVisible(true);
    }

    private JPanel createFormulairePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Marge autour du formulaire
                BorderFactory.createLineBorder(Color.BLACK) // Bordure noire autour du formulaire
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Marge entre les composants

        // Ajout du texte "Ajouté un nouvel élève" au-dessus du formulaire
        JLabel titreLabel = new JLabel("Ajouté un nouvel élève");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titreLabel, gbc);

        gbc.gridy++; // Passer à la ligne suivante pour le formulaire

        // Ajout des labels et des champs de texte pour chaque champ
        panel.add(new JLabel("Matricule :"), gbc);
        gbc.gridx = 1;
        JTextField matriculeField = new JTextField(20);
        panel.add(matriculeField, gbc);

        // Réinitialisation de la position pour les composants suivants
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        JTextField nomField = new JTextField(20);
        panel.add(nomField, gbc);

        // Ajout du champ pour le Prénom
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        JTextField prenomField = new JTextField(20);
        panel.add(prenomField, gbc);

        // Ajout du champ pour le Sexe
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sexe :"), gbc);
        gbc.gridx = 1;
        JComboBox<String> sexeComboBox = new JComboBox<>(new String[]{"M", "F"});
        panel.add(sexeComboBox, gbc);

        // Ajout du champ pour la Date de Naissance
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Date de Naissance :"), gbc);
        gbc.gridx = 1;
        JTextField dateNaissanceField = new JTextField(20);
        panel.add(dateNaissanceField, gbc);

        // Ajout du champ pour le Lieu de Naissance
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lieu de Naissance :"), gbc);
        gbc.gridx = 1;
        JTextField lieuNaissanceField = new JTextField(20);
        panel.add(lieuNaissanceField, gbc);

        // Ajout du champ pour le Père
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Père :"), gbc);
        gbc.gridx = 1;
        JTextField pereField = new JTextField(20);
        panel.add(pereField, gbc);

        // Ajout du champ pour la Mère
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mère :"), gbc);
        gbc.gridx = 1;
        JTextField mereField = new JTextField(20);
        panel.add(mereField, gbc);

        // Ajout du champ pour l'Adresse
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1;
        JTextField adresseField = new JTextField(20);
        panel.add(adresseField, gbc);

        // Ajout du champ pour le Tuteur
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tuteur :"), gbc);
        gbc.gridx = 1;
        JTextField tuteurField = new JTextField(20);
        panel.add(tuteurField, gbc);

        // Ajout du champ pour le Numéro de Tuteur
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Numéro de Tuteur :"), gbc);
        gbc.gridx = 1;
        JTextField numeroTuteurField = new JTextField(20);
        panel.add(numeroTuteurField, gbc);

        // Ajout du champ pour la Classe
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Classe :"), gbc);
        gbc.gridx = 1;
        JTextField classeField = new JTextField(20);
        panel.add(classeField, gbc);

        // Ajout du champ pour la Date d'Inscription
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Date d'Inscription :"), gbc);
        gbc.gridx = 1;
        JTextField dateInscriptionField = new JTextField(20);
        panel.add(dateInscriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Occupera 2 colonnes pour le bouton
        JButton inscriptionButton = new JButton("Inscrire");
        panel.add(inscriptionButton, gbc);

        // Action à effectuer lors du clic sur le bouton Inscrire
        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insérer les données dans la base de données
                try {
                    Connection connection = dbConnection.getCon();
                    String queryEcolier = "INSERT INTO ecolier(matricule, nom, prenom,  date_naiss, lieu_naiss, pere, mere, adresse, tuteur, num_tuteur, classe, date_ins,sexe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    String queryElevePrimaire = "INSERT INTO eleve_primaire(matricule, nom, prenom, sexe, classe, status) VALUES (?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatementEcolier = connection.prepareStatement(queryEcolier);
                    PreparedStatement preparedStatementElevePrimaire = connection.prepareStatement(queryElevePrimaire);

                    // Set parameters for ecolier table
                    preparedStatementEcolier.setString(1, matriculeField.getText());
                    preparedStatementEcolier.setString(2, nomField.getText());
                    preparedStatementEcolier.setString(3, prenomField.getText());

                    preparedStatementEcolier.setString(4, dateNaissanceField.getText());
                    preparedStatementEcolier.setString(5, lieuNaissanceField.getText());
                    preparedStatementEcolier.setString(6, pereField.getText());
                    preparedStatementEcolier.setString(7, mereField.getText());
                    preparedStatementEcolier.setString(8, adresseField.getText());
                    preparedStatementEcolier.setString(9, tuteurField.getText());
                    preparedStatementEcolier.setString(10, numeroTuteurField.getText());
                    preparedStatementEcolier.setString(11, classeField.getText());
                    preparedStatementEcolier.setString(12, dateInscriptionField.getText());
                    preparedStatementEcolier.setString(13, (String)sexeComboBox.getSelectedItem());

                    // Execute query for ecolier table
                    preparedStatementEcolier.executeUpdate();

                    // Set parameters for eleve_primaire table
                    preparedStatementElevePrimaire.setString(1, matriculeField.getText());
                    preparedStatementElevePrimaire.setString(2, nomField.getText());
                    preparedStatementElevePrimaire.setString(3, prenomField.getText());
                    preparedStatementElevePrimaire.setString(4, (String)sexeComboBox.getSelectedItem());
                    preparedStatementElevePrimaire.setString(5, classeField.getText());
                    preparedStatementElevePrimaire.setString(6, "N"); // Assuming status is always 'N' initially

                    // Execute query for eleve_primaire table
                    preparedStatementElevePrimaire.executeUpdate();

                    // Close prepared statements
                    preparedStatementEcolier.close();
                    preparedStatementElevePrimaire.close();

                    JOptionPane.showMessageDialog(null, "L'élève a été inscrit avec succès !");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription de l'élève !" + ex.getMessage());
                }
            }
        });

        return panel;
    }

    private JPanel createReInscriptionPanel() {
        JPanel panel = new JPanel();
        // Ajoutez ici les composants pour la réinscription
        return panel;
    }

    private JPanel createListePanel() {
        JPanel panel = new JPanel();
        // Ajoutez ici les composants pour afficher la liste d'élèves
        return panel;
    }

}
