package com.sxpcwlkj.service.impl;

import com.sxpcwlkj.config.SysInfoConfig;
import com.sxpcwlkj.entity.SystemInfo;
import com.sxpcwlkj.service.SysService;
import org.springframework.stereotype.Service;

@Service
public class SysServiceImpl implements SysService {

    @Override
    public SystemInfo getSys() {
        SysInfoConfig  sysInfoConfig=new SysInfoConfig();

        return sysInfoConfig.getAll(new SystemInfo());
    }
}
