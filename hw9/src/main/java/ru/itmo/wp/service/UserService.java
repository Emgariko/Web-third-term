package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostWriteForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    /** @noinspection FieldCanBeLocal, unused */
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostWriteForm postWriteForm, TagService tagService) {
        String[] rawTags = postWriteForm.getTags().trim().split("\\s+");
        List<Tag> tags = new ArrayList<>();
        if (!(rawTags.length == 1 && rawTags[0].isEmpty())) {
            tags = Arrays.stream(rawTags).distinct().map(Tag::new).collect(Collectors.toList());
        }
        Post post = new Post();
        post.setTitle(postWriteForm.getTitle());
        post.setText(postWriteForm.getText());

        tags.forEach((tag) -> {
            Tag foundTag = tagService.findByName(tag);
            if (foundTag == null) {
                foundTag = tagService.save(tag);
                // there can be a bug if two users try to save a post with the same tags at the same moment.
                // :TODO: fix it.
            }
            tag.setId(foundTag.getId());
        });

        post.setTags(tags);
        user.addPost(post);
        userRepository.save(user);
    }
}
