<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 5:32 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="modal fade" style="color: #0b0b0b" tabindex="-1" id="add-user-modal">
    <div class="modal-dialog">
        <form>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username<span
                                style="color: red"><b>*</b></span></label>
                        <input type="text" id="username" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password<span
                                style="color: red"><b>*</b></span></label>
                        <input type="password" id="password" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="first-name" class="form-label">First Name<span
                                        style="color: red"><b>*</b></span></label>
                                <input type="text" id="first-name" class="form-control" required/>
                            </div>
                            <div class="col">
                                <label for="last-name" class="form-label">Last Name<span
                                        style="color: red"><b>*</b></span></label>
                                <input type="text" id="last-name" class="form-control" required/>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="phone-number" class="form-label">Phone Number<span
                                style="color: red"><b>*</b></span></label>
                        <input type="text" id="phone-number" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email Address<span
                                style="color: red"><b>*</b></span></label>
                        <input type="email" id="email" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address<span
                                style="color: red"><b>*</b></span></label>
                        <input type="text" id="address" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Staff Member<span
                                style="color: red"><b>*</b></span></label>
                        <input type="checkbox" id="staff-member" class="form-check-input"/>
                        <div class="form-text">Checking this field will give the user access to the admin panel.</div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addUser(event)" type="submit"
                            id="btn-add-user">
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" id="btn-spinner"
                              hidden=""></span>
                        Add
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>