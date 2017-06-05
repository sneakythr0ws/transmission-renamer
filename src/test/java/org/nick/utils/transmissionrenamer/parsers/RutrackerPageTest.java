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
    public void parseTitle() {
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
        Assert.assertTrue(!TEST_RUTRACKER_PAGE1.isMovie());
        Assert.assertTrue(TEST_RUTRACKER_PAGE2.isMovie());
        Assert.assertTrue(TEST_RUTRACKER_PAGE3.isMovie());

        Assert.assertTrue(!new RutrackerPage().setCategories(Arrays.asList("Главная", "Кино, Видео и ТВ", "2")).isMovie());
        Assert.assertTrue(new RutrackerPage().setCategories(Arrays.asList("Главная", "Кино, Видео и ТВ", "Зарубежное кино")).isMovie());
    }

    @Test
    public void findPosterImg() {
        Assert.assertEquals(TEST_RUTRACKER_PAGE1.getPostImg(), "http://i82.fastpic.ru/big/2016/1001/b4/a89a16abb20252cc956e486f42e7d5b4.png");
        Assert.assertEquals(TEST_RUTRACKER_PAGE2.getPostImg(), "http://i4.imageban.ru/out/2011/11/24/dcc373bd679521e1210e15c82e1d5953.png");
        Assert.assertEquals(TEST_RUTRACKER_PAGE3.getPostImg(), "http://i94.fastpic.ru/big/2017/0528/c1/105acdfccaa5f34de0c796b2a32c3cc1.png");
    }

    @Test
    public void getSignature() {
        Assert.assertEquals(TEST_RUTRACKER_PAGE1.getSignature(), RutrackerPage.SIGNATURE_RUTRACKER);
        Assert.assertEquals(TEST_RUTRACKER_PAGE2.getSignature(), RutrackerPage.SIGNATURE_RUTRACKER);
        Assert.assertEquals(TEST_RUTRACKER_PAGE3.getSignature(), RutrackerPage.SIGNATURE_RUTRACKER);
    }

    @Test
    public void findCategories() {
        Assert.assertEquals(TEST_RUTRACKER_PAGE1.getCategories(), Arrays.asList("Главная", "Разное", "Разное (раздачи)", "Видео"));
        Assert.assertEquals(TEST_RUTRACKER_PAGE2.getCategories(), Arrays.asList("Главная", "Кино, Видео и ТВ", "Мультфильмы", "Иностранные мультфильмы"));
        Assert.assertEquals(TEST_RUTRACKER_PAGE3.getCategories(), Arrays.asList("Главная", "Кино, Видео и ТВ", "Зарубежное кино", "Фильмы 2016-2017"));
    }
}