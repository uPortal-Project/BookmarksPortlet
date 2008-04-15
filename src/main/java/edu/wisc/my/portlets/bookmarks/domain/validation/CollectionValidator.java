package edu.wisc.my.portlets.bookmarks.domain.validation;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;

public class CollectionValidator  extends EntryValidator {

    /**
     * @see edu.wisc.my.portlets.bookmarks.domain.validation.EntryValidator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class clazz) {
        return CollectionFolder.class.isAssignableFrom(clazz) && super.supports(clazz);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.domain.validation.EntryValidator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        final CollectionFolder collection = (CollectionFolder)obj;
        this.validateUrl(collection, errors);
    }

    private void validateUrl(CollectionFolder collection, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "portlet.bookmark.error.url.required");

        String url = collection.getUrl();
        collection.setUrl(url);

        try {
            new URL(url);
        }
        catch (MalformedURLException mue) {
            errors.rejectValue("url", "portlet.bookmark.error.url.malformed");
        }
    }
}
