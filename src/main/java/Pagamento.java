import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pagamento {
    private double credito;

    public Pagamento(){
        zeraCredito();
    }

    private void zeraCredito(){
        credito = 0;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getCredito() {
        return credito;
    }

    public String validaDataPagamento(Date dataPg, Date dataVencimento) {
        String resultadoPagamento = null;

        if (dataPg.before(dataVencimento)){
            resultadoPagamento = "Pagamento antes do vencimento";
        }
        else if (dataPg.after(dataVencimento)){
            resultadoPagamento = "Pagamento atrasado";
        }
        else  {
            resultadoPagamento = "Pagamento no vencimento";
        }


        return resultadoPagamento;

    }
    public boolean validaData(String data){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public String validaValorPago(double valorPago, double valorFatura) {
        String mensagem = null;
        if (valorPago < 0) {
            mensagem = "Pagamento não pode ser negativo";
        }
        else if(valorPago > valorFatura){
            mensagem = "Pagamento não pode ser maior do o valor da fatura";
        }
        else if(valorPago < valorFatura){
            mensagem = "Pagamento não pode ser menor do o valor da fatura";
        }
        else{
            mensagem = "Valor de pagamento correto";
        }

        return mensagem;
    }

    public String validaCreditoFatura(double valorCredito) {
        String mensagem = null;
        double valorCreditoConta = getCredito();
        valorCreditoConta=((valorCreditoConta + valorCredito));
        setCredito(valorCreditoConta);

        mensagem = "Crédito na loja de R$"+valorCreditoConta;
        return mensagem;
    }
}

