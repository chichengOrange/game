/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.migo.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.migo.dao.SysUserDao;
import com.migo.entity.SysUserEntity;
import com.migo.service.SysRoleService;
import com.migo.service.SysUserRoleService;
import com.migo.service.SysUserService;
import com.migo.utils.Constant;
import com.migo.utils.MatchUtil;
import com.migo.utils.RRException;
import com.migo.utils.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 知秋
 * @email fei6751803@163.com
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return sysUserDao.queryByUserName(username);
    }

    @Override
    public SysUserEntity queryObject(Long userId) {
        return sysUserDao.queryObject(userId);
    }

    @Override
    public List<SysUserEntity> queryList(Map<String, Object> map){
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        user.setPassword(new Sha256Hash(user.getPassword()).toHex());
        sysUserDao.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword()).toHex());
        }
        sysUserDao.update(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        sysUserDao.deleteBatch(userId);
    }

    @Override
    @Transactional
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user){
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }


    @Transactional(value = "transactionManager", rollbackFor = {RuntimeException.class})
    public void saveBatch(List<Map<String, String>> users) throws Exception {
        if (users != null && users.size() != 0) {
            Map<String, String> m0 = (Map)users.get(0);
            StringBuffer msgBuf = new StringBuffer();
            String[] comps = new String[]{"username", "mobile", "email"};
            if (!CollectionUtils.isSubCollection(Arrays.asList(comps), m0.keySet())) {
                throw new Exception("标题必须包含 " + Arrays.asList(comps).toString());
            } else {
                String br = "<br/>";
                String and = " & ";
                String existName = this.sysUserDao.queryWithGroupConcat();
                List<String> nameList = new LinkedList();
                List<SysUserEntity> userEntities = new ArrayList();
                Gson gson = new Gson();

                for(int i = 0; i < users.size(); ++i) {
                    StringBuffer line = new StringBuffer("第 " + i + " 行 ");
                    Map<String, String> map = (Map)users.get(i);
                    String name = (String)map.get("username");
                    if (StringUtil.isEmpty(name)) {
                        line.append(" username:不能为空").append(and);
                    } else {
                        if (StringUtil.isNotEmpty(existName) && existName.contains(name)) {
                            line.append("username:" + name + " 数据库已存在").append(and);
                        } else if (nameList.contains(map.get("username"))) {
                            line.append("username:" + name + " 重复").append(and);
                        }

                        nameList.add(name);
                    }

                    String mobile = (String)map.get("mobile");
                    if (StringUtil.isNotEmpty(mobile) && !MatchUtil.checkPhone(mobile)) {
                        line.append(mobile + " 电话格式不正确").append(and);
                    }

                    String email = (String)map.get("email");
                    if (StringUtil.isNotEmpty(email) && !MatchUtil.checkEmaile(email)) {
                        line.append(email + " 邮件格式不正确").append(and);
                    }

                    if (line.toString().contains(and)) {
                        msgBuf.append(line.substring(0, line.length() - and.length())).append(br);
                    }

                    if (!msgBuf.toString().contains(br)) {
                        String json = gson.toJson(map);
                        SysUserEntity entity = (SysUserEntity)gson.fromJson(json, SysUserEntity.class);
                        entity.setPassword((new Sha256Hash("infoPower168")).toHex());
                        userEntities.add(entity);
                    }
                }

                if (msgBuf.toString().contains(br)) {
                    System.out.println(msgBuf.toString());
                    throw new Exception(msgBuf.toString());
                } else {
                    userEntities.forEach((user) -> {
                        user.setCreateUserId(ShiroUtils.getUserId());
                        user.setCreateTime(new Date());
                        this.sysUserDao.save(user);
                        Long[] roles = new Long[]{2L};
                        this.sysUserRoleService.saveOrUpdate(user.getUserId(), Arrays.asList(roles));
                    });
                }
            }
        } else {
            throw new Exception("空数据");
        }
    }
}
