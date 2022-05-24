package org.livevox.phonebook.application.entrypoints.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactRequest implements Serializable  {

    @NotNull(message = "FirstName is a mandatory field")
    @NotBlank(message = "FirstName can't be empty")
    private String firstName;

    @NotNull(message = "LastName is a mandatory field")
    @NotBlank(message = "LastName can't be empty")
    private String lastName;

    @NotNull(message = "Phone is a mandatory field")
    @NotBlank(message = "Phone can't be empty")
    private String phone;
}
