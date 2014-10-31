package com.seitenbau.stu.util;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class CamelCaseTest {

    @Test
    public void testCamelCaseExploder() {
        assertThat(CamelCase.explode("")).isEqualTo("");
        assertThat(CamelCase.explode("rainer weinhold")).isEqualTo(
                "rainer weinhold");
        assertThat(CamelCase.explode("rainer_weinhold")).isEqualTo(
                "rainer weinhold");
        assertThat(CamelCase.explode("Lower")).isEqualTo("lower");
        assertThat(CamelCase.explode("LowerUpper")).isEqualTo("lower upper");
        assertThat(CamelCase.explode("LowerUpperID")).isEqualTo(
                "lower upper ID");
        assertThat(CamelCase.explode("LowerUpperID_Key")).isEqualTo(
                "lower upper ID key");
        assertThat(CamelCase.explode("AddRemoveVBLentries")).isEqualTo(
                "add remove VBL entries");
        assertThat(CamelCase.explode("AddRemove5VBLentries")).isEqualTo(
                "add remove 5 VBL entries");
        assertThat(CamelCase.explode("AddRemove42VBLentries")).isEqualTo(
                "add remove 42 VBL entries");
        assertThat(CamelCase.explode("AddRemove_42_VBLentries")).isEqualTo(
                "add remove 42 VBL entries");
        assertThat(CamelCase.explode("T01_01_02")).isEqualTo("T01 01 02");
        assertThat(CamelCase.explode("VERSION")).isEqualTo("VERSION");
        assertThat(CamelCase.explode("VERSION_NUMBER")).isEqualTo("VERSION NUMBER");
    }
    
    @Test
    public void testCamelCaseImplodeFast() {
        assertThat(CamelCase.implodeFast()).isEqualTo("");
        assertThat(CamelCase.implodeFast("",null)).isEqualTo("");
        assertThat(CamelCase.implodeFast("",null,"0")).isEqualTo("0");
        assertThat(CamelCase.implodeFast("Rainer","weinhold","0")).isEqualTo("RainerWeinhold0");
    }
    
    @Test
    public void testCamelCaseFirstLower() {
        assertThat(CamelCase.makeFirstLowerCase(null)).isEqualTo("");
        assertThat(CamelCase.makeFirstLowerCase("")).isEqualTo("");
        assertThat(CamelCase.makeFirstLowerCase("R")).isEqualTo("r");
        assertThat(CamelCase.makeFirstLowerCase("RAINerWeinhold")).isEqualTo("rAINerWeinhold");
        assertThat(CamelCase.makeFirstLowerCase("rainerWeinhold")).isEqualTo("rainerWeinhold");
    }
    
    @Test
    public void testCamelCaseFirstUpper() {
        assertThat(CamelCase.makeFirstUpperCase(null)).isEqualTo("");
        assertThat(CamelCase.makeFirstUpperCase("")).isEqualTo("");
        assertThat(CamelCase.makeFirstUpperCase("R")).isEqualTo("R");
        assertThat(CamelCase.makeFirstUpperCase("RAINerWeinhold")).isEqualTo("RAINerWeinhold");
        assertThat(CamelCase.makeFirstUpperCase("rainerWeinhold")).isEqualTo("RainerWeinhold");
    }
    
    @Test
    public void testCamelCleanup() {
      assertThat(CamelCase.makeFirstOfBlockUppercase(null)).isEqualTo("");
      assertThat(CamelCase.makeFirstOfBlockUppercase("")).isEqualTo("");
      assertThat(CamelCase.makeFirstOfBlockUppercase("NWR_ID")).isEqualTo("NwrId");
    }

}
