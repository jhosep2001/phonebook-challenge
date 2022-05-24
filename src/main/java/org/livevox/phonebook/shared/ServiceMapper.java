package org.livevox.phonebook.shared;

import org.livevox.phonebook.application.entrypoints.web.model.ContactRequest;
import org.livevox.phonebook.application.entrypoints.web.model.ContactResponse;
import org.livevox.phonebook.domain.entity.ContactDomain;
import org.livevox.phonebook.infrastructure.db.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ContactResponse convertToResponse(ContactDomain contact);

    ContactDomain convertFromRequest(ContactRequest contact);

    ContactDomain convert(Contact contact);

    Contact convertToModel(ContactDomain contactDomain);
}
