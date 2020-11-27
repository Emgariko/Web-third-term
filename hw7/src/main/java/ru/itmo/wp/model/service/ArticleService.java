package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticleCreation(String title, String text) throws ValidationException {
        if (title.length() == 0) {
            throw new ValidationException("Title is required");
        }
        if (text.length() == 0) {
            throw new ValidationException("Text is required");
        }

        if (title.length() > 256) {
            throw new ValidationException("Title can't be longer than 256 characters");
        }
        if (text.length() > 65535) {
            throw new ValidationException("Text can't be longer than 65535 charaters");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAllArticles();
    }

    public List<Article> findAllUserArticles(User user) {
        return articleRepository.findAllUserArticles(user);
    }

    public void setVisibility(Long id, boolean boolValue) {
        articleRepository.setVisibility(id, boolValue);
    }

    public void validateSetVisibility(User user, Long articleId) throws ValidationException {
        Long articleAuthorId = articleRepository.findUserId(articleId);
        if (user.getId() != articleAuthorId) {
            throw new ValidationException("You have no permissions, it's not your article");
        }
    }
}
