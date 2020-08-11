package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * 处理省/市/区数据的持久层接口
 * @author dell
 *
 */
public interface DistrictMapper {
	
	/**
	 * 查询全国所有省/某省所有市/某市所有区的列表
	 * @param parent 父亲单位的行政代号，如果需要查询全国所有省，则使用"86"作为父级代号
	 * @return 匹配的全国所有省/某省所有市/某市所有区的列表
	 */
	List<District> findByParent(String parent);
	
	/**
	 * 根据省/市/区的行政代号查询名称
	 * @param code 省/市/区的行政代号
	 * @return 匹配的省/市/区的名称，如果没有匹配的数据，则返回null
	 */
	String findNameByCode(String code);
	
}
