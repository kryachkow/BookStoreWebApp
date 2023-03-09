<!DOCTYPE html>
<%@include file="/WEB-INF/include/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <title>Catalog</title>
</head>
<body>

<header class="align--center pt3">
    <%@include file="/WEB-INF/include/navbar.jspf" %>
    <div class="container--lg border--bottom pb3">
        <h1 class="mb2">Catalog</h1>
    </div>
    <nav>
        <ul>
            <li class="inline-block">
                <form action="catalog" method="get" id="pageSizeForm">
                    <input type="hidden" name="pageNumber" value="1">
                    <label for="pageSize">Page Size:</label>
                    <select id="pageSize" name="pageSize">
                        <option value="5" ${requestScope.pagination.pageSize == 5 ? ' selected' : ''}>
                            5
                        </option>
                        <option value="10" ${requestScope.pagination.pageSize == 10 ? ' selected' : ''}>
                            10
                        </option>
                        <option value="20" ${requestScope.pagination.pageSize == 20 ? ' selected' : ''}>
                            20
                        </option>
                    </select>
                </form>
            </li>

            <li class="inline-block">
                <form action="catalog" method="get" id="sortingForm">
                    <input type="hidden" name="pageNumber" value="1">
                    <label for="sorting">Sorting type:</label>
                    <select id="sorting" name="sorting">
                        <option value="bookTitle" ${requestScope.catalogFilter.sorting == 'bookTitle' ? ' selected' : ''}>
                            Book Title
                        </option>
                        <option value="price" ${requestScope.catalogFilter.sorting == 'price' ? ' selected' : ''}>
                            Price
                        </option>
                        <option value="author" ${requestScope.catalogFilter.sorting == 'author' ? ' selected' : ''}>
                            Author
                        </option>
                    </select>
                </form>
            </li>

            <li class="inline-block">
                <form action="catalog" method="get" id="invertedForm">
                    <input type="hidden" name="pageNumber" value="1">
                    <label for="inverted">Inverted:</label>
                    <input type="checkbox" name="inverted"
                           id="inverted" ${requestScope.catalogFilter.inverted ? 'checked' : ''}
                           value="${!requestScope.catalogFilter.inverted}">
                </form>
            </li>
        </ul>
    </nav>
</header>

