/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extensions.jms.api.exception;

import org.mule.runtime.extension.api.runtime.exception.ExceptionHandler;

/**
 * {@link ExceptionHandler} for the JMS extension.
 * 
 * @since 4.0
 */
public class JmsExceptionHandler extends ExceptionHandler {

  @Override
  public Exception enrichException(Exception e) {
    return getRootErrorException(e);
  }
}
