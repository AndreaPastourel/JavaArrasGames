import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        // -- LookAndFeel Nimbus pour un rendu plus moderne
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            // En cas d'erreur, on reste sur le look par défaut
            ex.printStackTrace();
        }

        // Initialisation de la fenêtre
        frame = new JFrame("ArrasGames - Connexion");
        frame.setSize(400, 400);
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

        // ----- Panel principal -----
        JPanel pnlConnexion = new JPanel(new GridBagLayout());
        pnlConnexion.setBackground(Color.WHITE);  // Arrière-plan blanc
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // ----- Logo (optionnel) -----
        // Ajuste ou supprime si tu n'as pas de logo
        // Vérifie que "logo.png" se trouve bien à l'endroit où ton code peut le charger
        JLabel lblLogo;
        try {
            lblLogo = new JLabel(new ImageIcon("logo.png"));
        } catch (Exception e) {
            // Si le logo n'est pas trouvé, on place un label vide pour ne pas bloquer l'interface
            lblLogo = new JLabel("");
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlConnexion.add(lblLogo, gbc);

        // ----- Titre -----
        JLabel lblTitle = new JLabel("Connexion à ArrasGames", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(new Color(60, 63, 65));
        gbc.gridy = 1;
        pnlConnexion.add(lblTitle, gbc);

        // ----- Champ Nom d'utilisateur -----
        JLabel lblUsername = new JLabel("Nom d'utilisateur :");
        lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsername.setForeground(new Color(60, 63, 65));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        pnlConnexion.add(lblUsername, gbc);

        txtUsername = new JTextField(15);
        txtUsername.setToolTipText("Entrez votre nom d'utilisateur");
        txtUsername.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );
        gbc.gridx = 1;
        pnlConnexion.add(txtUsername, gbc);

        // ----- Champ Mot de passe -----
        JLabel lblPassword = new JLabel("Mot de passe :");
        lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPassword.setForeground(new Color(60, 63, 65));
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlConnexion.add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setToolTipText("Entrez votre mot de passe");
        txtPassword.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                )
        );
        gbc.gridx = 1;
        pnlConnexion.add(txtPassword, gbc);

        // ----- Bouton de Connexion -----
        JButton btnLogin = new JButton("Connexion");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(75, 110, 175));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Couleur au survol
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(55, 90, 155));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(75, 110, 175));
            }
        });

        // Action sur le clic
        btnLogin.addActionListener(e -> verifierConnexion());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        pnlConnexion.add(btnLogin, gbc);

        // ----- On ajoute le panel à la fenêtre -----
        frame.add(pnlConnexion);
        frame.setVisible(true);
    }

    private void verifierConnexion() {
        String username = txtUsername.getText().trim();
        char[] password = txtPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = new Connexion().getCon()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("password");

                // Remplacer $2y$ par $2a$ pour compatibilité avec jBCrypt
                if (hashedPasswordFromDB.startsWith("$2y$")) {
                    hashedPasswordFromDB = hashedPasswordFromDB.replaceFirst("\\$2y\\$", "\\$2a\\$");
                }

                // Convertit le tableau de char[] en String
                String inputPassword = String.valueOf(password);

                // Vérifiez le mot de passe avec jBCrypt
                if (BCrypt.checkpw(inputPassword, hashedPasswordFromDB)) {
                    JOptionPane.showMessageDialog(frame, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    // Lance l'interface suivante
                    new InterfaceForfait();
                } else {
                    JOptionPane.showMessageDialog(frame, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
