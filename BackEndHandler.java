/*
 *  @author Lucas Felipe C. Nincao - Ciência da Computação - UEL 2010
 */

import javax.swing.JOptionPane;

// classe para a criação de threads que realizam as operações de back-end
public class BackEndHandler extends Thread {
    private Esteganografia esteg;
    private EsteganografiaCliente cliente;
    private EmbutirArquivo clienteEmbutir;
    private String mensagem;
    private String ArqData;
    private String outFile;
    private String inFile;


    public BackEndHandler(String mensagem, String inFile, String ArqData, String outFile, Object cliente) {
        esteg = new Esteganografia();
        this.mensagem = mensagem;
        this.inFile = inFile;
        this.ArqData = ArqData;
        this.outFile = outFile;

        if (mensagem == null)
            clienteEmbutir = (EmbutirArquivo) cliente;
        else
            this.cliente = (EsteganografiaCliente) cliente;
    }

    public void run() {
        if (mensagem == null) // operações relacionadas a arquivo
        {
            if (clienteEmbutir.action) // codifica
            {
                boolean result = esteg.codificaArquivo(inFile, ArqData, outFile);
                if (result)
                    JOptionPane.showMessageDialog(clienteEmbutir, esteg.getMensagem(), "Operação foi um sucesso", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(clienteEmbutir, esteg.getMensagem(), "Operação falhou", JOptionPane.WARNING_MESSAGE);
            } else // decodifica
            {
                String result = esteg.decodificaArquivo(inFile);
                if (!result.equals("#FAILED#"))
                    JOptionPane.showMessageDialog(clienteEmbutir, result, "Operação foi um sucesso", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(clienteEmbutir, esteg.getMensagem(), "Operação falhou", JOptionPane.WARNING_MESSAGE);
            }

            clienteEmbutir.operationComplete(esteg.getMensagem());
        } else // mensagens relacionadas às operações
        {
            if (cliente.acao) // codifica
            {
                boolean result = esteg.codificaMensagem(mensagem, inFile, outFile);
                if (result)
                    JOptionPane.showMessageDialog(cliente, "Mensagem codificada com sucesso no arquivo " + outFile, "Operação foi um sucesso", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(cliente, esteg.getMensagem(), "Operação falhou", JOptionPane.WARNING_MESSAGE);
            } // decodifica
            else {
                String result = esteg.decodificaMensagem(inFile);
                if (result.equals("#FAILED#"))
                    JOptionPane.showMessageDialog(cliente, esteg.getMensagem(), "Operação falhou", JOptionPane.WARNING_MESSAGE);
                else
                    cliente.txtMensagem.setText(result);
            }

            cliente.operacaoConcluida(esteg.getMensagem());
        }
    }
}