import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Fn_Dossier extends JFrame {
   final private JComboBox  combo_classe = new JComboBox();
    private JButton rechercherButton;
    private JTable elevesTable;

    public String query = "";
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;

    public Fn_Dossier() {
        setTitle("Gestion des Dossiers des Élèves du primaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création du panneau supérieur
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Ajoutez ici les classes disponibles dans le JComboBox
        combo_classe.addItem("Classe 1A");
        combo_classe.addItem("Classe 1B");
        combo_classe.addItem("Classe 2A");
        combo_classe.addItem("Classe 2B");
        topPanel.add(combo_classe);
        rechercherButton = new JButton("Rechercher");
        topPanel.add(rechercherButton);

        // Création du panneau inférieur
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        elevesTable = new JTable();
        loadElevesData(); // Charger les données des élèves
        JScrollPane scrollPane = new JScrollPane(elevesTable);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajout des panneaux à la fenêtre
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void loadElevesData() {
       try {
           // Connexion à la base de données Oracle
          DBConnection con = new DBConnection();
          query = "SELECT * FROM ecolier";
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
           while(rs.next()) {
   
              model.addRow(new Object[]{rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"), rs.getString("date_naiss"),
                     rs.getString("lieu_naiss"),rs.getString("date_naiss"),rs.getString("pere"),rs.getString("mere"),
                       rs.getString("tuteur"),rs.getString("num_tuteur"),rs.getInt("classe")
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


}
