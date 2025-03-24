import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        // --- On peut désactiver le look & feel Nimbus pour tout gérer à la main ---
        // try {
        //     UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (Exception ex) {
        //     ex.printStackTrace();
        // }

        // Fenêtre principale
        frame = new JFrame("ArrasGames - Connexion");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Confirmation à la fermeture
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

        // ----- Panel principal avec dégradé en arrière-plan -----
        JPanel pnlBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();

                // Dégradé vertical du haut vers le bas
                Color startColor = new Color(25, 27, 44); // Couleur sombre
                Color endColor   = new Color(32, 20, 51); // Couleur encore plus sombre / violacée

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
        Color textColor   = Color.WHITE;
        Color accentColor = new Color(255, 64, 129); // Rose flashy
        Color fieldBg     = new Color(60, 63, 65);   // Gris foncé pour les champs

        // ----- Logo (optionnel) -----
        JLabel lblLogo;
        try {
            // Mettez votre chemin d'image ici si vous avez un logo
            lblLogo = new JLabel(new ImageIcon("logo.png"));
        } catch (Exception e) {
            lblLogo = new JLabel("");
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlBackground.add(lblLogo, gbc);

        // ----- Titre -----
        JLabel lblTitle = new JLabel("Connexion à ArrasGames", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(textColor);
        gbc.gridy = 1;
        pnlBackground.add(lblTitle, gbc);

        // ----- Label Nom d'utilisateur -----
        JLabel lblUsername = new JLabel("Nom d'utilisateur :");
        lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsername.setForeground(textColor);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        pnlBackground.add(lblUsername, gbc);

        // ----- Champ Nom d'utilisateur -----
        txtUsername = new JTextField(15);
        txtUsername.setToolTipText("Entrez votre nom d'utilisateur");
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setBackground(fieldBg);
        txtUsername.setCaretColor(Color.WHITE);
        txtUsername.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );
        gbc.gridx = 1;
        pnlBackground.add(txtUsername, gbc);

        // ----- Label Mot de passe -----
        JLabel lblPassword = new JLabel("Mot de passe :");
        lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPassword.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlBackground.add(lblPassword, gbc);

        // ----- Champ Mot de passe -----
        txtPassword = new JPasswordField(15);
        txtPassword.setToolTipText("Entrez votre mot de passe");
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setBackground(fieldBg);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );
        gbc.gridx = 1;
        pnlBackground.add(txtPassword, gbc);

        // ----- Bouton de Connexion -----
        JButton btnLogin = new JButton("Connexion");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(accentColor);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Effet au survol
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(accentColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(accentColor);
            }
        });

        // Action du bouton
        btnLogin.addActionListener(e -> verifierConnexion());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        pnlBackground.add(btnLogin, gbc);

        // ----- Ajout du panel à la fenêtre -----
        frame.add(pnlBackground);
        frame.setVisible(true);
    }

    private void verifierConnexion() {
        String username = txtUsername.getText().trim();
        char[] password = txtPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(frame,
                    "Veuillez remplir tous les champs.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try (Connection conn = new Connexion().getCon()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password");

                // Compatibilité avec jBCrypt
                if (hashedPasswordFromDB.startsWith("$2y$")) {
                    hashedPasswordFromDB =
                            hashedPasswordFromDB.replaceFirst("\\$2y\\$", "\\$2a\\$");
                }

                String inputPassword = String.valueOf(password);

                if (BCrypt.checkpw(inputPassword, hashedPasswordFromDB)) {
                    JOptionPane.showMessageDialog(frame,
                            "Connexion réussie !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.dispose();
                    // Lance l'interface suivante
                    new InterfaceForfait();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Nom d'utilisateur ou mot de passe incorrect.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Nom d'utilisateur ou mot de passe incorrect.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Erreur de connexion à la base de données.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }
}