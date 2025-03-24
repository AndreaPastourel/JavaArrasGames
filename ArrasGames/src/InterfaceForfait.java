import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.Duration;

public class InterfaceForfait {
    private JFrame frame;
    private JTextField txtCode;

    public InterfaceForfait() {
        // Optionnel : désactiver le LookAndFeel Nimbus pour gérer le style personnalisé
        // try {
        //     UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (Exception ex) {
        //     ex.printStackTrace();
        // }

        frame = new JFrame("ArrasGames - Gestion des Forfaits");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

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

        // --- Panel principal avec dégradé en arrière-plan ---
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
        gbc.insets = new Insets(10, 20, 10, 20);

        // Palette de couleurs
        Color textColor = Color.WHITE;
        Color accentColor = new Color(255, 64, 129); // Rose flashy
        Color fieldBg = new Color(60, 63, 65);       // Gris foncé

        // --- Titre ---
        JLabel lblTitle = new JLabel("Gestion des Forfaits", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlBackground.add(lblTitle, gbc);

        // --- Champ Code ---
        JLabel lblCode = new JLabel("Entrez le code :");
        lblCode.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCode.setForeground(textColor);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        pnlBackground.add(lblCode, gbc);

        txtCode = new JTextField(15);
        txtCode.setToolTipText("Saisissez le code du forfait");
        txtCode.setForeground(textColor);
        txtCode.setBackground(fieldBg);
        txtCode.setCaretColor(textColor);
        txtCode.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        pnlBackground.add(txtCode, gbc);

        // --- Bouton Vérifier ---
        JButton btnSave = new JButton("Vérifier");
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(accentColor);
        btnSave.setFocusPainted(false);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnSave.setBackground(accentColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btnSave.setBackground(accentColor);
            }
        });

        btnSave.addActionListener(e -> recupererForfait());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        pnlBackground.add(btnSave, gbc);

        frame.add(pnlBackground);
        frame.setVisible(true);
    }

    private void recupererForfait() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer un code.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = new Connexion().getCon()) {
            // Requête SQL pour récupérer le temps et le statut d'utilisation du forfait
            String query = "SELECT time, status FROM reservations WHERE code = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Vérification si le forfait a déjà été utilisé
                String usedStatus = rs.getString("status");
                if (usedStatus != null && (usedStatus.equalsIgnoreCase("used") || usedStatus.equalsIgnoreCase("expired"))) {
                    JOptionPane.showMessageDialog(frame, "Ce forfait a déjà été utilisé ou a expirée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long tempsRestantSeconds = rs.getLong("time");
                if (tempsRestantSeconds > 0) {
                    // Conversion du temps restant en Duration
                    Duration tempsRestant = Duration.ofSeconds(tempsRestantSeconds);
                    long minutes = tempsRestant.toMinutes();
                    long secondes = tempsRestant.minusMinutes(minutes).getSeconds();
                    JOptionPane.showMessageDialog(frame,
                            "Temps restant : " + minutes + " minutes et " + secondes + " secondes.",
                            "Information", JOptionPane.INFORMATION_MESSAGE);

                    // Mettre à jour la base de données pour marquer le forfait comme utilisé
                    String updateQuery = "UPDATE reservations SET status = 'used' WHERE code = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setString(1, code);
                    updateStmt.executeUpdate();

                    // Passage à l'interface suivante (exemple avec InterfaceTemps, à implémenter)
                    frame.dispose();
                    new InterfaceTemps(tempsRestant);
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
    }
}