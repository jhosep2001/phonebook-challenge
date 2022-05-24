package org.livevox.phonebook.infrastructure.db;

import lombok.extern.slf4j.Slf4j;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.domain.repository.ContactRepository;
import org.livevox.phonebook.shared.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ContactRepositoryImpl implements ContactRepository {

    private ContactJpa jpa;

    private ServiceMapper serviceMapper;

    @Autowired
    public ContactRepositoryImpl(ContactJpa jpa, ServiceMapper serviceMapper) {
        this.jpa = jpa;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ContactDomain> getAllContacts() {
        return jpa.findAll().stream()
                .map(serviceMapper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDomain createContact(ContactDomain contactDomain) {
        try {
            Contact newContact = jpa
                    .save(serviceMapper.convertToModel(contactDomain));
            return serviceMapper.convert(newContact);
        } catch (Exception ex) {
            log.error("Something go wrong saving contact " + ex);
            return null;
        }
    }

    @Override
    public Optional<List<ContactDomain>> findByText(String searchText) {
        try {
            return Optional.of(jpa
                    .findContactsByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(
                            searchText,
                            searchText,
                            searchText)
                    .stream().map(serviceMapper::convert)
                    .collect(Collectors.toList()));
        } catch (Exception ex) {
            log.error("Something go wrong searching for contact " + ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ContactDomain> findById(Integer idContact) {
        return jpa.findById(idContact)
                .map(serviceMapper::convert);
    }

    @Override
    public ContactDomain updateContact(ContactDomain entity) {
        try {
            Contact updatedContact = jpa
                    .save(serviceMapper.convertToModel(entity));
            return serviceMapper.convert(updatedContact);
        } catch (Exception ex) {
            log.error("Something go wrong saving contact " + ex);
            return null;
        }
    }
}
