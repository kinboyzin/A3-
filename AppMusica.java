import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AppMusica extends JFrame {
    private JTextField campoTexto, campoCantor, campoExcluir;
    private JButton botaoMostrar, botaoLimpar, botaoSair, botaoAlterar, botaoInserir, botaoExcluir;
    private CRUDDB cruddb;

    public AppMusica() {
        cruddb = new CRUDDB();

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
                String musica = campoTexto.getText();
                String cantor = campoCantor.getText();
                if (!musica.isEmpty() && !cantor.isEmpty()) {
                    try {
                        cruddb.inserirMusica(musica, cantor);
                        JOptionPane.showMessageDialog(AppMusica.this, "Música e cantor adicionados ao banco de dados!");
                        campoTexto.setText("");
                        campoCantor.setText("");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(AppMusica.this, "Erro ao inserir no banco: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(AppMusica.this, "Preencha todos os campos.");
                }
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(campoExcluir.getText());
                    if (cruddb.excluirMusica(id)) {
                        JOptionPane.showMessageDialog(AppMusica.this, "Música excluída com sucesso!");
                        campoExcluir.setText("");
                    } else {
                        JOptionPane.showMessageDialog(AppMusica.this, "Música não encontrada.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Insira um número válido.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Erro ao excluir do banco: " + ex.getMessage());
                }
            }
        });

        botaoMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> musicas = cruddb.listarMusicas();
                    if (musicas.isEmpty()) {
                        JOptionPane.showMessageDialog(AppMusica.this, "Nenhuma música no banco de dados.");
                    } else {
                        StringBuilder todasMusicas = new StringBuilder();
                        for (String musica : musicas) {
                            todasMusicas.append(musica).append("\n");
                        }
                        JOptionPane.showMessageDialog(AppMusica.this, "Músicas:\n" + todasMusicas);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AppMusica.this, "Erro ao consultar o banco: " + ex.getMessage());
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
                JFrame frameAlterar = new JFrame("Alterar Música");
                frameAlterar.setSize(300, 200);
                frameAlterar.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
                frameAlterar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel labelTexto = new JLabel("Número da música:");
                JTextField campoTexto = new JTextField(5);
                JLabel labelNovaMusica = new JLabel("Nova música:");
                JTextField campoNovaMusica = new JTextField(15);
                JLabel labelNovoCantor = new JLabel("Novo cantor:");
                JTextField campoNovoCantor = new JTextField(15);
                JButton botaoConfirmar = new JButton("Confirmar");
                JButton botaoCancelar = new JButton("Cancelar");

                frameAlterar.add(labelTexto);
                frameAlterar.add(campoTexto);
                frameAlterar.add(labelNovaMusica);
                frameAlterar.add(campoNovaMusica);
                frameAlterar.add(labelNovoCantor);
                frameAlterar.add(campoNovoCantor);
                frameAlterar.add(botaoConfirmar);
                frameAlterar.add(botaoCancelar);

                botaoConfirmar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent confirmarEvent) {
                        try {
                            int id = Integer.parseInt(campoTexto.getText());
                            String novaMusica = campoNovaMusica.getText();
                            String novoCantor = campoNovoCantor.getText();
                            if (!novaMusica.isEmpty() && !novoCantor.isEmpty()) {
                                if (cruddb.alterarMusica(id, novaMusica, novoCantor)) {
                                    JOptionPane.showMessageDialog(AppMusica.this, "Música alterada com sucesso!");
                                    frameAlterar.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(AppMusica.this, "Música não encontrada.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(AppMusica.this, "Preencha todos os campos.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AppMusica.this, "Digite um número válido.");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(AppMusica.this, "Erro ao alterar o banco: " + ex.getMessage());
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
