package com.hitomi.crawler;

import com.hitomi.crawler.constants.HitomiSite;
import com.hitomi.crawler.job.JobService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.jobrunr.scheduling.BackgroundJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@Import(JobRunrStorageConfiguration.class)
public class CrawlerApplicationEx implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobService hitomiJobService;

    public static void main(String[] args) {

        SpringApplication.run(CrawlerApplicationEx.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate(HitomiSite.HITOMI_URL_HOME);

            int firstPage = Integer.parseInt(page.locator(HitomiSite.HITOMI_FIRST_PAGE).innerText());
            int lastPage = Integer.parseInt(page.locator(HitomiSite.HITOMI_LAST_PAGE).innerText());
            Stream<Integer> pagesStream = IntStream.range(firstPage, 2).boxed();

            BackgroundJob.enqueue(pagesStream,
                    (currentPage) -> hitomiJobService.processPage(HitomiSite.HITOMI_URL_INDEX_PAGE, currentPage));
        }
    }
}
