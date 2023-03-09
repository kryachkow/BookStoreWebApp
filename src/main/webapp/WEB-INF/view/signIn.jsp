<%@include file="/WEB-INF/include/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <title>Sign In</title>
</head>
<body>
<header class="align--center pt3">
    <div class="container--lg border--bottom pb3">
        <h1 class="mb2">Sign In</h1>
    </div>
</header>

<main>
    <div class="bg--light-gray rounded p1 m2 border--full align--center">
        <form id="form" method="post" action="signIn">

            <table class="table container--lg pt1 pb2">
                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="email">Email</label>
                    </td>
                    <td>
                        <input class="form-control" placeholder="Enter Email" name="email" type="email"
                               id="email" value="<c:out value="${requestScope.signInForm.email}" default="" />"
                               required>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="password">Password</label>
                    </td>
                    <td>
                        <input class="form-control" type="password" placeholder="Enter Password"
                               name="password"
                               id="password"
                               required
                               minlength="8"
                               maxlength="32">
                    </td>
                </tr>
            </table>


            <div style="color: red ; text-align: center;">
                <c:out value="${requestScope.signInError}" default="" />
            </div>

            <div>
                <button class="btn btn--secondary m1" type="submit">Sign In</button>
            </div>


        </form>
    </div>
</main>

<script type="application/javascript" src="${pageContext.request.contextPath}/js/signInValidation.js"></script>
</body>
</html>
