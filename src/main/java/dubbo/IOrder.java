package dubbo;

public interface IOrder {
    /**
     * 同步调用
     * @param id
     * @return
     */
    public Order selectById(long id);
    /**
     * 异步调用通知模式
     * @param id
     * @param beanListener
     */
    public void selectById(long id, BeanListener beanListener);
    /**
     * 回调监听器
     */
    interface BeanListener{
        public String beanNotify(Order order);
    }
}
