
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

public class PagamentoTest {

    @Test
    public void pagarContaAtesVencimento() throws ParseException {
        //arrange
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPg;
        dataPg = formato.parse("23/05/2020");
        Date dataVencimento;
        dataVencimento = formato.parse("31/05/2020");
        double valorPago = 0.0;
        double valorFatura = 0.0;
        String codigoBarras = null;
        boolean juros = false;
        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento antes do vencimento";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaDataPagamento(dataPg,dataVencimento);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarContaDepoisVencimentoSemJuros() throws ParseException {
        //arrange
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPg;
        dataPg = formato.parse("06/06/2020");
        Date dataVencimento;
        dataVencimento = formato.parse("31/05/2020");
        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento atrasado";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaDataPagamento(dataPg,dataVencimento);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarContaNoVencimento() throws ParseException {
        //arrange
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPg;
        dataPg = formato.parse("31/05/2020");
        Date dataVencimento;
        dataVencimento = formato.parse("31/05/2020");
        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento no vencimento";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaDataPagamento(dataPg,dataVencimento);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void validarDataReal() throws ParseException {
        //arrange
        String data;
        data = "31/05/2020";

        boolean resultadoPagamento ;
        boolean resultadoEsperado = true;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaData(data);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarDataInvalida(){
        //arrange
        String data;
        data = "32/05/2020";

        boolean resultadoPagamento ;
        boolean resultadoEsperado = false;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaData(data);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }


    @Test
    public void pagarComValorCorreto(){
        //arrange
        double valorPago = 100.0;
        double valorFatura = 100.0;

        String resultadoPagamento = null;
        String resultadoEsperado = "Valor de pagamento correto";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaValorPago(valorPago,valorFatura);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarComValorMenor(){
        //arrange
        double valorPago = 10.0;
        double valorFatura = 100.0;

        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento não pode ser menor do o valor da fatura";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaValorPago(valorPago,valorFatura);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarComValorMaior(){
        //arrange
        double valorPago = 1000.0;
        double valorFatura = 100.0;

        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento não pode ser maior do o valor da fatura";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaValorPago(valorPago,valorFatura);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void pagarValorNegativo(){
        //arrange
        double valorPago = -1.0;
        double valorFatura = 100.0;

        String resultadoPagamento = null;
        String resultadoEsperado = "Pagamento não pode ser negativo";;
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaValorPago(valorPago,valorFatura);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }


    @Test
    public void atualizarCreditoLoja() throws Exception {
        //arrange
        double adicionarCreditoLoja = 100.00;
        final String METHOD = "getCredito";
        String resultadoPagamento = null;
        String resultadoEsperado = "Crédito na loja de R$900.0";
        Pagamento pag = new Pagamento();
        Pagamento moq = PowerMockito.spy(pag);
        PowerMockito.when(moq, METHOD).thenReturn((Double)800.0);
        //ACT
        resultadoPagamento = moq.validaCreditoFatura(adicionarCreditoLoja);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);

        PowerMockito.verifyPrivate(moq, Mockito.times(1)).invoke(METHOD);
    }

    @Test
    public void incluirCreditoLoja(){
        //arrange
        double creditoLoja = 100.00;

        String resultadoPagamento = null;
        String resultadoEsperado = "Crédito na loja de R$100.0";
        Pagamento pag = new Pagamento();
        //ACT
        resultadoPagamento = pag.validaCreditoFatura(creditoLoja);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);
    }

    @Test
    public void atualizarCreditoLojaValorNegativo() throws Exception {
        //arrange
        double adicionarCreditoLoja = -100.00;
        final String METHOD = "getCredito";
        String resultadoPagamento = null;
        String resultadoEsperado = "Crédito na loja de R$-10.0";
        Pagamento pag = new Pagamento();
        Pagamento moq = PowerMockito.spy(pag);
        PowerMockito.when(moq, METHOD).thenReturn((Double)90.0);
        //ACT
        resultadoPagamento = moq.validaCreditoFatura(adicionarCreditoLoja);
        //Assert
        System.out.println(resultadoPagamento);
        Assert.assertEquals(resultadoEsperado,resultadoPagamento);

        PowerMockito.verifyPrivate(moq, Mockito.times(1)).invoke(METHOD);
    }

}
