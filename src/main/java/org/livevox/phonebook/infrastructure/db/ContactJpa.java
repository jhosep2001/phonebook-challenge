package org.livevox.phonebook.infrastructure.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactJpa extends JpaRepository<Contact,Integer> {

    List<Contact> findContactsByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(
            String firstName, String lastName, String phone);

    @Query("SELECT c FROM Contact c " +
                    "WHERE c.firstName LIKE %:text% " +
                    "OR c.lastName LIKE %:text% " +
                    "OR c.phone LIKE %:text%"
    )
    List<Contact> findByText(@Param("text") String textSearch);
}
