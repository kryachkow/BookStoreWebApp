package com.task.bookstorewebbapp.tag;

import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CaptchaTag extends TagSupport {

  private static final String IMAGE_CODE_FIRST_PART = "<img src=\"data:image/jpeg;base64,";
  private static final String IMAGE_CODE_SECOND_PART = "\" title=\"captcha.jpeg\">";
  private static final String HIDDEN_FIELD_CODE_FIRST_PART = "<input type=\"hidden\" id=\"captchaId\" name=\"captchaId\"  value=\"";
  private static final String HIDDEN_FIELD_CODE_SECOND_PART = "\">";


  @Override
  public int doStartTag() throws JspException {
    JspWriter out = pageContext.getOut();
    try {
      out.print(IMAGE_CODE_FIRST_PART + pageContext.getRequest().getAttribute(
          Constants.CAPTCHA_IMAGE_ATTRIBUTE) + IMAGE_CODE_SECOND_PART);
      String captchaId = String.valueOf(
          pageContext.getRequest().getAttribute(Constants.CAPTCHA_ID_ATTRIBUTE));
      if (captchaId != null) {
        out.print(HIDDEN_FIELD_CODE_FIRST_PART
            + pageContext.getRequest().getAttribute(Constants.CAPTCHA_ID_ATTRIBUTE)
            + HIDDEN_FIELD_CODE_SECOND_PART);
      }
    } catch (IOException e) {
      throw new JspException(e);
    }
    return SKIP_BODY;
  }

}
