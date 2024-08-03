package com.g1RssApi.routines;

import com.g1RssApi.models.CategoryModel;
import com.g1RssApi.models.NoticeModel;
import com.g1RssApi.repositories.CategoryRepository;
import com.g1RssApi.repositories.NoticeRepository;
import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@EnableScheduling
public class NoticeRoutine {

    private final int delay = 36000;

    @Autowired // Injecao de dependencias
    private NoticeRepository noticeRepository;

    @Autowired // Injecao de dependencias
    private CategoryRepository categoryRepository;

    /**
     * Sincroniza as noticias em determinado tempo de acordo
     * com as categorias cadastradas e as adiciona no banco de dados.
     */
    @Scheduled(fixedDelay = delay)
    public void syncNotices() {
        try {
            List<CategoryModel> categoriesList = this.categoryRepository.findAll();
            if (!categoriesList.isEmpty()) {

                for (CategoryModel categoryModel : categoriesList) {
                    List<SyndEntry> noticesList = readFeed(categoryModel.getLink());
                    for (SyndEntry notice : noticesList) {

                        if (!this.noticeRepository.findByTitleAndPubDate(notice.getTitle(), notice.getPublishedDate()).isPresent()) {

                            System.out.println("There are categories being synchronized");

                            MediaEntryModule mediaModule = (MediaEntryModule) notice.getModule(MediaModule.URI);

                            NoticeModel newNotice = new NoticeModel();
                            newNotice.setTitle(notice.getTitle());
                            newNotice.setDescription(notice.getDescription().getValue());
                            newNotice.setImageUrl(getLinkImage(mediaModule));
                            newNotice.setPubDate(notice.getPublishedDate());
                            newNotice.setCategory(categoryModel);

                            noticeRepository.save(newNotice);
                        }  // If
                    }  // For
                }  // For
            } else {
                System.out.println("There are no categories to be synchronized");
            }
        } catch (FeedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca as noticias em um endpoint XML.
     * @param feedUrl url da fonte das noticias.
     * @return List com as noticias desserializadas.
     */
    private List<SyndEntry> readFeed(String feedUrl) throws IOException, FeedException {
        URL url = new URL(feedUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));

        return feed.getEntries();
    }

    /**
     * Separa o link da midia da descricao da noticia.
     * @param mediaModule modulo que possui o link da midia.
     * @return String do URI da midia.
     */
    private String getLinkImage(MediaEntryModule mediaModule) {
        if (mediaModule != null && mediaModule.getMediaContents().length > 0) {
            return String.valueOf(mediaModule.getMediaContents()[0].getReference());
        }
        return "";
    }
}
