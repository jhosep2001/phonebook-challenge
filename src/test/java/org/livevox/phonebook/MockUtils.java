package org.livevox.phonebook;

import org.livevox.phonebook.application.entrypoints.web.model.ContactRequest;
import org.livevox.phonebook.application.entrypoints.web.model.ContactResponse;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.infrastructure.db.Contact;

public class MockUtils {

    public static ContactRequest contactRequest() {
        ContactRequest model = new ContactRequest();
        model.setFirstName("testName");
        model.setLastName("testLastName");
        model.setPhone("testPhone");
        return model;
    }

    public static ContactResponse contactResponse() {
        ContactResponse model = new ContactResponse();
        model.setFirstName("testName");
        model.setLastName("testLastName");
        model.setPhone("testPhone");
        model.setId(11);
        return model;
    }

    public static ContactDomain contactDomain() {
        return ContactDomain.builder()
                .id(11)
                .firstName("testName")
                .lastName("testLastName")
                .phone("testPhone")
                .build();
    }

    public static Contact contact() {
        Contact model = new Contact();
        model.setFirstName("testName");
        model.setLastName("testLastName");
        model.setId(11);
        model.setPhone("testPhone");
        return model;
    }
}
