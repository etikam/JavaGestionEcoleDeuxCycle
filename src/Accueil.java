import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class Accueil extends JFrame {
    final private Paneau p_primaire = new Paneau();//conteneur global de l'onglet primare
    final private Paneau p_college = new Paneau();//conteneur global de l'onglet collège
    final private Paneau p_examen = new Paneau();//conteneur global de l'onglet examen
    final private JPanel p_fonctionnalite = new JPanel();
    final private JPanel p_fonctionnalite_college = new JPanel();
    final private JPanel p_footer = new JPanel();
    final private JPanel p_footer_college = new JPanel();
    final private JPanel p_header = new JPanel();
    final private JPanel p_header_college = new JPanel();
    final private JPanel p_fonctionnalite_examen = new JPanel();
    final private JPanel p_header_examen = new JPanel();
    final private JPanel p_footer_examen = new JPanel();
//Création des buttons pour les differente fonctionnalité du primaire
    private JButton btn_eleve_primaire = new JButton();
    private JButton btn_enseignant_primaire = new JButton();
    private JButton btn_notes_primaire = new JButton();
    private JButton btn_historique_primaire = new JButton();
    private JButton btn_dossier_primaire = new JButton();

    //Création des buttons pour les differente fonctionnalité du collège
    private JButton btn_eleve_college = new JButton();
    private JButton btn_enseignant_college = new JButton();
    private JButton btn_notes_college = new JButton();
    private JButton btn_historique_college = new JButton();
    private JButton btn_dossier_college = new JButton();
    private JButton btn_matiere_college = new JButton();


    public Accueil() {
        // deffinitiano des propriétés de la fenetre
        setTitle("Menu Accueil de MonEcole");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Création des onglets
        JTabbedPane onglets = new JTabbedPane();
        // Ajout des Onglets
        onglets.add("Primaire", p_primaire);
        onglets.add("Collège", p_college);
        onglets.add("Examen", p_examen);
        add(onglets);
        // Initialisation de l'interface utilisateur
        initUI_primaire(); //initialisation de l'onglet du primaire
        initUI_college(); //initialisation de l'onglet du collège
        initUI_examen();  //initialisation de l'onglet de l'examen


        btn_eleve_primaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ouverture de la fenetre de gestion des eleves

                ElevePrimaire fn = new ElevePrimaire();
            }
        });



        setVisible(true);
    }
    protected void initUI_primaire() {

        initialise_header("Menu de Gestion de l'Ecole primaire");
        // Fixer la taille du panneau footer
        initialise_footer("Copyright, tout droit reservé.      Design by Etikam");
        // Création du conteneur de fonctionnalité
        initialise_container_primaire();
        p_primaire.setLayout(new BorderLayout());
        p_primaire.add(p_header,BorderLayout.NORTH);
        p_primaire.add(p_fonctionnalite,BorderLayout.CENTER);
        p_primaire.add(p_footer,BorderLayout.SOUTH);
        // Fermeture de la fenêtre lorsque l'utilisateur clique sur la croix
    }
    protected void initialise_header(String msg_header){
        JLabel message = new JLabel(msg_header);
        message.setFont(new Font("Arial",Font.BOLD,40));
        message.setForeground(Color.white);
        message.setBackground(Color.white);
        p_header.setBackground(Color.GRAY);
        p_header.add(message);
        p_header.setPreferredSize(new Dimension(getWidth(), 100)); // Hauteur fixe de 50 pixels
    }
    protected void initialise_footer(String msg_footer){
        JLabel message_footer = new JLabel(msg_footer);
        message_footer.setForeground(Color.white);
        message_footer.setFont(new Font("Algerian",Font.ITALIC,15));
        p_footer.setBackground(Color.black);
        p_footer.add(message_footer,BorderLayout.CENTER);
        p_footer.setPreferredSize(new Dimension(getWidth(), 50)); // Hauteur fixe de 50 pixels

    }
    protected void initialise_container_primaire(){
        p_fonctionnalite.setLayout(new GridLayout(2, 3, 50, 50));
        p_fonctionnalite.setBackground(Color.WHITE);
        //créatino du conteneur header
        //Ajout des Cards au conteneur de fonctionnalité pour le primaire
        String desc1 = "Ici la gestion de tout ce qui concerne les Elèves";
        String desc2 = "Ici la gestion de tout ce qui concerne les Enseignants";
        String desc3 = "Ici la gestion de tout ce qui concerne les Notes/Résultat";
        String desc4 = "Ici la gestion de tout ce qui concerne l'historiques des Elèves";
        String desc5 = "Ici la gestion de tout ce qui concerne les Dossiers des Eleves";
        p_fonctionnalite.add(new Card("Eleve", desc1, new ImageIcon("assets/students.png"), btn_eleve_primaire));
        p_fonctionnalite.add(new Card("Enseignants", desc2, new ImageIcon("assets/enseignants.png"), btn_enseignant_primaire));
        p_fonctionnalite.add(new Card("Notes", desc3, new ImageIcon("assets/notes.png"), btn_notes_primaire));
        p_fonctionnalite.add(new Card("Historique", desc4, new ImageIcon("assets/historiques.png"), btn_historique_primaire));
        p_fonctionnalite.add(new Card("Dossiers", desc5, new ImageIcon("assets/dossiers.png"), btn_dossier_primaire));
        // Ajout du panneau de fonctionnalité au panneau primaire

        // Ajout des onglets à la fenêtre
    }
    protected void initUI_college(){
        //Initialisation des elements de l'onglet Collège
        initialise_header_college("Menu de Gestion du Collège");
        // Fixer la taille du panneau footer
        initialise_footer_college("Copyright, tout droit reservé.      Design by Etikam");
        // Création du conteneur de fonctionnalité
        initialise_container_college();

        p_college.setLayout(new BorderLayout());
        p_college.add(p_header_college,BorderLayout.NORTH);
        p_college.add(p_fonctionnalite_college,BorderLayout.CENTER);
        p_college.add(p_footer_college,BorderLayout.SOUTH);
    }

    protected void initialise_header_college(String msg){
        JLabel message = new JLabel(msg);
        message.setFont(new Font("Arial",Font.BOLD,40));
        message.setForeground(Color.white);
        message.setBackground(Color.white);
        p_header_college.setBackground(Color.GRAY);
        p_header_college.add(message);
        p_header_college.setPreferredSize(new Dimension(getWidth(), 100)); // Hauteur fixe de 50 pixels
    }

    protected void initialise_footer_college(String msg){
        JLabel message_footer = new JLabel(msg);
        message_footer.setForeground(Color.white);
        message_footer.setFont(new Font("Algerian",Font.ITALIC,15));
        p_footer.setBackground(Color.black);
        p_footer_college.add(message_footer,BorderLayout.CENTER);
        p_footer_college.setPreferredSize(new Dimension(getWidth(), 50)); // Hauteur fixe de 50 pixels
    }
    protected void initialise_container_college(){
        p_fonctionnalite_college.setLayout(new GridLayout(2, 3, 50, 50));
        p_fonctionnalite_college.setBackground(Color.WHITE);
        //créatino du conteneur header
        //Ajout des Cards au conteneur de fonctionnalité pour le primaire
        String desc1 = "Ici la gestion de tout ce qui concerne les Elèves";
        String desc2 = "Ici la gestion de tout ce qui concerne les Professeur";
        String desc3 = "Ici la gestion de tout ce qui concerne les Notes/resultats";
        String desc4 = "Ici la gestion de tout ce qui concerne les Matières";
        String desc5 = "Ici la gestion de tout ce qui concerne les Dossiers";
        String desc6 = "Ici la gestion de tout ce qui concerne l'Historique";
        p_fonctionnalite_college.add(new Card("Eleves Collège", desc1, new ImageIcon("assets/students.png"), new JButton()));
        p_fonctionnalite_college.add(new Card("Professeur", desc2, new ImageIcon("assets/enseignants.png"), new JButton()));
        p_fonctionnalite_college.add(new Card("Notes", desc3, new ImageIcon("assets/notes.png"), new JButton()));
        p_fonctionnalite_college.add(new Card("Matières", desc4, new ImageIcon("assets/matieres.png"), new JButton()));
        p_fonctionnalite_college.add(new Card("Dossiers", desc5, new ImageIcon("assets/dossiers.png"), new JButton()));
        p_fonctionnalite_college.add(new Card("Historique", desc6, new ImageIcon("assets/historiques.png"), new JButton()));
        // Ajout du panneau de fonctionnalité au panneau primaire
    }
    protected void initUI_examen(){
        initialise_header_examen("Menu de Gestion des Examens");

        initialise_footer_examen("Copyright, tout droit reservé.      Design by Etikam");

        initialise_container_examen();
        //Initialisation des elements de l'onglet Examen
    }

    protected void initialise_container_examen(){
        p_fonctionnalite_examen.setLayout(new GridLayout(2, 3, 50, 50));
        p_fonctionnalite_examen.setBackground(Color.WHITE);
        //créatino du conteneur header
        //Ajout des Cards au conteneur de fonctionnalité pour le primaire
        String desc1 = "Ici la gestion de tout ce qui concerne les Elèves";
        String desc2 = "Ici la gestion de tout ce qui concerne les Professeur";
        String desc3 = "Ici la gestion de tout ce qui concerne les Notes/resultats";
        String desc4 = "Ici la gestion de tout ce qui concerne les Matières";
        String desc5 = "Ici la gestion de tout ce qui concerne les Dossiers";
        String desc6 = "Ici la gestion de tout ce qui concerne l'Historique";
        p_fonctionnalite_examen.add(new Card("Eleves Collège", desc1, new ImageIcon("assets/students.png"), new JButton()));
        p_fonctionnalite_examen.add(new Card("Professeur", desc2, new ImageIcon("assets/enseignants.png"), new JButton()));
        p_fonctionnalite_examen.add(new Card("Notes", desc3, new ImageIcon("assets/notes.png"), new JButton()));
        p_fonctionnalite_examen.add(new Card("Matières", desc4, new ImageIcon("assets/matieres.png"), new JButton()));
        p_fonctionnalite_examen.add(new Card("Dossiers", desc5, new ImageIcon("assets/dossiers.png"), new JButton()));
        p_fonctionnalite_examen.add(new Card("Historique", desc6, new ImageIcon("assets/historiques.png"), new JButton()));
        // Ajout du panneau de fonctionnalité au panneau primaire
    }

    protected void initialise_header_examen(String msg){


    }

    protected void initialise_footer_examen(String msg_footer){
        JLabel message_footer = new JLabel(msg_footer);
        message_footer.setForeground(Color.white);
        message_footer.setFont(new Font("Algerian",Font.ITALIC,15));
        p_footer_examen.setBackground(Color.black);
        p_footer_examen.add(message_footer,BorderLayout.CENTER);
        p_footer_examen.setPreferredSize(new Dimension(getWidth(), 50)); // Hauteur fixe de 50 pixels

    }



    //=====================EVENEMENTS SUR LES BOUTTONS=============================//

}
