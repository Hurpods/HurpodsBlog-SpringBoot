package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.RoleDAO;
import com.hurpods.springboot.hurpodsblog.pojo.Role;
import com.hurpods.springboot.hurpodsblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDAO roleDAO;

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}
