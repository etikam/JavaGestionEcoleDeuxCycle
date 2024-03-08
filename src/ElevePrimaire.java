import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ElevePrimaire extends JFrame {

    private DBConnection dbConnection;
    private JPanel liste_header = new JPanel();
    private JPanel liste_container = new JPanel();
    private JPanel liste_footer = new JPanel();

    // Création des onglets et des panneaux
    private JTabbedPane onglets = new JTabbedPane();
    private JPanel inscriptionPanel = createFormulairePanel();
    private JPanel reInscriptionPanel = createReInscriptionPanel();
    private JPanel listePanel = createListePanel();
    private JTable elevesTable = new JTable();
    public String query = "";
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;

    public ElevePrimaire() {
        setTitle("Gestion des élèves primaires");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre
        setResizable(false);

        dbConnection = new DBConnection(); // Initialisation de la connexion à la base de données

        // Ajout des onglets à la JTabbedPane
        onglets.addTab("Inscription", inscriptionPanel);
        onglets.addTab("Réinscription", reInscriptionPanel);
        onglets.addTab("Liste", listePanel);

        // Ajout de la JTabbedPane à la fenêtre
        add(onglets);

        loadElevesData();
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

        // Ajout du texte "Ajouter un nouvel élève" au-dessus du formulaire
        JLabel titreLabel = new JLabel("Ajouter un nouvel élève");
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Superposition verticale des composants
        initListPanel();
        panel.add(liste_header);
        panel.add(liste_container);
        panel.add(liste_footer);
        return panel;
    }

    private void initListPanel() {
        // Initialisation des éléments du header
        init_list_header();
        // Initialisation des éléments du container
        init_list_container();
        // Initialisation des éléments du footer
        init_list_footer();
    }

    private void init_list_header() {
        JLabel l_search = new JLabel("Taper une recherche: ");
        JLabel l_classe = new JLabel("Classe: ");

        JTextField txt_search = new JTextField(40);
        JComboBox<String> classeComboBox = new JComboBox<>(new String[]{"Classe 1", "Classe 2", "Classe 3"}); // Exemple de valeurs de classe

        JButton actualiserButton = new JButton("Actualiser");
        actualiserButton.addActionListener(e -> {
            // Implémenter l'action de l'actualisation
            loadElevesData();
        });

        liste_header.add(l_search);
        liste_header.add(txt_search);
        liste_header.add(l_classe);
        liste_header.add(classeComboBox);
        liste_header.add(actualiserButton);
    }

    private void init_list_container() {
        // Implémenter la création et le remplissage du tableau avec les élèves de la base de données
    }

    private void init_list_footer() {
        JLabel nombreLabel = new JLabel("Nombre d'élèves: "); // Label pour afficher le nombre d'élèves

        // Implémenter la récupération du nombre d'élèves depuis la base de données et l'afficher dans le label

        liste_footer.add(nombreLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElevePrimaire());
    }


    private void loadElevesData() {
        try {
            // Connexion à la base de données Oracle
            DBConnection con = new DBConnection();
            query = "SELECT * FROM eleve_primaire ORDER BY nom";
            ps = con.getCon().prepareStatement(query);
            rs = ps.executeQuery();
            // Création du modèle de table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Matricule");
            model.addColumn("Nom");
            model.addColumn("Prénom");
            model.addColumn("Sexe");
            model.addColumn("Classe");
            model.addColumn("Status");

            while (rs.next()) {

                int classe_from_db = rs.getInt("classe");
                String class_in_table = String.valueOf(classe_from_db);
                class_in_table = (classe_from_db == 1) ? class_in_table + " ère Année" : class_in_table + " ème Année";

                model.addRow(new Object[]{rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"),

                        rs.getString("sexe"), class_in_table, rs.getString("status"),
                });
            }

            // Fermeture des ressources
            rs.close();
            ps.close();

            // Définir le modèle de table pour la JTable
            elevesTable.setModel(model);

            liste_container.add(elevesTable);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des données des élèves. Erreur" + e.getMessage());
        }
    }
}
