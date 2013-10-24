package com.seitenbau.stu.dsl.requirement;

import com.seitenbau.stu.dsl.requirement.RequirementDsl.RequirementState;

public interface FeatureDetector
{
  void initWithParameter(String parameters);

  String getFeatureId();

  RequirementState getState();
}
