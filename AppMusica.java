import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppMusica extends JFrame {
    private JTextField campoTexto, campoCantor, campoExcluir;
    private JButton botaoMostrar, botaoLimpar, botaoSair, botaoAlterar, botaoInserir, botaoExcluir;
    private ArrayList<String> musicas, cantores;

    public AppMusica() {
        musicas = new ArrayList<>();
        cantores = new ArrayList<>();

        setTitle("Músicas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); 

        
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelMusica = new JLabel("Música:");
        campoTexto = new JTextField(15);
        JLabel labelCantor = new JLabel("Cantor:");
        campoCantor = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelEntrada.add(labelMusica, gbc);

        gbc.gridx = 1;
        painelEntrada.add(campoTexto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelEntrada.add(labelCantor, gbc);

        gbc.gridx = 1;
        painelEntrada.add(campoCantor, gbc);

        
        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 10, 10));
        botaoMostrar = new JButton("Mostrar");
        botaoLimpar = new JButton("Limpar");
        botaoSair = new JButton("Sair");
        botaoAlterar = new JButton("Alterar");
        botaoInserir = new JButton("Inserir");
        botaoExcluir = new JButton("Excluir");

        painelBotoes.add(botaoInserir);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoAlterar);
        painelBotoes.add(botaoMostrar);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoSair);

      
        JPanel painelExcluir = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelExcluir = new JLabel("Excluir (nº):");
        campoExcluir = new JTextField(5);

        painelExcluir.add(labelExcluir);
        painelExcluir.add(campoExcluir);

        
        add(painelEntrada, BorderLayout.NORTH);
        add(painelExcluir, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        configurarBotoes();
    }

    private void configurarBotoes() {
        botaoInserir.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String texto = campoTexto.getText();
                String cantor = campoCantor.getText();
                if (!texto.isEmpty() && !cantor.isEmpty()) {
                    musicas.add(texto);
                    cantores.add(cantor);
                    JOptionPane.showMessageDialog(AppMusica.this, "Música e cantor adicionados com sucesso!");
                    campoTexto.setText("");
                    campoCantor.setText("");
                } else if (texto.isEmpty()) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Por favor, insira uma música.");
                } else if (cantor.isEmpty()) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Por favor insira um cantor.");
                }
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String input = campoExcluir.getText();
                try {
                    int indice = Integer.parseInt(input) - 1;
                    if (indice >= 0 && indice < musicas.size()) {
                        musicas.remove(indice);
                        cantores.remove(indice);
                        JOptionPane.showMessageDialog(AppMusica.this, "Música excluída com sucesso!");
                        campoExcluir.setText("");
                    } else {
                        JOptionPane.showMessageDialog(AppMusica.this, "Número inválido.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Por favor, insira um número válido ou verifique se há músicas listadas.");
                }
            }
        });

        botaoMostrar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (musicas.isEmpty() && cantores.isEmpty()) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Nenhuma música e cantor disponível.");
                } else {
                    StringBuilder todasMusicas = new StringBuilder();
                    for (int i = 0; i < musicas.size(); i++) {
                        todasMusicas.append((i + 1)).append(". ").append(musicas.get(i)).append(" - ").append(cantores.get(i)).append("\n");
                    }
                    JOptionPane.showMessageDialog(AppMusica.this, "Músicas:\n" + todasMusicas);
                }
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                campoTexto.setText("");
                campoCantor.setText("");
            }
        });

        botaoAlterar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (musicas.isEmpty()) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Nenhuma música disponível para alterar.");
                    return;
                }

                JFrame frameAlterar = new JFrame("Alterar Música");
                frameAlterar.setSize(300, 200);
                frameAlterar.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
                frameAlterar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel labelTexto = new JLabel("Número da música:");
                JTextField campoTexto = new JTextField(5);
                JLabel labelNovaNota = new JLabel("Nova música:");
                JTextField campoNovaMusica = new JTextField(15);
                JButton botaoConfirmar = new JButton("Confirmar");
                JButton botaoCancelar = new JButton("Cancelar");

                frameAlterar.add(labelTexto);
                frameAlterar.add(campoTexto);
                frameAlterar.add(labelNovaNota);
                frameAlterar.add(campoNovaMusica);
                frameAlterar.add(botaoConfirmar);
                frameAlterar.add(botaoCancelar);

                botaoConfirmar.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent confirmarEvent) {
                        try {
                            int indice = Integer.parseInt(campoTexto.getText()) - 1;
                            if (indice >= 0 && indice < musicas.size()) {
                                String novoTexto = campoNovaMusica.getText();
                                if (!novoTexto.isEmpty()) {
                                    musicas.set(indice, novoTexto);
                                    JOptionPane.showMessageDialog(AppMusica.this, "Música alterada com sucesso!");
                                    frameAlterar.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(AppMusica.this, "A nova música não pode estar vazia.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(AppMusica.this, "Número da música inválido.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AppMusica.this, "Digite um número válido.");
                        }
                    }
                });

                botaoCancelar.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent cancelarEvent) {
                        frameAlterar.dispose();
                    }
                });

                frameAlterar.setVisible(true);
            }
        });

        botaoSair.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        AppMusica app = new AppMusica();
        app.setVisible(true);
    }
}
