package com.task.bookstorewebbapp.filter.gzip.stream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GzipServletOutputStream extends ServletOutputStream {

  private final GZIPOutputStream gzipOutputStream;
  private boolean flag = true;

  public GzipServletOutputStream(OutputStream outputStream) throws IOException {
    gzipOutputStream = new GZIPOutputStream(outputStream);
  }

  @Override
  public void write(int b) throws IOException {
    if(this.isReady()) {
      gzipOutputStream.write(b);
    } else {
      throw new IOException("Stream is closed!");
    }
  }

  @Override
  public void write(byte[] b) throws IOException {
    if(this.isReady()) {
      gzipOutputStream.write(b);
    } else {
      throw new IOException("Stream is closed!");
    }
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if(this.isReady()) {
      gzipOutputStream.write(b, off, len);
    }
  }

  @Override
  public void flush() throws IOException {
    gzipOutputStream.flush();
  }

  @Override
  public void close() throws IOException {
    if(isReady()) {
      gzipOutputStream.close();
      flag = false;
    }
  }

  @Override
  public boolean isReady() {
    return flag;
  }

  @Override
  public void setWriteListener(WriteListener writeListener) {
  }
}
