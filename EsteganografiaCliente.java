/*	
 *  @author Lucas Felipe C. Nincao - Ciência da Computação - UEL 2010
 */

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EsteganografiaCliente extends JFrame implements ActionListener, KeyListener {
    public static final String VERSION = "0.88";
    private final int OFFSET = Esteganografia.OFFSET;
    private final int height = 350; // altura da tela
    private final int width = 520; // largura da tela
    private boolean isEmbutivel; // verifica a capacidade do arquivo
    public boolean acao; // true -> codificação, false -> decodificação
    public String mensagem;
    private JPanel painel, painelInf;
    private JLabel ArqEntrada, ArqSaida, lblMensagem, lblStatus, lblFiller1, lblFiller2, TamEntrada;
    public JTextArea txtMensagem;
    private JTextField txtArqEntrada, txtArqSaida;
    private JScrollPane scrollPane;
    private JButton btnEmbutir, btnRecuperar, btnSair, btnEntrada, btnSaida, btnSobre, btnAjuda, btnEmbutirArq;;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JFileChooser escolheArq;
    private BackEndHandler backend;
    private long inputTamArquivo;

    public EsteganografiaCliente() {
        super("Esteganografia - Lucas Nincao/2010");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        escolheArq = new JFileChooser("./");
        inputTamArquivo = -1;
        isEmbutivel = false;

        ArqEntrada = new JLabel("Entrada");
        ArqSaida = new JLabel("Saída");
        lblMensagem = new JLabel("Mensagem");
        lblStatus = new JLabel("Pronto");
        TamEntrada = new JLabel();
        lblFiller1 = new JLabel(" ");
        lblFiller2 = new JLabel(" ");

        txtArqEntrada = new JTextField(20);
        txtArqSaida = new JTextField(20);
        txtMensagem = new JTextArea(6, 24);
        txtMensagem.addKeyListener(this);
        scrollPane = new JScrollPane(txtMensagem);
        txtMensagem.setCaretColor(Color.blue);

        btnEmbutir = new JButton("Embutir");
        btnRecuperar = new JButton("Recuperar");
        btnSair = new JButton("Fechar");
        btnEntrada = new JButton("...");
        btnSaida = new JButton("...");
        btnSobre = new JButton("Sobre");
        btnEmbutirArq = new JButton("Embutir arquivo");
        btnAjuda = new JButton("Ajuda");

        btnEmbutir.addActionListener(this);
        btnRecuperar.addActionListener(this);
        btnSair.addActionListener(this);
        btnEntrada.addActionListener(this);
        btnSaida.addActionListener(this);
        btnSobre.addActionListener(this);
        btnAjuda.addActionListener(this);
        btnEmbutirArq.addActionListener(this);

        // Definindo parâmetros de cor
        getContentPane().setBackground(Color.black);
        ArqEntrada.setForeground(Color.black);
        ArqSaida.setForeground(Color.black);
        lblMensagem.setForeground(Color.black);
        lblStatus.setForeground(Color.blue);
        TamEntrada.setForeground(Color.black);
        btnAjuda.setBackground(Color.white);
        btnAjuda.setForeground(Color.black);
        btnEmbutirArq.setBackground(Color.white);
        btnEmbutirArq.setForeground(Color.blue);
        btnEmbutir.setBackground(Color.white);
        btnEmbutir.setForeground(Color.black);
        btnRecuperar.setBackground(Color.white);
        btnRecuperar.setForeground(Color.black);
        btnSobre.setBackground(Color.white);
        btnSobre.setForeground(Color.black);
        btnEntrada.setBackground(Color.white);
        btnEntrada.setForeground(Color.blue);
        btnSaida.setBackground(Color.white);
        btnSaida.setForeground(Color.blue);
        btnSair.setBackground(Color.white);
        btnSair.setForeground(Color.red);
        txtArqEntrada.setBackground(Color.white);
        txtArqEntrada.setForeground(Color.blue);
        txtArqSaida.setBackground(Color.white);
        txtArqSaida.setForeground(Color.blue);
        txtArqEntrada.setCaretColor(Color.blue);
        txtArqSaida.setCaretColor(Color.blue);
        txtMensagem.setBackground(Color.white);
        txtMensagem.setForeground(Color.blue);

        painel = new JPanel();
        painel.setBackground(Color.white);

        //configurando o layout do aplicativo
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        painel.setLayout(gbl);

        gbc.weighty = 1;

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbl.setConstraints(lblFiller1, gbc);
        painel.add(lblFiller1);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbl.setConstraints(ArqEntrada, gbc);
        painel.add(ArqEntrada);

        gbc.gridx = 2;
        gbl.setConstraints(txtArqEntrada, gbc);
        painel.add(txtArqEntrada);

        gbc.gridx = 3;
        gbl.setConstraints(btnEntrada, gbc);
        painel.add(btnEntrada);

        gbc.gridx = 4;
        gbl.setConstraints(TamEntrada, gbc);
        painel.add(TamEntrada);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbl.setConstraints(ArqSaida, gbc);
        painel.add(ArqSaida);

        gbc.gridx = 2;
        gbl.setConstraints(txtArqSaida, gbc);
        painel.add(txtArqSaida);

        gbc.gridx = 3;
        gbl.setConstraints(btnSaida, gbc);
        painel.add(btnSaida);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbl.setConstraints(lblMensagem, gbc);
        painel.add(lblMensagem);

        gbc.gridx = 2;
        gbc.fill = 1;
        gbc.gridwidth = 2;
        gbl.setConstraints(scrollPane, gbc);
        painel.add(scrollPane);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = 0;
        gbc.gridwidth = 1;
        gbl.setConstraints(btnEmbutir, gbc);
        painel.add(btnEmbutir);

        gbc.gridx = 2;
        gbl.setConstraints(btnRecuperar, gbc);
        painel.add(btnRecuperar);

        gbc.gridx = 3;
        gbl.setConstraints(btnEmbutirArq, gbc);
        painel.add(btnEmbutirArq);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbl.setConstraints(lblFiller2, gbc);
        painel.add(lblFiller2);

        gbc.fill = gbc.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.anchor = gbc.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbl.setConstraints(lblStatus, gbc);
        painel.add(lblStatus);

        gbc.fill = 0;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbl.setConstraints(btnAjuda, gbc);
        painel.add(btnAjuda);

        gbc.gridx = 3;
        gbl.setConstraints(btnSobre, gbc);
        painel.add(btnSobre);

        getContentPane().add(painel);
        setSize(width, height);
        setResizable(false);

        acao = true;

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - width) / 2, (d.height - height) / 2);
        setVisible(true);
    }

    public static void main(String args[]) {
        new Autenticar();
    }

    public void keyPressed(KeyEvent k) {}
    public void keyReleased(KeyEvent k) {}
    public void keyTyped(KeyEvent k) {
        mensagem = txtMensagem.getText();
        int size = mensagem.length();

        if (size >= 32766) {
            JOptionPane.showMessageDialog(this, "Esse é o tamanho máximo possível da mensagem!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            txtMensagem.setText(mensagem.substring(0, 32765));
        }

        updateEmbutibilidade();
        return;
    }

    //verifica e indica se a mensagem poderá ser embutida para dentro do arquivo escolhido
    private void updateEmbutibilidade() {
        mensagem = txtMensagem.getText();
        int tamMensagem = mensagem.length();
        lblStatus.setText("Tamanho mínimo do arquivo de entrada: " + (tamMensagem * 4 + 20 + OFFSET) + " bytes (" + ((tamMensagem * 4 + 20 + OFFSET) / 1024 + 1) + " KB)");

        if (txtArqEntrada.getText().length() > 0 && tamMensagem > 0) {
            if (inputTamArquivo - OFFSET > tamMensagem * 4)
                isEmbutivel = true;
            else
                isEmbutivel = false;
        }
    }

    //controle de eventos dos botões
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // Botão pesquisa de arquivo de entrada foi pressionado
        if (obj == btnEntrada) {
            if (escolheArq.showOpenDialog(this) == escolheArq.APPROVE_OPTION) {
                String path = escolheArq.getSelectedFile().getPath();
                txtArqEntrada.setText(path);
                inputTamArquivo = new File(path).length();
                TamEntrada.setText("" + inputTamArquivo + " bytes");
                updateEmbutibilidade();
            }
        }

        // Botão pesquisa de arquivo de saída foi pressionado
        if (obj == btnSaida) {
            if (escolheArq.showSaveDialog(this) == escolheArq.APPROVE_OPTION)
                txtArqSaida.setText(escolheArq.getSelectedFile().getPath());
        }

        // Botão embutir foi pressionado
        if (obj == btnEmbutir) {
            acao = true;
            if (validarEntrada()) {
                operacaoIniciada("Codificando... Aguarde...");
                backend = new BackEndHandler(txtMensagem.getText(), txtArqEntrada.getText(), null, txtArqSaida.getText(), this);
                backend.start();
            } else return;
        }

        // Botão recuperar foi pressionado
        if (obj == btnRecuperar) {
            acao = false;
            if (validarEntrada()) {
                operacaoIniciada("Decodificando... Aguarde...");
                backend = new BackEndHandler(txtMensagem.getText(), txtArqEntrada.getText(), null, txtArqSaida.getText(), this);
                backend.start();
            } else return;
        }

        // Botão sobre foi pressionado
        if (obj == btnSobre) {
            AboutFrame about = new AboutFrame();
        }

        // Botão ajuda foi pressionado
        if (obj == btnAjuda) {
            try {
                Runtime run = Runtime.getRuntime();
                run.exec("notepad help.txt");
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(this, "Não pôde abrir arquivo de ajuda\n" + ee, "Operação fracassou", JOptionPane.ERROR_MESSAGE);
                System.out.println("Erro: " + ee);
            }
        }

        if (obj == btnEmbutirArq) {
            new EmbutirArquivo();
        }

        if (obj == btnSair)
            System.exit(0);
    }

    //Executa a validação dos dados de entrada
    private boolean validarEntrada() {
        if (txtArqEntrada.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Escolha um arquivo de entrada!", "Requer arquivo de entrada", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (acao) // true -> operarão de codificação
        {
            if (!isEmbutivel) //verifica se a mensagem cabe no arquivo de entrada
            {
                JOptionPane.showMessageDialog(this, "Mensagem grande demais para ser embutida no arquivo.\nEscolha um maior arquivo de entrada.", "Não embutível", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (txtArqSaida.getText().length() <= 0) //caso não tenha inserido local de saída
            {
                JOptionPane.showMessageDialog(this, "Escolha o arquivo de saída!", "Requer arquivo de saída", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (txtMensagem.getText().length() <= 0) //caso a mensagem esteja vazia
            {
                JOptionPane.showMessageDialog(this, "Insira a mensagem!", "Mensagem vazia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        return true;
    }

    //atribuição booleana aos botões, indicando início e término de execução
    private void operacaoIniciada(String status) {
        btnEntrada.setEnabled(false);
        btnSaida.setEnabled(false);
        btnEmbutir.setEnabled(false);
        btnRecuperar.setEnabled(false);
        btnSair.setEnabled(false);
        lblStatus.setText(status);
    }

    public void operacaoConcluida(String status) {
        btnEntrada.setEnabled(true);
        btnSaida.setEnabled(true);
        btnEmbutir.setEnabled(true);
        btnRecuperar.setEnabled(true);
        btnSair.setEnabled(true);
        lblStatus.setText(status);
    }
}


// classe  pra identificação do usuário
class Autenticar extends JFrame implements ActionListener {
    private int width = 300, height = 150;
    private JPanel painel;
    private JLabel label, filler;
    private JPasswordField password;
    private JButton btnAutenticar, btnAjuda;
    private int tentativas = 3; // número de chances de acertar a senha será 3

    public Autenticar() {
        super("Esteganografia - Lucas Nincao/2010");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label = new JLabel("Insira a senha de acesso");
        filler = new JLabel("                                                                    ");
        password = new JPasswordField(22);
        btnAutenticar = new JButton("Autenticar");
        btnAutenticar.setDefaultCapable(true);
        btnAjuda = new JButton("Ajuda");

        btnAjuda.setToolTipText("Conferir ajuda");

        password.addActionListener(this);
        btnAutenticar.addActionListener(this);
        btnAjuda.addActionListener(this);

        painel = (JPanel) getContentPane();
        painel.setLayout(new FlowLayout());

        // define os parâmetros de cores
        painel.setBackground(Color.white);
        label.setForeground(Color.black);
        password.setBackground(Color.white);
        password.setForeground(Color.blue);
        password.setEchoChar('#');
        password.setCaretColor(Color.orange);
        btnAutenticar.setBackground(Color.white);
        btnAjuda.setBackground(Color.white);
        btnAutenticar.setForeground(Color.blue);
        btnAjuda.setForeground(Color.red);

        painel.add(label);
        painel.add(password);
        painel.add(filler);
        painel.add(btnAutenticar);
        painel.add(btnAjuda);

        setSize(295, 140);
        setResizable(false);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - width) / 2, (d.height - height) / 2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAutenticar || e.getSource() == password) {
            if (new String(password.getPassword()).equals("uel2010")) //define a senha de acesso ao software
            {
                setVisible(false);
                new EsteganografiaCliente();
            } else {
                String tent = String.format("    Senha inválida.\n    Tentativas restantes: %d", tentativas - 1);
                String ciao = String.format("      Ciao, amico mio. >:-]");
                tentativas--;
                if (tentativas > 0) {
                    JOptionPane.showMessageDialog(this, tent, "Acesso negado", JOptionPane.WARNING_MESSAGE);
                }
                if (tentativas == 0) // tentativas esgotadas -> fecha o sistema
                {
                    JOptionPane.showMessageDialog(this, ciao, "Andare con Dio", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else return;
            }
        } else {
            try {
                AboutFrame about = new AboutFrame();
            } catch (Exception ex) {}
        }
    }
}