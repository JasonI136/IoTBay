<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 1/5/2023
  Time: 1:17 pm
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>

    <%--$(window).on('load', function () {--%>
    <%--    $('#add-user-modal').modal('show');--%>
    <%--});--%>

    function deleteUser(id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch("${pageContext.request.contextPath}/admin/user/" + id, {
                    method: "DELETE"
                })
                    .then(response => response.json())
                    .then(json => {
                        if (json.statusCode === 200) {
                            Swal.fire({
                                title: "Success",
                                icon: 'success',
                                text: "User deleted successfully",
                                showCancelButton: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: 'OK'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    window.location.reload();
                                }
                            });
                        } else {
                            Swal.fire({
                                title: "Error",
                                icon: 'error',
                                text: json.data,
                                showCancelButton: false,
                                confirmButtonColor: '#3085d6',
                                confirmButtonText: 'OK'
                            });
                        }
                    })
                    .catch(error => {
                        Swal.fire({
                            title: "Error",
                            icon: 'error',
                            text: error,
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK'
                        });
                    })
            }
        });


    }

    function addUser(event) {
        event.preventDefault();
        var form = document.querySelector('#add-user-modal form');
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        document.querySelector('#btn-add-user').setAttribute('disabled', 'disabled');
        document.querySelector('#btn-add-user #btn-spinner').removeAttribute('hidden')

        var username = document.querySelector('#add-user-modal #username').value;
        var password = document.querySelector('#add-user-modal #password').value;
        var firstName = document.querySelector('#add-user-modal #first-name').value;
        var lastName = document.querySelector('#add-user-modal #last-name').value;
        var phoneNumber = document.querySelector('#add-user-modal #phone-number').value;
        var email = document.querySelector('#add-user-modal #email').value;
        var address = document.querySelector('#add-user-modal #address').value;
        var isStaff = document.querySelector('#add-user-modal #staff-member').checked;

        var payload = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            emailAddress: email,
            address: address,
            staffMember: isStaff
        };

        fetch("${pageContext.request.contextPath}/admin/users/add", {
            method: "POST",
            body: JSON.stringify(payload)
        })
            .then(response => response.json())
            .then(json => {
                if (json.statusCode === 200) {
                    Swal.fire({
                        title: "Success",
                        target: '#add-user-modal',
                        icon: 'success',
                        text: "User added successfully",
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            form.reset();
                            window.location.reload();
                        }
                    });
                } else {
                    Swal.fire({
                        title: "Error",
                        target: '#add-user-modal',
                        icon: 'error',
                        text: json.data,
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        document.querySelector('#btn-add-user').removeAttribute('disabled');
                        document.querySelector('#btn-add-user #btn-spinner').setAttribute('hidden', 'hidden')
                    });
                }
            }).catch(error => {
            Swal.fire({
                title: "Error",
                target: '#add-user-modal',
                icon: 'error',
                text: error,
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK'
            }).finally(() => {
                document.querySelector('#btn-add-user').removeAttribute('disabled');
                document.querySelector('#btn-add-user #btn-spinner').setAttribute('hidden', 'hidden')
            });
        })

    }

    var table = new Tabulator("#user-table", {
        ajaxURL: "${pageContext.request.contextPath}/admin/users",
        ajaxConfig: {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        },
        ajaxResponse: function (url, params, response) {
            return {
                "data": response.data.items,
                "last_page": response.data.totalPages,
            }
        },
        layout: "fitColumns",
        paginationMode: "remote",
        paginationSize: 10,
        pagination: true,
        dataSendParams: {
            "size": "limit"
        },
        columns: [
            {title: "User ID", field: "id", width: 150},
            {title: "Username", field: "username", width: 150},
            {title: "First Name", field: "firstName", width: 150},
            {title: "Last Name", field: "lastName", width: 150},
            {title: "Phone", field: "phoneNumber", width: 150},
            {title: "Address", field: "address", width: 150},
            {title: "Email", field: "email", width: 150},
            {
                title: "Staff",
                field: "isStaff",
                hozAlign: "center",
                width: 50,
                formatter: "tickCross",
            },
            {
                title: "Actions",
                formatter: function (cell, formatterParams) {
                    return `
                        <button class='btn btn-primary btn-sm js-show-modal1 border-dark' onClick='showEditUserModal(\${cell.getData().id})'>Edit</button>
                        <button class='btn btn-danger btn-sm border-dark' onclick='deleteUser(\${cell.getData().id})'>Delete</button>
                    `
                },
                width: 150,
                frozen: true,
                hozAlign: "center"
            }
        ]
    });


    function showEditUserModal(id) {
        fetch("${pageContext.request.contextPath}/admin/user/" + id, {
            method: "GET"
        })
            .then(response => response.json())
            .then(json => {
                if (json.statusCode === 200) {
                    var user = json.data;
                    document.querySelector('#btn-edit-user').setAttribute('data-user-id', user.id);
                    document.querySelector('#edit-user-modal #username').value = user.username;
                    document.querySelector('#edit-user-modal #password').placeholder = "(unchanged)"
                    document.querySelector('#edit-user-modal #first-name').value = user.firstName;
                    document.querySelector('#edit-user-modal #last-name').value = user.lastName;
                    document.querySelector('#edit-user-modal #phone-number').value = user.phoneNumber;
                    document.querySelector('#edit-user-modal #email').value = user.email;
                    document.querySelector('#edit-user-modal #address').value = user.address;
                    document.querySelector('#edit-user-modal #staff-member').checked = user.isStaff;
                    $('#edit-user-modal').modal('show');
                } else {
                    Swal.fire({
                        title: "Error",
                        icon: 'error',
                        text: json.data,
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    });
                }
            })
            .catch(error => {
                Swal.fire({
                    title: "Error",
                    icon: 'error',
                    text: error,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            })
    }

    function updateUser(event) {
        event.preventDefault();
        var form = document.querySelector('#edit-user-modal form');
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        var username = document.querySelector('#edit-user-modal #username').value;
        var password = document.querySelector('#edit-user-modal #password').value;
        var firstName = document.querySelector('#edit-user-modal #first-name').value;
        var lastName = document.querySelector('#edit-user-modal #last-name').value;
        var phoneNumber = document.querySelector('#edit-user-modal #phone-number').value;
        var email = document.querySelector('#edit-user-modal #email').value;
        var address = document.querySelector('#edit-user-modal #address').value;
        var isStaff = document.querySelector('#edit-user-modal #staff-member').checked;


        var payload = {
            username: username,
            password: password ? password : undefined,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            emailAddress: email,
            address: address,
            staffMember: isStaff
        };

        document.querySelector('#btn-edit-user').setAttribute('disabled', 'disabled');
        document.querySelector('#btn-edit-user #btn-spinner').removeAttribute('hidden')

        fetch("${pageContext.request.contextPath}/admin/user/" + document.querySelector('#btn-edit-user').getAttribute('data-user-id'), {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.json())
            .then(json => {
                if (json.statusCode === 200) {
                    Swal.fire({
                        title: "Success",
                        icon: 'success',
                        text: "User updated successfully",
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            form.reset();
                            window.location.reload();
                        }
                    });
                } else {
                    Swal.fire({
                        title: "Error",
                        icon: 'error',
                        text: json.data,
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        document.querySelector('#btn-edit-user').removeAttribute('disabled');
                        document.querySelector('#btn-edit-user #btn-spinner').setAttribute('hidden', 'hidden')
                    });
                }
            })
            .catch(error => {
                Swal.fire({
                    title: "Error",
                    icon: 'error',
                    text: error,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                }).finally(() => {
                    document.querySelector('#btn-edit-user').removeAttribute('disabled');
                    document.querySelector('#btn-edit-user #btn-spinner').setAttribute('hidden', 'hidden')
                });
            })


    }

    $('#search-input').on('keydown', function (ev) {
        if (ev.keyCode === 13) {
            var searchTerm = ev.target.value;

            if (!searchTerm) {
                table.setData("${pageContext.request.contextPath}/admin/users")
            } else {
                table.setData("${pageContext.request.contextPath}/admin/users?searchName=" + searchTerm);
            }
        }
    })