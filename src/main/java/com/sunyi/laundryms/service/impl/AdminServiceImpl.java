package com.sunyi.laundryms.service.impl;

import com.sunyi.laundryms.domain.bo.CheckInAdminBO;
import com.sunyi.laundryms.domain.entity.*;
import com.sunyi.laundryms.domain.mapper.AdminMapper;
import com.sunyi.laundryms.service.IAdminService;
import com.sunyi.laundryms.common.result.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public PageUtils getUserByKeyWord(String keyWord, PageUtils page) {
        page.setCountSum(adminMapper.countUser());
        page.setData(adminMapper.getUserByKeyWord(keyWord,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public PageUtils getAllUserByPage(PageUtils page) {
        page.setCountSum(adminMapper.countUser());
        page.setData(adminMapper.getAllUserByPage((page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public int deleteUser(String phone) {
        int i = adminMapper.deleteUser(phone);
        return i;
    }


    @Override
    public PageUtils getClerkByKeyWord(String keyWord,PageUtils page) {
        page.setCountSum(adminMapper.countAdmin());
        page.setData(adminMapper.getClerkByKeyWord(keyWord,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public List<User> getAllAdmin() {
        return adminMapper.getAllAdmin();
    }

    @Override
    public PageUtils getAllAdminByPage(PageUtils page) { //控制层调用该方法并传参page的实例化对象
        page.setCountSum(adminMapper.countAdmin());
        page.setData(adminMapper.getAllAdminByPage((page.getPageNo() - 1) * page.getPageSize(), page.getPageSize()));
        return page;
    }

    @Override
    public int deleteAdmin(String phone) {
        if (adminMapper.deleteAdmin(phone)>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int addAdmin(User user) {
        int i = adminMapper.addAdmin(user.getName(), user.getPhone(), user.getPassword());
        return i;
    }


    @Override
    public PageUtils getDeviceByKeyWord(String keyWord,PageUtils page) {
        page.setCountSum(adminMapper.countDevice());
        page.setData(adminMapper.getDeviceByKeyWord(keyWord,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public PageUtils getAllDeviceByPage(PageUtils page) {
        page.setCountSum(adminMapper.countDevice());
        page.setData(adminMapper.getAllDeviceByPage((page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public int deleteDevice(int id) {
        int i = adminMapper.deleteDevice(id);
        return i;
    }

    @Override
    public int addDevice(Device device) {
        int i = adminMapper.addDevice(device.getCategory(), device.getAutomation(), device.getDrainage(), device.getCapacity(), device.getEnergy_efficiency());
        return i;
    }


    @Override
    public PageUtils getOrderByKeyWord(String keyWord, PageUtils page) {
        page.setCountSum(adminMapper.countOrder());
        page.setData(adminMapper.getOrderByKeyWord(keyWord,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public PageUtils getAllOrderByPage(PageUtils page) {
        page.setCountSum(adminMapper.countOrder());
        page.setData(adminMapper.getAllOrderByPage((page.getPageNo()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public boolean deleteOrder(int id) {
        int i = adminMapper.deleteOrder(id);
        if (i>0){
            log.info("订单记录删除成功");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateOrderState(int id) {
        Order order = adminMapper.findOrderById(id);
        if (order.getDelivery().equals("上门") && order.getState().equals("洗前取货完成")){
            order.setState("洗后待送货");
            adminMapper.updateOrderState(order);
            log.info("洗衣完成，洗后待送货");
            return true;
        }
        if (order.getDelivery().equals("到店") && order.getState().equals("洗前取货完成")){
            order.setState("洗后待取货");
            adminMapper.updateOrderState(order);
            log.info("洗衣完成，洗后待取货");
            return true;
        }
        if (order.getState().equals("洗前待取货")){
            order.setState("洗前取货完成");
            adminMapper.updateOrderState(order);
            log.info("取货完成，洗前取货完成");
            return true;
        }
        if (order.getCollect().equals("上门")){
            order.setState("洗后送货完成");
            adminMapper.updateOrderState(order);
            log.info("洗衣完成，洗后送货完成");
            return true;
        }
        if (order.getCollect().equals("到店")){
            order.setState("洗后取货完成");
            adminMapper.updateOrderState(order);
            log.info("洗衣完成，洗后取货完成");
            return true;
        }
        return false;
    }


    @Override
    public int addLaundryKnowledgeUrl(LaundryKnowledge laundryKnowledge) {
        if (adminMapper.updateAllLaundryKnowledge()>0){
            if (adminMapper.addLaundryKnowledgeUrl(laundryKnowledge)>0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public LaundryKnowledge findLaundryKnowledgeUrl() {
        LaundryKnowledge laundryKnowledgeUrl = adminMapper.findLaundryKnowledgeUrl();
        return laundryKnowledgeUrl;
    }

    @Override
    public int addCheckInAdmin(CheckInAdminBO bo) {
        CheckInAdmin checkInAdmin = new CheckInAdmin();
        BeanUtils.copyProperties(bo,checkInAdmin);
        checkInAdmin.setCreateAt(new Date());
        return adminMapper.addCheckInAdmin(checkInAdmin);
    }


}
