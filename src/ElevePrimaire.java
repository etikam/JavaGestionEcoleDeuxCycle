import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ElevePrimaire extends JFrame {

    private DBConnection dbConnection;
    private JPanel liste_header = new JPanel(); //l'entete pour l'onglet liste
    private JPanel liste_container = new JPanel(); // le milieu pour l'onglet liste
    private JPanel liste_footer = new JPanel(); // le pied de page pour l'onglet liste

    // Création des onglets et des panneaux
    private JTabbedPane onglets = new JTabbedPane();  // les onglets
    private JPanel inscriptionPanel = new JPanel(); //l'onglet d'inscription
    private JPanel reInscriptionPanel = createReInscriptionPanel(); // l'onglet de réinscription
    private JPanel listePanel = new JPanel();  // l'onglet de liste
    private JTable elevesTable = new JTable();  // la table des eleves pour l'onglet de liste

    JLabel nombreLabel = new JLabel("Nombre d'élements: ");// le nombre d'elements dans le tableau

    //VARIABLES DE REQUETTES
    public String query = "";
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;
    JLabel l_search = new JLabel("Taper une recherche: ");

    JLabel l_classe = new JLabel("Classe: ");


    JComboBox combo_classe = new JComboBox();
    JTextField txt_search = new JTextField(40);

    JLabel l_status  = new JLabel("Status");
    JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Tous","N", "R"});

    // Ajout du combo pour le filtre par sexe
    JLabel l_sexe  = new JLabel("Sexe");
    JComboBox<String> comboSexe = new JComboBox<>(new String[]{"Tous","M", "F"});

    public ElevePrimaire() {

        //initialisation des composants de la fenetre
        setTitle("Gestion des élèves primaires");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre
        setResizable(true);

        dbConnection = new DBConnection(); // Initialisation de la connexion à la base de données

        // Ajout des onglets à la JTabbedPane
        onglets.addTab("Inscription", inscriptionPanel);
        inscriptionPanel.setBackground(Color.gray);
        JPanel p = createFormulairePanel();
        inscriptionPanel.add(p);
        onglets.addTab("Réinscription", reInscriptionPanel);
        onglets.addTab("Liste", listePanel);

        // Ajout de la JTabbedPane à la fenêtre
        add(onglets);
        initListPanel();
        loadElevesData(txt_search.getText(),combo_classe.getSelectedItem().toString(),comboStatus.getSelectedItem().toString(),comboSexe.getSelectedItem().toString());
        // Ajout du ChangeListener pour détecter le changement d'onglet

       // elevesTable.setPreferredSize(new Dimension(this.getWidth(),liste_container.getHeight()));

        setVisible(true);
    }


    private JPanel createFormulairePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titreLabel = new JLabel("Ajouté un nouvel élève");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titreLabel, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Matricule :"), gbc);
        gbc.gridx = 1;
        JTextField matriculeField = new JTextField(20);
        panel.add(matriculeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        JTextField nomField = new JTextField(20);
        panel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        JTextField prenomField = new JTextField(20);
        panel.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sexe :"), gbc);
        gbc.gridx = 1;
        JComboBox<String> sexeComboBox = new JComboBox<>(new String[]{"M", "F"});
        panel.add(sexeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Date de Naissance :"), gbc);
        gbc.gridx = 1;
        JTextField dateNaissanceField = new JTextField(20);
        panel.add(dateNaissanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lieu de Naissance :"), gbc);
        gbc.gridx = 1;
        JTextField lieuNaissanceField = new JTextField(20);
        panel.add(lieuNaissanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Père :"), gbc);
        gbc.gridx = 1;
        JTextField pereField = new JTextField(20);
        panel.add(pereField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mère :"), gbc);
        gbc.gridx = 1;
        JTextField mereField = new JTextField(20);
        panel.add(mereField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1;
        JTextField adresseField = new JTextField(20);
        panel.add(adresseField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tuteur :"), gbc);
        gbc.gridx = 1;
        JTextField tuteurField = new JTextField(20);
        panel.add(tuteurField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Numéro de Tuteur :"), gbc);
        gbc.gridx = 1;
        JTextField numeroTuteurField = new JTextField(20);
        panel.add(numeroTuteurField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Classe :"), gbc);
        gbc.gridx = 1;
        JTextField classeField = new JTextField(20);
        panel.add(classeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Date d'Inscription :"), gbc);
        gbc.gridx = 1;
        JTextField dateInscriptionField = new JTextField(20);
        panel.add(dateInscriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton inscriptionButton = new JButton("Inscrire");
        panel.add(inscriptionButton, gbc);

        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = dbConnection.getCon();
                    String queryEcolier = "INSERT INTO ecolier(matricule, nom, prenom, sexe, date_naiss, lieu_naiss, pere, mere, adresse, tuteur, num_tuteur, classe, date_ins) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    String queryElevePrimaire = "INSERT INTO eleve_primaire(matricule, nom, prenom, sexe, classe, status) VALUES (?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatementEcolier = connection.prepareStatement(queryEcolier);
                    PreparedStatement preparedStatementElevePrimaire = connection.prepareStatement(queryElevePrimaire);

                    preparedStatementEcolier.setString(1, matriculeField.getText());
                    preparedStatementEcolier.setString(2, nomField.getText());
                    preparedStatementEcolier.setString(3, prenomField.getText());
                    preparedStatementEcolier.setString(4, (String) sexeComboBox.getSelectedItem());
                    preparedStatementEcolier.setString(5, dateNaissanceField.getText());
                    preparedStatementEcolier.setString(6, lieuNaissanceField.getText());
                    preparedStatementEcolier.setString(7, pereField.getText());
                    preparedStatementEcolier.setString(8, mereField.getText());
                    preparedStatementEcolier.setString(9, adresseField.getText());
                    preparedStatementEcolier.setString(10, tuteurField.getText());
                    preparedStatementEcolier.setString(11, numeroTuteurField.getText());
                    preparedStatementEcolier.setInt(12, Integer.parseInt(classeField.getText()));
                    preparedStatementEcolier.setString(13, dateInscriptionField.getText());

                    preparedStatementEcolier.executeUpdate();

                    preparedStatementElevePrimaire.setString(1, matriculeField.getText());
                    preparedStatementElevePrimaire.setString(2, nomField.getText());
                    preparedStatementElevePrimaire.setString(3, prenomField.getText());
                    preparedStatementElevePrimaire.setString(4, (String) sexeComboBox.getSelectedItem());
                    preparedStatementElevePrimaire.setInt(5, Integer.parseInt(classeField.getText()));
                    preparedStatementElevePrimaire.setString(6, "N");

                    preparedStatementElevePrimaire.executeUpdate();

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



    //PARAMETRAGE DE L'ONGLET LISTE
    private void initListPanel() {
        listePanel.setBackground(Color.white);
        elevesTable.setSize(listePanel.getWidth()-100,listePanel.getHeight()-100);
        // Initialisation des éléments du header
        init_list_header();
        // Initialisation des éléments du container
        init_list_container();
        // Initialisation des éléments du footer
        init_list_footer();

        //classement des conteneur sur le page de l'onglet
        listePanel.setLayout(new BorderLayout());
        liste_header.setBackground(Color.gray);
        listePanel.add(liste_header,BorderLayout.NORTH);
        liste_container.setBackground(Color.white);
        listePanel.add(liste_container, BorderLayout.CENTER);
        liste_footer.setBackground(Color.black);
        listePanel.add(liste_footer,BorderLayout.SOUTH);


        // Chargement des liste dans la table
        //loadElevesData();

        // Ajustement du tableau pour remplir tout l'espace horizontal du conteneur parent
        JScrollPane scrollPane = new JScrollPane(elevesTable);
        scrollPane.setBackground(Color.white);
        liste_container.setLayout(new BorderLayout());
        liste_container.add(scrollPane, BorderLayout.CENTER);
    }

    private void init_list_header() {
        //declaration customisation des composants locaux

        l_search.setForeground(Color.white);
        l_search.setFont(new Font("Algerian",Font.BOLD,15));
        l_classe.setForeground(Color.white);
        l_classe.setFont(new Font("Algerian",Font.BOLD,15));
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
        }// Exemple de valeurs de classe
            // Ajout du combo pour le filtre par status

        JButton actualiserButton = new JButton("Actualiser");
        actualiserButton.addActionListener(e -> {
            // Actualisation de la liste
            loadElevesData(txt_search.getText(),combo_classe.getSelectedItem().toString(),comboStatus.getSelectedItem().toString(),comboSexe.getSelectedItem().toString());
        });



        liste_header.add(l_search);
        liste_header.add(txt_search);
        liste_header.add(l_classe);
        liste_header.add(combo_classe);
        liste_header.add(l_status);
        liste_header.add(comboStatus);
        liste_header.add(l_sexe);
        liste_header.add(comboSexe);
        liste_header.add(actualiserButton);

        //modification des dimension du panel d'entete

        liste_header.setPreferredSize(new Dimension(listePanel.getWidth(),80));
    }

    private void init_list_container() {
        // Implémenter la création et le remplissage du tableau avec les élèves de la base de données
    }

    private void init_list_footer() {
         // Label pour afficher le nombre d'élèves
        nombreLabel.setForeground(Color.white);
        nombreLabel.setFont(new Font("Arial Black",Font.BOLD,20));
        // Implémenter la récupération du nombre d'élèves depuis la base de données et l'afficher dans le label
        liste_footer.setPreferredSize(new Dimension(this.getWidth(),70));
        liste_footer.add(nombreLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElevePrimaire());
    }

    // methode de chargement de la liste des etudiants dans la table
    private void loadElevesData(String searchterm, String classe_choosed, String status_choosed, String sexe_choosed) {
        // Construction de la requête de base
        String chars[]={};
        if(classe_choosed!="Toutes"|| classe_choosed!=null){
            chars = classe_choosed.split(" ");
        }
        //traitement des parametre pour la génération de la requete

        int classe = (classe_choosed==null || classe_choosed=="Toutes")?0:(Integer.parseInt(chars[0]));

        String status = (status_choosed != null && status_choosed != "Tous")?status_choosed:null;
        String sexe = (sexe_choosed != null && sexe_choosed != "Tous")?sexe_choosed:null;
        String search = (!searchterm.isEmpty())?searchterm:null;


        String query = "SELECT * FROM eleve_primaire";

        // Ajout de clauses conditionnelles en fonction des paramètres
        if (classe != 0 || status !=null || sexe != null || search !=null){

            query += " WHERE";

            // Ajout de la clause pour la classe
            if (classe != 0) {
                query += " classe = " + classe;
            }

            // Ajout de la clause pour le statut
            if (status != null ) {
                if (classe != 0) {
                    query += " AND";
                }
                query += " status = '" + status + "'";}            // Ajout de la clause pour le sexe
            if (sexe != null) {
                if (classe != 0 || status != null) {
                    query += " AND";
                }
                query += " sexe = '" + sexe + "'";
            }

            if(search != null){
                if(classe != 0 || status != null || sexe != null){
                    query += " AND";
                }
                query += " matricule = '" + search + "'";
            }
        }

        query += " ORDER BY nom";



        try {
            // Connexion à la base de données
            DBConnection con = new DBConnection();
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
                // Ajout des données de l'élève au modèle de table
                int classe_from_db = rs.getInt("classe");
                String class_in_table = (classe_from_db == 1) ? classe_from_db + " ère Année" : classe_from_db + " ème Année";

                model.addRow(new Object[]{rs.getString("matricule"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("sexe"), class_in_table, rs.getString("status")});
            }
            int nbr = ps.executeUpdate();

            //System.out.println("nombre de lignes: " +nbr);
            nombreLabel.setText(nbr==0?"aucune ligne correspondante":(nbr>1?String.valueOf(nbr)+" Lignes correspondantes":String.valueOf(nbr)+" Ligne correspondante"));
            // Fermeture des ressources
            rs.close();
            ps.close();

            // Définition du modèle de table pour la JTable
            elevesTable.setModel(model);

            liste_container.add(elevesTable);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des données des élèves. Erreur" + e.getMessage());
        }
    }



}
