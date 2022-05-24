package org.livevox.phonebook.domain.repository;

import org.livevox.phonebook.domain.entity.ContactDomain;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<ContactDomain> getAllContacts();

    ContactDomain createContact(ContactDomain contactDomain);

    Optional<List<ContactDomain>> findByText(String searchText);

    Optional<ContactDomain> findById(Integer idContact);

    ContactDomain updateContact(ContactDomain entity);
}
