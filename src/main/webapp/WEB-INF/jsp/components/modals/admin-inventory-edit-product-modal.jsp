<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 5:32 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" style="color: #0b0b0b" tabindex="-1" id="edit-product-modal">
    <div class="modal-dialog modal-xl">
        <form>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col">
                            <div class="mb-3">
                                <label for="product-name" class="form-label">Product Name<span
                                        style="color: red"><b>*</b></span></label>
                                <input type="text" id="product-name" class="form-control" required/>
                            </div>
                            <div class="mb-3">
                                <label for="product-price" class="form-label">Price<span
                                        style="color: red"><b>*</b></span></label>
                                <div class="input-group">
                                    <span class="input-group-text">$</span>
                                    <input type="number" step="0.001" id="product-price" class="form-control" required/>
                                </div>

                                <div class="form-text">
                                    Precision up to 3 decimal places.
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="product-description" class="form-label">Description</label>
                                <textarea class="form-control" id="product-description" rows="10"></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="product-quantity" class="form-label">Stock<span
                                        style="color: red"><b>*</b></span></label>
                                <input type="number" id="product-quantity" class="form-control" required/>
                            </div>
                            <div class="mb-3">
                                <label for="product-image-url" class="form-label">Image URL</label>
                                <input type="text" id="product-image-url" class="form-control"/>
                            </div>

                            <div class="mb-3">
                                <label for="product-category" class="form-label">Category<span
                                        style="color: red"><b>*</b></span></label>
                                <select class="form-select" id="product-category" required>
                                </select>
                            </div>
                        </div>
                        <div class="col">
                            <img src="https://placehold.co/600x400" class="img-thumbnail" id="img-product-modal">
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button class="btn btn-primary" onclick="updateProduct(event)" type="submit"
                            id="btn-update-product">
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" id="btn-spinner"
                              hidden=""></span>
                        Save
                    </button>
                </div>
            </div>
        </form>
    </div>

</div>