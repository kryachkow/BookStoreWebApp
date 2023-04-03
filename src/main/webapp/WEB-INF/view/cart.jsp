<!DOCTYPE html>
<%@include file="/WEB-INF/include/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <title>Cart</title>
</head>
<body>

<header class="align--center pt3">
    <%@include file="/WEB-INF/include/navbar.jspf" %>
    <div class="container--lg border--bottom pb3">
        <h1 class="mb2">Cart</h1>
    </div>
</header>

<c:choose>
    <c:when test="${sessionScope.cart.isEmpty()}">
        <div class="align--center pt1 pb1">
            <p class="h4 text--dark-gray bold">Your cart is empty!</p>
        </div>
    </c:when>
    <c:otherwise>

        <div>
            <table class="container--lg pt1 pb1">
                <thead>
                <tr class="bg--primary-color align--center pt3 pb3">
                    <th class="h3 text--white mb1 p1 bold align--center">Book Title</th>
                    <th class="h3 text--white mb1 p1 bold align--center">Author</th>
                    <th class="h3 text--white mb1 p1 bold align--center">Publisher</th>
                    <th class="h3 text--white mb1 p1 bold align--center">Quantity</th>
                    <th class="h3 text--white mb1 p1 bold align--center">Price</th>
                    <th class="h3 text--white mb1 p1 bold align--center">Remove</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${requestScope.orderParts}" var="orderPart">
                    <tr class="tab">
                        <th class="align--center p1">${orderPart.bookEntity.bookTitle}</th>
                        <th class="align--center p1">${orderPart.bookEntity.author}</th>
                        <th class="align--center p1">${orderPart.bookEntity.publisherEntity.name}</th>
                        <th class="align--center p1">
                            <div class="inline-block">
                                <form action="cart" method="get">
                                    <input type="hidden" name="bookId"
                                           value="${orderPart.bookEntity.id}">
                                    <input type="hidden" name="command" value="decrease">
                                    <button type="submit" class="inline-block btn--default">-
                                    </button>
                                </form>
                                    ${orderPart.quantity}
                                <form action="cart" method="get">
                                    <input type="hidden" name="bookId"
                                           value="${orderPart.bookEntity.id}">
                                    <input type="hidden" name="command" value="increase">
                                    <button type="submit" class="inline-block btn--default">+
                                    </button>
                                </form>
                            </div>
                        </th>
                        <th class="align--center p1">${orderPart.bookEntity.price}</th>
                        <th class="align--center p1">
                            <form action="cart" method="get">
                                <input type="hidden" name="bookId" id="bookId"
                                       value="${orderPart.bookEntity.id}">
                                <input type="hidden" name="command" id="command" value="remove">
                                <button class="centered btn--default bt" type="submit">X</button>
                            </form>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="centered align--center">
                <h1 class="text--dark-gray centered">Total price is ${requestScope.totalPrice}</h1>
            </div>
        </div>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <div class="align--center pt1 pb1">
                    <p class="h4 text--dark-gray bold">Please <a href="signIn">Sign In</a> or <a
                            href="signUp">SignUp</a> to make the order! </p>
                </div>
            </c:when>

            <c:otherwise>
                <form action="order" method="post">
                    <div class="align--center">
                        <label class="h4 text--dark-gray bold" for="paymentTypeId">Choose payment type</label>
                        <select id="paymentTypeId" name="paymentTypeId" required>
                            <c:forEach items="${requestScope.paymentTypes}" var="paymentType">
                                <option value=${paymentType.id}>
                                        ${paymentType.paymentType}
                                </option>
                            </c:forEach>
                        </select>

                        <label class="h4 text--dark-gray mb1 p1 bold" for="paymentDetails">Payment details:</label>
                        <input type="text" id="paymentDetails" name="paymentDetails"
                               placeholder="Enter your phone and payment details(card number/your address)"
                               minlength="4">

                    </div>

                    <div class="centered align--center">
                        <button type="submit" class="btn--default btn">Make Order</button>
                    </div>

                </form>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


<div class="align--center" style="color: red ; text-align: center;">
    <c:out value="${requestScope.error}" default=""/>
</div>


</body>
</html>
