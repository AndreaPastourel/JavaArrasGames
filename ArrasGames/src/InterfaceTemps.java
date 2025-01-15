import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class InterfaceTemps {
    private JFrame frame;

    public InterfaceTemps(Duration tempsRestant) {
        long tempsMillis=tempsRestant.toMillis();
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
                int option = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Création du panel avec GridBagLayout
        JPanel pnlTemps = new JPanel(new GridBagLayout());
        pnlTemps.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Étiquette pour afficher le décompte
        JLabel lblTemps = new JLabel(formatTime(tempsMillis), SwingConstants.CENTER);
        lblTemps.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTemps.setForeground(Color.BLUE);

        // Ajout de l'étiquette au panel
        pnlTemps.add(lblTemps, gbc);
        frame.add(pnlTemps, BorderLayout.CENTER);

        // Affichage de la fenêtre
        frame.setVisible(true);

        // Lancer le décompte
        startCountdown(lblTemps, tempsMillis);
    }

    /**
     * Méthode pour lancer un décompte visuel
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
                    // Mettre à jour le JLabel avec le temps restant
                    label.setText(formatTime(timeLeft));

                    // Réduire le temps restant d'une seconde
                    timeLeft -= 1000;
                }
            }
        }, 0, 1000); // Mise à jour toutes les secondes
    }

    /**
     * Formate le temps en millisecondes en une chaîne au format HH:MM:SS
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