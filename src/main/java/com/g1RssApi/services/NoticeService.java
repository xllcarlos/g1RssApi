package com.g1RssApi.services;

import com.g1RssApi.dtos.NoticeDTO;
import com.g1RssApi.models.NoticeModel;
import com.g1RssApi.repositories.NoticeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired // Injecao de dependencias
    private NoticeRepository noticeRepository;

    /**
     * Cria uma nova noticia.
     * @param noticeDTO dto com as informacoes da noticia.
     * @return ResponseEntity com status criado.
     */
    public ResponseEntity createNotice(NoticeDTO noticeDTO) {
        NoticeModel newNotice = new NoticeModel();
        BeanUtils.copyProperties(noticeDTO, newNotice);
        this.noticeRepository.save(newNotice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Busca todas as noticias cadastradas.
     * @return ResponseEntity com as noticias.
     */
    public ResponseEntity getAllNotices() {
        List<NoticeModel> list = this.noticeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * Busca uma noticia conforme um id recebido por parametro.
     * @param id id da noticia a ser buscada.
     * @return ResponseEntity.OK se a noticia for encontrada
     * ou ResponseEntity.NOT_FOUND se a noticia nao for encontrada.
     */
    public ResponseEntity getNotice(Long id) {
        Optional<NoticeModel> optionalNotice = this.noticeRepository.findById(id);
        if (optionalNotice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalNotice);
    }

    /**
     * Atualiza uma noticia conforme id e dto recebidos por parametro.
     * @param id id da noticia a ser atualizada.
     * @param noticeDTO dto com as informacoes da noticia a ser atualizada.
     * @return ResponseEntity.OK se a noticia for encontrada e atualizada
     * ou ResponseEntity.NOT_FOUND se a noticia nao for encontrada.
     */
    public ResponseEntity updateNotice(Long id, NoticeDTO noticeDTO) {
        Optional<NoticeModel> optionalNotice = this.noticeRepository.findById(id);
        if (optionalNotice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        NoticeModel noticeObejct = optionalNotice.get();
        BeanUtils.copyProperties(noticeDTO, noticeObejct);
        this.noticeRepository.save(noticeObejct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Exclui uma noticia conforme id recebido por parametro.
     * @param id id da noticia a ser excluida.
     * @return ResponseEntity.OK se a noticia for encontrada e excluida
     * ou ResponseEntity.NOT_FOUND se a noticia nao for encontrada.
     */
    public ResponseEntity deleteNotice(Long id) {
        Optional<NoticeModel> optionalNotice = this.noticeRepository.findById(id);
        if (optionalNotice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        NoticeModel noticeObejct = optionalNotice.get();
        this.noticeRepository.delete(noticeObejct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
