/* Phonebook */
const searchContacts = () => {
    const text = document.getElementById("search").value;
    const table = document.getElementById("search-results");
    table.classList.remove("display");

    fetch("http://localhost:8080/api/contact/search?text="+text, {
        method: 'GET',
    })
        .then(response => response.json())
        .catch(error => console.error('Error:', error))
        .then(response => {
            if (response && response.length > 0) {
                const table = document.getElementById("search-results");
                table.classList.add("display");
                const tbody = table.tBodies[0];
                tbody.innerHTML = "";

                response.forEach(c => {
                    const row = document.createElement("tr");
                    const name = document.createElement("td");
                    const lastName = document.createElement("td");
                    const phone = document.createElement("td");
                    name.textContent = c.firstName;
                    lastName.textContent = c.lastName;
                    phone.textContent = c.phone;

                    row.appendChild(name)
                    row.appendChild(lastName)
                    row.appendChild(phone)
                    tbody.appendChild(row);
                });
                console.log('Success search:', response);
            }

        });
}