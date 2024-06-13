package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<Livro> livro = new ArrayList<>();
    public void exibeMenu() throws JsonProcessingException {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar séries por ator
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódios por trecho
                    10 - Top 5 episódios por série
                    11 - Buscar episódios a partir de uma data 
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    getTodosLivros();
                    break;
                case 4:
                    buscarAutoresVivosEmDeterminadoPeriodo();
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private LivroRepository repositorio;
    private AutorRepository autorRepositorio;
    public Principal(LivroRepository repositorio,  AutorRepository autorRepositorio) {
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }


    private void buscarSerieWeb() throws JsonProcessingException {
        DadosLivro dados = getDadosLivro();
        Livro livro = new Livro(dados);
        System.out.println("dados" + dados);
         repositorio.save(livro);

    }


    private DadosLivro getDadosLivro() throws JsonProcessingException {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "%20"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
        return dados;
    }

    private void getTodosLivros(){
        livro = repositorio.todosOsLivros();
        livro.stream()
                .forEach(System.out::println);
    }

    private void buscarAutoresVivosEmDeterminadoPeriodo(){
        System.out.println("Qual o ano?");
        var ano = leitura.nextLine();
        List<Autor> episodiosEncontrados = autorRepositorio.autoresVivosEmDeterminadoAno(Integer.parseInt(ano));
        episodiosEncontrados.forEach(e ->
                System.out.printf("Autor: %s Nascido em %s - Até %s \n",
                        e.getName() , e.getBirth_year() , e.getDeath_year() ));
    }

}
