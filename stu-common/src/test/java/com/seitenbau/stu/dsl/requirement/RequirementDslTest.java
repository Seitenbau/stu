package com.seitenbau.stu.dsl.requirement;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

import com.seitenbau.stu.dsl.requirement.RequirementDsl.RequirementState;

public class RequirementDslTest
{

  @Test(expected = IllegalArgumentException.class)
  public void create_nullParameter()
  {
    new RequirementDsl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void create_invalidDslParameter()
  {
    new RequirementDsl("    : ::: ");
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void create_invalidDSlStateParameter()
  {
    new RequirementDsl("offline:feature-id");
  }

  @Test
  public void create_defaults()
  {
    RequirementDsl sut = new RequirementDsl("feature-id");
    assertThat(sut.getState()).isEqualTo(RequirementState.NOT_IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo("feature-id");
    assertThat(sut.isNotAvaiable()).isTrue();
  }

  @Test
  public void create_NotImpl()
  {
    RequirementDsl sut = new RequirementDsl("notImpL:feature-id");
    assertThat(sut.getState()).isEqualTo(RequirementState.NOT_IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo("feature-id");
    assertThat(sut.isNotAvaiable()).isTrue();
  }

  @Test
  public void create_NotImplemented()
  {
    RequirementDsl sut = new RequirementDsl("notImplemented:feature-id");
    assertThat(sut.getState()).isEqualTo(RequirementState.NOT_IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo("feature-id");
    assertThat(sut.isNotAvaiable()).isTrue();
  }

  @Test
  public void create_Implemented()
  {
    RequirementDsl sut = new RequirementDsl("Implemented:feature-id");
    assertThat(sut.getState()).isEqualTo(RequirementState.IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo("feature-id");
    assertThat(sut.isNotAvaiable()).isFalse();
  }

  @Test
  public void create_Impl()
  {
    RequirementDsl sut = new RequirementDsl("Impl:feature-id");
    assertThat(sut.getState()).isEqualTo(RequirementState.IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo("feature-id");
    assertThat(sut.isNotAvaiable()).isFalse();
  }

  @Test
  public void checkCustomActive()
  {
    String name = ActiveDetector.class.getName();
    RequirementDsl sut = new RequirementDsl("cusTom:" + name);
    assertThat(sut.getState()).isEqualTo(RequirementState.IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo(name);
    assertThat(sut.isNotAvaiable()).isFalse();
  }
  
  @Test
  public void isdslYes() {
    assertThat(RequirementDsl.isRequirement("notImpl:rainer")).isTrue();
  }
  
  @Test
  public void isdslNo() {
    assertThat(RequirementDsl.isRequirement("nothingels: ")).isFalse();
    assertThat(RequirementDsl.isRequirement(" : ")).isFalse();
  }
  
  @Test
  public void checkCustomInActive()
  {
    String name = InActiveDetector.class.getName();
    RequirementDsl sut = new RequirementDsl("cusTom:" + name);
    assertThat(sut.getState()).isEqualTo(RequirementState.NOT_IMPLEMENTED);
    assertThat(sut.getFeatureId()).isEqualTo(name);
    assertThat(sut.isNotAvaiable()).isTrue();
  }

  public static class ActiveDetector extends AbstractDetector
  {

    public RequirementState getState()
    {
      return RequirementState.IMPLEMENTED;
    }

  }

  public static class InActiveDetector extends AbstractDetector
  {

    public RequirementState getState()
    {
      return RequirementState.NOT_IMPLEMENTED;
    }

  }
}
