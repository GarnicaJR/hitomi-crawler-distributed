package com.hitomi.crawler.job;

import com.hitomi.crawler.constants.HitomiSite;
import com.hitomi.crawler.model.HManga;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class JobService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void processPage(String urlBase, int currentPage){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();

            Page page = browser.newPage();
            page.navigate(urlBase + currentPage);
            Locator rows = page.locator(HitomiSite.HITOMI_MANGA_ITEM);

            List<String> listOfMangas = new LinkedList<>();

            for (int i = 0; i < rows.count(); i++) {
                String linkToManga = HitomiSite.HITOMI_MANGA_URL_BASE + rows.nth(i).getAttribute("href");
                listOfMangas.add(linkToManga);

            }

            for(String mangaLink: listOfMangas){

                page.navigate(mangaLink);
                Locator mangaTitle = page.locator(HitomiSite.HITOMI_MANGA_TITLE);

                HManga hManga = HManga.builder().name(mangaTitle.innerText())
                                                .build();
                logger.info(hManga.toString());
                System.exit(0);
            }
        }
    }
}
