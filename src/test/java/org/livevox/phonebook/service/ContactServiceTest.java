package org.livevox.phonebook.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.livevox.phonebook.MockUtils;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.domain.repository.ContactRepository;
import org.livevox.phonebook.domain.service.ContactServiceImpl;
import org.livevox.phonebook.shared.CustomException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ContactServiceTest {

    @Mock
    ContactRepository contactRepository;
    
    @InjectMocks
    ContactServiceImpl contactService;

    @Test
    void shouldGetAllContacts() {
        ContactDomain domain = MockUtils.contactDomain();

        when(contactRepository.getAllContacts())
                .thenReturn(List.of(domain));

        List<ContactDomain> result = contactService.getAllContacts();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(domain.getFirstName(), result.get(0).getFirstName());
        Assertions.assertEquals(domain.getLastName(), result.get(0).getLastName());
        Assertions.assertEquals(domain.getPhone(), result.get(0).getPhone());
        verify(contactRepository).getAllContacts();
    }

    @Test
    void shouldCreateContactsMappedToDomain() {
        ContactDomain domain = MockUtils.contactDomain();
        
        when(contactRepository.createContact(domain))
                .thenReturn(domain);

        ContactDomain result = contactService.createContact(domain);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(domain.getId(), result.getId());
        Assertions.assertEquals(domain.getFirstName(), result.getFirstName());
        Assertions.assertEquals(domain.getPhone(), result.getPhone());
        verify(contactRepository).createContact(domain);
    }

    @Test
    void shouldBeNullByThrowErrorCreateContact() {
        ContactDomain domain = MockUtils.contactDomain();
        
        when(contactRepository.createContact(domain))
                .thenReturn(null);

        Assertions.assertThrows(CustomException.class,() -> contactService.createContact(domain));
        verify(contactRepository).createContact(domain);
    }

    @Test
    void shouldUpdateContactsMappedToDomain() {
        ContactDomain domain = MockUtils.contactDomain();

        when(contactRepository.findById(domain.getId()))
                .thenReturn(Optional.of(domain));

        when(contactRepository.updateContact(domain))
                .thenReturn(domain);

        ContactDomain result = contactService.updateContact(domain);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(domain.getId(), result.getId());
        Assertions.assertEquals(domain.getFirstName(), result.getFirstName());
        Assertions.assertEquals(domain.getPhone(), result.getPhone());
        verify(contactRepository).updateContact(domain);
    }

    @Test
    void shouldReturnListFindByText() {
        ContactDomain domain = MockUtils.contactDomain();
        String textSearch = "Test";

        when(contactRepository.findByText(textSearch))
                .thenReturn(Optional.of(List.of(domain)));

        List<ContactDomain> result = contactService.findByText(textSearch);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(domain.getId(), result.get(0).getId());
        Assertions.assertEquals(domain.getFirstName(),  result.get(0).getFirstName());
        Assertions.assertEquals(domain.getPhone(),  result.get(0).getPhone());
        verify(contactRepository).findByText(textSearch);
    }

    @Test
    void shouldReturnFindById() {
        ContactDomain domain = MockUtils.contactDomain();
        Integer search = 11;

        when(contactRepository.findById(search))
                .thenReturn(Optional.of(domain));

        ContactDomain result = contactService.findById(search);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(domain.getId(), result.getId());
        Assertions.assertEquals(domain.getFirstName(),  result.getFirstName());
        Assertions.assertEquals(domain.getPhone(),  result.getPhone());
        verify(contactRepository).findById(search);
    }

}
