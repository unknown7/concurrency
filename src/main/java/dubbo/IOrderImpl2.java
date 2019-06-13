package dubbo;

public class IOrderImpl2 implements IOrder {
    @Override
    public Order selectById(long id) {
        return new Order(id,"购买了开课吧的高级架构师课程,优惠100");
    }

    @Override
    public void selectById(long id, BeanListener beanListener) {
        Order order = new Order(id, "购买了开课吧的高级架构师课程,优惠100-参数回调");
        System.out.println(beanListener.beanNotify(order));
    }
}
