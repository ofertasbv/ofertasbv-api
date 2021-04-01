package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Produto;
import com.br.oferta.api.util.filter.ProdutoFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface ProdutoServiceImpl {

    List<Produto> findAll();

    Optional<Produto> findById(Long id);

    List<Produto> findByIsPromocaoBy(Long id);

    Produto create(Produto p);

    ResponseEntity update(Long id, Produto p);

    ResponseEntity delete(Long id);

    List<Produto> findByNome(String nome);

    List<Produto> findByDestaque(boolean destaque);

    Page<Produto> findAllByPage(Pageable pageable);

    Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable);

    List<Produto> findBySubCategoriaById(Long id);

    List<Produto> findByPromocaoById(Long id);

    List<Produto> findByPessoaById(Long id);

    Produto findByCodBarra(String codigoBarra);

    void excluirFoto(String foto);

}
