package org.livevox.phonebook.application.entrypoints.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.livevox.phonebook.application.entrypoints.web.model.ContactRequest;
import org.livevox.phonebook.application.entrypoints.web.model.ContactResponse;
import org.livevox.phonebook.domain.port.ContactService;
import org.livevox.phonebook.shared.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ContactsController {

    private ContactService contactService;

    private ServiceMapper serviceMapper;

    @Autowired
    public ContactsController(ContactService contactService, ServiceMapper serviceMapper) {
        this.contactService = contactService;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping("/")
    public String contacts(Model model) {
        log.info("Attending contacts main request");
        model.addAttribute("contacts", contactService.getAllContacts()
                .stream()
                .map(serviceMapper::convertToResponse)
                .collect(Collectors.toList())
        );
        model.addAttribute("contactRequest", new ContactRequest());
        return "index";
    }

    @PostMapping(value = "/add-contact")
    public String addTodo(@RequestBody @Valid @ModelAttribute("contactRequest") ContactRequest contactRequest,
                          BindingResult result, Model model) {
        log.info("Attending add-contact request");
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("contacts", contactService.getAllContacts()
                    .stream()
                    .map(serviceMapper::convertToResponse)
                    .collect(Collectors.toList())
            );
            return "index";
        }
        contactService.createContact(serviceMapper.convertFromRequest(contactRequest));
        return "redirect:/";
    }

}
