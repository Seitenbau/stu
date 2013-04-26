package com.seitenbau.testing.templates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.seitenbau.testing.logger.Logger;
import com.seitenbau.testing.logger.TestLoggerFactory;

/**
 * Helper to execute Velocity Templates from the classpath
 */
public class VelocityGenerator
{
  Logger logger = TestLoggerFactory.get(VelocityGenerator.class);

  /**
   * Merge the given model with a velocity template from the classpath
   * into an output file.
   * 
   * @param model Model objekt injected into the velocity context with
   *        var-name "model".
   * @param templateInClassPath Path to the actual templare resource
   *        in the classpath
   * @param into Target prefix, e.g. "target/". for an actual filename
   *        and subfolder the templare needs so set the variabels
   *        "package" and "filename"
   * 
   * @throws Exception
   */
  public void executeTemplate(Object model, String templateInClassPath, String into) throws Exception
  {
    int last = into.lastIndexOf("/");
    if (last != -1)
    {
      new File(into.substring(0, last)).mkdirs();
    }
    VelocityContext context = new VelocityContext();
    context.put("model", model);
    Template template = createNewVelocityEngine().getTemplate(templateInClassPath);
    executeTemplate(context, template, into);
  }

  protected VelocityEngine createNewVelocityEngine()
  {
    VelocityEngine ve = new VelocityEngine();

    // Template must be available in the classpath
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
    ve.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getCanonicalName());
    return ve;
  }

  protected void executeTemplate(VelocityContext context, Template template, String into) throws IOException
  {
    StringWriter sw = new StringWriter();
    executeMerge(context, template, sw);
    String filename = (String) context.get("filename");
    String pkg = (String) context.get("package");
    if (filename == null)
    {
      throw new IllegalArgumentException("filename not set by the template");
    }
    String path = into + "";
    if (pkg != null && pkg.length() > 1)
    {
      path = into + pkg.replace(".", "/") + "/";
      new File(path).mkdirs();
    }
    String fn = path + filename;
    if (!filename.contains("."))
    {
      fn += ".java";
    }
    FileWriter out = new FileWriter(fn);
    out.append(sw.toString());
    out.close();
    logger.debug("Written template result into : " + fn);
  }

  protected void executeMerge(VelocityContext context, Template template, Writer sw)
  {
    template.merge(context, sw);
  }
}
