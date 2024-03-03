import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class login  extends JFrame {
    /* =======CONTENEURISATION=================================*/
   final private Paneau p1 = new Paneau(); // Pour l'image de connexion
    final private Paneau p2 = new Paneau(); // pour les champs de saisi pour la connexion
    final private Paneau p3 = new Paneau(); // pour le boutton de connexion
    private JButton btn_connexion = new JButton("Connexion");
    private  JTextField t_username = new JTextField(20);
    private  JPasswordField t_pwd= new JPasswordField(20);
    public String query = "";
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;

    private JLabel error_auth = new JLabel("login ou mot de passe incorrecte");
    public login(){

        /* =================PROPRIETES DE LA FENETRE============================ */
        setTitle("Formulaire de connexion");
        setSize(350,450);
        setLayout(new GridLayout(3,1));
        setResizable(false);
        error_auth.setForeground(Color.red);
        error_auth.setVisible(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            /*=========CONTENU DU PREMIER CONTENEUR (pour l'image)  */
            this.conteneur1();
           /*========CONTENU DU SECOND CONTENEUR (pour les champ de connexion*/
            this.conteur2();
                /*=======CONTENU DU TROISIEME CONTENEUR */
            this.conteur3();


        ajout_panels();

            this.setVisible(true);







    }

    private void ajout_panels(){
        this.add(p1);
        this.add(p2);
        this.add(p3);
    }

    private  void conteneur1(){
        p1.setBackground(Color.WHITE);
        ImageIcon login_image = new ImageIcon("assets/login_image.png");
        Image img = login_image.getImage();
        // Redimensionner l'image pour s'adapter au JPanel
        img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon login_image_resized = new ImageIcon(img);
        p1.add(new JLabel(login_image_resized), BorderLayout.CENTER);
    }

    private void conteur2(){
        p2.setBackground(Color.WHITE);
        Paneau p_champ = new Paneau(); // un sous panneau pour les champ
        p_champ.setLayout(new GridLayout(4,1,2,2));
        p_champ.setBackground(Color.WHITE);
        JLabel l_username = new JLabel("Username: ");
        l_username.setHorizontalAlignment(SwingConstants.CENTER);
        l_username.setVerticalAlignment(SwingConstants.BOTTOM);
        Dimension t_username_d = new Dimension(200,30);

        t_username.setPreferredSize(t_username_d);
        JLabel l_pwd= new JLabel("Password: ");
        l_pwd.setHorizontalAlignment(SwingConstants.CENTER);
        l_pwd.setVerticalAlignment(SwingConstants.BOTTOM);



        p_champ.add(l_username);
        p_champ.add(t_username);
        p_champ.add(l_pwd);
        p_champ.add(t_pwd);

        p2.add(p_champ,BorderLayout.CENTER);

    }
    private void conteur3(){
        p3.setBackground(Color.WHITE);


        p3.add(btn_connexion,BorderLayout.NORTH);
        p3.add(error_auth);

        // A l'action du clique sur le boutton de connexion
        btn_connexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnection connect = new DBConnection();
                //recuperation des données saisies
                Connection con=connect.getCon();
                String login= t_username.getText();
                char[] pwd = t_pwd.getPassword();
                String mot_de_pass = new String(pwd);

                Arrays.fill(pwd,' ');
                try{

                    query ="SELECT * FROM auth WHERE login=? and password=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1,login);
                    ps.setString(2,mot_de_pass);
                    int resultat = ps.executeUpdate();
                    //si la requette renvoie aumoins un resultat
                    if(resultat>0){
                        dispose();
                        Accueil a = new Accueil(); //on ouvre la page d'accueil
                    }else{ // sinon

                        error_auth.setVisible(true); // on affiche l'erreur de non correspondance des informations

                    }
                }catch(SQLException er){
                    JOptionPane.showMessageDialog(null,"Erreur" + er.getMessage());
                }
            }
        });

    }
}
