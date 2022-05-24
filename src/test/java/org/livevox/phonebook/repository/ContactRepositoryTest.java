package org.livevox.phonebook.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.infrastructure.db.Contact;
import org.livevox.phonebook.infrastructure.db.ContactJpa;
import org.livevox.phonebook.infrastructure.db.ContactRepositoryImpl;
import org.livevox.phonebook.shared.ServiceMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

}
