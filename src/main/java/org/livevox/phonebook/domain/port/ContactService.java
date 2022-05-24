package org.livevox.phonebook.domain.port;

import org.livevox.phonebook.domain.entity.ContactDomain;

import java.util.List;

public interface ContactService {

    List<ContactDomain> getAllContacts();

    List<ContactDomain> findByText(String searchText);

    ContactDomain createContact(ContactDomain convertFromRequest);

    ContactDomain findById(Integer idContact);

    ContactDomain updateContact(ContactDomain entity);
}
