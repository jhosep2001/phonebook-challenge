package org.livevox.phonebook.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.livevox.phonebook.MockUtils;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.infrastructure.db.Contact;
import org.livevox.phonebook.infrastructure.db.ContactJpa;
import org.livevox.phonebook.infrastructure.db.ContactRepositoryImpl;
import org.livevox.phonebook.shared.ServiceMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContactRepositoryTest {

    @Mock
    ContactJpa contactJpa;

    @Mock
    ServiceMapper serviceMapper;

    @InjectMocks
    ContactRepositoryImpl contactRepository;

    @Test
    void shouldGetAllContactsMappedToDomain() {
        when(contactJpa.findAll())
                .thenReturn(List.of(new Contact(), new Contact()));

        when(serviceMapper.convert(any(Contact.class)))
                .thenReturn(ContactDomain.builder()
                        .firstName("test")
                        .lastName("test2")
                        .phone("test3")
                        .build());

        List<ContactDomain> result = contactRepository.getAllContacts();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("test", result.get(0).getFirstName());
        Assertions.assertEquals("test2", result.get(0).getLastName());
        Assertions.assertEquals("test3", result.get(0).getPhone());
        verify(contactJpa).findAll();
        verify(serviceMapper, times(2)).convert(any(Contact.class));
    }

    @Test
    void shouldCreateContactsMappedToDomain() {
        Contact model = MockUtils.contact();
        ContactDomain domain = MockUtils.contactDomain();

        when(serviceMapper.convertToModel(domain))
                .thenReturn(model);
        when(contactJpa.save(model))
                .thenReturn(model);
        when(serviceMapper.convert(model))
                .thenReturn(domain);

        ContactDomain result = contactRepository.createContact(domain);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getFirstName(), result.getFirstName());
        Assertions.assertEquals(model.getPhone(), result.getPhone());
        verify(contactJpa).save(model);
        verify(serviceMapper, times(1)).convertToModel(domain);
        verify(serviceMapper, times(1)).convert(model);
    }

    @Test
    void shouldBeNullByThrowErrorCreateContact() {
        Contact model = MockUtils.contact();
        ContactDomain domain = MockUtils.contactDomain();

        when(serviceMapper.convertToModel(domain))
                .thenReturn(model);
        when(contactJpa.save(model))
                .thenThrow(IllegalStateException.class);
        when(serviceMapper.convert(model))
                .thenReturn(domain);

        ContactDomain result = contactRepository.createContact(domain);

        Assertions.assertNull(result);
        verify(contactJpa).save(model);
        verify(serviceMapper, times(1)).convertToModel(domain);
        verify(serviceMapper, times(0)).convert(model);
    }

    @Test
    void shouldUpdateContactsMappedToDomain() {
        Contact model = MockUtils.contact();
        ContactDomain domain = MockUtils.contactDomain();

        when(serviceMapper.convertToModel(domain))
                .thenReturn(model);
        when(contactJpa.save(model))
                .thenReturn(model);
        when(serviceMapper.convert(model))
                .thenReturn(domain);

        ContactDomain result = contactRepository.updateContact(domain);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getFirstName(), result.getFirstName());
        Assertions.assertEquals(model.getPhone(), result.getPhone());
        verify(contactJpa).save(model);
        verify(serviceMapper, times(1)).convertToModel(domain);
        verify(serviceMapper, times(1)).convert(model);
    }

    @Test
    void shouldReturnListFindByText() {
        Contact model = MockUtils.contact();
        ContactDomain domain = MockUtils.contactDomain();
        String textSearch = "Test";

        when(contactJpa.findContactsByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(textSearch, textSearch, textSearch))
                .thenReturn(List.of(model));
        when(serviceMapper.convert(model))
                .thenReturn(domain);

        Optional<List<ContactDomain>> result = contactRepository.findByText(textSearch);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, result.get().size());
        Assertions.assertEquals(model.getId(), result.get().get(0).getId());
        Assertions.assertEquals(model.getFirstName(),  result.get().get(0).getFirstName());
        Assertions.assertEquals(model.getPhone(),  result.get().get(0).getPhone());
        verify(contactJpa).findContactsByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(textSearch, textSearch, textSearch);
        verify(serviceMapper, times(1)).convert(model);
    }

    @Test
    void shouldReturnFindById() {
        Contact model = MockUtils.contact();
        ContactDomain domain = MockUtils.contactDomain();
        Integer search = 11;

        when(contactJpa.findById(search))
                .thenReturn(Optional.of(model));
        when(serviceMapper.convert(model))
                .thenReturn(domain);

        Optional<ContactDomain> result = contactRepository.findById(search);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(model.getId(), result.get().getId());
        Assertions.assertEquals(model.getFirstName(),  result.get().getFirstName());
        Assertions.assertEquals(model.getPhone(),  result.get().getPhone());
        verify(contactJpa).findById(search);
        verify(serviceMapper, times(1)).convert(model);
    }

}
