import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import org.openqa.selenium.By;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;

public class StaticPageTest extends TestBase {

    static class Page {
        String url;
        String content;
        Boolean containsImage;
    }

    private Page[] pages = {
        new Page() {{
            url = "https://gis.inf.elte.hu/tiszta-tisza/contact";
            content = "Follow us";
            containsImage = true;
        }},
        new Page() {{
            url = "https://gis.inf.elte.hu/tiszta-tisza/about";
            content = "About the map";
            containsImage = true;
        }},
        new Page() {{
            url = "https://gis.inf.elte.hu/tiszta-tisza/news";
            content = "";
            containsImage = false;
        }}
    };

    private void assertPageLoadsCorrectly(Page pageData) {
        StaticPage page = new StaticPage(pageData.url, driver);

        // Reading the page title
        assertEquals("Page title should be 'Tiszta Tisza Térkép'", 
                 "Tiszta Tisza Térkép", page.getTitle());

        WebElement body = page.getBody();
        assertTrue("Body should be displayed for " + pageData.url, body.isDisplayed());

        if (!pageData.content.isEmpty()) {
            assertTrue("Expected content not found in body for " + pageData.url,
                page.bodyContains(pageData.content));
        }

        if (pageData.containsImage) {
            assertTrue("Expected visible image (outside of nav bar) on " + pageData.url,
                page.hasVisibleImageOutsideNav());
        } else {
            assertFalse("Did not expect visible image (outside of nav bar) on " + pageData.url,
                page.hasVisibleImageOutsideNav());
        }
    }

    // Static Page test
    @Test
    public void testOnePageLoadsCorrectly() {
        assertPageLoadsCorrectly(pages[0]);
    }

    // Multiple page test from array
    @Test
    public void testPagesLoadCorrectly() {
        for (Page pageData : pages) {
            assertPageLoadsCorrectly(pageData);
        }
    }

    // History test
    @Test
    public void testHistory() {
        for (Page page : pages) {
            driver.get(page.url);
            assertEquals("Expected to land on correct page URL", page.url, driver.getCurrentUrl());
        }

        for (int i = pages.length - 2; i >= 0; i--) {
            driver.navigate().back();
            assertEquals("After going back, expected page URL", pages[i].url, driver.getCurrentUrl());
        }

        for (int i = 1; i < pages.length; i++) {
            driver.navigate().forward();
            assertEquals("After going forward, expected page URL", pages[i].url, driver.getCurrentUrl());
        }
    }
}
