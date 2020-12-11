package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostWriteForm;

import java.util.Arrays;

@Component
public class PostWriteFormWriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostWriteForm.class.equals(clazz);
    }

    private boolean validateTags(String tags) {
        return (Arrays.stream(tags.trim().split("\\s+")).
                allMatch(str -> str.matches("[A-Za-z]{1,64}"))) ||
                tags.trim().isEmpty();
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostWriteForm postWriteForm = (PostWriteForm) target;
            if (!validateTags(postWriteForm.getTags())) {
                errors.rejectValue("tags", "tags.invalid", "Tags should be a words split by spaces not longer than 64 charters, summary length shouldn't be longer than 256.");
            }
        }
    }
}
