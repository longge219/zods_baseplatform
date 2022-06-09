package com.zods.flink.sink.hbase.pool;
import com.zods.flink.sink.hbase.exception.ConnectionException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.io.Closeable;
import java.io.Serializable;
/**
 * @author: jianglong
 * @description: 连接池基本操作
 * @create: 2020-03-07
 **/
public abstract class ConnectionPoolBase<T> implements Closeable, Serializable {

    private static final long serialVersionUID = 536428799879058482L;

    protected GenericObjectPool<T> internalPool;

    public ConnectionPoolBase() {
    }

    /**
     * @param: 池配置
     * @param: 池对象工厂
     */
    public ConnectionPoolBase(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        this.initPool(poolConfig, factory);
    }

    /**
     * @Description: 初始化对象池
     * @param: 池配置
     * @param: 池对象工厂
     */
    protected void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        if (this.internalPool != null)
            this.destroy();
        this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
    }

    /**
     * @Description: 销毁对象池
     */
    protected void destroy() {
        this.close();
    }

    /**
     * @Description: 获得池对象
     * @return: 池对象
     */
    protected T getResource() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            throw new ConnectionException("Could not get a resource from the pool", e);
        }
    }

    /**
     * @Description: 返回池对象
     * @param: 池对象
     */
    protected void returnResource(final T resource) {
        if (null != resource)
            try {
                internalPool.returnObject(resource);
            } catch (Exception e) {
                throw new ConnectionException("Could not return the resource to the pool", e);
            }
    }

    /**
     * @Description: 废弃池对象
     * @param: 池对象
     */
    protected void invalidateResource(final T resource) {
        if (null != resource)
            try {
                internalPool.invalidateObject(resource);
            } catch (Exception e) {
                throw new ConnectionException("Could not invalidate the resource to the pool", e);
            }
    }

    /**
     * @Description: 获得池激活数
     * @return: 激活数
     */
    public int getNumActive() {
        if (isInactived()) {
            return -1;
        }
        return this.internalPool.getNumActive();
    }

    /**
     * @Description: 获得池空闲数
     * @return: 空闲数
     */
    public int getNumIdle() {
        if (isInactived()) {
            return -1;
        }
        return this.internalPool.getNumIdle();
    }

    /**
     * @Description: 获得池等待数
     * @return: 等待数
     */
    public int getNumWaiters() {
        if (isInactived()) {
            return -1;
        }
        return this.internalPool.getNumWaiters();
    }

    /**
     * @Description: 获得平均等待时间
     * @return: 平均等待时间
     */
    public long getMeanBorrowWaitTimeMillis() {
        if (isInactived()) {
            return -1;
        }
        return this.internalPool.getMeanBorrowWaitTimeMillis();
    }

    /**
     * @Description: 获得最大等待时间
     * @return: 最大等待时间
     */
    public long getMaxBorrowWaitTimeMillis() {
        if (isInactived()) {
            return -1;
        }
        return this.internalPool.getMaxBorrowWaitTimeMillis();
    }

    /**
     * @Description: 池是否关闭
     * @return: 是否关闭
     */
    public boolean isClosed() {
        try {
            return this.internalPool.isClosed();
        } catch (Exception e) {
            throw new ConnectionException("Could not check closed from the pool", e);
        }
    }

    /**
     * @Description: 池是否失效
     * @return: 是否失效
     */
    private boolean isInactived() {
        try {
            return this.internalPool == null || this.internalPool.isClosed();
        } catch (Exception e) {
            throw new ConnectionException("Could not check inactived from the pool", e);
        }
    }

    /**
     * @Description: 添加池对象
     * @param: 池对象数量
     */
    protected void addObjects(final int count) {
        try {
            for (int i = 0; i < count; i++) {
                this.internalPool.addObject();
            }
        } catch (Exception e) {
            throw new ConnectionException("Error trying to add idle objects", e);
        }
    }

    /**
     * @Description: 清除对象池
     */
    public void clear() {
        try {
            this.internalPool.clear();
        } catch (Exception e) {
            throw new ConnectionException("Could not clear the pool", e);
        }
    }

    /**
     * @Description: 关闭对象池
     */
    public void close() {
        try {
            this.internalPool.close();
        } catch (Exception e) {
            throw new ConnectionException("Could not destroy the pool", e);
        }
    }
}
