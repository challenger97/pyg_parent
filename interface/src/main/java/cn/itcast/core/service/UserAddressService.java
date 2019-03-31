package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.entity.Result;
import org.opensaml.ws.wssecurity.Username;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserAddressService {

    public void insert(String username, Address address);

    public List<Address> findAll(String username);

    public void delete(Long id);
}
