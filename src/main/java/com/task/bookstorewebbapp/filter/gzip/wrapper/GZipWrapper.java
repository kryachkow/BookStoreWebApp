package com.task.bookstorewebbapp.filter.gzip.wrapper;

import com.task.bookstorewebbapp.filter.gzip.stream.GzipServletOutputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipWrapper extends HttpServletResponseWrapper {


  private GzipServletOutputStream gzipStream;
  private ServletOutputStream outputStream;
  private PrintWriter printWriter;

  public GZipWrapper(HttpServletResponse response) throws IOException {
    super(response);
  }

  public void finish() throws IOException {
    if (printWriter != null) {
      printWriter.close();
    }
    if (outputStream != null) {
      outputStream.close();
    }
    if (gzipStream != null) {
      gzipStream.close();
    }
  }

  @Override
  public void flushBuffer() throws IOException {
    if (printWriter != null) {
      printWriter.flush();
    }
    if (outputStream != null) {
      outputStream.flush();
    }
    super.flushBuffer();
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (printWriter != null) {
      throw new IllegalStateException("printWriter already defined");
    }
    if (outputStream == null) {
      initGzip();
      outputStream = gzipStream;
    }
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (outputStream != null) {
      throw new IllegalStateException("printWriter already defined");
    }
    if (printWriter == null) {
      initGzip();
      printWriter = new PrintWriter(new OutputStreamWriter(gzipStream, getResponse().getCharacterEncoding()));
    }
    return printWriter;
  }

  @Override
  public void setContentLength(int len) {
  }

  private void initGzip() throws IOException {
    gzipStream = new GzipServletOutputStream(getResponse().getOutputStream());
  }

}
