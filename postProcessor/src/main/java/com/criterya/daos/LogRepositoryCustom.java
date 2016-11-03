package com.criterya.daos;

import com.criterya.model.Log;

public interface LogRepositoryCustom{
	Integer getLastBlobId(Log log);
}
