package com.task.bookstorewebbapp.filter.gzip;

import com.task.bookstorewebbapp.filter.gzip.wrapper.GZipWrapper;
import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


//@WebFilter("/*")
public class GzipFilter implements Filter {



  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if ( canZip(httpRequest) ) {
      httpResponse.addHeader(Constants.CONTENT_ENCODING_HEADER, Constants.ENCODING);
      GZipWrapper gzipResponse =
          new GZipWrapper(httpResponse);
      chain.doFilter(request, gzipResponse);
      gzipResponse.finish();
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean canZip(HttpServletRequest httpRequest) {
    String acceptEncoding =
        httpRequest.getHeader(Constants.ACCEPT_ENCODING_HEADER);

    return acceptEncoding != null &&
        acceptEncoding.contains(Constants.ENCODING) && !httpRequest.getRequestURI().contains("error");
  }
}
