package com.sxpcwlkj.service;

import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.entity.User;

public interface LoginService {

    Member getUserByName(String getMapByName);

    User getUserById(long userId);
}
