package org.nick.utils.transmissionrenamer.parsers;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RutrackerPageTest {
    private static final RutrackerPage TEST_RUTRACKER_PAGE1 = new RutrackerPage(Thread.currentThread().getContextClassLoader().getResource("jay&bob.html"));
    private static final RutrackerPage TEST_RUTRACKER_PAGE2 = new RutrackerPage(Thread.currentThread().getContextClassLoader().getResource("iceage.html"));
    private static final RutrackerPage TEST_RUTRACKER_PAGE3 = new RutrackerPage(Thread.currentThread().getContextClassLoader().getResource("logan.html"));

    @Test
    public void findTitle() {
        Assert.assertEquals(TEST_RUTRACKER_PAGE1.getTitle(),
                "Джей и молчаливый Боб наносят ответный удар (расширенная версия) / Jay and Silent Bob Strike Back (Extended Cut) [2001, Комедия, HDRip]");
        Assert.assertEquals(TEST_RUTRACKER_PAGE2.getTitle(),
                "Ледниковый период / Ice Age (Крис Уэдж, Крис Уэдж / Chris Wedge, Chris Wedge) [2002, мультфильм, комедия, приключения, семейный, BDRip]");
        Assert.assertEquals(TEST_RUTRACKER_PAGE3.getTitle(),
                "Логан / Logan (Джеймс Мэнголд / James Mangold) [2017, США, фантастика, боевик, триллер, HDRip]");
    }

    @Test
    public void parseTitle() throws Exception {
        Assert.assertEquals(TEST_RUTRACKER_PAGE1.parseTitle().get(),
                new TrackerPage.ParsedTitle(
                        "Джей и молчаливый Боб наносят ответный удар (расширенная версия) / Jay and Silent Bob Strike Back",
                        "Extended Cut",
                        (short) 2001,
                        "Комедия")
        );
        Assert.assertEquals(TEST_RUTRACKER_PAGE2.parseTitle().get(), new TrackerPage.ParsedTitle(
                "Ледниковый период / Ice Age",
                "Крис Уэдж, Крис Уэдж / Chris Wedge, Chris Wedge",
                (short) 2002,
                "мультфильм"
        ));
        Assert.assertEquals(TEST_RUTRACKER_PAGE3.parseTitle().get(), new TrackerPage.ParsedTitle(
                "Логан / Logan",
                "Джеймс Мэнголд / James Mangold",
                (short) 2017,
                "США"
        ));
    }

    @Test
    public void isMovie() {
        Assert.assertTrue(!new RutrackerPage().setCategories(Arrays.asList("Главная", "Кино, Видео и ТВ", "2")).isMovie());
        Assert.assertTrue(new RutrackerPage().setCategories(Arrays.asList("Главная", "Кино, Видео и ТВ", "Зарубежное кино")).isMovie());
    }

    @Test
    public void findPosterImg() throws Exception {
    }

    @Test
    public void getSignature() throws Exception {
    }

    @Test
    public void findCategories() throws Exception {
    }


}