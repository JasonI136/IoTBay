<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 23/4/2023
  Time: 11:50 pm
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>

    var contextPath = "${pageContext.request.contextPath}";

    async function updateAccountDetails() {
        $('#updateBtn').toggle();
        $('#editBtn button').text("Edit Account Details");

        const userDetailsForm = document.getElementById("userDetailsForm");
        const formData = new FormData(userDetailsForm);
        console.log("Context path:", contextPath);

        await fetch(contextPath + "/user/details/modify", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: userDetailsForm.username.value,
                firstname: userDetailsForm.firstname.value,
                lastname: userDetailsForm.lastname.value,
                address: userDetailsForm.address.value,
                email: userDetailsForm.email.value,
                phone: userDetailsForm.phone.value
            })
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                if (data.statusCode === 200) {
                    // Display a success message
                    swal.fire({
                        title: 'Success',
                        icon: 'success',
                        text: "You're details have been updated",
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    });
                } else {
                    // Display an error message
                    swal.fire({
                        title: data.message,
                        icon: 'error',
                        text: data.data,
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    });
                }
            })
            .catch(error => {
                swal.fire({
                    title: 'Error',
                    icon: 'error',
                    text: "An unexpected error occurred",
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            });

        var inputs = document.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].getAttribute("disabled")) {
                inputs[i].removeAttribute("disabled");
            } else {
                inputs[i].setAttribute("disabled", "disabled");
            }
        }
    }

    function toggleEdit() {
        $('#updateBtn').toggle();
        const editBtn = $('#editBtn button');
        if (editBtn.text().trim() === "Edit Account Details") {
            editBtn.text("Cancel");
        } else {
            editBtn.text("Edit Account Details");
        }

        const inputs = document.getElementsByTagName("input");
        for (let i = 0; i < inputs.length; i++) {
            if (inputs[i].getAttribute("disabled")) {
                inputs[i].removeAttribute("disabled");
            } else {
                inputs[i].setAttribute("disabled", "disabled");
            }
        }
    }
