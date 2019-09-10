package com.fsc.generate.model.service;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.BizChargeCardBinding;
import com.fsc.generate.model.db.ddo.BizVmsUser;
import com.fsc.generate.model.db.ddo.ToolsOperation;
import com.fsc.generate.model.db.ddo.ToolsUser;
import com.fsc.generate.model.db.factory.CcSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CcService {

    @Autowired
    CcSessionFactory sessionFactory;


    public void deleteByMac(String mac) throws CrmException {
         sessionFactory.executeSql(BizChargeCardBinding.class, BizChargeCardBinding.DELETE_BY_MAC,mac);
    }

}
