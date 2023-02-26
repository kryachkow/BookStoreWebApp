<%@include file="/WEB-INF/include/header.jspf" %>
<%@ taglib prefix="m" uri="/WEB-INF/include/captchaTag.tld" %>
<html>
<head>
    <%@include file="/WEB-INF/include/head.jspf" %>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <title>Sign Up</title>
</head>
<body>
<header class="align--center pt3">
    <div class="container--lg border--bottom pb3">
        <h1 class="mb2">Sign Up</h1>
    </div>
</header>

<main>
    <div class="bg--light-gray rounded p1 m2 border--full align--center">
        <form id="form" method="post" action="signUp" enctype="multipart/form-data">

            <table class="table container--lg pt1 pb2">
                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="email">Email</label>
                    </td>
                    <td>
                        <input class="form-control" placeholder="Enter Email" name="email" type="email"
                               id="email" value="<c:out value="${requestScope.registrationForm.email}" default="" />"
                               required>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="name">Name</label>
                    </td>
                    <td>
                        <input class="form-control" type="text" placeholder="Enter name" name="name"
                               id="name"
                               required minlength="4" maxlength="24"
                        value="<c:out value="${requestScope.registrationForm.name}" default="" />">
                    </td>
                </tr>

                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="surname">Surname</label>
                    </td>
                    <td>
                        <input class="form-control" type="text" placeholder="Enter surname" name="surname"
                               id="surname"
                               required minlength="4" maxlength="24"
                               value="<c:out value="${requestScope.registrationForm.surname}" default="" />">
                    </td>
                </tr>


                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="nickname">Nickname</label>
                    </td>
                    <td>
                        <input class="form-control" placeholder="Enter Nickname"
                               name="nickname"
                               id="nickname"
                               minlength="4"
                               maxlength="24"
                               required value="<c:out value="${requestScope.registrationForm.nickname}" default="" />">
                    </td>
                </tr>

            </table>

            <table id = "table" class="container--lg pt1 pb2">
                <tr>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="password">Password</label>
                        <input class="form-control" type="password" placeholder="Enter Password"
                               name="password"
                               id="password"
                               required
                               minlength="8"
                               maxlength="32">
                    </td>
                    <td>
                        <label class="h3 text--white mb1 p1 bold" for="repeatPassword">Repeat
                            Password</label>
                        <input class="form-control" type="password" placeholder="Repeat Password"
                               name="repeatPassword"
                               id="repeatPassword"
                               required minlength="8"
                               maxlength="32">
                    </td>
                </tr>
            </table>
            <div class="inline-block">
                <label class="h3 text--white mb1 p1 bold" for="mailingSubscription">Mailing Subscription</label>
                <input class="ui-icon-circle-check " type="checkbox" name="mailingSubscription" id = "mailingSubscription" value = "true">
            </div>

            <div class="inline-block">
                <label class="h3 text--white mb1 p1 bold" for="avatar">Upload JPG avatar!</label>
                <input class="btb btn--sm " type="file" name="avatar" id = "avatar" accept=".jpg">
            </div>

            <div class="centered p1">
                <div class="inline-block">
                    <label class="h3 text--white mb1 p1 bold" for="captchaInput">Captcha</label>
                    <input class="ui-icon-circle-check " type="text" name="captchaInput" id = "captchaInput">
                </div>
                <div class="inline-block">
                    <m:captcha>

                    </m:captcha>
                </div>
            </div>


            <div style="color: red ; text-align: center;">
                <c:out value="${requestScope.signUpError}" default="" />
            </div>

            <div>
                <button class="btn btn--secondary m1" type="submit">Sign Up</button>
            </div>


        </form>
    </div>
</main>

<script type="application/javascript" src="${pageContext.request.contextPath}/js/signUpValidation.js"></script>
</body>
</html>
