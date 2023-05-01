<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 1/5/2023
  Time: 10:30 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://unpkg.com/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
<script type="text/javascript" src="https://unpkg.com/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>
<main>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Inventory</h1>

        <div class="row">
            <div class="card p-all-10">
                <div class="flex-b" style="gap: 5px">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <i class="fa-solid fa-basket-shopping"></i>

                            Products
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" onclick="showAddProductModal()"><i
                                    class="fa-solid fa-plus"></i> Add</a></li>
                        </ul>
                    </div>
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <i class="fa fa-object-group" aria-hidden="true"></i>

                            Category
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" onclick="addCategory()"><i
                                    class="fa-solid fa-plus"></i>
                                Add</a></li>
                            <li><a class="dropdown-item" onclick="deleteCategory()"><i
                                    class="fa-solid fa-trash-can"></i> Delete</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="card p-all-10 gy-2">
                <div class="input-group">
                    <span class="input-group-text" id="basic-addon1">Search</span>
                    <input type="text" class="form-control" placeholder="Enter search term"
                           aria-describedby="basic-addon1" id="search-input">
                </div>
            </div>
        </div>

        <div class="row">
            <div id="product-table" class="gy-2 bg-transparent p-all-0 "></div>
        </div>
    </div>
</main>

<!-- Edit Product Modal-->
<jsp:include page="../../components/modals/admin-inventory-edit-product-modal.jsp"/>

<!-- Add Product Modal-->
<jsp:include page="../../components/modals/admin-inventory-add-product-modal.jsp"/>


<script src="${pageContext.request.contextPath}/public/js/jsp/admin-inventory.js.jsp"></script>
