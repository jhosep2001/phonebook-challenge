<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>PhoneBook</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/phonebook.js"></script>
</head>
<body>
<div class="container">
    <div class="pure-g">
        <div class="pure-u-1">
            <div class="header">
                <img class="logo" src="../../img/phonebook.png"/>
                <p>v 1.0</p>
            </div>

        </div>
    </div>
    <c:if test="${errors != null}">
        <div class="warning ">
            <h3>Be Careful!</h3>
            <c:forEach items="${errors}" var="error">
                <p class="">${error.getDefaultMessage()}</p>
            </c:forEach>
        </div>
    </c:if>
    <div class="pure-g">
        <div class="pure-u-sm-1 pure-u-1-3">
            <div class="box">
                <h2><i class="fa fa-user-plus"></i>New contact</h2>
                <form:form method="post" class="pure-form" id="add" action="/add-contact"
                           modelAttribute="contactRequest">
                    <fieldset class="pure-group">
                        <form:input id="firstName" type="text" class="pure-input-1-2" placeholder="First Name"
                                    path="firstName"/>
                        <form:input id="lastName" type="text" class="pure-input-1-2" placeholder="Last Name"
                                    path="lastName"/>
                        <form:input id="phone" class="pure-input-1-2" placeholder="Phone" path="phone"/>
                    </fieldset>
                    <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">
                        <i class="fa fa-user-plus"></i>Add
                    </button>
                </form:form>
            </div>
        </div>
        <div class="pure-u-sm-1 pure-u-1-3">
            <div class="box">
                <h2><i class="fa fa-search"></i>Search contact</h2>
                <div class="pure-form">
                    <fieldset class="pure-group">
                        <input type="text" id="search" class="pure-input-1-2">
                    </fieldset>
                    <button onclick="searchContacts()" class="pure-button pure-input-1-2 pure-button-primary">
                        <i class="fa fa-search"></i>Search
                    </button>
                    <br>
                    <table class="pure-table" id="search-results">
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="pure-u-sm-1 pure-u-1-3">
            <div class="box">
                <h2><i class="fa fa-users"></i> Contacts</h2>
                <c:if test="${contacts != null}">
                    <table class="pure-table">
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${contacts}" var="contact">
                            <tr>
                                <td>${contact.firstName}</td>
                                <td>${contact.lastName}</td>
                                <td>${contact.phone}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>