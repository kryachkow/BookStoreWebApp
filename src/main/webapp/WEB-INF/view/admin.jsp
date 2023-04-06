<!DOCTYPE html>
<%@include file="/WEB-INF/include/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <title>ERROR 403 ACCESS FORBIDDEN</title>
</head>
<body>

<header class="align--center pt3">
    <%@include file="/WEB-INF/include/navbar.jspf" %>
</header>

<main>
    <div class="align--center pt3">
        <div class="container border--bottom pb3">
            <h1 class="mb2">ADMIN</h1>
        </div>
    </div>
    <div class="container pt3 mt2 text--gray align--center">
        <p class="mb3">Menu</p>
        <div class="grid-row">
            <div class="grid-column span-half mb3">
                <a class="link" href="../catalog" title="stylesheet">Catalog</a>
            </div>
            <c:choose >
                <c:when test="${sessionScope.user == null}">
                    <div class="grid-column span-half mb3">
                        <a class="link" href="../guest/signUp">SignUp</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid-column span-half mb3">
                        <a class="link" href="../user/logOut">LogOut</a>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</main>

</body>
</html>
