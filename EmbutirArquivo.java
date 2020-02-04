/*
 *  @author Lucas Felipe C. Nincao - Ciência da Computação - UEL 2010
 */
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class EmbutirArquivo extends JFrame implements ActionListener {
    public static final String VERSION = "0.8";
    private final int OFFSET = Esteganografia.OFFSET;
    private final int width = 520; //440;
    private final int height = 350;
    private JPanel panel, panelBottom;
    private JLabel lblInFile, lblDataFile, lblOutFile, lblFiller1, lblFiller2;
    private JLabel lblSizeIn, lblSizeData, lblStatus, lblFiller3;
    private JTextField txtInFile, txtDataFile, txtOutFile;
    private JButton btnEmbed, btnRetrieve, btnBrowseIn, btnBrowseData, btnBrowseOut;
    private JButton btnExit, btnAbout;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JFileChooser fileChooser;

    private BackEndHandler backend;
    private File inFile, ArqData;
    private boolean inFileExists, dataFileExists;
    private long requiredFileSize;

    public boolean action; // True indica operação de codificação; false indica decoficação

    public EmbutirArquivo() {
        super("Esteganografia - Lucas Nincao/2010");
        action = true; // codifica
        inFileExists = false;
        dataFileExists = false;
        fileChooser = new JFileChooser("./");

        lblInFile = new JLabel("Entrada"); // arquivo usado pra embutir a mensagem
        lblOutFile = new JLabel("Saída"); // arquivo de saída, contendo o arquivo de dados embutido
        lblDataFile = new JLabel("Arq a Embutir"); // arquivo a ser embutido
        lblStatus = new JLabel("Pronto...");
        lblSizeIn = new JLabel();
        lblSizeData = new JLabel();
        lblFiller1 = new JLabel(" ");
        lblFiller2 = new JLabel(" ");
        lblFiller3 = new JLabel(" ");

        txtInFile = new JTextField(20);
        txtDataFile = new JTextField(20);
        txtOutFile = new JTextField(20);

        btnEmbed = new JButton("Embutir");
        btnRetrieve = new JButton("Recuperar");
        btnExit = new JButton("Fechar");
        btnBrowseIn = new JButton("...");
        btnBrowseData = new JButton("...");
        btnBrowseOut = new JButton("...");
        btnAbout = new JButton("Sobre");

        btnEmbed.addActionListener(this);
        btnRetrieve.addActionListener(this);
        btnExit.addActionListener(this);
        btnBrowseIn.addActionListener(this);
        btnBrowseData.addActionListener(this);
        btnBrowseOut.addActionListener(this);
        btnAbout.addActionListener(this);

        // define parâmetros de cores
        getContentPane().setBackground(Color.white);
        lblInFile.setForeground(Color.black);
        lblOutFile.setForeground(Color.black);
        lblDataFile.setForeground(Color.black);
        lblStatus.setForeground(Color.blue);
        lblSizeIn.setForeground(Color.black);
        lblSizeData.setForeground(Color.black);
        btnEmbed.setBackground(Color.white);
        btnEmbed.setForeground(Color.black);
        btnRetrieve.setBackground(Color.white);
        btnRetrieve.setForeground(Color.black);
        btnAbout.setBackground(Color.white);
        btnAbout.setForeground(Color.black);
        btnBrowseIn.setBackground(Color.white);
        btnBrowseIn.setForeground(Color.blue);
        btnBrowseData.setBackground(Color.white);
        btnBrowseData.setForeground(Color.blue);
        btnBrowseOut.setBackground(Color.white);
        btnBrowseOut.setForeground(Color.blue);
        btnExit.setBackground(Color.white);
        btnExit.setForeground(Color.black);
        txtInFile.setBackground(Color.white);
        txtInFile.setForeground(Color.black);
        txtDataFile.setBackground(Color.white);
        txtDataFile.setForeground(Color.black);
        txtOutFile.setBackground(Color.white);
        txtOutFile.setForeground(Color.black);
        txtInFile.setCaretColor(Color.black);
        txtOutFile.setCaretColor(Color.black);
        txtDataFile.setCaretColor(Color.black);

        panel = new JPanel();
        panel.setBackground(Color.white);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        panel.setLayout(gbl);

        gbc.weighty = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbl.setConstraints(lblFiller1, gbc);
        panel.add(lblFiller1);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbl.setConstraints(lblInFile, gbc);
        panel.add(lblInFile);

        gbc.gridx = 2;
        gbl.setConstraints(txtInFile, gbc);
        panel.add(txtInFile);

        gbc.gridx = 3;
        gbl.setConstraints(btnBrowseIn, gbc);
        panel.add(btnBrowseIn);

        gbc.gridx = 4;
        gbl.setConstraints(lblSizeIn, gbc);
        panel.add(lblSizeIn);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbl.setConstraints(lblDataFile, gbc);
        panel.add(lblDataFile);

        gbc.gridx = 2;
        gbl.setConstraints(txtDataFile, gbc);
        panel.add(txtDataFile);

        gbc.gridx = 3;
        gbl.setConstraints(btnBrowseData, gbc);
        panel.add(btnBrowseData);

        gbc.gridx = 4;
        gbl.setConstraints(lblSizeData, gbc);
        panel.add(lblSizeData);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbl.setConstraints(lblOutFile, gbc);
        panel.add(lblOutFile);

        gbc.gridx = 2;
        gbl.setConstraints(txtOutFile, gbc);
        panel.add(txtOutFile);

        gbc.gridx = 3;
        gbl.setConstraints(btnBrowseOut, gbc);
        panel.add(btnBrowseOut);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbl.setConstraints(lblFiller3, gbc);
        panel.add(lblFiller3);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = 0;
        gbc.gridwidth = 1;
        gbl.setConstraints(btnEmbed, gbc);
        panel.add(btnEmbed);

        gbc.gridx = 2;
        gbl.setConstraints(btnRetrieve, gbc);
        panel.add(btnRetrieve);

        gbc.gridx = 3;
        gbl.setConstraints(btnAbout, gbc);
        panel.add(btnAbout);

        gbc.gridx = 2;
        gbc.gridy = 7;
        gbl.setConstraints(lblFiller2, gbc);
        panel.add(lblFiller2);

        gbc.fill = gbc.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.anchor = gbc.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbl.setConstraints(lblStatus, gbc);
        panel.add(lblStatus);

        getContentPane().add(panel);
        setSize(width, height);
        setResizable(false);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - width) / 2, (d.height - height) / 2);
        setVisible(true);
    }

    private boolean validaEntrada() {
        if (txtInFile.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this,
                "Escolha o arquivo de entrada.",
                "Arquivo de entrada requerido", JOptionPane
                .WARNING_MESSAGE);
            return false;
        }

        if (action) // codifica
        {
            if (txtOutFile.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Escolha um nome para o arquivo de saída.",
                    "Arquivo de saída requerido", JOptionPane
                    .WARNING_MESSAGE);
                return false;
            }
            if (txtDataFile.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Escolha o arquivo a ser embutido.",
                    "Arquivo requerido", JOptionPane
                    .WARNING_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private boolean updateEmbedability() {
        if (dataFileExists) {
            lblSizeData.setText("" + ArqData.length() / 1024 +
                " KB");
            requiredFileSize = (ArqData.length() + ArqData
                .getName().length()) * 4 + OFFSET + 4 + 16;
            lblStatus.setText(
                "Tamanho mínimo do arquivo de entrada: " +
                requiredFileSize + " bytes (" +
                requiredFileSize / 1024 + " KB)");
        }


        if (dataFileExists && inFileExists) {
            lblSizeIn.setText("" + inFile.length() / 1024 +
            " KB");
            if (inFile.length() > requiredFileSize)
                return true;
        } else
            return false;

        return false;
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // botão de busca pressionado
        if (obj == btnBrowseIn) {
            if (fileChooser.showOpenDialog(this) == fileChooser
                .APPROVE_OPTION) {
                inFile = fileChooser.getSelectedFile();
                txtInFile.setText(inFile.getPath());
                if (!inFile.exists()) {
                    JOptionPane.showMessageDialog(this,
                        "Arquivo de entrada '" + inFile
                        .getName() +
                        "' inexistente.",
                        "Arquivo não existe", JOptionPane
                        .WARNING_MESSAGE);
                    inFileExists = false;
                } else {
                    inFileExists = true;
                    updateEmbedability();
                }
            }
        }

        // botão de busca pressionado
        if (obj == btnBrowseData) {
            if (fileChooser.showSaveDialog(this) == fileChooser
                .APPROVE_OPTION) {
                ArqData = fileChooser.getSelectedFile();
                txtDataFile.setText(ArqData.getPath());
                if (!ArqData.exists()) {
                    JOptionPane.showMessageDialog(this,
                        "Arquivo '" + ArqData.getName() +
                        "' inexistente.",
                        "Arquivo não existe", JOptionPane
                        .WARNING_MESSAGE);
                    dataFileExists = false;
                } else {
                    dataFileExists = true;
                    updateEmbedability();
                }
            }
        }

        // botão de busca pressionado
        if (obj == btnBrowseOut) {
            if (fileChooser.showSaveDialog(this) == fileChooser
                .APPROVE_OPTION)
                txtOutFile.setText(fileChooser.getSelectedFile()
                    .getPath());
        }

        // botão embutir pressionado
        if (obj == btnEmbed) {
            action = true; // codifica

            if (!validaEntrada())
                return;

            if (updateEmbedability()) {
                operationStarted("Codificando...");
                backend = new BackEndHandler(null, txtInFile
                    .getText(), txtDataFile.getText(),
                    txtOutFile.getText(), this);
                backend.start();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Arquivo grande demais para ser embutido neste arquivo de entrada\nEscolha outro arquivo de entrada",
                    "Arquivo maior requerido", JOptionPane
                    .WARNING_MESSAGE);
                return;
            }
        }

        // botão recuperar pressionado
        if (obj == btnRetrieve) {
            action = false; // decodifica

            if (validaEntrada()) {
                operationStarted("Decodificando...");
                backend = new BackEndHandler(null, txtInFile
                    .getText(), txtDataFile.getText(),
                    txtOutFile.getText(), this);
                backend.start();
            } else return;
        }

        // botão sobre pressionado
        if (obj == btnAbout) {
            AboutFrame about = new AboutFrame();
        }

        if (obj == btnExit)
            System.exit(0);
    }

    private void operationStarted(String status) {
        btnBrowseIn.setEnabled(false);
        btnBrowseData.setEnabled(false);
        btnBrowseOut.setEnabled(false);
        btnEmbed.setEnabled(false);
        btnRetrieve.setEnabled(false);
        btnExit.setEnabled(false);
        lblStatus.setText(status);
    }

    public void operationComplete(String status) {
        btnBrowseIn.setEnabled(true);
        btnBrowseData.setEnabled(true);
        btnBrowseOut.setEnabled(true);
        btnEmbed.setEnabled(true);
        btnRetrieve.setEnabled(true);
        btnExit.setEnabled(true);
        lblStatus.setText(status);
    }
}