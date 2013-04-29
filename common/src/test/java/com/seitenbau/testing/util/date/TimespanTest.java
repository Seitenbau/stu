package com.seitenbau.testing.util.date;

import org.junit.Test;
import static org.fest.assertions.Assertions.*;

public class TimespanTest
{
  @Test
  public void zero()
  {
    Timespan sut = new Timespan(0);
    assertThat(sut.inMS()).isEqualTo(0);
    assertThat(sut.inSeconds()).isEqualTo(0);
    assertThat(sut.inMinutes()).isEqualTo(0);
    assertThat(sut.inHours()).isEqualTo(0);
    assertThat(sut.inDays()).isEqualTo(0);
  }
  
  @Test
  public void positive_1s()
  {
    Timespan sut = new Timespan(1000);
    assertThat(sut.inMS()).isEqualTo(1000);
    assertThat(sut.inSeconds()).isEqualTo(1);
    assertThat(sut.inMinutes()).isEqualTo(0);
    assertThat(sut.inHours()).isEqualTo(0);
    assertThat(sut.inDays()).isEqualTo(0);
  }
  
  @Test
  public void positive_1m()
  {
    Timespan sut = new Timespan(1000*60);
    assertThat(sut.inMS()).isEqualTo(1000*60);
    assertThat(sut.inSeconds()).isEqualTo(60);
    assertThat(sut.inMinutes()).isEqualTo(1);
    assertThat(sut.inHours()).isEqualTo(0);
    assertThat(sut.inDays()).isEqualTo(0);
  }
  
  @Test
  public void positive_1h()
  {
    Timespan sut = new Timespan(1000*60*60);
    assertThat(sut.inMS()).isEqualTo(1000*60*60);
    assertThat(sut.inSeconds()).isEqualTo(60*60);
    assertThat(sut.inMinutes()).isEqualTo(60);
    assertThat(sut.inHours()).isEqualTo(1);
    assertThat(sut.inDays()).isEqualTo(0);
  }
  
  @Test
  public void positive_1d()
  {
    Timespan sut = new Timespan(1000*60*60*24);
    assertThat(sut.inMS()).isEqualTo(1000*60*60*24);
    assertThat(sut.inSeconds()).isEqualTo(60*60*24);
    assertThat(sut.inMinutes()).isEqualTo(60*24);
    assertThat(sut.inHours()).isEqualTo(24);
    assertThat(sut.inDays()).isEqualTo(1);
  }
  
  
  @Test
  public void negative_24h()
  {
    Timespan sut = new Timespan(-86400000);
    assertThat(sut.inMS()).isEqualTo(-86400000);
    assertThat(sut.inSeconds()).isEqualTo(-86400);
    assertThat(sut.inMinutes()).isEqualTo(-1440);
    assertThat(sut.inHours()).isEqualTo(-24);
    assertThat(sut.inDays()).isEqualTo(-1);
  }
}
