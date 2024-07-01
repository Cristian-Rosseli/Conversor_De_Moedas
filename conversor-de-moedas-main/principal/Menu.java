package principal;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.DadosMoedas;
import service.ConsumoAPi;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Menu {
    public void exibeMenu() throws IOException, InterruptedException {
        ConsumoAPi consumindo = new ConsumoAPi();
        Gson gson = new Gson();
        Scanner scan = new Scanner(System.in);
        String base_Url = "";
        String body_Url = "";

        boolean continuar = true;

        while (continuar) {

            var mensagem = """
                    Escolha A moeda para conversão:
                                 
                    Código: | País de Orígem:
                      USD ->  United States Dollar
                      BRL ->  Real Brasileiro
                      EUR ->  Euro
                      EGP ->  Libra egípcia
                      JPY ->  Iene japonês
                      RUB ->  Russian Ruble
                      """;
            System.out.println(mensagem);

            System.out.println("Digite as 3 letras do código da moeda a ser convertida: ");
            var moedaASerConvertida = scan.nextLine().toUpperCase();

            System.out.println("Digite as 3 letras do código da moeda para qual quer converter: ");
            var moedaParaConversao = scan.nextLine().toUpperCase();

            var inputAmostra = """
                        Conversão:
            * Partindo para conversão: %s -> %s *
          _____________________________________________
                    """.formatted(moedaASerConvertida, moedaParaConversao);
            System.out.println(inputAmostra);



            switch (moedaASerConvertida) {
                case "USD":
                case "BRL":
                case "EUR":
                case "EGP":
                case "JPY":
                case "RUB":
                    base_Url = moedaASerConvertida;
                    break;

                default:
                    System.out.println("Código de moeda inválido");
                    return;
            }
            switch (moedaParaConversao) {
                case "USD":
                case "BRL":
                case "EUR":
                case "EGP":
                case "JPY":
                case "RUB":
                    body_Url = moedaParaConversao;
                    break;

                default:
                    System.out.println("Código de moeda inválido");
                    return;
            }

            String json = consumindo.receberDados("https://v6.exchangerate-api.com/v6/3770ce990269323dd905c165/latest/USD");

            DecimalFormat formatadorDecimal = new DecimalFormat("##.##");

            try {
                DadosMoedas responDadosMoedas = gson.fromJson(json, DadosMoedas.class);

                System.out.println("Digite o valor a ser convertido: ");
                double valorASerConvertido = scan.nextDouble();
                scan.nextLine();


                double valorConvertido = responDadosMoedas.convert(base_Url, body_Url, valorASerConvertido);
                String valorDeConversao = formatadorDecimal.format(valorConvertido);


                System.out.println(valorASerConvertido + " " + base_Url + " é igual a " + valorDeConversao + " " + body_Url);
            } catch (JsonSyntaxException e) {
                System.out.println("Erro ao desserializar: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro durante a execução do programa: " + e.getMessage());
            }
            System.out.println("Deseja fazer outra conversão? Digite [S] para continuar convertendo e [N] para sair!");
            String resposta = scan.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                continuar = false;
                scan.close();
            }}


    }}
