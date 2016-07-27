package org.codepay.common.utils;

import org.codepay.common.exception.ServiceException;

public interface TransactionExecutable {
    void run() throws ServiceException;
}
