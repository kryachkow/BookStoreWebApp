<%@include file="WEB-INF/include/header.jspf"%>
<html>
<head>
  <%@include file="/WEB-INF/include/head.jspf" %>
  <title>Book Store</title>
</head>
<body>

<header class="align--center pt3">
  <div class="container border--bottom pb3">
    <h1 class="mb2">Welcome to BookStore!</h1>
  </div>
</header>

<main>
  <div class="container pt3 mt2 text--gray align--center">
    <p class="mb3">Menu</p>
    <div class="grid-row">
      <div class="grid-column span-half mb3">
        <a class="link" href="hello-servlet" title="stylesheet">Catalog</a>
      </div>
      <div class="grid-column span-half mb3">
        <a class="link" href="signUp" title="stylesheet">SignUp</a>
      </div>
    </div>
  </div>
</main>

</body>
</html>