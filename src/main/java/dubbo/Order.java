package dubbo;

import java.io.Serializable;

/**
 * 订单实体类
 */
public class Order implements Serializable {
    /**
     * 订单编号
     */
    private long id;
    /**
     * 订单描述
     */
    private String desc;
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }
    public Order() {
    }
    public Order(long id, String desc) {
        this.id = id;
        this.desc = desc;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}