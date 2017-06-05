package org.nick.utils.transmissionrenamer.parsers;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RutrackerPageTest {
    @Test
    public void findTitle() throws Exception {
    }

    @Test
    public void parseTitle() throws Exception {

    }

    @Test
    public void isMovie() throws Exception {
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