package com.seitenbau.stu.dsl.requirement;

import java.util.regex.Pattern;

/**
 * The Requirement DSL:
 * 
 * <ul>
 *   <li>notImp:feature-id or notImplemented:feature-id 
 *   <li>Implemented:feature-id or impl:feature-id 
 *   <li>custom:factory-clazz:parameters
 * </ul>
 * 
 */
public class RequirementDsl
{

  /**
   * The enum of the dsl state
   */
  public enum RequirementState
  {
    NOT_IMPLEMENTED("notImplemented", "notImpl"),
    IMPLEMENTED("impl", "Implemented"),
    CUSTOM("custom");

    String[] _reps;

    RequirementState(String... stringReps)
    {
      _reps = stringReps;
    }

    static RequirementState fromString(String value)
    {
      for (RequirementState fs : values())
      {
        for (String s : fs._reps)
        {
          if (s.equalsIgnoreCase(value))
          {
            return fs;
          }
        }
      }
      throw new IllegalArgumentException("FeatureDslNotKnown");
    }
  }

  static Pattern pattern = Pattern
      .compile("(?i)^(\\s*?(impl|implemented|notimpl|notimplemented|custom)\\s*:)?(?!.*[\\s]).+$");

  String id;

  RequirementState state;

  FeatureDetector _custom;

  /**
   * Get the current State.
   * 
   * @return The State {@link RequirementState.IMPLEMENTED} or
   *         {@link RequirementState.NOT_IMPLEMENTED}.
   *         {@link RequirementState.CUSTOM} is automatically
   *         resolved, and therefore will never be returned here!!
   */
  public RequirementState getState()
  {
    if (_custom != null)
    {
      return _custom.getState();
    }
    else
    {
      return state;
    }
  }

  public RequirementDsl(String dsl)
  {
    if (dsl == null)
    {
      throw new IllegalArgumentException("no dsl given");
    }
    String[] parts = dsl.split(":");
    if (parts.length == 0)
    {
      throw new IllegalArgumentException("no dsl given");
    }
    state = RequirementState.NOT_IMPLEMENTED;
    id = null;
    if (parts.length == 1)
    {
      id = parts[0];
    }
    else
    {
      state = RequirementState.fromString(parts[0]);
      if (state.equals(RequirementState.CUSTOM))
      {
        String customDsl = dsl.substring("custom:".length());
        _custom = DetectorFactory.create(customDsl);
      }
      else
      {
        id = parts[1];
      }
    }
  }

  public String getFeatureId()
  {
    if (_custom != null)
    {
      return _custom.getFeatureId();
    }
    else
    {
      return id;
    }
  }

  public boolean isNotAvaiable()
  {
    return getState() == RequirementState.NOT_IMPLEMENTED;
  }

  public static boolean isRequirement(String reqDsl)
  {
    try
    {
      new RequirementDsl(reqDsl);
      return true;
    }
    catch (IllegalArgumentException e)
    {
      return false;
    }
  }

}
