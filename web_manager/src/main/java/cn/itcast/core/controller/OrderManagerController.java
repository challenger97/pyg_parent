package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderManager")
public class OrderManagerController {

    @Reference
    private OrderService orderService;

    @Autowired
    HttpServletResponse response;

    @RequestMapping("/findAll")
    public List<Order> findAll(){
        List<Order> list = orderService.findAll();
        return list;
    }

    /**
     * 分页查询
     * @param page 当前页
     * @param rows 每页展示数据条数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer rows) {
        PageResult result = orderService.findPage(null, page, rows);
        return result;
    }


    /**
     * 条件查询
     * @param order
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Order order, Integer page, Integer rows) {
        PageResult result = orderService.findPage(order, page, rows);
        return result;
    }

    /**
     * 根据id查询某一个订单数据
     * @param orderId
     * @return
     */
    @RequestMapping("/findOne")
    public Order findById(Long orderId){
        Order order = orderService.findById(orderId);
        return order;
    }

    /**
     * 查询某一天的销售总金额
     * @param
     * @return
     */
    @RequestMapping("/findTotalMoney")
    @ResponseBody
    public Map<String ,Object> findTotalMoney(){
        Map<String, Object> map = orderService.findTotalMoney();
        return map;
    }

    /**
     * 导出excel
     */
    @RequestMapping("/createExcel")
    public void createExceltest() throws Exception{
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
//建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("成绩表");
//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("用户订单一览表");
//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
//在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("用户名");
        row2.createCell(1).setCellValue("商品id");
        row2.createCell(2).setCellValue("支付金额");
        row2.createCell(3).setCellValue("下单时间");
        row2.createCell(4).setCellValue("收货地址");
        row2.createCell(5).setCellValue("手机号");
        row2.createCell(6).setCellValue("买家名称");
        row2.createCell(7).setCellValue("商家id");

        //日期格式转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //获取order所有数据
        List<Order> orders = orderService.findAll();
        int i=2;
        if (orders!=null&&orders.size()>0){
            //遍历数组

                for (Order order : orders) {
                    //改变支付金额,商家id,日期格式
                    String payMent = String.valueOf(order.getPayment());
                    String orderId=String.valueOf( order.getOrderId());
                    String formatTime = simpleDateFormat.format(order.getUpdateTime());
                    //创建行数,有多少数据创建多少行
                    HSSFRow row=sheet.createRow(i);
                    row.createCell(0).setCellValue(order.getUserId());
                    row.createCell(1).setCellValue(orderId);
                    row.createCell(2).setCellValue(payMent);
                    row.createCell(3).setCellValue(formatTime);
                    row.createCell(4).setCellValue(order.getReceiverAreaName());
                    row.createCell(5).setCellValue(order.getReceiverMobile());
                    row.createCell(6).setCellValue(order.getReceiver());
                    row.createCell(7).setCellValue(orderId);
                    i++;
                }


        }


        //输出Excel文件
        //创建输出流
        OutputStream output= null;

        output = response.getOutputStream();
        //添加响应头,附件
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=details.xls");
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();


    }

}
