import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Fn_Dossier extends JFrame {
    final private JComboBox<String> combo_classe = new JComboBox<>();
    final private JLabel lb_combo = new JLabel("Choisir La classe");
    private JButton rechercherButton = new JButton("Actualiser");
    private JButton supprimerButton = new JButton("Supprimer");
    private JTable elevesTable;
    private JLabel photoLabel = new JLabel(); // JLabel pour afficher l'image

    public String query = "";
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;
    private JPanel topPanel = new JPanel();

    public Fn_Dossier() {
        setTitle("Gestion des Dossiers des Élèves du primaire");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        // Création du panneau supérieur
        lb_combo.setFont(new Font("Algerian", Font.BOLD, 20));
        lb_combo.setForeground(Color.white);
        topPanel.add(lb_combo);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Ajoutez ici les classes disponibles dans le JComboBox
        DBConnection con = new DBConnection();
        combo_classe.addItem("Toutes");
        try {
            query = "SELECT * FROM classe_primaire";
            ps = con.getCon().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                combo_classe.addItem(String.valueOf(rs.getInt("num_classe") + (rs.getInt("num_classe") == 1 ? " ère Annéé" : " ème Année")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur: " + e.getMessage());
        }
        //Gestion du panel de haut
        topPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        topPanel.setBackground(Color.GRAY);
        topPanel.add(combo_classe);
        rechercherButton.setAlignmentX(10);
        rechercherButton.setAlignmentY(10);

        topPanel.add(rechercherButton);
        topPanel.add(supprimerButton); // Ajout du bouton "Supprimer"

        // Ajout d'un ActionListener pour le bouton Actualiser
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadElevesData();
            }
        });

        // Ajout d'un ActionListener pour le bouton Supprimer
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = elevesTable.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet élément ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String matricule = (String) elevesTable.getValueAt(selectedRow, 0);
                        supprimerEleve(matricule); // Supprimer l'élève de la base de données
                        loadElevesData(); // Actualiser l'affichage
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un élément à supprimer.");
                }
            }
        });

        // Création du panneau inférieur
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        elevesTable = new JTable();
        // Ajout d'un ListSelectionListener pour détecter la sélection d'une ligne dans le tableau
        elevesTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = elevesTable.getSelectedRow();
            if (selectedRow != -1) { // S'assurer qu'une ligne est sélectionnée
                String matricule = (String) elevesTable.getValueAt(selectedRow, 0);
                afficherPhoto(matricule); // Afficher la photo en fonction du matricule sélectionné
            }
        });
        JScrollPane scrollPane = new JScrollPane(elevesTable);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le JLabel pour afficher la photo
        bottomPanel.add(photoLabel, BorderLayout.EAST);

        // Ajout des panneaux à la fenêtre
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void afficherPhoto(String matricule) {
        try {
            DBConnection con = new DBConnection();
            query = "SELECT photo FROM ecolier WHERE matricule = ?";
            ps = con.getCon().prepareStatement(query);
            ps.setString(1, matricule);
            rs = ps.executeQuery();
            if (rs.next()) {
                // L'image est stockée dans la base de données sous forme de Blob
                // Vous devez extraire l'image Blob et la convertir en ImageIcon pour l'afficher dans le JLabel
                Blob blob = rs.getBlob("photo");
                if (blob != null) {
                    byte[] imageData = blob.getBytes(1, (int) blob.length());
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    photoLabel.setIcon(imageIcon);
                } else {
                    photoLabel.setIcon(null); // Effacer l'image s'il n'y a pas de photo disponible
                }
            } else {
                photoLabel.setIcon(null); // Effacer l'image si aucun enregistrement correspondant n'est trouvé
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la photo. Erreur" + e.getMessage());
        }
    }

    private void loadElevesData() {
        try {
            // Connexion à la base de données Oracle
            DBConnection con = new DBConnection();
            query = "SELECT * FROM ecolier ORDER BY nom";
            ps = con.getCon().prepareStatement(query);
            rs = ps.executeQuery();
            // Création du modèle de table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Matricule");
            model.addColumn("Nom");
            model.addColumn("Prénom");
            model.addColumn("Date de Naissance");
            model.addColumn("Lieu de Naissance");
            model.addColumn("Pere");
            model.addColumn("Mere");
            model.addColumn("Adresse");
            model.addColumn("tuteur");
            model.addColumn("num tuteur");
            model.addColumn("classe");
            model.addColumn("date_inscription");
            while (rs.next()) {
                String date = rs.getString("date_naiss").substring(0, 10);
                int classe_from_db = rs.getInt("classe");
                String class_in_table = String.valueOf(classe_from_db);
                class_in_table = (classe_from_db == 1) ? class_in_table + " ère Année" : class_in_table + " ème Année";

                model.addRow(new Object[]{rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), date,
                        rs.getString("lieu_naiss"), rs.getString("pere"), rs.getString("mere"),
                        rs.getString("adresse"), rs.getString("tuteur"), rs.getString("num_tuteur"), class_in_table,rs.getString("date_inscription")
                });
            }

            // Fermeture des ressources
            rs.close();
            ps.close();

            // Définir le modèle de table pour la JTable
            elevesTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des données des élèves. Erreur" + e.getMessage());
        }
    }

    private void supprimerEleve(String matricule) {
        // en faite c'est pas une suppression dans la base de donnée mais un deplacement de la table ecolier à la table historiques
        try {
            DBConnection con = new DBConnection();
            query = "DELETE FROM ecolier WHERE matricule = ?";
            ps = con.getCon().prepareStatement(query);
            ps.setString(1, matricule);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "L'élève a été supprimé avec succès.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'élève. Erreur" + e.getMessage());
        }
    }
}
