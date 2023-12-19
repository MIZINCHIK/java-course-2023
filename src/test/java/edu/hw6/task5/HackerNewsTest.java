package edu.hw6.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task5.HackerNews.hackerNewsTopStories;
import static edu.hw6.task5.HackerNews.news;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HackerNewsTest {
    @Test
    @DisplayName("Getting top stories")
    void hackerNewsTopStories_default_correct() {
        long[] topStories = hackerNewsTopStories();
        assertThat(topStories).isNotNull();
        assertThat(topStories.length > 0).isTrue();
    }

    @Test
    @DisplayName("Getting news title")
    void news_37570037_title() {
        assertThat(news(37570037)).isEqualTo("JDK 21 Release Notes");
    }
}
