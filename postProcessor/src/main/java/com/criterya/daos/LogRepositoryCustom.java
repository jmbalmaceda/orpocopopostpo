package com.criterya.daos;

import java.util.List;

import com.criterya.model.Log;

public interface LogRepositoryCustom{
	Integer getUltimoBlobId(Log log);
	Integer getPrimerBlobId(Log log);
	List<Integer> getBlobsId(Log log);
}
