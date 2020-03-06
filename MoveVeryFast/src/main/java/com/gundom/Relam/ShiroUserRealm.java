package com.gundom.Relam;

import com.gundom.DAO.SysMenuDao;
import com.gundom.DAO.SysRoleMenuDao;
import com.gundom.DAO.SysUserDao;
import com.gundom.DAO.SysUserRoleDao;
import com.gundom.Entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private SysMenuDao sysMenuDao;


    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher myMatcher=new HashedCredentialsMatcher();
        myMatcher.setHashAlgorithmName("MD5");
        myMatcher.setHashIterations(1);
        super.setCredentialsMatcher(myMatcher);
    }

    /**
     *授权信息的获取及封装
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取登录用户信息
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //2.基于用户id查询用户角色id
        Integer userId = user.getId();
        List<Integer> rolesIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        if (rolesIds == null || rolesIds.size()==0) {
            throw new AuthorizationException();
        }
        //3.基于角色id查询菜单id
        Integer[] array={};
        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(rolesIds.toArray(array));
        if (menuIds==null || menuIds.size()==0) {
            throw new AuthorizationException();
        }

        //4.基于菜单id查询权限标识
        List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
        if (permissions==null || permissions.size()==0) {
            throw new AuthorizationException();
        }
        Set<String> set=new HashSet<>();
        for (String permission : permissions) {
            if(!StringUtils.isEmpty(permission)){
                set.add(permission);
            }
        }

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(set);

        return info;
    }

    /**
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //1.获取用户名(从Token获取)
        UsernamePasswordToken uPToken= (UsernamePasswordToken) authenticationToken;

        //2.基于用户名查询用户对象
        String username = uPToken.getUsername();
        //3.判定用户是否存在
        SysUser userResult = sysUserDao.findUserByUserName(username);
        if (userResult==null) {
            throw new UnknownAccountException();
        }
        //4.判定用户是否被禁用
        if (userResult.getValid()==0) {
            throw new LockedAccountException();
        }
        //5.判定用户信息并返回,传递给认证管理器进行认证
        ByteSource credentialsSalt=ByteSource.Util.bytes(userResult.getSalt());
        //argo[0]->身份信息,argo[1]->用户已加密的密码;agro[2]->密码盐值的包装类;args[3]->名字
        SimpleAuthenticationInfo sInfo=new SimpleAuthenticationInfo(userResult, userResult.getPassword(),credentialsSalt,getName());
        return sInfo;
    }
}