<main>
    <div class="align--center">
        <form action="catalog" method="get" id="searchForm">
            <input type="hidden" name="pageNumber" value="1">
            <input type="hidden" name="pageSize" value="${requestScope.pagination.pageSize}">
            <input type="hidden" name="inverted" value="${requestScope.catalogFilter.inverted}">
            <input type="hidden" name="sorting" value="${requestScope.catalogFilter.sorting}">

            <ul>
                <li class="inline-block">
                    <label for="titleSearch">Title search</label>
                    <input type="text" id="titleSearch" name="titleSearch" minlength="1"
                           maxlength="32" placeholder="Enter title to search"
                           value="<c:out value="${requestScope.catalogFilter.titleSearch}" default="" />">
                </li>
                <li class="inline-block">
                    <label for="minPrice">Min price</label>
                    <input type="number" id="minPrice" name="minPrice" min="0" max="20000"
                           placeholder="Enter min price"
                           value="<c:out value="${requestScope.catalogFilter.minPrice}" default="" />">
                </li>
                <li class="inline-block">
                    <label for="maxPrice">Max price</label>
                    <input type="number" id="maxPrice" name="maxPrice" min="0" max="20000"
                           placeholder="Enter max price"
                           value="<c:out value="${requestScope.catalogFilter.maxPrice}" default="" />">
                </li>

                <li class="inline-block">
                    <label for="categoryId">Category</label>
                    <select id="categoryId" name="categoryId">
                        <option value="0">No category search</option>
                        <c:forEach items="${requestScope.categories}" var="category">
                            <option value=${category.id} ${requestScope.catalogFilter.categoryId == category.id ? ' selected' : ''}>
                                    ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </li>
                <li class="inline-block">
                    <label for="publisherId">Publisher</label>
                    <select id="publisherId" name="publisherId">
                        <option value="0">No publisher search</option>
                        <c:forEach items="${requestScope.publishers}" var="publisher">
                            <option value=${publisher.id} ${requestScope.catalogFilter.publisherId == publisher.id ? ' selected' : ''}>
                                    ${publisher.name}
                            </option>
                        </c:forEach>
                    </select>
                </li>
                <li class="inline-block">
                    <button class="btn btn--sm" type="submit">Apply!</button>
                </li>
            </ul>

        </form>

    </div>
    <div class="rounded">
        <table class="container--lg pt1 pb1">
            <thead>
            <tr class="bg--dark-gray align--center pt3 pb3">
                <th class="h3 text--white mb1 p1 bold align--center">Book Title</th>
                <th class="h3 text--white mb1 p1 bold align--center">Author</th>
                <th class="h3 text--white mb1 p1 bold align--center">Publisher</th>
                <th class="h3 text--white mb1 p1 bold align--center">Category</th>
                <th class="h3 text--white mb1 p1 bold align--center">Page Number</th>
                <th class="h3 text--white mb1 p1 bold align--center">Price</th>
                <th class="h3 text--white mb1 p1 bold align--center">Add To Cart</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.books}" var="book">
                <tr class="tab">
                    <th class="align--center p1">${book.bookTitle}</th>
                    <th class="align--center p1">${book.author}</th>
                    <th class="align--center p1">${book.publisherEntity.name}</th>
                    <th class="align--center p1">${book.categoryEntity.name}</th>
                    <th class="align--center p1">${book.pageNumber}</th>
                    <th class="align--center p1">${book.price}</th>
                    <th class="align--center p1">
                        <button class="btn btn--secondary">Add To Cart</button>
                    </th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>



    <div class="container--lg pt1 pb1 centered">
        <ul class="inline-block">
            <li class="inline-block">
                <form action="catalog" method="get" name="pageForm" class="pageForm">
                    <input type="hidden" name="pageNumber" value="1">
                    <div ${(requestScope.pagination.pageNumber <= 2) ? 'hidden' : ''} >
                        <button type="submit" class="btn btn-primary mb-2">1</button>
                    </div>
                </form>
            </li>

            <li class="inline-block">
                <div ${(requestScope.pagination.pageNumber <= 2 ) ? 'hidden' : ''}>
                    <button class="btn btn-primary mb-2">...</button>
                </div>
            </li>


            <li class="inline-block">

                <form action="catalog" method="get" name="pageForm" class="pageForm">
                    <input type="hidden" name="pageNumber"
                           value="${requestScope.pagination.previousPage}">
                    <div class="col-auto" ${(requestScope.pagination.pageNumber > 1) ? '' : 'hidden'}>
                        <button type="submit"
                                class="btn btn-primary mb-2">${requestScope.pagination.previousPage}</button>
                    </div>
                </form>

            </li>

            <li class="inline-block btn btn-primary mb-2">
                <p><c:out value="${requestScope.pagination.pageNumber}"/></p>
            </li>

            <li class="inline-block">
                <form action="catalog" method="get" name="pageForm" class="pageForm" >
                    <input type="hidden" name="pageNumber"
                           value="${requestScope.pagination.nextPage}">
                    <div class="col-auto" ${requestScope.pagination.nextPage != -1 ? '' : 'hidden'}>
                        <button type="submit"
                                class="btn btn-primary mb-2">${requestScope.pagination.nextPage}</button>
                    </div>
                </form>
            </li>

            <li class="inline-block">
                <div ${requestScope.pagination.lastPage == -1 ||
                        requestScope.pagination.lastPage == requestScope.pagination.nextPage
                        ? 'hidden' : ''}>
                    <button class="btn btn-primary mb-2">...</button>
                </div>
            </li>


            <li class="inline-block">
                <form action="catalog" method="get" name="pageForm" class="pageForm">
                    <input type="hidden" name="pageNumber"
                           value="${requestScope.pagination.lastPage}">
                    <div class="col-auto" ${requestScope.pagination.lastPage == -1 ||
                            requestScope.pagination.lastPage == requestScope.pagination.nextPage ? 'hidden' : ''} >
                        <button type="submit"
                                class="btn btn-primary mb-2">${requestScope.pagination.lastPage}</button>
                    </div>
                </form>
            </li>
        </ul>
    </div>
    <script type="application/javascript"
            src="${pageContext.request.contextPath}/js/catalog.js"></script>
</main>

</body>
</html>
