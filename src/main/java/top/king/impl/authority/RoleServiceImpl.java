package top.king.impl.authority;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.FieldConst;
import top.king.common.ResultModel;
import top.king.entity.authority.Role;
import top.king.entity.dto.AuthorityDTO;
import top.king.mapper.ResourcesMapper;
import top.king.mapper.authority.RoleMapper;
import top.king.service.authority.RoleService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    ResourcesMapper resourcesMapper;

    @Override
    public ResultModel<List<Role>> queryRoles(BaseQuery<Map> param) {
        ResultModel<List<Role>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<Role> menus = (Page<Role>) roleMapper.queryRoles(param);
            resultModel.setData(menus);
            resultModel.setCount(menus.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询角色信息出错！", e);
            resultModel.setMsg("查询角色信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteRole(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            roleMapper.deleteRole(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除角色出错！", e);
            resultModel.setMsg("删除角色出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateRole(Role menu) {
        ResultModel resultModel = new ResultModel();
        try {
            roleMapper.updateRole(menu);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改角色出错！", e);
            resultModel.setMsg("修改角色出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel insertRole(Role record) {
        ResultModel resultModel = new ResultModel();
        try {
            roleMapper.insertSelective(record);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加角色出错！", e);
            resultModel.setMsg("添加角色出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateAuthority(AuthorityDTO authorityDTO) {
        ResultModel resultModel = new ResultModel();
        try {
            roleMapper.deleteAuthority(authorityDTO.getRole());
            if (!CollectionUtils.isEmpty(authorityDTO.getResources())) {
                List<String> collect = resourcesMapper.selectAllByResourceCode(authorityDTO.getResources()).stream().map(String::valueOf).collect(Collectors.toList());
                authorityDTO.setResources(collect);
                roleMapper.insertAuthority(authorityDTO);
            }
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改角色权限出错！", e);
            resultModel.setMsg("修改角色权限出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<List<List>> queryRoleAuthority(String role, List<Map> all) {
        ResultModel<List<List>> resultModel = new ResultModel<>();
        try {
            List<String> resources = roleMapper.queryAuthorityCode(role);
            HashSet<String> set = new HashSet<>(resources);
            if (!CollectionUtils.isEmpty(resources) && !CollectionUtils.isEmpty(all)) {
                List<List> lists = new ArrayList<>(16);
                // 深度优先遍历
                LinkedList<Map> stack = new LinkedList<>(all);
                LinkedList<String> authority = new LinkedList<>();
                int count = 1;
                while (!stack.isEmpty()) {
                    Map<String, Object> map = stack.pop();
                    String code = (String) map.get(FieldConst.VALUE);
                    if (set.contains(code) || "*".equals(code)) {
                        if (map.containsKey(FieldConst.CHILDREN)) {
                            List<Map> children = (List<Map>) map.get(FieldConst.CHILDREN);
                            count += children.size();
                            for (int i = children.size() - 1; i >= 0; i--) {
                                stack.push(children.get(i));
                            }
                            if (CollectionUtils.isEmpty(authority) || !("*".equals(code) && "*".equals(authority.getLast()))) {
                                authority.add(code);
                            }
                            count--;
                            continue;
                        } else {
                            authority.add(code);
                        }
                        count--;
                        lists.add(new ArrayList<>(authority));
                        if (count <= 0) {
                            authority.clear();
                            count = 1;
                        } else {
                            authority.removeLast();
                        }
                    } else {
                        count--;
                        if (count <= 0) {
                            count = 1;
                            authority.clear();
                        }
                    }
                }
                resultModel.setData(lists);
            }
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("获得角色权限出错！", e);
            resultModel.setMsg("获得角色权限出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }


    @Override
    public ResultModel<List<Map>> queryAuthority() {
        ResultModel<List<Map>> resultModel = new ResultModel<>();
        try {
            // 所有资源信息
            List<Map<String, String>> allResources = roleMapper.queryAuthority();
            // 根据菜单层级分组，0,1,2
            List<Map<String, String>> first = new ArrayList<>();
            List<Map<String, String>> second = new ArrayList<>();
            List<Map<String, String>> third = new ArrayList<>();
            // 映射表，parent_id:Set[menu_id]
            Map<String, Set<String>> mapTable = new HashMap<>(16);
            for (Map<String, String> map : allResources) {
                String layer = map.get(FieldConst.LAYER);
                switch (layer) {
                    case "0":
                        first.add(map);
                        break;
                    case "1":
                        second.add(map);
                        break;
                    case "2":
                        third.add(map);
                        break;
                    default:
                        break;
                }
                if (!ObjectUtils.isEmpty(mapTable.get(map.get(FieldConst.PARENT_ID)))) {
                    mapTable.get(map.get(FieldConst.PARENT_ID)).add(map.get(FieldConst.MENU_ID));
                } else {
                    Set<String> set = new HashSet<>(16);
                    set.add(map.get(FieldConst.MENU_ID));
                    mapTable.put(map.get(FieldConst.PARENT_ID), set);
                }
            }
            // 资源列表
            // 获取分级的列表
            Map<String, Map<String, Object>> three = createAuthorityRule(third, null, null);
            Map<String, Map<String, Object>> two = createAuthorityRule(second, three, mapTable);
            Map<String, Map<String, Object>> one = createAuthorityRule(first, two, mapTable);
            List<Map> mapList = new ArrayList<>(one.values());
            resultModel.setData(mapList);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询权限出错！", e);
            resultModel.setMsg("查询权限出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    /**
     * 生成嵌套菜单等级关系
     *
     * @param first    当前菜单等级集合
     * @param child    子集合
     * @param mapTable 映射表，关联父菜单需要拼接的子菜单
     * @date 2020/11/27
     */
    public Map<String, Map<String, Object>> createAuthorityRule(List<Map<String, String>> first, Map<String, Map<String, Object>> child, Map<String, Set<String>> mapTable) {
        // 菜单id作为key
        Map<String, Map<String, Object>> one = new LinkedHashMap<>(16);
        for (Map<String, String> map : first) {
            String menuId = map.get(FieldConst.MENU_ID);
            // 已存在则改为父子格式，不存在则只添加label、value、category
            if (one.containsKey(menuId)) {
                Map<String, Object> objectMap = one.get(menuId);
                // 如果已为父子格式则直接添加，否则构建
                if (ObjectUtils.isEmpty(objectMap.get(FieldConst.CHILDREN))) {
                    List<Map<String, String>> children = new ArrayList<>(4);
                    Map<String, String> map1 = new HashMap<>(4);
                    Map<String, String> map2 = new HashMap<>(4);
                    map1.put(FieldConst.LABEL, (String) objectMap.get(FieldConst.RESOURCE_NAME));
                    map1.put(FieldConst.VALUE, (String) objectMap.get(FieldConst.RESOURCE_CODE));
                    map2.put(FieldConst.VALUE, map.get(FieldConst.RESOURCE_CODE));
                    map2.put(FieldConst.LABEL, map.get(FieldConst.RESOURCE_NAME));
                    children.add(map1);
                    children.add(map2);
                    objectMap.put(FieldConst.VALUE, "*");
                    objectMap.remove(FieldConst.RESOURCE_CODE);
                    objectMap.remove(FieldConst.RESOURCE_NAME);
                    objectMap.put(FieldConst.CHILDREN, children);
                } else {
                    Map<String, String> map2 = new HashMap<>(4);
                    map2.put(FieldConst.VALUE, map.get(FieldConst.RESOURCE_CODE));
                    map2.put(FieldConst.LABEL, map.get(FieldConst.RESOURCE_NAME));
                    List<Map> list = (List<Map>) objectMap.get(FieldConst.CHILDREN);
                    list.add(map2);
                }
            } else {
                Map<String, Object> cur = new HashMap<>(4);
                cur.put(FieldConst.LABEL, map.get(FieldConst.RESOURCE_CATEGORY));
                cur.put(FieldConst.VALUE, map.get(FieldConst.RESOURCE_CODE));
                // 如果存在子菜单则根据映射表来添加
                if (!ObjectUtils.isEmpty(child)) {
                    Set<String> set = mapTable.get(map.get(FieldConst.MENU_ID));
                    List<Map<String, Object>> children = new ArrayList<>(4);
                    if (set != null && set.size() > 0) {
                        set.stream().map(child::get).forEach(children::add);
                        cur.put(FieldConst.CHILDREN, children);
                    }
                }
                // 保存资源名供父子格式整合
                cur.put(FieldConst.RESOURCE_NAME, map.get(FieldConst.RESOURCE_NAME));
                cur.put(FieldConst.RESOURCE_CODE, map.get(FieldConst.RESOURCE_CODE));
                one.put(menuId, cur);
            }
        }
        return one;
    }
}
