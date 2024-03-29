package com.task.bookstorewebbapp.tag;

import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.model.User;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AvatarTag extends TagSupport {

  private static final String GUEST_NAV = "<a class=\"h3 mb1 p1 bold float--right pr4 inline-block\" href=\"guest/signIn\">SignIn</a>\n";
  private static final String USER_NAV = """
      <div class="top-nav">
          <div class="float--right pr4 pt2">
              <h3 class="inline-block">%s</h1>
              <img class="inline-block" src="%s" alt="%s" style="width:50px;height:50px;">
              <a class="h3 mb1 p1 bold float--right pr4 inline-block" href="../cart">Cart</a>
          </div>
      </div>""";

  private static final String CART_NAV = """
      <a class="h3 mb1 p1 bold float--right pr4 inline-block" href="cart">Cart</a>
            """;


  @Override
  public int doStartTag() throws JspException {
    JspWriter out = pageContext.getOut();
    User user = (User) pageContext.getSession().getAttribute(Constants.USER_ATTRIBUTE);
    try {
      return user == null
          ? generateGuestHeader(out) : generateUserHeader(out, user);
    } catch (IOException e) {
      throw new JspException(e);
    }
  }

  private int generateUserHeader(JspWriter out, User user) throws IOException {
    out.print(
        String.format(USER_NAV, user.getNickname(), user.getAvatarSource(), user.getNickname()));
    return SKIP_BODY;
  }


  private int generateGuestHeader(JspWriter out) throws IOException {
    out.print(GUEST_NAV + CART_NAV);
    return SKIP_BODY;
  }
}
