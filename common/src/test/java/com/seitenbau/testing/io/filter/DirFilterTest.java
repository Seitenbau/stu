package com.seitenbau.testing.io.filter;

import java.io.File;
import static org.mockito.Mockito.*;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.testing.mockito.MockitoRule;

public class DirFilterTest
{
  @Rule
  public MockitoRule rule = new MockitoRule();

  @Mock
  File file;

  @Test
  public void MatchDirectoryFlag_True()
  {
    // prepare
    DirFilter sut = new DirFilter();
    when(file.isDirectory()).thenReturn(true);
    when(file.getName()).thenReturn("anDir");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_False()
  {
    // prepare
    DirFilter sut = new DirFilter();
    when(file.isDirectory()).thenReturn(false);
    when(file.getName()).thenReturn("anDir");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isFalse();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_True()
  {
    // prepare
    DirFilter sut = new DirFilter(".*");
    when(file.isDirectory()).thenReturn(true);
    when(file.getName()).thenReturn("anDir");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_True2()
  {
    // prepare
    DirFilter sut = new DirFilter("anDir");
    when(file.isDirectory()).thenReturn(true);
    when(file.getName()).thenReturn("anDir");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isTrue();
  }

  @Test
  public void MatchDirectoryFlag_WithPattern_False()
  {
    // prepare
    DirFilter sut = new DirFilter("aNDir");
    when(file.isDirectory()).thenReturn(true);
    when(file.getName()).thenReturn("anDir");

    // execute
    boolean result = sut.accept(file);

    // prepare
    assertThat(result).isFalse();
  }
}
