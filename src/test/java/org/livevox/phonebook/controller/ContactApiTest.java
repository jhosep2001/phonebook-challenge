package org.livevox.phonebook.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.livevox.phonebook.MockUtils;
import org.livevox.phonebook.application.entrypoints.api.ContactApi;
import org.livevox.phonebook.application.entrypoints.web.model.ContactRequest;
import org.livevox.phonebook.application.entrypoints.web.model.ContactResponse;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.domain.port.ContactService;
import org.livevox.phonebook.shared.ServiceMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ContactApiTest {

    @Mock
    ContactService contactService;

    @Mock
    ServiceMapper serviceMapper;

    @InjectMocks
    ContactApi contactApi;

    @Test
    void shouldGetAllContactsMappedToDomain() {
        ContactDomain domain = MockUtils.contactDomain();
        ContactResponse response = MockUtils.contactResponse();

        when(contactService.getAllContacts())
                .thenReturn(List.of(domain, domain));

        when(serviceMapper.convertToResponse(domain))
                .thenReturn(response);

        List<ContactResponse> result = contactApi.getAllContacts();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(domain.getFirstName(), result.get(0).getFirstName());
        Assertions.assertEquals(domain.getLastName(), result.get(0).getLastName());
        Assertions.assertEquals(domain.getPhone(), result.get(0).getPhone());
        verify(contactService).getAllContacts();
        verify(serviceMapper, times(2)).convertToResponse(domain);
    }

    @Test
    void shouldCreateContactsMappedToDomain() {
        ContactDomain domain = MockUtils.contactDomain();
        ContactResponse response = MockUtils.contactResponse();
        ContactRequest request = MockUtils.contactRequest();

        when(serviceMapper.convertFromRequest(request))
                .thenReturn(domain);
        when(contactService.createContact(domain))
                .thenReturn(domain);
        when(serviceMapper.convertToResponse(domain))
                .thenReturn(response);

        ContactResponse result = contactApi.createContact(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(response.getId(), result.getId());
        Assertions.assertEquals(response.getFirstName(), result.getFirstName());
        Assertions.assertEquals(response.getPhone(), result.getPhone());
        verify(contactService).createContact(domain);
        verify(serviceMapper, times(1)).convertToResponse(domain);
        verify(serviceMapper, times(1)).convertFromRequest(request);
    }

    @Test
    void shouldUpdateContactsMappedToDomain() {
        ContactDomain domain = MockUtils.contactDomain();
        ContactResponse response = MockUtils.contactResponse();
        ContactRequest request = MockUtils.contactRequest();
        Integer id = 11;

        when(serviceMapper.convertFromRequest(request))
                .thenReturn(domain);
        when(contactService.updateContact(domain))
                .thenReturn(domain);
        when(serviceMapper.convertToResponse(domain))
                .thenReturn(response);

        ContactResponse result = contactApi.updateContact(id, request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(response.getId(), result.getId());
        Assertions.assertEquals(response.getFirstName(), result.getFirstName());
        Assertions.assertEquals(response.getPhone(), result.getPhone());
        verify(contactService).updateContact(domain);
        verify(serviceMapper, times(1)).convertToResponse(domain);
        verify(serviceMapper, times(1)).convertFromRequest(request);
    }



    @Test
    void shouldReturnFindById() {
        ContactResponse response = MockUtils.contactResponse();
        ContactDomain domain = MockUtils.contactDomain();
        Integer search = 11;

        when(contactService.findById(search))
                .thenReturn(domain);
        when(serviceMapper.convertToResponse(domain))
                .thenReturn(response);

        ContactResponse result = contactApi.getContactById(search);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(response.getId(), result.getId());
        Assertions.assertEquals(response.getFirstName(),  result.getFirstName());
        Assertions.assertEquals(response.getPhone(),  result.getPhone());
        verify(contactService).findById(search);
        verify(serviceMapper, times(1)).convertToResponse(domain);
    }

}
