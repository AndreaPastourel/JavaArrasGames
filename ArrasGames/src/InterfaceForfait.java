import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;

public class InterfaceForfait {
    private JFrame frame;
    private JTextField txtCode;

    public InterfaceForfait() {
        // -- Appliquer le LookAndFeel Nimbus pour un rendu plus moderne
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Initialisation de la fenêtre
        frame = new JFrame("ArrasGames - Gestion des Forfaits");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Fermeture avec confirmation
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        frame,
                        "Voulez-vous vraiment quitter ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // --- PANEL PRINCIPAL ---
        JPanel pnlForfait = new JPanel(new GridBagLayout());
        pnlForfait.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Titre
        JLabel lblTitle = new JLabel("Gestion des Forfaits", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(new Color(60, 63, 65));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlForfait.add(lblTitle, gbc);

        // Champ Code
        JLabel lblCode = new JLabel("Entrez le code :");
        lblCode.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCode.setForeground(new Color(60, 63, 65));

        txtCode = new JTextField(15);
        txtCode.setToolTipText("Saisissez le code du forfait");
        txtCode.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        pnlForfait.add(lblCode, gbc);
        gbc.gridx = 1;
        pnlForfait.add(txtCode, gbc);

        // Bouton Vérifier
        JButton btnSave = new JButton("Vérifier");
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(75, 110, 175));
        btnSave.setFocusPainted(false);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(new Color(55, 90, 155));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(new Color(75, 110, 175));
            }
        });

        btnSave.addActionListener(e -> recupererForfait());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        pnlForfait.add(btnSave, gbc);

        // Ajout du panel principal à la frame
        frame.add(pnlForfait);
        frame.setVisible(true);
    }

    private void recupererForfait() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer un code.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = new Connexion().getCon()) {
            // Requête SQL pour récupérer la durée en secondes
            String query = "SELECT time FROM reservations WHERE code = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long tempsRestantSeconds = rs.getLong("time"); // Récupération du temps en secondes

                if (tempsRestantSeconds > 0) {
                    // Convertir les secondes en Duration
                    Duration tempsRestant = Duration.ofSeconds(tempsRestantSeconds);

                    // Afficher le temps restant (en minutes et secondes)
                    long minutes = tempsRestant.toMinutes();
                    long secondes = tempsRestant.minusMinutes(minutes).getSeconds();
                    JOptionPane.showMessageDialog(frame, "Temps restant : " + minutes + " minutes et " + secondes + " secondes.", "Information", JOptionPane.INFORMATION_MESSAGE);

                    // Passer à l'interface suivante (par exemple un décompte)
                    frame.dispose();
                    new InterfaceTemps(tempsRestant); // Interface suivante à implémenter
                } else {
                    JOptionPane.showMessageDialog(frame, "Temps restant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Code invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors de la récupération du forfait.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }


    }}
