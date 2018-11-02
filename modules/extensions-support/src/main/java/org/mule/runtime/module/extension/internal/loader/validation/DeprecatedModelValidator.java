/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.internal.loader.validation;


import static org.mule.runtime.extension.api.util.NameUtils.getComponentModelTypeName;
import static org.mule.runtime.module.extension.internal.loader.validation.ModelValidationUtils.isCompiletime;

import org.mule.runtime.api.meta.model.ExtensionModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.meta.model.util.IdempotentExtensionWalker;
import org.mule.runtime.extension.api.loader.ExtensionModelValidator;
import org.mule.runtime.extension.api.loader.Problem;
import org.mule.runtime.extension.api.loader.ProblemsReporter;

/**
 * Validates that the parameters which are annotated with {@link org.mule.runtime.extension.api.annotation.deprecated.Deprecated}
 * are not required.
 *
 * @since 4.2.0
 */
public class DeprecatedModelValidator implements ExtensionModelValidator {

  @Override
  public void validate(ExtensionModel extensionModel, ProblemsReporter problemsReporter) {

    if (!isCompiletime(extensionModel)) {
      return;
    }
    new IdempotentExtensionWalker() {

      @Override
      protected void onParameter(ParameterizedModel owner, ParameterGroupModel groupModel, ParameterModel model) {
        if (model.isRequired() && model.isDeprecated()) {
          problemsReporter.addError(new Problem(model,
                                                String
                                                    .format("Parameter \"%s\" from %s named \"%s\" is required but has been marked as deprecated. Required parameters cannot be deprecated. Use the @Optional annotation to make it optional and add a default value.",
                                                            model.getName(), getComponentModelTypeName(owner), owner.getName())));
        }
      }



    }.walk(extensionModel);

  }

}
