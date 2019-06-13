package dubbo;

/**
 * 订单实现类
 */
public class IOrderImpl implements IOrder {

    @Override
    public Order selectById(long id) {
        System.out.println("selectById-------");
        System.out.println(KaikebaRpcContext.getContext().getAttachment("user"));
        System.out.println(KaikebaRpcContext.getContext().getAttachment("password"));
        KaikebaRpcContext.getServerContext().setAttachment("server","22222222222222");
        return new Order(id,"购买了开课吧的JAVA高并发课程");
    }


    @Override
    public void selectById(long id, BeanListener beanListener) {
        System.out.println("selectById-notify-------------");
        System.out.println(KaikebaRpcContext.getContext().getAttachment("user"));
        System.out.println(KaikebaRpcContext.getContext().getAttachment("password"));
        Order order = new Order(id, "购买了开课吧的JAVA高并发课程-参数回调");
        System.out.println(beanListener.beanNotify(order));
        KaikebaRpcContext.getServerContext().setAttachment("server","333333333333");
    }

}