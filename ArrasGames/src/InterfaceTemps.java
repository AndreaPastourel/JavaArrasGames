import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class InterfaceTemps {
    private JFrame frame;

    public InterfaceTemps(Duration tempsRestant) {
        long tempsMillis = tempsRestant.toMillis();
        System.out.println(tempsRestant);
        System.out.println(tempsMillis);

        // Initialisation de la fenêtre
        frame = new JFrame("ArrasGames - Gestion des Forfaits");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Fermeture avec confirmation
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame,
                        "Voulez-vous vraiment quitter ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Panel personnalisé avec dégradé sombre
        JPanel pnlBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                // Dégradé vertical du haut vers le bas
                Color startColor = new Color(25, 27, 44);
                Color endColor = new Color(32, 20, 51);
                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
            }
        };
        pnlBackground.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Étiquette pour afficher le décompte
        JLabel lblTemps = new JLabel(formatTime(tempsMillis), SwingConstants.CENTER);
        lblTemps.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTemps.setForeground(Color.WHITE); // Texte en blanc pour un bon contraste

        // Ajout de l'étiquette au panel
        pnlBackground.add(lblTemps, gbc);
        frame.add(pnlBackground, BorderLayout.CENTER);

        // Affichage de la fenêtre
        frame.setVisible(true);

        // Lancer le décompte
        startCountdown(lblTemps, tempsMillis);
    }

    /**
     * Lance un décompte visuel en mettant à jour le JLabel chaque seconde
     *
     * @param label           JLabel pour afficher le temps restant
     * @param remainingMillis Temps restant en millisecondes
     */
    private void startCountdown(JLabel label, long remainingMillis) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            long timeLeft = remainingMillis;

            @Override
            public void run() {
                if (timeLeft <= 0) {
                    timer.cancel();
                    label.setText("Terminé !");
                } else {
                    label.setText(formatTime(timeLeft));
                    timeLeft -= 1000;
                }
            }
        }, 0, 1000); // Mise à jour toutes les secondes
    }

    /**
     * Formate le temps en millisecondes sous le format HH:MM:SS
     *
     * @param millis Temps en millisecondes
     * @return Chaîne formatée
     */
    private String formatTime(long millis) {
        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis / (1000 * 60)) % 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}