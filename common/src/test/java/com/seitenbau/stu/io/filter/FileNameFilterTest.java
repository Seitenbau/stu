package com.seitenbau.stu.io.filter;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.stu.io.filter.FileNameFilter;
import com.seitenbau.stu.mockito.MockitoRule;

public class FileNameFilterTest
{
  @Rule
  public MockitoRule rule = new MockitoRule();

  @Mock
  File file;

  @Test
  public void MatchDirectoryFlag_True()
  {
    // prepare
    FileNameFilter sut = new FileNameFilter();
    when(file.isFile()).thenReturn(true);
    when(file.getName()).thenReturn("aFile");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_False()
  {
    // prepare
    FileNameFilter sut = new FileNameFilter();
    when(file.isFile()).thenReturn(false);
    when(file.getName()).thenReturn("aFile");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isFalse();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_True()
  {
    // prepare
    FileNameFilter sut = new FileNameFilter(".*");
    when(file.isFile()).thenReturn(true);
    when(file.getName()).thenReturn("aFile");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_True2()
  {
    // prepare
    FileNameFilter sut = new FileNameFilter("aFile");
    when(file.isFile()).thenReturn(true);
    when(file.getName()).thenReturn("aFile");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_False()
  {
    // prepare
    FileNameFilter sut = new FileNameFilter("aNFile");
    when(file.isFile()).thenReturn(true);
    when(file.getName()).thenReturn("aFile");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isFalse();
  }
}
