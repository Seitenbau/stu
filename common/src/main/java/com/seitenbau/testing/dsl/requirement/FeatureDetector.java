package com.seitenbau.testing.dsl.requirement;

import com.seitenbau.testing.dsl.requirement.RequirementDsl.RequirementState;

public interface FeatureDetector
{
  void initWithParameter(String parameters);

  String getFeatureId();

  RequirementState getState();
}
