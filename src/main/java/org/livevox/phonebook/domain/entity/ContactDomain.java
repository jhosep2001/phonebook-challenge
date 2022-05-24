package org.livevox.phonebook.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDomain {

    private Integer id;

    private String firstName;

    private String lastName;

    private String phone;

}