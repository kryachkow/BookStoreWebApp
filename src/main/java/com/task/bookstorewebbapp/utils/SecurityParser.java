package com.task.bookstorewebbapp.utils;


import com.task.bookstorewebbapp.model.security.SecurityParameters;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class SecurityParser {


  private static final String CONSTRAINT = "constraint";
  private static final String URL_PATTERN = "url-pattern";
  private static final String ROLE = "role";


  private SecurityParser() {
  }


  public static List<SecurityParameters> getSecurityParameters(String resource) {
    List<SecurityParameters> securityParameters = new ArrayList<>();
    try {
      Document jdom = buildDocument(resource);
      Element root = jdom.getRootElement();
      for (Element constraintElement : root.getChildren(CONSTRAINT)) {
        securityParameters.add(getParamsFromElement(constraintElement));
      }
    } catch (IOException | JDOMException e) {
      throw new RuntimeException(e);
    }
    return securityParameters;
  }

  private static Document buildDocument(String resource)
      throws JDOMException, IOException {
    SAXBuilder saxBuilder = new SAXBuilder();
    return saxBuilder.build(new File(resource));
  }

  private static SecurityParameters getParamsFromElement(Element element) {
    SecurityParameters securityParameters = new SecurityParameters();
    securityParameters.setUrlPattern(element.getChildText(URL_PATTERN));
    List<String> roleList = new ArrayList<>();
    for (Element roleElems : element.getChildren(ROLE)) {
      roleList.add(roleElems.getText());
    }
    securityParameters.setRoleNames(roleList);
    return securityParameters;
  }


}
