import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    private String title, description; //Le titre et la description du conteneur
    private ImageIcon icone; //l'icone de la catégorie

    private JButton btn; // le boutton d'action sur la catégory

    public Card(String titre, String desc, ImageIcon icone, JButton btn) {

        JLabel title = new JLabel(titre);
        title.setFont(new Font("Arial Black",Font.BOLD,25));
        JLabel description = new JLabel(desc);
        this.btn = btn;
        this.btn.setText("Ouvrir");
        this.icone=icone;
        Image img = icone.getImage();
        // Redimensionner l'image pour s'adapter au JPanel
        img = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icone_resized = new ImageIcon(img);
        this.add(new JLabel(icone_resized), BorderLayout.CENTER);
        this.add(title);
        this.add(description);
        this.add(btn);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageIcon getIcone() {
        return icone;
    }

    public void setIcone(ImageIcon icone) {
        this.icone = icone;
    }

    public JButton getBtn() {
        return btn;
    }

    public void setBtn(JButton btn) {
        this.btn = btn;
    }
}



