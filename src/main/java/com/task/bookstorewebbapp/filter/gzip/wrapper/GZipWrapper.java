package com.task.bookstorewebbapp.filter.gzip.wrapper;

import com.task.bookstorewebbapp.filter.gzip.stream.GzipServletOutputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipWrapper extends HttpServletResponseWrapper {

  private GzipServletOutputStream gzipOutputStream;
  private PrintWriter printWriter;

  public GZipWrapper(HttpServletResponse response) {
    super(response);
  }

  public void close() throws IOException {
    if (gzipOutputStream != null) {
      gzipOutputStream.close();
    }
    if (printWriter != null) {
      printWriter.close();
    }
  }


  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (printWriter != null) {
      throw new IllegalStateException(
          "PrintWriter obtained already - cannot get OutputStream");
    }
    if (gzipOutputStream == null) {
      gzipOutputStream = new GzipServletOutputStream(
          getResponse().getOutputStream());
    }
    return gzipOutputStream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (this.printWriter == null && this.gzipOutputStream != null) {
      throw new IllegalStateException(
          "OutputStream obtained already - cannot get PrintWriter");
    }
    if (printWriter == null) {
      gzipOutputStream = new GzipServletOutputStream(
          getResponse().getOutputStream());
      printWriter = new PrintWriter(new OutputStreamWriter(
          gzipOutputStream, getResponse().getCharacterEncoding()));
    }
    return printWriter;
  }


  @Override
  public void flushBuffer() throws IOException {
    if(gzipOutputStream != null)
      gzipOutputStream.flush();
    if(printWriter != null)
      printWriter.flush();
    super.flushBuffer();
  }
}
