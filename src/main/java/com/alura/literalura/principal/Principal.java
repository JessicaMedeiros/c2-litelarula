package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Year;
import java.util.*;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<Livro> livro = new ArrayList<>();

    private List<Autor> autor = new ArrayList<>();

    public void exibeMenu() throws JsonProcessingException {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo título
                    2 - listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivoes em um determinado ano
                    5 - Lista livros em um determinado idioma                   
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    getTodosLivros();
                    break;
                case 3:
                    getTodosAutores();
                    break;
                case 4:
                    buscarAutoresVivosEmDeterminadoPeriodo();
                    break;
                case 5:
                    buscarLivrosEmDeterminadosIdioma();
                    break;
                case 6:
                    buscarAutorPorNome();
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


    private void buscarLivroWeb() throws JsonProcessingException {
        DadosLivro dados = getDadosLivro();

        String nomeAutor = dados.authors().get(0).name();

        Livro livro = new Livro(dados);

        Optional<Autor> autorEscontrado = autorRepositorio.findByName(nomeAutor);

        if(autorEscontrado.isPresent()){
            livro.setAutor(autorEscontrado.get());
            repositorio.save(livro);
        } else {
            Autor autor = new Autor(dados.authors().get(0).birth_year(), dados.authors().get(0).death_year(), dados.authors().get(0).name());
            autorRepositorio.save(autor);
            livro.setAutor(autor);
            repositorio.save(livro);
        }

        System.out.println("Livro salvo com sucesso no banco de dados\n");
        System.out.println(livro);

    }


    private DadosLivro getDadosLivro() throws JsonProcessingException {
        System.out.println("Digite o nome do livro para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "%20"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
        return dados;
    }

    private void getTodosLivros(){
        livro = repositorio.todosOsLivros();
        livro.forEach(e ->
                System.out.printf("\n\nLivro: %s \n\n",
                        e.getTitle() ));
        livro.stream()
                .forEach(System.out::println);
    }

    private void getTodosAutores(){
        autor = autorRepositorio.findAll();
        autor.stream()
                .forEach(System.out::println);
    }

    private void buscarAutorPorNome(){
        System.out.println("Qual o nome do autor que está procurando?");
        String nomeAutor = leitura.nextLine();

        Optional<Autor> autoresEscontrados = autorRepositorio.buscarAutorPeloNome(nomeAutor);


        if (autoresEscontrados.isPresent()) {
            Autor autor = autoresEscontrados.get();
            System.out.printf("\n\nAutor: %s \nAno de nascimento: %s \nAno de falecimento: %s \n\n",
                    autor.getName(), autor.getBirth_year(), autor.getDeath_year());
        } else {
            System.out.printf("Não foi encontrado autor com o nome: " + nomeAutor);
        }


    }

    private void buscarAutoresVivosEmDeterminadoPeriodo(){
        System.out.println("Qual o ano?");
        int ano = leitura.nextInt();
        int anoAtual = Year.now().getValue();

        if (ano < 1200 || ano > anoAtual) {
            System.out.println("Valor " + ano + " é inválido. Deve ser maior que 1200 e menor que o ano atual.");
            return;
        }

        List<Autor> autoresEncontrados = autorRepositorio.autoresVivosEmDeterminadoAno(ano);
        autoresEncontrados.stream()
                .forEach(System.out::println);


    }

    public void buscarLivrosEmDeterminadosIdioma() {

        System.out.println("""
                     Insira o idioma para realizar a busca?
                        es - espanhol 
                        en - inglês 
                        fr - francês 
                        pt - português""");

        var idioma = leitura.nextLine();

        List<Livro> livrosEncontrados = repositorio.buscarLivrosPeloIdioma(idioma);

        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma " + idioma);
        } else {

            long contagem = livrosEncontrados.stream().count();
            System.out.printf("Encontrados %d livros no idioma %s:\n", contagem, idioma);

            livrosEncontrados.stream()
                    .forEach(System.out::println);

        }
    }
}
