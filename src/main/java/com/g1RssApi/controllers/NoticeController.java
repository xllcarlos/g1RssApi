package com.g1RssApi.controllers;

import com.g1RssApi.dtos.NoticeDTO;
import com.g1RssApi.services.NoticeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notices")
public class NoticeController {

    @Autowired // Injecao de dependencias
    private NoticeService noticeService;

    /**
     * Rota para cadastrar de uma nova noticia.
     *
     * @param noticeDTO dto com informacoes da noticia.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @PostMapping
    public ResponseEntity createNotice(@RequestBody @Valid NoticeDTO noticeDTO) {
        return this.noticeService.createNotice(noticeDTO);
    }

    /**
     * Rota para retornar todas as noticias cadastradas.
     *
     * @return ResponseEntity provido pelo NoticeService.
     */
    @GetMapping
    public ResponseEntity getAllNotices() {
        return this.noticeService.getAllNotices();
    }

    /**
     * Rota para retornar uma noticia conforme id recebido na URL.
     *
     * @param id id da noticia a ser buscada.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @GetMapping("/{id}")
    public ResponseEntity getNotice(@PathVariable(name = "id") Long id) {
        return this.noticeService.getNotice(id);
    }

    /**
     * Rota para atualizar uma noticia.
     * @param id id da noticia a ser atualizada.
     * @param noticeDTO dto com informacoes da noticia.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @PutMapping("/{id}")
    public ResponseEntity updateNotice(@PathVariable(name = "id") Long id, @RequestBody @Valid NoticeDTO noticeDTO) {
        return this.noticeService.updateNotice(id, noticeDTO);
    }

    /**
     * Rota para excluir uma noticia.
     * @param id id da noticia a ser excluida.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @DeleteMapping("{id}")
    public ResponseEntity deleteNotice(@PathVariable(name = "id") Long id) {
        return this.noticeService.deleteNotice(id);
    }

    /**
     * Rota para retornar o feed de noticias.
     * @param authorization token do usuario.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @GetMapping("/feed")
    public ResponseEntity getFeed(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return this.noticeService.getFeed(authorization);
    }

}
