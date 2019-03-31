package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.AddressQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService{

    @Autowired
    private AddressDao addressDao;

    /**
     * 添加地址
     * @param address
     */
    @Override
    public void insert(String username,Address address) {
        address.setUserId(username);
        addressDao.insertSelective(address);
    }

    /**
     * 查询全部地址信息
     * @param username
     * @return
     */
    @Override
    public List<Address> findAll(String username) {
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andUserIdEqualTo(username);
        List<Address> addressList = addressDao.selectByExample(addressQuery);
        return addressList;
    }

    @Override
    public void delete(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }
}
