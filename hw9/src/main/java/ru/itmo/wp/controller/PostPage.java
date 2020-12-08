package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.PostCommentForm;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/writeComment")
    private String writeComment(@Valid @ModelAttribute("commentForm") PostCommentForm postCommentForm, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        Post post = postService.findById(postCommentForm.getPostId());
        Comment comment = new Comment();
        comment.setText(postCommentForm.getText());
        comment.setUser(getUser(httpSession));
        postService.writeComment(post, comment);
        return "redirect:/post/" + post.getId();
    }

    @Guest
    @GetMapping("/post/{id}")
    private String getPostById(Model model, @PathVariable String id, HttpSession httpSession) {
        try {
            Long longId = Long.parseLong(id);
            Post post = postService.findById(longId);
            if (post == null) {
                throw new NullPointerException();
            }
            model.addAttribute("commentForm", new PostCommentForm());
            model.addAttribute("curPost", post);
            model.addAttribute("comments", postService.findById(longId).getComments());
        } catch (NumberFormatException | NullPointerException exception) {
            putError(httpSession, "This post does not exist");
            return "redirect:/";
        }
        return "PostPage";
    }
}
