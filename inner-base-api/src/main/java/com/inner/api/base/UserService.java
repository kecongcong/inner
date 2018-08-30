package com.inner.api.base;


import com.inner.api.base.bean.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
