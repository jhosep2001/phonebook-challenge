package org.livevox.phonebook.application.entrypoints.api;

import lombok.extern.slf4j.Slf4j;
import org.livevox.phonebook.application.entrypoints.web.model.ContactRequest;
import org.livevox.phonebook.application.entrypoints.web.model.ContactResponse;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.domain.port.ContactService;
import org.livevox.phonebook.shared.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@Slf4j
public class ContactApi {

    private ContactService contactService;

    private ServiceMapper serviceMapper;

    @Autowired
    public ContactApi(ContactService contactService, ServiceMapper serviceMapper) {
        this.contactService = contactService;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping("/contacts")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponse> getAllContacts() {
        log.info("Starting Get all contacts request");
        return contactService.getAllContacts().stream().map(serviceMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactResponse getContactById(@PathVariable("id") Integer idContact) {
        log.info("Starting Get contact by id request");
        return serviceMapper.convertToResponse(contactService.findById(idContact));
    }

    @GetMapping("/contact/search")
    public List<ContactResponse> contacts(@RequestParam("text") String searchText) {
        log.info("Attending search contact request");
        return contactService.findByText(searchText)
                .stream().map(serviceMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse createContact(@RequestBody @Valid ContactRequest contactRequest) {
        log.info("Starting create contact request");
        return serviceMapper.convertToResponse(
                contactService.createContact(
                        serviceMapper.convertFromRequest(contactRequest)));

    }

    @PutMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse updateContact(@PathVariable("id") Integer idContact,
                                          @RequestBody @Valid ContactRequest contactRequest) {
        log.info("Starting create contact request");
        ContactDomain entity = serviceMapper.convertFromRequest(contactRequest);
        entity.setId(idContact);
        return serviceMapper.convertToResponse(contactService.updateContact(entity));
    }

}
