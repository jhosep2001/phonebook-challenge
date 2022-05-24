package org.livevox.phonebook.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.domain.repository.ContactRepository;
import org.livevox.phonebook.domain.port.ContactService;
import org.livevox.phonebook.shared.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactDomain> getAllContacts() {
        return contactRepository.getAllContacts();
    }

    @Override
    public List<ContactDomain> findByText(String searchText) {
        return contactRepository.findByText(searchText)
                .orElse(null);
    }

    @Override
    public ContactDomain createContact(ContactDomain contactDomain) {
        ContactDomain result =this.contactRepository.createContact(contactDomain);
        if (result == null)
            throw new CustomException("Something happen creating contact","Internal error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        return result;
    }

    @Override
    public ContactDomain findById(Integer idContact) {
        return contactRepository.findById(idContact).orElse(null);
    }

    @Override
    public ContactDomain updateContact(ContactDomain entity) {
        if (this.findById(entity.getId()) != null ) {
            return this.contactRepository.updateContact(entity);
        } else {
            log.error("Contact id not found");
            throw new CustomException("Contact id not found","Contact not found",
                    HttpStatus.BAD_REQUEST);
        }


    }
}
